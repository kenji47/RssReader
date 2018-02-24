package com.kenji1947.rssreader.data.repository;

import com.kenji1947.rssreader.data.database.ArticleDao;
import com.kenji1947.rssreader.domain.entities.Article;
import com.kenji1947.rssreader.domain.repository.ArticleRepository;
import com.kenji1947.rssreader.domain.util.SchedulersProvider;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

/**
 * Created by kenji1947 on 10.11.2017.
 */

//TODO Везде используется: Completable.defer(() -> feedDao.unFavouriteArticle(articleId)));
public class ArticleRepositoryImpl implements ArticleRepository {
    private static final long CLICK_THROTTLE_WINDOW_MILLIS = 300L; //TODO Объяснить

    private ArticleDao articleDao;
    private SchedulersProvider schedulersProvider;
    private Subject<Boolean> articleServiceUpdate = PublishSubject.create();


    public ArticleRepositoryImpl(ArticleDao articleDao, SchedulersProvider schedulersProvider) {
        this.articleDao = articleDao;
        this.schedulersProvider = schedulersProvider;
    }

    //TODO Move to Feed Intercator
    @Override
    public void notifyArticleModified() {
        articleServiceUpdate.onNext(true);
    }

    @Override
    public Observable<Boolean> observeArticlesModification() {
        return articleServiceUpdate
                .throttleFirst(CLICK_THROTTLE_WINDOW_MILLIS, TimeUnit.MILLISECONDS)
                .subscribeOn(schedulersProvider.getIo());
    }

    //TODO Объяснить Single.defer(Single)
    @Override
    public Single<List<Article>> getArticles(long feedId) {
        return Single.defer(() -> articleDao.getArticles(feedId)).subscribeOn(schedulersProvider.getIo());
        //subscribeOn
        //return database.getArticles(feedId);
    }

    @Override
    public Observable<List<Article>> getArticlesAndObserve(long feedId) {
        return articleDao.getArticlesAndObserve(feedId).subscribeOn(schedulersProvider.getIo());
    }

    @Override
    public Single<List<Article>> getFavouriteArticles() {
        return articleDao.getFavouriteArticles()
                .subscribeOn(schedulersProvider.getIo());
    }

    @Override
    public Completable markArticleAsRead(long articleId) {
        return articleDao.markArticleAsRead(articleId).subscribeOn(schedulersProvider.getIo());
    }

    @Override
    public Completable favouriteArticle(long articleId) {
        return articleDao.favouriteArticle(articleId).subscribeOn(schedulersProvider.getIo());
    }

    @Override
    public Completable unFavouriteArticle(long articleId) {
        return articleDao.unFavouriteArticle(articleId).subscribeOn(schedulersProvider.getIo());
    }

    @Override
    public Single<Long> getUnreadArticlesCount() {
        return articleDao.getUnreadArticlesCount().subscribeOn(schedulersProvider.getIo());
    }
}
