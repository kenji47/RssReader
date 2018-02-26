package com.kenji1947.rssreader.di.application.modules;

import com.kenji1947.rssreader.data.api.fetch_feed.feedly_api.FeedApiService;
import com.kenji1947.rssreader.data.api.fetch_feed.plain_rss_atom.model.ApiConverter;
import com.kenji1947.rssreader.data.api.fetch_feed.plain_rss_atom.parser.EarlParserWrapper;
import com.kenji1947.rssreader.data.api.fetch_feed.plain_rss_atom.parser.ParserWrapper;
import com.kenji1947.rssreader.data.api.fetch_feed.plain_rss_atom.parser.FeedParser;
import com.kenji1947.rssreader.data.api.fetch_feed.plain_rss_atom.parser.FeedParserImpl;
import com.kenji1947.rssreader.data.util.CurrentTimeProvider;
import com.kenji1947.rssreader.di.Injector;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by chamber on 10.12.2017.
 */

@Module
public class ApiModule {

    @Provides
    @Singleton
    FeedApiService provideFeedService(FeedParser feedParser, ApiConverter apiConverter) {
        return Injector.provideFeedService(feedParser, apiConverter);
    }


    @Provides
    @Singleton
    FeedParser provideFeedParser(final CurrentTimeProvider currentTimeProvider, final ParserWrapper externalParserWrapper) {
        return new FeedParserImpl(externalParserWrapper);
    }

    @Provides
    @Singleton
    ParserWrapper provideExternalParserWrapper(final CurrentTimeProvider currentTimeProvider) {
        return new EarlParserWrapper(currentTimeProvider);
    }

    public interface Exposes {
        FeedParser feedParser();
        FeedApiService feedService();
    }
}
