package com.kenji1947.rssreader.data.api.fetch_feed.feedly_api;

import com.kenji1947.rssreader.data.api.ApiRetrofit;
import com.kenji1947.rssreader.data.api.fetch_feed.FetchFeedApiService;
import com.kenji1947.rssreader.data.api.fetch_feed.feedly_api.model.ArticleItem;
import com.kenji1947.rssreader.domain.entities.Article;
import com.kenji1947.rssreader.domain.entities.Feed;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import timber.log.Timber;

/**
 * Created by chamber on 24.02.2018.
 */

public class FetchFeedApiServiceFeedly implements FetchFeedApiService {
    private ApiRetrofit apiRetrofit;

    public FetchFeedApiServiceFeedly(ApiRetrofit apiRetrofit) {
        this.apiRetrofit = apiRetrofit;
    }

    @Override
    public Single<Feed> fetchFeed(String feedUrl) throws IOException {
        return apiRetrofit.getFeedArticles(feedUrl)
                .map(feedArticlesResponse -> {
                    Feed feed = new Feed();

                    List<Article> articles = new ArrayList<>();

                    for (ArticleItem articleItem : feedArticlesResponse.getItems()) {
                        articles.add(new Article(
                                articleItem.getTitle(),
                                articleItem.getCanonicalUrl(),
                                "",
                                articleItem.getVisual().getUrl(),
                                articleItem.getPublished()
                        ));
                    }

                    feed.articles = articles;
                    return feed;
                });

    }

    @Override
    public Single<List<Article>> fetchArticles(String feedUrl) {
        return apiRetrofit.getFeedArticles(feedUrl)
                .map(feedArticlesResponse -> {
                    List<Article> articles = new ArrayList<>();
                    //TODO Обязательно проверять на null

                    for (ArticleItem articleItem : feedArticlesResponse.getItems()) {
                        articles.add(new Article(
                                articleItem.getTitle(),
                                articleItem.getCanonicalUrl(),
                                getContent(articleItem),
                                getImageLink(articleItem),
                                articleItem.getPublished()
                        ));
                    }
                    return articles;
                });
    }

    //TODO Вынести в парсер
    private String getContent(ArticleItem articleItem) {
        Timber.d("getContent " + articleItem.getTitle() + " " + articleItem.getId());
        if (articleItem.getContent() != null)
            return articleItem.getContent().getContent();

        if (articleItem.getSummary() != null) {
            String content = articleItem.getSummary().getContent();
            if (getImageLink(articleItem) != null) {
                String imageUrl = getImageLink(articleItem);
                content =  "<div><img src=\"" + imageUrl + "\" /></div>" + " " + content;
            }
            return content;
        }
        return "";
    }

    private String getImageLink(ArticleItem articleItem) {
        if (articleItem.getVisual() != null) {
            return articleItem.getVisual().getUrl();
        }
        return null;
    }
}
