package com.kenji1947.rssreader.data.database;

import com.kenji1947.rssreader.domain.entities.Article;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

/**
 * Created by kenji1947 on 11.11.2017.
 */

public interface ArticleDao {
    Single<List<Article>> getArticles(long feedId);
    Single<List<Article>> getFavouriteArticles();
    Completable markArticleAsRead(long articleId);
    Completable favouriteArticle(long articleId);
    Completable unFavouriteArticle(long articleId);
    Single<Long> getUnreadArticlesCount();
}
