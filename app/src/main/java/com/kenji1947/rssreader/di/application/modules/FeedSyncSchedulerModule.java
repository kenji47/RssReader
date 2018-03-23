package com.kenji1947.rssreader.di.application.modules;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.support.v4.app.NotificationManagerCompat;

import com.kenji1947.rssreader.data.worker.feed_sync_scheduler.alarm_manager.FeedUpdateSchedulerAlarmManager;
import com.kenji1947.rssreader.data.worker.feed_sync_scheduler.alarm_manager.FeedUpdateSimpleService;
import com.kenji1947.rssreader.data.worker.feed_sync_scheduler.job_sceduler_presenter.FeedUpdateService2;
import com.kenji1947.rssreader.data.worker.feed_sync_scheduler.job_sceduler_presenter.FeedUpdateSchedulerJob;
import com.kenji1947.rssreader.data.worker.feed_sync_scheduler.job_scheduler.FeedUpdateService;
import com.kenji1947.rssreader.data.worker.feed_sync_scheduler.job_sceduler_presenter.JobInfoFactory;
import com.kenji1947.rssreader.data.worker.feed_sync_scheduler.job_sceduler_presenter.JobSchedulerWrapper;
import com.kenji1947.rssreader.data.worker.feed_sync_scheduler.job_sceduler_presenter.JobSchedulerWrapperImpl;
import com.kenji1947.rssreader.data.worker.notifications.NotificationFactory;
import com.kenji1947.rssreader.data.worker.notifications.NotificationFactoryImpl;
import com.kenji1947.rssreader.data.worker.notifications.NotificationManager;
import com.kenji1947.rssreader.data.worker.notifications.NotificationManagerImpl;
import com.kenji1947.rssreader.data.worker.preference.PreferenceManager;
import com.kenji1947.rssreader.domain.worker.FeedUpdateScheduler;
import com.kenji1947.rssreader.presentation.MainActivity;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import timber.log.Timber;

/**
 * Created by chamber on 10.12.2017.
 */

@Module
public class FeedSyncSchedulerModule {
    private static final int FEEDS_UPDATE_JOB_ID = 8791;
    private static final int FEEDS_UPDATE_INTERVAL_MINS = 4000;

    //TODO Объявить зависимости, которые аннотированы (провайдятся)
    @Provides
    @Singleton
    FeedUpdateScheduler provideFeedUpdateScheduler(Context context, PreferenceManager preferenceManager,
                                                   @Named("FeedUpdateServicePendingIntent")
                                                           PendingIntent servicePendingIntent) {

        //return new FeedUpdateSchedulerAlarmManager(preferenceManager, provideAlarmManager(context), servicePendingIntent);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Timber.d( "LOLLIPOP");
            return new FeedUpdateSchedulerJob(
                    provideFeedsUpdateJobService(context),
                    preferenceManager,
                    provideJobSchedulerWrapper(provideJobScheduler(context)),
                    provideJobInfo(provideFeedsUpdateJobService(context)));
        } else {
            Timber.d("OLD " + Build.VERSION.SDK_INT + "");
            return new FeedUpdateSchedulerAlarmManager(preferenceManager, provideAlarmManager(context), servicePendingIntent);
        }
    }
    //---
    //TODO Alaram Manager
    AlarmManager provideAlarmManager(Context context) {
        return (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    }

//    @Provides
//    @Singleton
//    @Named("FeedUpdateNotificationPendingIntent")
//    PendingIntent provideFeedUpdateNotificationPendingIntent(Context context) {
//        final Intent targetActivityIntent = new Intent(context, MainActivity.class); //TODO Заменить
//        return PendingIntent.getActivity(context,
//                0, targetActivityIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//    }

    //TODO JobScheduler
    JobSchedulerWrapper provideJobSchedulerWrapper(JobScheduler jobScheduler) {
        return new JobSchedulerWrapperImpl(jobScheduler);
    }

    JobScheduler provideJobScheduler(Context context) {
        Timber.d("provideJobScheduler");
        return (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
    }

    JobInfo provideJobInfo(ComponentName feedsUpdateJobService) {
        return JobInfoFactory.createJobInfo(
                FEEDS_UPDATE_JOB_ID,
                FEEDS_UPDATE_INTERVAL_MINS,
                feedsUpdateJobService);
    }

    ComponentName provideFeedsUpdateJobService(Context context) {
        return new ComponentName(context, FeedUpdateService2.class);
    }
    //---
//    @Provides
//    @Singleton
//    AlarmManager provideAlarmManager(Context context) {
//        return (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//    }
//    @Provides
//    @Singleton
//    JobSchedulerWrapper provideJobSchedulerWrapper(JobScheduler jobScheduler) {
//        return new JobSchedulerWrapperImpl(jobScheduler);
//    }

//    @Provides
//    @Singleton
//    JobScheduler provideJobScheduler(Context context) {
//        Timber.d("provideJobScheduler");
//        return (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
//    }

//    @Provides
//    @Singleton
//    JobInfo provideJobInfo(ComponentName feedsUpdateJobService) {
//        return JobInfoFactory.createJobInfo(
//                FEEDS_UPDATE_JOB_ID,
//                FEEDS_UPDATE_INTERVAL_MINS,
//                feedsUpdateJobService);
//    }
//
//    @Provides
//    @Singleton
//    ComponentName provideFeedsUpdateJobService(Context context) {
//        return new ComponentName(context, FeedUpdateService2.class);
//    }
    //---
    @Provides
    @Singleton
    @Named("FeedUpdateServicePendingIntent")
    PendingIntent provideFeedUpdateServicePendingIntent(Context context) {
        Intent intent = new Intent(context, FeedUpdateSimpleService.class);
        return PendingIntent.getService(context, 0, intent, 0);
    }

//    @Provides
//    @Singleton
//    NotificationFactory provideNotificationFactory(Context context, final Resources resources) {
//        return new NotificationFactoryImpl(context, resources);
//    }
//
//
//    @Provides
//    @Singleton
//    NotificationManager provideNotificationManager(NotificationManagerCompat notificationManagerCompat) {
//        return new NotificationManagerImpl(notificationManagerCompat);
//    }
//
//    @Provides
//    @Singleton
//    NotificationManagerCompat provideNotificationManagerCompat(Context context) {
//        return NotificationManagerCompat.from(context);
//    }


    @Provides
    @Singleton
    FeedUpdateService provideFeedServiceFake() {
        return new FeedUpdateService();
    }

    @Provides
    @Singleton
    FeedUpdateService2 provideFeedService2Fake() {return new FeedUpdateService2();}

    public interface Exposes {
        FeedUpdateScheduler provideFeedUpdateScheduler();
        //NotificationManager provideNotificationManager();
        //NotificationFactory provideNotificationFactory();
    }
}
