package com.kenji1947.rssreader.data.worker.feed_update_scheduler.job_sceduler_presenter;

import android.app.job.JobInfo;

/**
 * Created by chamber on 07.12.2017.
 */

public interface JobSchedulerWrapper {
    int schedule(JobInfo jobInfo);
    void cancel(int jobId);
}
