package com.kenji1947.rssreader.data;

import com.kenji1947.rssreader.domain.util.SchedulersProvider;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by chamber on 17.12.2017.
 */

public class SchedulersTrampoline implements SchedulersProvider {
    @Override
    public Scheduler getMain() {
        return Schedulers.trampoline();
    }

    @Override
    public Scheduler getComputation() {
        return Schedulers.trampoline();
    }

    @Override
    public Scheduler getTrampoline() {
        return Schedulers.trampoline();
    }

    @Override
    public Scheduler getNewThread() {
        return Schedulers.trampoline();
    }

    @Override
    public Scheduler getIo() {
        return Schedulers.trampoline();
    }
}
