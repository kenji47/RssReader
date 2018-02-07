package com.kenji1947.rssreader.di.application.test.v21;

import com.kenji1947.rssreader.di.application.modules.AlarmSchedulerServiceModule;
import com.kenji1947.rssreader.di.application.modules.ApiModule;
import com.kenji1947.rssreader.di.application.modules.AppModule;
import com.kenji1947.rssreader.di.application.modules.ConnectivityModule;
import com.kenji1947.rssreader.di.application.modules.DatabaseModule;
import com.kenji1947.rssreader.di.application.modules.RepositoryModule;
import com.kenji1947.rssreader.di.application.modules.RouterModule;
import com.kenji1947.rssreader.di.application.modules.SchedulerServiceModule;
import com.kenji1947.rssreader.di.application.modules.SchedulersModule;
import com.kenji1947.rssreader.di.application.modules.UtilsModule;
import com.kenji1947.rssreader.di.application.test.BaseComponent;
import com.kenji1947.rssreader.di.application.test.BaseSchedulers;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by chamber on 28.12.2017.
 */
//@Singleton
//@Component(modules = {
//        AppModule.class,
//        RouterModule.class,
//        RepositoryModule.class,
//        SchedulersModule.class,
//        DatabaseModule.class,
//        ConnectivityModule.class,
//        SchedulerServiceModule.class,
//        //AlarmSchedulerServiceModule.class,
//        BaseSchedulers.class,
//        ApiModule.class,
//        UtilsModule.class})
public interface AppComponent21 extends BaseComponent {

}
