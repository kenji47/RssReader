package com.kenji1947.rssreader.data.worker.preference;

import android.content.Context;
import android.content.SharedPreferences;

import timber.log.Timber;

/**
 * Created by chamber on 08.12.2017.
 */

public class PreferenceManagerImpl implements PreferenceManager {
    private long DEFAULT_FEED_UPDATE_SERVICE_PERIOD = 1_800_000; //30min
    private static final String USER_PREFERENCES = "user_preferences";
    private static final String KEY_SHOULD_UPDATE_FEEDS_IN_BACKGROUND = "key_should_update_feed_in_background";
    private static final String KEY_FEED_UPDATE_SERVICE_PERIOD = "key_feed_update_service_period";

    private final SharedPreferences preferences;

    public PreferenceManagerImpl(final Context context) {
        this.preferences = context.getSharedPreferences(USER_PREFERENCES, Context.MODE_PRIVATE);
    }

    @Override
    public void registerPreferenceChangeListener(SharedPreferences.OnSharedPreferenceChangeListener listener) {
        preferences.registerOnSharedPreferenceChangeListener(listener);
    }

    @Override
    public void unregisterPreferenceChangeListener(SharedPreferences.OnSharedPreferenceChangeListener listener) {
        preferences.unregisterOnSharedPreferenceChangeListener(listener);
    }

    @Override
    public boolean shouldUpdateFeedsInBackground() {
        return preferences.getBoolean(KEY_SHOULD_UPDATE_FEEDS_IN_BACKGROUND, true);
    }

    @Override
    public void setShouldUpdateFeedsInBackground(boolean shouldUpdate) {
        preferences.edit()
                .putBoolean(KEY_SHOULD_UPDATE_FEEDS_IN_BACKGROUND, shouldUpdate)
                .apply();
    }

    @Override
    public long getFeedUpdateServicePeriod() {
        return preferences.getLong(KEY_FEED_UPDATE_SERVICE_PERIOD, DEFAULT_FEED_UPDATE_SERVICE_PERIOD);
    }

    @Override
    public void setUpdateFeedsInBackgroundInterval(long intervalMillis) {
        preferences.edit()
                .putLong(KEY_FEED_UPDATE_SERVICE_PERIOD, intervalMillis)
                .apply();
    }
}
