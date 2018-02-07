package com.kenji1947.rssreader.data.worker.feed_update_scheduler.alarm_manager;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.SystemClock;

import com.kenji1947.rssreader.data.worker.preference.PreferenceManager;
import com.kenji1947.rssreader.domain.worker.FeedUpdateScheduler;

import timber.log.Timber;

/**
 * Created by chamber on 07.12.2017.
 */

public class FeedUpdateSchedulerAlarmManager implements FeedUpdateScheduler{

    private PreferenceManager preferenceManager;
    private AlarmManager alarmManager;
    private PendingIntent servicePendingIntent;

    public FeedUpdateSchedulerAlarmManager(
            PreferenceManager preferenceManager,
            AlarmManager alarmManager,
            PendingIntent servicePendingIntent) {

        this.preferenceManager = preferenceManager;
        this.alarmManager = alarmManager;
        this.servicePendingIntent = servicePendingIntent;
    }

    @Override
    public void scheduleBackgroundFeedUpdates() {
        //long period = preferenceManager.getFeedUpdateServicePeriod();
        long period = 5000;
        Timber.d("scheduleBackgroundFeedUpdates period: " + period);

        alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME,
                SystemClock.elapsedRealtime() + period, period, servicePendingIntent);
    }

    @Override
    public void cancelBackgroundFeedUpdates() {
        Timber.d("cancelBackgroundFeedUpdates");
        alarmManager.cancel(servicePendingIntent);
    }
}
