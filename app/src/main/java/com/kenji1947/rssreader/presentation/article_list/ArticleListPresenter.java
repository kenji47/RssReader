package com.kenji1947.rssreader.presentation.article_list;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.kenji1947.rssreader.domain.entities.Article;
import com.kenji1947.rssreader.domain.interactors.article.ArticleFavouriteInteractor;
import com.kenji1947.rssreader.domain.interactors.article.ArticleUnreadInteractor;
import com.kenji1947.rssreader.domain.interactors.article.ArticlesCrudInteractor;
import com.kenji1947.rssreader.domain.interactors.article.ObserveArticlesModificationInteractor;
import com.kenji1947.rssreader.domain.util.SchedulersProvider;
import com.kenji1947.rssreader.presentation.Screens;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import ru.terrakok.cicerone.Router;
import timber.log.Timber;

/**
 * Created by chamber on 14.12.2017.
 */

@InjectViewState
public class ArticleListPresenter extends MvpPresenter<ArticleListView> {
    private long feedId;
    private boolean isFavModeOn;
    private List<Article> articleList;
    private ArticlesCrudInteractor articlesCrudInteractor;
    private ArticleFavouriteInteractor articleFavouriteInteractor;
    private ArticleUnreadInteractor articleUnreadInteractor;
    private ObserveArticlesModificationInteractor observeArticleUpdatesInteractor;
    private SchedulersProvider schedulersProvider;
    private Router router;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Inject
    public ArticleListPresenter(long feedId, boolean isFavModeOn,
                                ArticlesCrudInteractor articlesCrudInteractor,
                                ArticleFavouriteInteractor articleFavouriteInteractor,
                                ArticleUnreadInteractor articleUnreadInteractor,
                                ObserveArticlesModificationInteractor observeArticleUpdatesInteractor,
                                SchedulersProvider schedulersProvider,
                                Router router) {
        this.feedId = feedId;
        this.isFavModeOn = isFavModeOn;
        this.articlesCrudInteractor = articlesCrudInteractor;
        this.articleFavouriteInteractor = articleFavouriteInteractor;
        this.articleUnreadInteractor = articleUnreadInteractor;
        this.observeArticleUpdatesInteractor = observeArticleUpdatesInteractor;
        this.schedulersProvider = schedulersProvider;
        this.router = router;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        //observeArticleUpdates();

        if (isFavModeOn) {
            getFavArticles();
        } else {
            getArticlesForFeedObserve(feedId);
            //getArticlesForFeed(feedId);
        }
    }

    private void observeArticleUpdates() {
        Timber.d("observeArticlesModification");
        compositeDisposable.add(observeArticleUpdatesInteractor.observeArticlesModification()
                .observeOn(schedulersProvider.getMain())
                .subscribe(this::onObserveArticleUpdatesSuccess)
        );
    }

    private void onObserveArticleUpdatesSuccess(boolean status) {
        Timber.d("onObserveArticleUpdatesSuccess");
        if (!isFavModeOn) {
            getArticlesForFeed(feedId);
        }
    }

    void getFavArticles() {
        articleFavouriteInteractor.getFavouriteArticles()
//                .doOnSubscribe(disposable -> {getViewState().showProgress(true);})
//                .doAfterTerminate(() -> {getViewState().showProgress(false);})
                .observeOn(schedulersProvider.getMain())
                .subscribe(articles -> {
                    articleList = articles;
                    getViewState().showArticles(articles);
                });
    }

    void getArticlesForFeed(long feedId) {
        this.feedId = feedId;
        articlesCrudInteractor.getArticles(feedId)
//                .doOnSubscribe(disposable -> {getViewState().showProgress(true);})
//                .doAfterTerminate(() -> {getViewState().showProgress(false);})
                .observeOn(schedulersProvider.getMain())
                .subscribe(articles -> {
                    articleList = articles;
                    getViewState().showArticles(articles);
                });
    }

    void getArticlesForFeedObserve(long feedId) {
        this.feedId = feedId;
        articlesCrudInteractor.getArticlesAndObserve(feedId)
//                .doOnSubscribe(disposable -> {getViewState().showProgress(true);})
//                .doAfterTerminate(() -> {getViewState().showProgress(false);})
                .observeOn(schedulersProvider.getMain())
                .subscribe(articles -> {
                    articleList = articles;
                    getViewState().showArticles(articles);
                });
    }

    //TODO Optimistic call
    void markArticleAsRead(long articleId) {
        articleUnreadInteractor.markArticleAsRead(articleId)
                .doOnComplete(() -> observeArticleUpdatesInteractor.notifyArticleModified())
                .observeOn(schedulersProvider.getMain())
                .subscribe(() -> {
                    Timber.d("onComplete markArticleAsRead");
                });
    }

    //TODO Optimistic call
    void toggleArticleFavourite (int pos) {
        (articleList.get(pos).isFavourite ?
        articleFavouriteInteractor.favouriteArticle(articleList.get(pos).id)
        : articleFavouriteInteractor.unFavouriteArticle(articleList.get(pos).id))
                .subscribe(() -> {Timber.d("onComplete toggleArticleFavourite");});
    }

    void showArticle(int pos) {
        router.navigateTo(Screens.ARTICLE_DETAIL_SCREEN, articleList.get(pos).link);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Timber.d("onDestroy");
        compositeDisposable.clear();
    }
}
