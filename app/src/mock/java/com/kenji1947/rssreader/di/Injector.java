package com.kenji1947.rssreader.di;

import com.kenji1947.rssreader.data.api.FeedApiService;

import com.kenji1947.rssreader.data.api.FeedApiServiceImpl;
import com.kenji1947.rssreader.data.api.model.ApiConverter;
import com.kenji1947.rssreader.data.api.parser.FeedParser;
import com.kenji1947.rssreader.data.connectivity.ConnectivityManagerWrapper;
import com.kenji1947.rssreader.data.connectivity.NetworkUtils;
import com.kenji1947.rssreader.data.connectivity.NetworkUtilsImpl;
import com.kenji1947.rssreader.data.util.AppSchedulers;
import com.kenji1947.rssreader.domain.util.SchedulersProvider;
import com.kenji1947.rssreader.fakes.FeedServiceFake;
import com.kenji1947.rssreader.fakes.NetworkUtilsFake;
import com.kenji1947.rssreader.fakes.SchedulersTrampoline;

/**
 * Created by chamber on 19.12.2017.
 */

public class Injector {

    public static SchedulersProvider provideSchedulers() {
       return new AppSchedulers();
        //return new SchedulersTrampoline();
    }

    public static NetworkUtils provideNetworkUtils(ConnectivityManagerWrapper wrapper) {
        //return new NetworkUtilsFake();
        return new NetworkUtilsImpl(wrapper);
    }
    public static FeedApiService provideFeedService(FeedParser feedParser, ApiConverter apiConverter) {
        return new FeedApiServiceImpl(feedParser, apiConverter);
        //return new FeedServiceFake();
    }
}
