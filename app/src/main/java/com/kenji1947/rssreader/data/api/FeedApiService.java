package com.kenji1947.rssreader.data.api;


import com.kenji1947.rssreader.data.api.model.ApiFeed;
import com.kenji1947.rssreader.domain.entities.Feed;

import io.reactivex.Single;

public interface FeedApiService {
    //Используется как для создания фида, так и для обновления статей
    Single<Feed> fetchFeed(String feedUrl);
}
