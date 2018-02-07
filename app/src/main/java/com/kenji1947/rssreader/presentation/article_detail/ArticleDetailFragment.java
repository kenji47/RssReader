package com.kenji1947.rssreader.presentation.article_detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.kenji1947.rssreader.App;
import com.kenji1947.rssreader.R;
import com.kenji1947.rssreader.di.presenter.ArticleDetailPresenterComponent;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chamber on 15.12.2017.
 */

public class ArticleDetailFragment extends MvpAppCompatFragment implements ArticleDetailView{
    public static final String TAG = ArticleDetailFragment.class.getSimpleName();
    private static final String ARG_ARTICLE_CONTENT_URL = "article_content_url";

    @BindView(R.id.webView_article_content) WebView webView_article_content;

    @InjectPresenter
    ArticleDetailPresenter presenter;
    @ProvidePresenter
    ArticleDetailPresenter providePresenter() {
        return ArticleDetailPresenterComponent.Initializer.init(App.INSTANCE.getAppComponent()).providePresenter();
    }

    public static ArticleDetailFragment newInstance(final String articleContentUrl) {
        final ArticleDetailFragment fragment = new ArticleDetailFragment();
        final Bundle arguments = new Bundle();
        arguments.putString(ARG_ARTICLE_CONTENT_URL, articleContentUrl);
        fragment.setArguments(arguments);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        final View fragmentView = inflater.inflate(R.layout.fragment_article_detail, container, false);
        ButterKnife.bind(this, fragmentView);
        return fragmentView;
    }

    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        extractArguments(getArguments());
    }

    private void extractArguments(final Bundle arguments) {
        setContentUrl(arguments.getString(ARG_ARTICLE_CONTENT_URL));
    }

    private void setupWebView(final String url) {
        webView_article_content.setWebViewClient(new ArticleWebClient(url));
        webView_article_content.getSettings().setJavaScriptEnabled(true);
        webView_article_content.loadUrl(url);
    }

    public void setContentUrl(final String url) {
        setupWebView(url);
    }
}
