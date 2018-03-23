package com.kenji1947.rssreader.data.worker.notifications;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.res.Resources;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.res.ResourcesCompat;


import com.kenji1947.rssreader.R;

/**
 * Created by chamber on 07.12.2017.
 */

public class NotificationFactoryImpl implements NotificationFactory {

    private final Context context;
    private final Resources resources;

    public NotificationFactoryImpl(final Context context, final Resources resources) {
        this.context = context;
        this.resources = resources;
    }

    @Override
    public Notification createNewArticlesNotification(final PendingIntent contentIntent) {
        final NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context);
        return notificationBuilder.setAutoCancel(true)
                .setColor(ResourcesCompat.getColor(resources, R.color.colorPrimary, null))
                .setSmallIcon(R.drawable.notification_icon)
                .setContentTitle(resources.getString(R.string.notification_feed_updated_title))
                .setContentText(resources.getString(R.string.notification_feed_updated_text))
                .setContentIntent(contentIntent)
                .build();
    }

    @Override
    public Notification createNewArticlesNotificationNew(String title, String content, PendingIntent contentIntent) {
        final NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context);
        return notificationBuilder.setAutoCancel(true)
                .setColor(ResourcesCompat.getColor(resources, R.color.colorPrimary, null))
                .setSmallIcon(R.drawable.notification_icon)
                .setContentTitle(title)
                .setContentText(content)
                .setContentIntent(contentIntent)
                .build();
    }

    @Override
    public Notification createFeedSyncNotification(String title, String content, PendingIntent contentIntent) {
        final NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context);
        return notificationBuilder.setAutoCancel(true)
                .setColor(ResourcesCompat.getColor(resources, R.color.colorPrimary, null))
                .setSmallIcon(R.drawable.notification_icon)
                .setContentTitle(title)
                .setContentText(content)
                .setContentIntent(contentIntent)
                .build();
    }
}
