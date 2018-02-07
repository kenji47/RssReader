package com.kenji1947.rssreader.data.api.parser;

import com.kenji1947.rssreader.data.api.model.ApiFeed;

import java.io.InputStream;

import io.reactivex.Single;


public interface FeedParser {

    Single<ApiFeed> parseFeed(InputStream inputStream, String feedUrl);
}
