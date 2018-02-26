package com.kenji1947.rssreader.data.api.fetch_feed.feedly_api;



import com.kenji1947.rssreader.domain.entities.Feed;

import java.io.IOException;

import io.reactivex.Single;

public interface FeedApiService {
    //Используется как для создания фида, так и для обновления статей
    Single<Feed> fetchFeed(String feedUrl) throws IOException;
}
