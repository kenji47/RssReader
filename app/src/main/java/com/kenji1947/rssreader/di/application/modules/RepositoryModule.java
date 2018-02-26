package com.kenji1947.rssreader.di.application.modules;

import com.kenji1947.rssreader.data.api.fetch_feed.feedly_api.FeedApiService;
import com.kenji1947.rssreader.data.database.ArticleDao;
import com.kenji1947.rssreader.data.database.FeedDao;
import com.kenji1947.rssreader.data.worker.preference.PreferenceManager;
import com.kenji1947.rssreader.domain.repository.ArticleRepository;
import com.kenji1947.rssreader.data.repository.ArticleRepositoryImpl;
import com.kenji1947.rssreader.domain.repository.FeedRepository;
import com.kenji1947.rssreader.data.repository.FeedRepositoryImpl;
import com.kenji1947.rssreader.domain.util.SchedulersProvider;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by kenji1947 on 11.11.2017.
 */

@Module
public class RepositoryModule {

    @Provides
    @Singleton
    FeedRepository provideFeedRepository(FeedApiService feedService,
                                         FeedDao feedDao,
                                         SchedulersProvider schedulersProvider,
                                         PreferenceManager preferenceManager) {
        return new FeedRepositoryImpl(feedService, feedDao, schedulersProvider, preferenceManager);
    }

    @Provides
    @Singleton
    ArticleRepository provideArticleRepository(ArticleDao articleDao, SchedulersProvider schedulersProvider) {
        return new ArticleRepositoryImpl(articleDao, schedulersProvider);
    }

    public interface Exposes {
        FeedRepository provideFeedRepository();
        ArticleRepository provideArticleRepository();
    }
}
