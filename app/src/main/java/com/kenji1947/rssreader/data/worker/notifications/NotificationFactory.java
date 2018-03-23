package com.kenji1947.rssreader.data.worker.notifications;

import android.app.Notification;
import android.app.PendingIntent;

/**
 * Created by chamber on 07.12.2017.
 */

public interface NotificationFactory {
    //TODO Разные сервисы используют разные методы
    Notification createNewArticlesNotification(PendingIntent contentIntent);
    Notification createNewArticlesNotificationNew(String title, String content, PendingIntent contentIntent);

    Notification createFeedSyncNotification(String title, String content, PendingIntent contentIntent);
}
