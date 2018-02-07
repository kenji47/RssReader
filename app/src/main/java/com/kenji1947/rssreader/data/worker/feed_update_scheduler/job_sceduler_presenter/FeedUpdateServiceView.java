package com.kenji1947.rssreader.data.worker.feed_update_scheduler.job_sceduler_presenter;

/**
 * Created by chamber on 24.12.2017.
 */

public interface FeedUpdateServiceView {
    void finishJob();
    void showNewArticlesNotification(int newArticlesCount, int unreadArticles);
}
