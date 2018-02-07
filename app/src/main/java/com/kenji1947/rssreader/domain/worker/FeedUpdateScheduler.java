package com.kenji1947.rssreader.domain.worker;

/**
 * Created by chamber on 07.12.2017.
 */

public interface FeedUpdateScheduler {
    void scheduleBackgroundFeedUpdates();
    void cancelBackgroundFeedUpdates();
}
