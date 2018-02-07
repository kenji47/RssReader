package com.kenji1947.rssreader.domain.interactors.article;

import com.kenji1947.rssreader.domain.repository.ArticleRepository;
import com.kenji1947.rssreader.domain.util.SchedulersProvider;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by chamber on 24.12.2017.
 */

public class ObserveArticleUpdatesInteractor {
    private ArticleRepository articleRepository;
    private SchedulersProvider schedulersProvider;

    @Inject
    public ObserveArticleUpdatesInteractor(ArticleRepository articleRepository, SchedulersProvider schedulersProvider) {
        this.articleRepository = articleRepository;
        this.schedulersProvider = schedulersProvider;
    }

    public Observable<Boolean> observeArticleUpdates() {
        return articleRepository.observeArticleUpdates();
    }

    //TODO Rename to FeedsUpdated
    //TODO Move to Feed
    public void notifyArticleUpdated() {
        articleRepository.notifyArticleUpdated();
    }
}
