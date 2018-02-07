package com.kenji1947.rssreader.presentation.new_feed;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

/**
 * Created by chamber on 13.12.2017.
 */

public interface FeedNewView extends MvpView {
    @StateStrategyType(AddToEndSingleStrategy.class)
    void showProgress(boolean progress);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void showMessage(String message);

    @StateStrategyType(SkipStrategy.class)
    void closeDialog();

//    @StateStrategyType(SkipStrategy.class)
//    void hideKeyboard();
//
//    @StateStrategyType(SkipStrategy.class)
//    void onNewFeedCreated();
}
