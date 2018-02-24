package com.kenji1947.rssreader.data.api;

import com.kenji1947.rssreader.data.api.model.ApiFeed;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by chamber on 14.02.2018.
 */

public interface ApiService {
    public interface APIService {
        @GET
        Single<ApiFeed> getFeed(@Url String url);
    }
}
