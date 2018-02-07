package com.kenji1947.rssreader.data.util;

import com.kenji1947.rssreader.domain.util.SchedulersProvider;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by kenji1947 on 07.11.2017.
 */

public class AppSchedulers implements SchedulersProvider {
    @Override
    public Scheduler getMain() {
        return AndroidSchedulers.mainThread();
    }

    @Override
    public Scheduler getComputation() {
        return Schedulers.computation();
    }

    @Override
    public Scheduler getTrampoline() {
        return Schedulers.trampoline();
    }

    @Override
    public Scheduler getNewThread() {
        return Schedulers.newThread();
    }

    @Override
    public Scheduler getIo() {
        return Schedulers.io();
    }
}
