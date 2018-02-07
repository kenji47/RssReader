package com.kenji1947.rssreader.presentation.new_feed;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.kenji1947.rssreader.App;
import com.kenji1947.rssreader.R;
import com.kenji1947.rssreader.di.presenter.FeedNewPresenterComponent;
import com.kenji1947.rssreader.presentation.Screens;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;
import timber.log.Timber;

/**
 * Created by chamber on 13.12.2017.
 */

//TODO Not used
public class FeedNewFragment extends MvpAppCompatFragment implements FeedNewView {

    //delete
    public static final String TAG = FeedNewFragment.class.getSimpleName();
    private static final long THROTTLE_MILLIS = 300L; //TODO Объяснить

    @BindView(R.id.button_add_feed) Button button_add_feed;
    @BindView(R.id.view_dialog_background) View view_dialog_background;
    @BindView(R.id.textInputEditText_feed_url) TextInputEditText textInputEditText_feed_url;
    @BindView(R.id.textView_error_message) TextView textView_error_message;
    @BindView(R.id.progressBar_loading_indicator) ProgressBar progressBar_loading_indicator;

    //delete
    private final Subject<Boolean> onFeedChangeSubject = BehaviorSubject.create();
    public Observable<Boolean> subscribeToFeedChange() {
        return onFeedChangeSubject;
    }

    public static FeedNewFragment newInstance() {
        Bundle args = new Bundle();

        FeedNewFragment fragment = new FeedNewFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @InjectPresenter
    FeedNewPresenter presenter;

    @ProvidePresenter
    FeedNewPresenter providePresenter() {
        return FeedNewPresenterComponent.Initializer.init(App.INSTANCE.getAppComponent()).provideFeedNew();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View fragmentView = inflater.inflate(R.layout.fragment_new_feed_coord, container, false);
        ButterKnife.bind(this, fragmentView);
        return fragmentView;
    }

    @OnClick(R.id.button_add_feed)
    void onButtonAddFeed() {presenter.addNewFeed(textInputEditText_feed_url.getText().toString());}

    @OnClick(R.id.view_dialog_background)
    void onViewDialogBackgroundClick() {back();}

    private void back() {
        presenter.back();
    }

//    @Override
//    public void hideKeyboard() {
//        Screens.hideKeyboardFrom(getContext(), getView());
//    }
//
//    //delete
//    @Override
//    public void onNewFeedCreated() {
//        Timber.d("onNewFeedCreated");
//        onFeedChangeSubject.onNext(true);
//        back();
//    }


    @Override
    public void closeDialog() {

    }

    @Override
    public void showProgress(boolean progress) {
        textView_error_message.setVisibility(progress ? View.GONE : View.VISIBLE);
        progressBar_loading_indicator.setVisibility(progress ? View.VISIBLE : View.GONE);
        button_add_feed.setEnabled(!progress);
    }

    @Override
    public void showMessage(String message) {
        textView_error_message.setText(message);
    }
}
