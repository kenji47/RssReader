package com.kenji1947.rssreader.data.worker.feed_sync_worker;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.kenji1947.rssreader.App;
import com.kenji1947.rssreader.R;
import com.kenji1947.rssreader.data.worker.notifications.NotificationFactory;
import com.kenji1947.rssreader.data.worker.notifications.NotificationManager;
import com.kenji1947.rssreader.di.presenter.FeedSyncServicePresenterComponent;

import javax.inject.Inject;
import javax.inject.Named;

import timber.log.Timber;

/**
 * Created by chamber on 17.03.2018.
 */

public class FeedSyncService extends Service implements FeedSyncServiceView {
    //TODO Сделать общим
    private static final int FEED_SYNC_NOTIFICATION_ID = 194747;

    @Inject
    NotificationManager notificationManager;
    @Inject
    NotificationFactory notificationFactory;
    @Inject
    @Named("FeedUpdateNotificationPendingIntent")
    PendingIntent notificationPendingIntent;

    FeedSyncServicePresenter presenter;

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.d("onCreate");
        App.INSTANCE.getAppComponent().inject(this);

        presenter = FeedSyncServicePresenterComponent.Initializer
                .init(App.INSTANCE.getAppComponent()).providePresenter();
        presenter.attachView(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        presenter.updateAllFeeds();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Timber.d("onDestroy");
        presenter.onDestroy();
    }

    @Override
    public void finishJob() {
        Timber.d("finishJob");
        stopSelf();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    //---

    @Override
    public void showFeedUpdatingProgressNotification(int feedsTotal, int feedProgress) {
        notificationManager.showNotification(
                FEED_SYNC_NOTIFICATION_ID,
                notificationFactory.createFeedSyncNotification(
                        getUpdateNotificationTitle(),
                        getUpdateNotificationText(feedsTotal, feedProgress),
                        notificationPendingIntent));
    }

    private String getUpdateNotificationTitle() {
        return getString(R.string.notification_feed_sync_update_title);
    }
    private String getUpdateNotificationText(int feedsTotal, int feedProgress) {
        return getString(R.string.notification_feed_sync_update_text, feedProgress, feedsTotal);
    }

    @Override
    public void showFeedUpdatingDoneNotification(int newArticlesCount) {
        notificationManager.showNotification(FEED_SYNC_NOTIFICATION_ID,
                notificationFactory.createFeedSyncNotification(
                        getDoneNotificationTitle(),
                        getDoneNotificationText(newArticlesCount),
                        notificationPendingIntent));
    }

    private String getDoneNotificationTitle() {
        return getString(R.string.notification_feed_sync_done_title);
    }
    private String getDoneNotificationText(int newArticlesCount) {
        return getString(R.string.notification_feed_sync_done_text, newArticlesCount);
    }
}
