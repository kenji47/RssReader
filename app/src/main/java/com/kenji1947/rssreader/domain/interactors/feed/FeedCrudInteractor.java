package com.kenji1947.rssreader.domain.interactors.feed;

import com.kenji1947.rssreader.data.worker.preference.PreferenceManager;
import com.kenji1947.rssreader.domain.entities.Feed;
import com.kenji1947.rssreader.domain.repository.FeedRepository;
import com.kenji1947.rssreader.domain.util.SchedulersProvider;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Created by chamber on 07.12.2017.
 */

public class FeedCrudInteractor {
    private FeedRepository feedRepository;
    private SchedulersProvider schedulersProvider;

    @Inject
    public FeedCrudInteractor(FeedRepository feedRepository, SchedulersProvider schedulersProvider) {
        this.feedRepository = feedRepository;
        this.schedulersProvider = schedulersProvider;
    }

    public Single<List<Feed>> getFeeds() {
        return feedRepository.getFeeds();
    };

    public Observable<List<Feed>> getFeedsAndObserve() {return feedRepository.getFeedsAndObserve();}

    public Completable createNewFeed(String feedUrl) {
        return feedRepository.createNewFeed(feedUrl);
    };

    public Completable deleteFeed(long feedId) {
        return feedRepository.deleteFeed(feedId);
    };
}
