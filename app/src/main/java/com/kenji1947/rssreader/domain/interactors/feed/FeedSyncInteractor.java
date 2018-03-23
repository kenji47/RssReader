package com.kenji1947.rssreader.domain.interactors.feed;

import com.kenji1947.rssreader.domain.repository.AppPreferences;
import com.kenji1947.rssreader.domain.repository.FeedRepository;
import com.kenji1947.rssreader.domain.util.RxSchedulersProvider;
import com.kenji1947.rssreader.domain.worker.FeedSyncWorker;
import com.kenji1947.rssreader.domain.worker.FeedUpdateScheduler;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Created by chamber on 07.12.2017.
 */

public class FeedSyncInteractor {
    private FeedRepository feedRepository;
    private RxSchedulersProvider schedulersProvider;
    private FeedUpdateScheduler feedUpdateScheduler;
    private FeedSyncWorker feedSyncWorker;

    @Inject
    public FeedSyncInteractor(FeedRepository feedRepository,
                              FeedUpdateScheduler feedUpdateScheduler,
                              RxSchedulersProvider schedulersProvider,
                              FeedSyncWorker feedSyncWorker) {
        this.feedRepository = feedRepository;
        this.schedulersProvider = schedulersProvider;
        this.feedUpdateScheduler = feedUpdateScheduler;
        this.feedSyncWorker = feedSyncWorker;
    }

    //---
    public Completable startFeedSync() {
        return Completable.fromAction(feedSyncWorker::startFeedSync);
    }

    public Completable cancelFeedSync() {
        return Completable.fromAction(feedSyncWorker::cancelFeedSync);
    }
    //---

    public Single<Boolean> shouldUpdateFeedsInBackground() {
        return feedRepository.shouldUpdateFeedsInBackground();
    };

    public Observable<Boolean> observeShouldUpdateFeedsInBackground() {
        return feedRepository.observeShouldUpdateFeedsInBackground();
    }

    public Completable setShouldUpdateFeedsInBackground(boolean shouldUpdate) {
        return feedRepository.setShouldUpdateFeedsInBackground(shouldUpdate);
    };


    public Completable enableBackgroundFeedUpdate() {
        return setShouldUpdateFeedsInBackground(true)
                .concatWith(Completable.fromAction(feedUpdateScheduler::scheduleBackgroundFeedUpdates));
    }
    //TODO Сначала действия с сервисом, а потом менять префы
    public Completable disableBackgroundFeedUpdate() {
        return setShouldUpdateFeedsInBackground(false)
                .concatWith(Completable.fromAction(feedUpdateScheduler::cancelBackgroundFeedUpdates));
    }

    //---
    public Completable setUpdateFeedsInBackgroundInterval(long intervalMillis) {
        //TODO concatWith
        return feedRepository.setUpdateFeedsInBackgroundInterval(intervalMillis)
                .toSingleDefault(42)//TODO How to transform to Completable without default value
                .flatMap(integer -> shouldUpdateFeedsInBackground())
                //restart scheduler
                .flatMapCompletable(shouldUpdate ->
                        shouldUpdate
                                ? Completable.fromAction(() -> {
                            feedUpdateScheduler.cancelBackgroundFeedUpdates();
                            feedUpdateScheduler.scheduleBackgroundFeedUpdates();})
                                : Completable.complete());
    }
    public Single<Long> getUpdateFeedsInBackgroundInterval() {
        return feedRepository.getUpdateFeedsInBackgroundInterval();
    };

    public Single<AppPreferences> getPreferencesData() {
        return feedRepository.getPreferencesData();
    }
}
