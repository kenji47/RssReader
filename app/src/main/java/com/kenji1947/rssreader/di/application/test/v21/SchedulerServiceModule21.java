package com.kenji1947.rssreader.di.application.test.v21;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;

import com.kenji1947.rssreader.data.worker.feed_sync_scheduler.job_sceduler_presenter.FeedUpdateSchedulerJob;
import com.kenji1947.rssreader.data.worker.feed_sync_scheduler.job_sceduler_presenter.FeedUpdateService2;
import com.kenji1947.rssreader.data.worker.feed_sync_scheduler.job_sceduler_presenter.JobInfoFactory;
import com.kenji1947.rssreader.data.worker.feed_sync_scheduler.job_sceduler_presenter.JobSchedulerWrapper;
import com.kenji1947.rssreader.data.worker.feed_sync_scheduler.job_sceduler_presenter.JobSchedulerWrapperImpl;
import com.kenji1947.rssreader.data.worker.feed_sync_scheduler.job_scheduler.FeedUpdateService;
import com.kenji1947.rssreader.data.worker.notifications.NotificationFactory;
import com.kenji1947.rssreader.data.worker.notifications.NotificationManager;
import com.kenji1947.rssreader.data.worker.preference.PreferenceManager;
import com.kenji1947.rssreader.domain.worker.FeedUpdateScheduler;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import timber.log.Timber;

/**
 * Created by chamber on 10.12.2017.
 */

@Module
public class SchedulerServiceModule21 {
    private static final int FEEDS_UPDATE_JOB_ID = 8791;
    private static final int FEEDS_UPDATE_INTERVAL_MINS = 4000;

    @Provides
    @Singleton
    FeedUpdateScheduler provideFeedUpdateScheduler(ComponentName feedsUpdateJobService,
                                                   PreferenceManager preferenceManager,
                                                   JobSchedulerWrapper jobSchedulerWrapper,
                                                   JobInfo jobInfo) {

        //return new FeedUpdateSchedulerAlarmManager(preferenceManager, alarmManager, servicePendingIntent);
        return new FeedUpdateSchedulerJob(feedsUpdateJobService, preferenceManager, jobSchedulerWrapper, jobInfo);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            Log.i("ApplicationComponent", "LOLLIPOP");
//            return new FeedUpdateSchedulerJob(feedsUpdateJobService, preferenceManager, jobSchedulerWrapper, jobInfo);
//        } else {
//            Log.i("ApplicationComponent", + Build.VERSION.SDK_INT + "");
//            return new FeedUpdateSchedulerAlarmManager(preferenceManager, alarmManager, servicePendingIntent);
//        }
    }
  



    @Provides
    @Singleton
    JobSchedulerWrapper provideJobSchedulerWrapper(JobScheduler jobScheduler) {
        return new JobSchedulerWrapperImpl(jobScheduler);
    }

    @Provides
    @Singleton
    JobScheduler provideJobScheduler(Context context) {
        Timber.d("provideJobScheduler");
        return (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
    }

    @Provides
    @Singleton
    JobInfo provideJobInfo(ComponentName feedsUpdateJobService) {
        return JobInfoFactory.createJobInfo(
                FEEDS_UPDATE_JOB_ID,
                FEEDS_UPDATE_INTERVAL_MINS,
                feedsUpdateJobService);
    }

    @Provides
    @Singleton
    ComponentName provideFeedsUpdateJobService(Context context) {
        return new ComponentName(context, FeedUpdateService2.class);
    }


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
        NotificationManager provideNotificationManager();
        NotificationFactory provideNotificationFactory();
    }
}
