package com.kenji1947.rssreader.data.api.parser;

import android.support.annotation.Nullable;

import com.annimon.stream.Stream;
import com.einmalfel.earl.AtomEntry;
import com.einmalfel.earl.AtomFeed;
import com.einmalfel.earl.EarlParser;
import com.einmalfel.earl.Feed;
import com.einmalfel.earl.Item;
import com.einmalfel.earl.RSSFeed;
import com.einmalfel.earl.RSSItem;
import com.kenji1947.rssreader.data.api.model.ApiArticle;
import com.kenji1947.rssreader.data.api.model.ApiFeed;
import com.kenji1947.rssreader.data.util.CurrentTimeProvider;

import java.io.InputStream;
import java.util.Date;
import java.util.List;

import timber.log.Timber;


public final class EarlParserWrapper implements ParserWrapper {

    private final CurrentTimeProvider currentTimeProvider;

    public EarlParserWrapper(final CurrentTimeProvider currentTimeProvider) {
        this.currentTimeProvider = currentTimeProvider;
    }

    @Override
    public ApiFeed parseOrThrow(final InputStream inputStream, final String feedUrl) throws Exception {
        return mapToApiFeed(EarlParser.parseOrThrow(inputStream, 0), feedUrl);
    }

    @Override
    public ApiFeed parse(final InputStream inputStream, final String feedUrl) {
        return mapToApiFeed(EarlParser.parse(inputStream, 0), feedUrl);
    }

    private ApiFeed mapToApiFeed(final Feed parsedFeed, final String feedUrl) {

        if (parsedFeed instanceof RSSFeed) {
            Timber.d("parsedFeed instanceof RSSFeed");
            for (Item item : parsedFeed.getItems()) {
                RSSItem rssItem = (RSSItem) item;
                Timber.d("AtomEntry: desc: " + rssItem.getDescription());
            }
        }
        else if (parsedFeed instanceof AtomFeed) {
            Timber.d("parsedFeed instanceof AtomFeed");
            for (Item item : parsedFeed.getItems()) {
                AtomEntry atomEntry = (AtomEntry) item;
                Timber.d("AtomEntry: desc: " + atomEntry.getDescription());
            }
        }

        for (Item item : parsedFeed.getItems()) {
            Timber.d("imageLink " + item.getImageLink());
        }

        final List<ApiArticle> apiArticles = Stream.of(parsedFeed.getItems())
                                                   .map(article -> new ApiArticle(article.getTitle(),
                                                           article.getLink(), article.getImageLink(),
                                                           getTimeForDate(article.getPublicationDate())))
                                                   .toList();
        return new ApiFeed(parsedFeed.getTitle(), parsedFeed.getImageLink(),
                parsedFeed.getLink(), parsedFeed.getDescription(), feedUrl, apiArticles);
    }

    private long getTimeForDate(@Nullable final Date date) {
        return (date != null) ? date.getTime() : currentTimeProvider.getCurrentTime();
    }
}
