package com.kenji1947.rssreader.data.api;

import com.kenji1947.rssreader.data.api.model.ApiConverter;
import com.kenji1947.rssreader.data.api.parser.FeedParser;
import com.kenji1947.rssreader.domain.entities.Feed;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import io.reactivex.Single;
import timber.log.Timber;


public final class FeedApiServiceImpl implements FeedApiService {

    private final FeedParser feedParser;
    private ApiConverter apiConverter;

    public FeedApiServiceImpl(final FeedParser feedParser, ApiConverter apiConverter) {
        this.feedParser = feedParser;
        this.apiConverter = apiConverter;
    }


    @Override
    public Single<Feed> fetchFeed(final String feedUrl) {
        Timber.d("fetchFeed " + feedUrl);
        try {
            final InputStream inputStream = new URL(feedUrl).openConnection().getInputStream();
            return feedParser.parseFeed(inputStream, feedUrl)
                    .doOnSuccess(feed -> closeStream(inputStream)) //TODO Перенести в finally?
                    .doOnError(throwable -> closeStream(inputStream))
                    .map(apiFeed -> apiConverter.apiToDomain(apiFeed));
        } catch (final IOException e) {
            Timber.e(e);
            return Single.error(e);
        }
    }

    private void closeStream(final InputStream inputStream) {
        try {
            inputStream.close();
        } catch (final IOException e) {
            Timber.e(e);
        }
    }
}
