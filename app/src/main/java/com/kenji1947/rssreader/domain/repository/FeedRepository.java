package com.kenji1947.rssreader.domain.repository;

import com.kenji1947.rssreader.domain.entities.Article;
import com.kenji1947.rssreader.domain.entities.Feed;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Created by kenji1947 on 09.11.2017.
 */

public interface FeedRepository {

    Single<Feed> getFeed(long id);
    Single<List<Feed>> getFeeds();
    Completable saveArticlesForFeed(long feedId, List<Article> articles);
    Single<Boolean> feedExists(String feedUrl);

    Observable<List<Feed>> getFeedsAndObserve();

    Completable deleteFeed(long feedId);

    Single<Feed> fetchFeed(String feedUrl);
    Completable createNewFeed(String feedUrl);

    Single<Boolean> shouldUpdateFeedsInBackground();
    Completable setShouldUpdateFeedsInBackground(boolean shouldUpdate);

    Completable setUpdateFeedsInBackgroundInterval(long intervalMillis);

    Single<Long> getUpdateFeedsInBackgroundInterval();

    Observable<Boolean> observeShouldUpdateFeedsInBackground();

    Single<AppPreferences> getPreferencesData();
}
