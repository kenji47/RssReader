package com.kenji1947.rssreader.di.application.test;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v4.app.NotificationManagerCompat;

import com.kenji1947.rssreader.data.worker.notifications.NotificationFactory;
import com.kenji1947.rssreader.data.worker.notifications.NotificationFactoryImpl;
import com.kenji1947.rssreader.data.worker.notifications.NotificationManager;
import com.kenji1947.rssreader.data.worker.notifications.NotificationManagerImpl;
import com.kenji1947.rssreader.domain.worker.FeedUpdateScheduler;
import com.kenji1947.rssreader.presentation.MainActivity;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by chamber on 28.12.2017.
 */
@Module
public class BaseSchedulers {
    @Provides
    @Singleton
    NotificationFactory provideNotificationFactory(Context context, final Resources resources) {
        return new NotificationFactoryImpl(context, resources);
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

    @Provides
    @Singleton
    @Named("FeedUpdateNotificationPendingIntent")
    PendingIntent provideFeedUpdateNotificationPendingIntent(Context context) {
        final Intent targetActivityIntent = new Intent(context, MainActivity.class); //TODO Заменить
        return PendingIntent.getActivity(context,
                0, targetActivityIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public interface Exposes {
        NotificationManager provideNotificationManager();
        NotificationFactory provideNotificationFactory();
    }
}
