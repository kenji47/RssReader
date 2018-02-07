package com.kenji1947.rssreader.presentation.article_list;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.kenji1947.rssreader.domain.entities.Article;
import com.kenji1947.rssreader.domain.entities.Feed;

import java.util.List;

/**
 * Created by chamber on 14.12.2017.
 */

public interface ArticleListView extends MvpView {
    @StateStrategyType(AddToEndSingleStrategy.class)
    void showProgress(boolean progress);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void showArticles(List<Article> articles);


}
