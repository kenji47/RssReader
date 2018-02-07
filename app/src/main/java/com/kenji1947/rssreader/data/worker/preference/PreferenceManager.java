package com.kenji1947.rssreader.data.worker.preference;

import android.content.SharedPreferences;

public interface PreferenceManager {
    boolean shouldUpdateFeedsInBackground();
    void setShouldUpdateFeedsInBackground(boolean shouldUpdate);
    long getFeedUpdateServicePeriod();
    void setUpdateFeedsInBackgroundInterval(long intervalMillis);
    void registerPreferenceChangeListener(SharedPreferences.OnSharedPreferenceChangeListener listener);
    void unregisterPreferenceChangeListener(SharedPreferences.OnSharedPreferenceChangeListener listener);
}
