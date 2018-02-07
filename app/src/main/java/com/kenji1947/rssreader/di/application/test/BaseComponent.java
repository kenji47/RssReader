package com.kenji1947.rssreader.di.application.test;

import com.kenji1947.rssreader.di.application.AppComponentExposes;
import com.kenji1947.rssreader.di.application.AppComponentInjects;
import com.kenji1947.rssreader.domain.worker.FeedUpdateScheduler;

/**
 * Created by chamber on 28.12.2017.
 */

public interface BaseComponent extends BaseComponentExposes, BaseComponentInjects {
    FeedUpdateScheduler provideFeedUpdateScheduler();
}
