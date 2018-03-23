package com.kenji1947.rssreader.di.application;

import com.kenji1947.rssreader.data.worker.feed_sync_scheduler.alarm_manager.FeedUpdateSimpleService;
import com.kenji1947.rssreader.data.worker.feed_sync_scheduler.job_sceduler_presenter.FeedUpdateService2;
import com.kenji1947.rssreader.data.worker.feed_sync_scheduler.job_scheduler.FeedUpdateService;
import com.kenji1947.rssreader.data.worker.feed_sync_worker.FeedSyncService;
import com.kenji1947.rssreader.data.worker.feed_sync_worker.FeedSyncWorkerImpl;
import com.kenji1947.rssreader.domain.worker.FeedSyncWorker;
import com.kenji1947.rssreader.presentation.MainActivity;
import com.kenji1947.rssreader.presentation.article_list.ArticleListFragment;
import com.kenji1947.rssreader.presentation.feed_list.FeedListFragment;
import com.kenji1947.rssreader.presentation.new_feed.FeedNewFragment;

/**
 * Created by kenji1947 on 11.11.2017.
 */

public interface AppComponentInjects {
    void inject(FeedListFragment fragment);
    void inject (FeedNewFragment fragment);
    void inject (ArticleListFragment fragment);
    void inject(FeedUpdateService feedUpdateService);
    void inject(FeedUpdateService2 feedUpdateService);
    void inject(FeedUpdateSimpleService feedUpdateSImpleService);
    void inject(MainActivity mainActivity);
    void inject(FeedSyncService feedSyncService);
}
