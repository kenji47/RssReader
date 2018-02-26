package com.kenji1947.rssreader.data.api.fetch_feed.feedly_api;

import com.kenji1947.rssreader.domain.entities.Feed;

import java.io.IOException;

import io.reactivex.Single;

/**
 * Created by chamber on 24.02.2018.
 */

public class FeedApiServiceRetrofit implements FeedApiService {
    @Override
    public Single<Feed> fetchFeed(String feedUrl) throws IOException {
        return null;
    }
}
