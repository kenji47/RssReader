package com.kenji1947.rssreader.presentation.article_detail;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.kenji1947.rssreader.domain.util.SchedulersProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by chamber on 15.12.2017.
 */

@InjectViewState
public class ArticleDetailPresenter extends MvpPresenter<ArticleDetailView> {
    private SchedulersProvider schedulersProvider;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Inject
    public ArticleDetailPresenter(SchedulersProvider schedulersProvider) {
        this.schedulersProvider = schedulersProvider;
    }
}
