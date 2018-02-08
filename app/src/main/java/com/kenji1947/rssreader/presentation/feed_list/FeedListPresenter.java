package com.kenji1947.rssreader.presentation.feed_list;

import android.support.v7.util.DiffUtil;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.kenji1947.rssreader.data.worker.error_handler.ErrorHandler;
import com.kenji1947.rssreader.domain.entities.Feed;
import com.kenji1947.rssreader.domain.interactors.feed.BackgroundFeedUpdateInteractor;
import com.kenji1947.rssreader.domain.interactors.feed.FeedCrudInteractor;
import com.kenji1947.rssreader.domain.interactors.feed.FeedUpdateInteractor;
import com.kenji1947.rssreader.domain.util.SchedulersProvider;
import com.kenji1947.rssreader.presentation.Screens;
import com.kenji1947.rssreader.presentation.article_list.ArticleListArgumentHolder;
import com.kenji1947.rssreader.presentation.common.DiffCalculator;
import com.kenji1947.rssreader.presentation.common.ListDataDiffHolder;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import ru.terrakok.cicerone.Router;
import ru.terrakok.cicerone.result.ResultListener;
import timber.log.Timber;

/**
 * Created by kenji1947 on 11.11.2017.
 */

@InjectViewState
public class FeedListPresenter extends MvpPresenter<FeedListView> {
    private FeedCrudInteractor feedCrudInteractor;
    private FeedUpdateInteractor updateAllFeedsInteractor;
    private BackgroundFeedUpdateInteractor backgroundUpdateInteractor;
    private SchedulersProvider schedulersProvider;
    private ErrorHandler errorHandler;
    private Router router;

    private CompositeDisposable compositeDisposable =  new CompositeDisposable();

    //сохранить данные
    private List<Feed> feeds;

    @Inject
    public FeedListPresenter(FeedCrudInteractor feedCrudInteractor,
                             FeedUpdateInteractor updateAllFeedsInteractor,
                             BackgroundFeedUpdateInteractor backgroundUpdateInteractor,
                             SchedulersProvider schedulersProvider,
                             ErrorHandler errorHandler,
                             Router router) {
        this.feedCrudInteractor = feedCrudInteractor;
        this.backgroundUpdateInteractor = backgroundUpdateInteractor;
        this.updateAllFeedsInteractor = updateAllFeedsInteractor;
        this.schedulersProvider = schedulersProvider;
        this.errorHandler = errorHandler;
        this.router = router;
        Timber.d("End construction");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }


    @Override
    public void onFirstViewAttach() {
        Timber.d("onFirstViewAttach");
        //getBackgroundFeedUpdatesStatus();
        //observeShouldUpdateFeedsInBackground();
        //getAllFeeds();
        getAllFeedsObserve();
        //updateAllFeeds(); //TODO Повесить на рефреш?
    }


    //Есди не удача выбрать из бд
    public void updateAllFeeds() {
        compositeDisposable.add(updateAllFeedsInteractor.updateAllFeeds()
                .observeOn(schedulersProvider.getMain())
                .doOnSubscribe(disposable -> getViewState().showProgress(true))
                //.doAfterTerminate(() -> getViewState().showProgress(false)) //TODO doFinally
                .subscribe(this::onUpdateAllFeedsSuccess, this::onUpdateAllFeedsError)
                );
    }
    private void onUpdateAllFeedsSuccess(List<Feed> feeds) {
        Timber.d("onSuccess");

        getViewState().showProgress(false);

        this.feeds = feeds;
        getViewState().showFeedSubscriptions(feeds);
    }
    private void onUpdateAllFeedsError(Throwable throwable) {
        Timber.d("onUpdateAllFeedsError " + throwable);
        getViewState().showProgress(false);

        errorHandler.handleErrorScreenFeedList(throwable, s -> getViewState().showErrorMessage(s));
        //TODO Нужно показать значок пустой список, если нет интернета и бд пуста
        getAllFeeds();
    }

    //--
    public void getAllFeeds() {
        Timber.d("getAllFeeds");
//        compositeDisposable.add(feedCrudInteractor.getFeeds()
//                .observeOn(schedulersProvider.getMain())
//                .doOnSubscribe(disposable -> getViewState().showProgress(true)) //TODO
//                //.doAfterTerminate(() -> getViewState().showProgress(false))
//                .doFinally(() -> {})
//                .subscribe(this::onGetAllFeedsSuccess, this::onGetAllFeedsError)
//        );
    }

    public void getAllFeedsObserve() {
        Timber.d("getAllFeedsObserve");
        compositeDisposable.add(feedCrudInteractor.getFeedsAndObserve()
                .map(newList -> DiffCalculator.calculateFeedListDiff(newList, feeds)) //TODO Move to DI
                .doOnSubscribe(disposable -> getViewState().showProgress(true)) //TODO
                .observeOn(schedulersProvider.getMain())
                .subscribe(this::onGetAllFeedsObservableSuccess, this::onGetAllFeedsError));
    }

