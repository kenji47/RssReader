package com.kenji1947.rssreader.presentation.settings;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.kenji1947.rssreader.App;
import com.kenji1947.rssreader.R;
import com.kenji1947.rssreader.di.presenter.SettingsPresenterComponent;
import com.kenji1947.rssreader.domain.repository.AppPreferences;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by chamber on 27.12.2017.
 */

public class SettingsFragment extends MvpAppCompatFragment implements SettingsView{
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.switch_enable_background_updates) Switch switch_enable_background_updates;
    @BindView(R.id.button_show_dialog_get_interval) Button button_show_dialog_get_interval;
    @BindView(R.id.textView3) TextView textView3;

    private Unbinder unbinder;

    @InjectPresenter
    SettingsPresenter presenter;

    @ProvidePresenter
    SettingsPresenter providePresenter() {
        return SettingsPresenterComponent
                .Initializer.init(App.INSTANCE.getAppComponent()).provideSettingsPresenter();
    }

    public static SettingsFragment newInstance() {
        Bundle args = new Bundle();
        SettingsFragment fragment = new SettingsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View fragmentView = inflater.inflate(R.layout.fragment_settings, container, false);
        unbinder = ButterKnife.bind(this, fragmentView);
        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initToolbar(getString(R.string.menu_settings));
        initSwitch();
        initIntervalDialog();
    }

    private void initSwitch() {
        switch_enable_background_updates.setOnCheckedChangeListener((compoundButton, b) -> {
            presenter.onEnableBackgroundUpdate(b);
        });
    }

    private void initIntervalDialog() {
        button_show_dialog_get_interval.setOnClickListener(view -> {
            presenter.changeUpdateInterval(System.currentTimeMillis());
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    private void initToolbar(String title) {
        toolbar.setTitle(title);
        AppCompatActivity activity = (AppCompatActivity)getActivity();
        activity.setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true); //TODO
        toolbar.setNavigationOnClickListener(view -> presenter.onBackPressed());
    }

    @Override
    public void setPreferences(AppPreferences preferences) {
        switch_enable_background_updates.setChecked(preferences.enableBackgroundUpdates);
        button_show_dialog_get_interval.setText(preferences.backgroundUpdatesInterval + "");
        textView3.setText(preferences.backgroundUpdatesInterval + "");
    }
}
