package com.kenji1947.rssreader.data.worker.feed_sync_worker;

import com.kenji1947.rssreader.data.worker.resource_manager.ResourceManager;
import com.kenji1947.rssreader.domain.entities.Feed;
import com.kenji1947.rssreader.domain.interactors.article.ObserveArticlesModificationInteractor;
import com.kenji1947.rssreader.domain.interactors.feed.FeedCrudInteractor;
import com.kenji1947.rssreader.domain.interactors.feed.FeedUpdateInteractor;
import com.kenji1947.rssreader.domain.util.RxSchedulersProvider;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import timber.log.Timber;

/**
 * Created by chamber on 17.03.2018.
 */

public class FeedSyncServicePresenter {
    private RxSchedulersProvider schedulersProvider;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private FeedSyncServiceView serviceView;
    private FeedCrudInteractor feedCrudInteractor;
    private FeedUpdateInteractor updateAllFeedsInteractor;
    private ObserveArticlesModificationInteractor observeArticleUpdatesInteractor;
    private ResourceManager resourceManager;

    @Inject
    public FeedSyncServicePresenter(
            FeedCrudInteractor feedCrudInteractor,
            FeedUpdateInteractor updateAllFeedsInteractor,
            ObserveArticlesModificationInteractor observeArticleUpdatesInteractor,
            ResourceManager resourceManager,
            RxSchedulersProvider schedulersProvider) {
        this.feedCrudInteractor = feedCrudInteractor;

        this.updateAllFeedsInteractor = updateAllFeedsInteractor;
        this.observeArticleUpdatesInteractor = observeArticleUpdatesInteractor;
        this.resourceManager = resourceManager;
        this.schedulersProvider = schedulersProvider;
    }

    private int feedProgress;
    private int newArticlesSum;
    private int feedsTotal;

    public void attachView(FeedSyncServiceView view) {
        serviceView = view;
    }

    public void onDestroy() {
        Timber.d("onDestroy");
        compositeDisposable.clear();
        serviceView = null;
    }

    public void updateAllFeeds() {
        Timber.d("updateAllFeeds");
        compositeDisposable.add(feedCrudInteractor.getFeeds()
                //.observeOn(schedulersProvider.getIo()) //TODO
                .subscribe(this::onUpdateAllFeedsSuccess, this::onError));
    }
    private void onUpdateAllFeedsSuccess(List<Feed> feeds) {
        feedsTotal = feeds.size();
        serviceView.showFeedUpdatingProgressNotification(feedsTotal, feedProgress);
        update(feeds);
        //start notification: feeds size

    }
    private void onError(Throwable throwable) {
        Timber.e(throwable);
        if (serviceView != null) {
            serviceView.finishJob();
        }
    }

    private void update(List<Feed> feeds) {
        compositeDisposable.add(updateAllFeedsInteractor.updateAllFeedsAndGetNewArticlesCountObservable()
                .subscribe(
                        integer -> onUpdateNext(integer),
                        throwable -> onError(throwable),
                        () -> onUpdateComplete()
                ));
    }

    private void onUpdateNext(int newArticlesCount) {
        newArticlesSum += newArticlesCount;
        feedProgress++;
        serviceView.showFeedUpdatingProgressNotification(feedsTotal, feedProgress);
        //update notification: feedProgress
    }


    private void onUpdateComplete() {
        if (serviceView != null) {
            //observeArticleUpdatesInteractor.notifyArticleModified();
            serviceView.showFeedUpdatingDoneNotification(newArticlesSum);
            serviceView.finishJob();
        }
    }
}
