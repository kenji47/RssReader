package com.kenji1947.rssreader.data.worker.notifications;

import android.app.Notification;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationManagerCompat;

/**
 * Created by chamber on 07.12.2017.
 */

public class NotificationManagerImpl implements NotificationManager {

    private final NotificationManagerCompat notificationManagerCompat;

    public NotificationManagerImpl(@NonNull final NotificationManagerCompat notificationManagerCompat) {
        this.notificationManagerCompat = notificationManagerCompat;
    }

    @Override
    public void showNotification(final int notificationId, final Notification notification) {
        notificationManagerCompat.notify(notificationId, notification);
    }

    @Override
    public void updateNotification(final int notificationId, final Notification notification) {
        notificationManagerCompat.notify(notificationId, notification);
    }

    @Override
    public void hideNotification(final int notificationId) {
        notificationManagerCompat.cancel(notificationId);
    }

    @Override
    public void hideNotifications() {
        notificationManagerCompat.cancelAll();
    }
}
