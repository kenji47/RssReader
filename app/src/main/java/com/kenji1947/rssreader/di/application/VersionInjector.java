package com.kenji1947.rssreader.di.application;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.job.JobInfo;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;

import com.kenji1947.rssreader.data.worker.feed_update_scheduler.alarm_manager.FeedUpdateSchedulerAlarmManager;
import com.kenji1947.rssreader.data.worker.feed_update_scheduler.job_sceduler_presenter.FeedUpdateSchedulerJob;
import com.kenji1947.rssreader.data.worker.feed_update_scheduler.job_sceduler_presenter.JobSchedulerWrapper;
import com.kenji1947.rssreader.data.worker.preference.PreferenceManager;
import com.kenji1947.rssreader.domain.worker.FeedUpdateScheduler;

import javax.inject.Named;

import timber.log.Timber;

/**
 * Created by chamber on 28.12.2017.
 */

public class VersionInjector {
//    FeedUpdateScheduler provideFeedUpdateScheduler(Context context,
//                                                   PreferenceManager preferenceManager,
//                                                   PendingIntent servicePendingIntent) {

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            Timber.d("LOLLIPOP");
//            return buildJobScheduler();
//        } else {
//            Timber.d("OLD " + Build.VERSION.SDK_INT + "");
//            //return new FeedUpdateSchedulerAlarmManager(preferenceManager, alarmManager, servicePendingIntent);
//        }
//    }
//
//    FeedUpdateScheduler buildJobScheduler() {
//        new FeedUpdateSchedulerJob(feedsUpdateJobService, preferenceManager, jobSchedulerWrapper, jobInfo);
//    }
}
