package com.kenji1947.rssreader;

import android.app.Application;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;

import com.kenji1947.rssreader.di.application.AppComponent;
import com.kenji1947.rssreader.di.application.DaggerAppComponent;
import com.kenji1947.rssreader.di.application.modules.ApiModule;
import com.kenji1947.rssreader.di.application.modules.AppModule;
import com.kenji1947.rssreader.di.application.modules.DatabaseModule;
import com.kenji1947.rssreader.di.application.modules.RepositoryModule;
import com.kenji1947.rssreader.di.application.modules.RouterModule;
import com.kenji1947.rssreader.di.application.modules.SchedulerServiceModule;
import com.kenji1947.rssreader.di.application.modules.SchedulersModule;
import com.kenji1947.rssreader.di.application.modules.UtilsModule;
import com.squareup.leakcanary.LeakCanary;

import ru.terrakok.cicerone.Cicerone;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.Router;
import timber.log.Timber;

import static android.util.Log.ERROR;
import static java.util.logging.Level.WARNING;

/**
 * Created by kenji1947 on 11.11.2017.
 */

public class App extends Application {
    public static App INSTANCE;
    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        initAppComponent(this);
        initTimber();

        Timber.d("onCreate");
        initNewComponent();
        //AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
//        LeakCanary.install(this);
//        LeakCanary.enableDisplayLeakActivity(this);
//        LeakCanary.refWatcher(this);
        //initCicerone();
    }

    private void initNewComponent() {

    }

    private void initAppComponent(App app) {
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(app))
                .repositoryModule(new RepositoryModule())
                .databaseModule(new DatabaseModule())
                .schedulersModule(new SchedulersModule())
                .routerModule(new RouterModule())
                .schedulerServiceModule(new SchedulerServiceModule())
                .apiModule(new ApiModule())
                .utilsModule(new UtilsModule())
                .build();

    }
    private void initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {
            Timber.plant(new Timber.Tree() {
                @Override
                protected void log(int priority, String tag, String message, Throwable t) {
                    if (priority == ERROR)
                        Log.e("ERROR", message + " " + t);
                        //YourCrashLibrary.log(priority, tag, message);
                }
            });
        }
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

}
