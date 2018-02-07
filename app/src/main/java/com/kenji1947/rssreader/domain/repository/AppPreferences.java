package com.kenji1947.rssreader.domain.repository;

/**
 * Created by chamber on 27.12.2017.
 */

public class AppPreferences {
    public boolean enableBackgroundUpdates;
    public long backgroundUpdatesInterval;

    public AppPreferences(boolean enableBackgroundUpdates, long backgroundUpdatesInterval) {
        this.enableBackgroundUpdates = enableBackgroundUpdates;
        this.backgroundUpdatesInterval = backgroundUpdatesInterval;
    }
}
