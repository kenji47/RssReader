package com.kenji1947.rssreader.data.worker.feed_sync_scheduler.job_scheduler;

import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.content.res.Configuration;

import com.kenji1947.rssreader.App;
import com.kenji1947.rssreader.data.worker.notifications.NotificationFactory;
import com.kenji1947.rssreader.data.worker.notifications.NotificationManager;
import com.kenji1947.rssreader.domain.interactors.feed.FeedUpdateInteractor;

import javax.inject.Inject;
import javax.inject.Named;

import timber.log.Timber;

/**
 * Created by chamber on 07.12.2017.
 */

public class FeedUpdateService extends JobService {
    private static final int NEW_ARTICLES_NOTIFICATION_ID = 194747;

//    @Inject
//    GetUserFeedsUseCase getUserFeedsUseCase;
//    @Inject
//    UpdateFeedUseCase updateFeedUseCase;
//    @Inject
//    GetUnreadArticlesCountUseCase getUnreadArticlesCountUseCase;

    @Inject
    FeedUpdateInteractor updateAllFeedsInteractor;

    @Inject
    NotificationManager notificationManager;
    @Inject
    NotificationFactory notificationFactory;
    @Inject
    @Named("FeedUpdateNotificationPendingIntent")
    PendingIntent notificationPendingIntent;

    //TODO CompositeSubscription?

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.d("onCreate");
        App.INSTANCE.getAppComponent().inject(this);

        //presenter create
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Timber.d("onDestroy");
        //destroy presenter
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Timber.d("onConfigurationChanged");
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Timber.d("onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        Timber.d("onRebind");
        super.onRebind(intent);
    }

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        //delegate to presenter

        Timber.d("onStartJob jobId: " + jobParameters.getJobId());
        updateAllFeedsInteractor.updateAllFeeds() //TODO Метод заменен
                //.subscribeOn(Schedulers.io()) //TODO Change to SP
                .doOnSuccess(feeds -> jobFinished(jobParameters, false))
                .subscribe(feeds -> {showNewArticlesNotification();},
                        throwable -> {jobFinished(jobParameters, false);});

        //показать уведомление тодько если колво новых статей увеличилось
        //уведомление должно отображать количество непрочитанных статей

        return true;
    }
    private void showNewArticlesNotification() {
        //delegate to presenter

        Timber.d("showNewArticlesNotification");
        notificationManager.showNotification(NEW_ARTICLES_NOTIFICATION_ID,
                notificationFactory.createNewArticlesNotification(notificationPendingIntent));
    }
    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        Timber.d("onStopJob jobId: " + jobParameters.getJobId());
        return false;
    }
}