    private void onGetAllFeedsObservableSuccess(ListDataDiffHolder<Feed> listDataDiffHolder) {
        Timber.d("onSuccess");
        getViewState().showProgress(false);
        this.feeds = listDataDiffHolder.getList();
        getViewState().showFeedObservableSubscriptions(listDataDiffHolder);
    }
    //--
    private void onGetAllFeedsSuccess(List<Feed> feeds) {
        Timber.d("onSuccess");

        getViewState().showProgress(false);

        this.feeds = feeds;
        getViewState().showFeedSubscriptions(feeds);
    }
    private void onGetAllFeedsError(Throwable throwable) {
        Timber.d("onGetAllFeedsError");

        getViewState().showProgress(false);

        errorHandler.handleErrorScreenFeedList(throwable, s -> getViewState().showErrorMessage(s));
    }

    //TODO Change, when dialog moved.
    public void showAddNewFeed() {
        Timber.d("showAddNewFeed");
        router.navigateTo(Screens.NEW_FEED_DIALOG);

        router.navigateTo(Screens.NEW_FEED_SCREEN);
        router.setResultListener(Screens.RESULT_NEW_FEED_SCREEN_UPDATE, new ResultListener() {
            @Override
            public void onResult(Object resultData) {
                if (resultData != null) {
                    boolean update = (boolean) resultData;
                    if (update) {
                        Timber.d("Update from NewFeed");
                        getAllFeeds();
                        unSubscribeNewFeedScreenResult();
                    };
                }
            }
        });
    }
    private void unSubscribeNewFeedScreenResult() {
        router.removeResultListener(Screens.RESULT_NEW_FEED_SCREEN_UPDATE);
    }


    public void unSubscribeFromFeed(long id) {
        Timber.d("unSubscribeFromFeed " + id);
        feedCrudInteractor
                .deleteFeed(id)
                .observeOn(schedulersProvider.getMain())
//                .doOnSubscribe(disposable -> getViewState().showProgress(true))
//                .doFinally(() -> getViewState().showProgress(false)) //TODO Гасит прогресс след обс
                .subscribe(this::onUnsubscribeFromFeedSuccess, this::onUnsubscribeFromFeedError);
    };
    private void onUnsubscribeFromFeedSuccess() {
        getViewState().onFeedChange(true);
        getAllFeeds(); //Update feeds
    }
    private void onUnsubscribeFromFeedError(Throwable throwable) {

    }


    public void showArticles(Feed feed) {
        Timber.d("showArticles");
        router.navigateTo(Screens.ARTICLE_LIST_SCREEN, new ArticleListArgumentHolder(feed.id, feed.title));
    };


    public void showFavouriteArticles() {
        Timber.d("showFavouriteArticles");
        router.navigateTo(Screens.ARTICLE_FAV_LIST_SCREEN);
    };

    public void showSettings() {
        Timber.d("showSettings");
        router.navigateTo(Screens.SETTINGS_SCREEN);
    }
    //background service
//    private void getBackgroundFeedUpdatesStatus() {
//        Timber.d("getBackgroundFeedUpdatesStatus");
//        compositeDisposable.add(backgroundUpdateInteractor.shouldUpdateFeedsInBackground()
//                .observeOn(schedulersProvider.getMain())
//                .subscribe(this::onGetBackgroundFeedUpdatesStatusSuccess,
//                        this::onGetBackgroundFeedUpdatesStatusError));
//    }
//
//    private void onGetBackgroundFeedUpdatesStatusSuccess(boolean updateStatus) {
//        Timber.d("onComplete getBackgroundFeedUpdatesStatus " + updateStatus);
//        getViewState().setIsBackgroundFeedUpdateEnabled(updateStatus);
//    }
//    private void onGetBackgroundFeedUpdatesStatusError(Throwable throwable) {
//        Timber.d(throwable);
//    }


    public void enableBackgroundFeedUpdates() {
        Timber.d("enableBackgroundFeedUpdates");
        compositeDisposable.add(backgroundUpdateInteractor.enableBackgroundFeedUpdate()
                .observeOn(schedulersProvider.getMain())
                .subscribe(this::onEnableBackgroundFeedUpdatesCompletion)
        );
    }
    private void onEnableBackgroundFeedUpdatesCompletion() {
        Timber.d("onComplete enableBackgroundFeedUpdates");
        getViewState().setIsBackgroundFeedUpdateEnabled(true);
    }


    public void disableBackgroundFeedUpdates() {
        Timber.d("disableBackgroundFeedUpdates");
        compositeDisposable.add(backgroundUpdateInteractor.disableBackgroundFeedUpdate()
                .observeOn(schedulersProvider.getMain())
                .subscribe(this::onDisableBackgroundFeedUpdatesCompletion)
        );
    }
    private void onDisableBackgroundFeedUpdatesCompletion() {
        Timber.d("onComplete enableBackgroundFeedUpdates");
        getViewState().setIsBackgroundFeedUpdateEnabled(false);
    }

    private void observeShouldUpdateFeedsInBackground() {
        Timber.d("observeShouldUpdateFeedsInBackground");
        compositeDisposable.add(backgroundUpdateInteractor.observeShouldUpdateFeedsInBackground()
                .observeOn(schedulersProvider.getMain())
                .subscribe(this::onObserveShouldUpdateFeedsInBackgroundNext, throwable -> {Timber.d(throwable);})
        );
    }

    private void onObserveShouldUpdateFeedsInBackgroundNext(boolean update) {
        Timber.d("onObserveShouldUpdateFeedsInBackgroundNext " + update);
        getViewState().setIsBackgroundFeedUpdateEnabled(update);
    }
}
