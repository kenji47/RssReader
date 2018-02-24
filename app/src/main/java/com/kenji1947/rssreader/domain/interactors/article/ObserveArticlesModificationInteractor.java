package com.kenji1947.rssreader.domain.interactors.article;

import com.kenji1947.rssreader.domain.repository.ArticleRepository;
import com.kenji1947.rssreader.domain.util.SchedulersProvider;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by chamber on 24.12.2017.
 */

//TODO An objectbox can notify only about changes in child entities such as adding and removing.
//TODO But he can not notify about modifications in child entities.
public class ObserveArticlesModificationInteractor {
    private ArticleRepository articleRepository;
    private SchedulersProvider schedulersProvider;

    @Inject
    public ObserveArticlesModificationInteractor(ArticleRepository articleRepository, SchedulersProvider schedulersProvider) {
        this.articleRepository = articleRepository;
        this.schedulersProvider = schedulersProvider;
    }

    public Observable<Boolean> observeArticlesModification() {
        return articleRepository.observeArticlesModification();
    }

    public void notifyArticleModified() {
        articleRepository.notifyArticleModified();
    }
}
