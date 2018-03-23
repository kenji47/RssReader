package com.kenji1947.rssreader.data.api;

import com.kenji1947.rssreader.data.api.fetch_feed.feedly_api.model.FeedArticlesResponse;
import com.kenji1947.rssreader.data.api.fetch_feed.plain_rss_atom.model.ApiFeed;
import com.kenji1947.rssreader.data.api.search_feed.model.FeedSearchResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by chamber on 26.02.2018.
 */

public interface ApiRetrofit {

    @GET("/v3/search/feeds")
    Single<FeedSearchResponse> searchFeed(@Query("query") String name);

    @GET("/v3/streams/contents")
    Single<FeedArticlesResponse> getFeedArticles(@Query("streamId") String streamId);

}
