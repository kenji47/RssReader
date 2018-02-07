package com.kenji1947.rssreader.di.application.test;

import com.kenji1947.rssreader.di.application.modules.ApiModule;
import com.kenji1947.rssreader.di.application.modules.AppModule;
import com.kenji1947.rssreader.di.application.modules.ConnectivityModule;
import com.kenji1947.rssreader.di.application.modules.DatabaseModule;
import com.kenji1947.rssreader.di.application.modules.RepositoryModule;
import com.kenji1947.rssreader.di.application.modules.RouterModule;
import com.kenji1947.rssreader.di.application.modules.SchedulerServiceModule;
import com.kenji1947.rssreader.di.application.modules.SchedulersModule;
import com.kenji1947.rssreader.di.application.modules.UtilsModule;
import com.kenji1947.rssreader.domain.interactors.feed.FeedCrudInteractor;

/**
 * Created by kenji1947 on 11.11.2017.
 */

public interface BaseComponentExposes extends
        AppModule.Exposes,
        RepositoryModule.Exposes,
        SchedulersModule.Exposes,
        DatabaseModule.Exposes,
        RouterModule.Exposes,
        ConnectivityModule.Exposes,
        ApiModule.Exposes,
        BaseSchedulers.Exposes,
        UtilsModule.Exposes{

}
