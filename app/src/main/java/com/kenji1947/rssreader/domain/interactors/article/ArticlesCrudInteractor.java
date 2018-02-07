package com.kenji1947.rssreader.domain.interactors.article;

import com.kenji1947.rssreader.domain.entities.Article;
import com.kenji1947.rssreader.domain.repository.ArticleRepository;
import com.kenji1947.rssreader.domain.util.SchedulersProvider;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

/**
 * Created by chamber on 08.12.2017.
 */

public class ArticlesCrudInteractor {
    private ArticleRepository articleRepository;
    private SchedulersProvider schedulersProvider;

    @Inject
    public ArticlesCrudInteractor(ArticleRepository articleRepository, SchedulersProvider schedulersProvider) {
        this.articleRepository = articleRepository;
        this.schedulersProvider = schedulersProvider;
    }

    public Single<List<Article>> getArticles(long feedId) {
        return articleRepository.getArticles(feedId);
    }
}
