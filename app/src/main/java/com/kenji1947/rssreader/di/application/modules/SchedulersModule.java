package com.kenji1947.rssreader.di.application.modules;

import com.kenji1947.rssreader.di.Injector;
import com.kenji1947.rssreader.domain.util.SchedulersProvider;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by kenji1947 on 11.11.2017.
 */
@Module
public class SchedulersModule {
    @Singleton
    @Provides
    SchedulersProvider provideSchedulers() {
        //return new AppSchedulers();
        return Injector.provideSchedulers();
    }

    public interface Exposes {
        SchedulersProvider provideSchedulers();
    }
}
