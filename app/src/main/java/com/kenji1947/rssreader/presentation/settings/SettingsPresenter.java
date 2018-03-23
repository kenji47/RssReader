package com.kenji1947.rssreader.presentation.settings;

import android.content.SharedPreferences;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.kenji1947.rssreader.data.worker.error_handler.ErrorHandler;
import com.kenji1947.rssreader.data.worker.preference.PreferenceManager;
import com.kenji1947.rssreader.domain.interactors.feed.FeedSyncInteractor;
import com.kenji1947.rssreader.domain.util.RxSchedulersProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import ru.terrakok.cicerone.Router;
import timber.log.Timber;

/**
 * Created by chamber on 27.12.2017.
 */

@InjectViewState
public class SettingsPresenter extends MvpPresenter<SettingsView> {
    private RxSchedulersProvider schedulersProvider;
    private FeedSyncInteractor backgroundFeedUpdateInteractor;
    private PreferenceManager preferenceManager;
    private ErrorHandler errorHandler;
    private Router router;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    SharedPreferences.OnSharedPreferenceChangeListener preferenceChangeListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
            Timber.d("onSharedPreferenceChanged " + s);
//            switch (s) {
//                case KEY_SHOULD_UPDATE_FEEDS_IN_BACKGROUND:
//                    Timber.d("KEY_SHOULD_UPDATE_FEEDS_IN_BACKGROUND " +
//                            sharedPreferences.getBoolean(KEY_SHOULD_UPDATE_FEEDS_IN_BACKGROUND, false));
//                    break;
//                case KEY_FEED_UPDATE_SERVICE_PERIOD:
//                    Timber.d("KEY_FEED_UPDATE_SERVICE_PERIOD " +
//                            sharedPreferences.getLong(KEY_FEED_UPDATE_SERVICE_PERIOD, DEFAULT_FEED_UPDATE_SERVICE_PERIOD));
//                    break;
//            }
        }
    };

    @Inject
    public SettingsPresenter(RxSchedulersProvider schedulersProvider,
                             FeedSyncInteractor backgroundFeedUpdateInteractor,
                             PreferenceManager preferenceManager,
                             ErrorHandler errorHandler,
                             Router router) {
        this.schedulersProvider = schedulersProvider;
        this.backgroundFeedUpdateInteractor = backgroundFeedUpdateInteractor;
        this.preferenceManager = preferenceManager;
        this.errorHandler = errorHandler;
        this.router = router;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        Timber.d("onFirstViewAttach");
        //preferenceManager.registerPreferenceChangeListener(preferenceChangeListener);
        initSettings();
    }

    private void initSettings() {
        backgroundFeedUpdateInteractor.getPreferencesData()
                .observeOn(schedulersProvider.getMain())
                .subscribe(preferences -> {
                    getViewState().setPreferences(preferences);
                });
    }


    public void onEnableBackgroundUpdate(boolean update) {
        (update ? backgroundFeedUpdateInteractor.enableBackgroundFeedUpdate()
                : backgroundFeedUpdateInteractor.disableBackgroundFeedUpdate())
                .observeOn(schedulersProvider.getMain())
                .subscribe(() -> {Timber.d("onEnableBackgroundUpdate Complete " + update);});
    }

    public void changeUpdateInterval(long intervalMillis) {
        backgroundFeedUpdateInteractor.setUpdateFeedsInBackgroundInterval(intervalMillis)
                .observeOn(schedulersProvider.getMain())
                .subscribe(() -> {Timber.d("changeUpdateInterval Complete " + intervalMillis);});
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
        //preferenceManager.unregisterPreferenceChangeListener(preferenceChangeListener);
    }

    public void onBackPressed() {
        router.exit();
    }
}
