package com.kenji1947.rssreader.di.application.test.v19;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v4.app.NotificationManagerCompat;

import com.kenji1947.rssreader.data.worker.feed_update_scheduler.alarm_manager.FeedUpdateSchedulerAlarmManager;
import com.kenji1947.rssreader.data.worker.feed_update_scheduler.alarm_manager.FeedUpdateSimpleService;
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

/**
 * Created by chamber on 10.12.2017.
 */

@Module
public class SchedulerServiceModule19 {

    @Provides
    @Singleton
    FeedUpdateScheduler provideFeedUpdateScheduler(
            PreferenceManager preferenceManager,
            AlarmManager alarmManager,
            @Named("FeedUpdateServicePendingIntent") PendingIntent servicePendingIntent
    ) {
        return new FeedUpdateSchedulerAlarmManager(preferenceManager, alarmManager, servicePendingIntent);
    }

    @Provides
    @Singleton
    AlarmManager provideAlarmManager(Context context) {
        return (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    }

    @Provides
    @Singleton
    NotificationManager provideNotificationManager(NotificationManagerCompat notificationManagerCompat) {
        return new NotificationManagerImpl(notificationManagerCompat);
    }

    @Provides
    @Singleton
    NotificationManagerCompat provideNotificationManagerCompat(Context context) {
        return NotificationManagerCompat.from(context);
    }

    //TODO Вынести в общее
    @Provides
    @Singleton
    @Named("FeedUpdateNotificationPendingIntent")
    PendingIntent provideFeedUpdateNotificationPendingIntent(Context context) {
        final Intent targetActivityIntent = new Intent(context, MainActivity.class); //TODO Заменить
        return PendingIntent.getActivity(context,
                0, targetActivityIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    @Provides
    @Singleton
    @Named("FeedUpdateServicePendingIntent")
    PendingIntent provideFeedUpdateServicePendingIntent(Context context) {
        Intent intent = new Intent(context, FeedUpdateSimpleService.class);
        return PendingIntent.getService(context, 0, intent, 0);
    }

    @Provides
    @Singleton
    NotificationFactory provideNotificationFactory(Context context, final Resources resources) {
        return new NotificationFactoryImpl(context, resources);
    }

//    @Provides
//    @Singleton
//    FeedUpdateSimpleService provideFeedUpdateSimpleService() {
//        return new FeedUpdateSimpleService();
//    }



    public interface Exposes {
        FeedUpdateScheduler provideFeedUpdateScheduler();
        NotificationManager provideNotificationManager();
        NotificationFactory provideNotificationFactory();
    }
}
