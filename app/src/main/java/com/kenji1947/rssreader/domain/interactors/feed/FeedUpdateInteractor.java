package com.kenji1947.rssreader.domain.interactors.feed;

import com.kenji1947.rssreader.data.connectivity.ConnectivityReceiver;
import com.kenji1947.rssreader.domain.entities.Article;
import com.kenji1947.rssreader.domain.entities.Feed;
import com.kenji1947.rssreader.domain.exceptions.NoNetworkException;
import com.kenji1947.rssreader.domain.repository.FeedRepository;
import com.kenji1947.rssreader.domain.util.SchedulersProvider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import timber.log.Timber;

/**
 * Created by chamber on 10.12.2017.
 */

public class FeedUpdateInteractor {
    private FeedRepository feedRepository;
    private ConnectivityReceiver connectivityReceiver;
    private SchedulersProvider schedulersProvider;

    @Inject
    public FeedUpdateInteractor(FeedRepository feedRepository,
                                ConnectivityReceiver connectivityReceiver,
                                SchedulersProvider schedulersProvider) {
        this.feedRepository = feedRepository;
        this.connectivityReceiver = connectivityReceiver;
        this.schedulersProvider = schedulersProvider;
    }


    public Single<Feed> updateFeed(long id) {
        return connectivityReceiver.isConnected()
                .flatMap(aBoolean -> aBoolean
                        ? feedRepository.getFeed(id)
                        : Single.error(new NoNetworkException()))
                .flatMap(feed -> fetchOnlyNewArticlesForFeed(feed))
                .flatMap(feed -> feedRepository.saveArticlesForFeed(feed.id, feed.articles).toSingleDefault(feed))
                .flatMap(feed -> feedRepository.getFeed(id));
    }

    //Used in presenter
    public Single<List<Feed>> updateAllFeeds() {
        return connectivityReceiver.isConnected()
                .flatMap(aBoolean -> aBoolean
                        ? feedRepository.getFeeds()
                        : Single.error(new NoNetworkException()))
                .toObservable()
                .flatMap(Observable::fromIterable)
                .flatMap(feed -> fetchOnlyNewArticlesForFeed(feed).toObservable()) //catch error
                .flatMap(feed -> feedRepository
                        .saveArticlesForFeed(feed.id, feed.articles).toObservable()) //TODO Сохранять сразу весь список в транзакции
                .count()
                .flatMap(count -> feedRepository.getFeeds());
    }

    public Completable updateAllFeeds2() {
        return connectivityReceiver.isConnected()
                .flatMap(aBoolean -> aBoolean
                        ? feedRepository.getFeeds()
                        : Single.error(new NoNetworkException()))
                .toObservable()
                .flatMap(Observable::fromIterable)
                .flatMap(feed -> fetchOnlyNewArticlesForFeed(feed).toObservable()) //catch error
                .toList()
                .flatMapCompletable(feeds -> feedRepository.saveFeeds(feeds));
//                .flatMap(feed -> feedRepository
//                        .saveArticlesForFeed(feed.id, feed.articles).toObservable()) //TODO Сохранять сразу весь список в транзакции
//                .count()
//                .flatMap(count -> feedRepository.getFeeds());
    }

    //Used by Update Service
    public Single<Integer> updateAllFeedsAndGetNewArticlesCount() {
        Timber.d("updateAllFeedsAndGetUpdatedCount");
        return connectivityReceiver.isConnected()
                .flatMap(aBoolean -> aBoolean
                        ? feedRepository.getFeeds()
                        : Single.error(new NoNetworkException()))
                .toObservable()
                .flatMap(Observable::fromIterable)
                .flatMap(feed -> fetchOnlyNewArticlesForFeed(feed).toObservable())
                .flatMap(feed -> feedRepository
                        .saveArticlesForFeed(feed.id, feed.articles)
                        .toSingleDefault(feed.articles.size())
                        //.onErrorReturn(throwable -> {return 0;})
                        .toObservable())
                //.reduce((integer, integer2) -> integer + integer2).toSingle(); //TODO Not working
                .toList()
                .map(integers -> {
                    int updatedArticlesSummary = 0;
                    for (int updated : integers) {
                        updatedArticlesSummary += updated;
                    }
                    return updatedArticlesSummary;
                });
    }


    Single<Feed> fetchOnlyNewArticlesForFeed(Feed feedFromDb) {
        return feedRepository.fetchFeed(feedFromDb.url) //TODO Catch error
                .map(feedFromRemote -> {

//                    Set<Article> articleSet = new HashSet<>();
//
//                    Map<String, Article> articleMap = new HashMap<>();
//                    List<Article> all = new ArrayList<>();
//                    all.addAll(feedFromDb.articles);
//                    all.addAll(feedFromRemote.articles);
//
//                    for (Article article : all) {
//                        if (!articleMap.containsKey(article.link))
//                            articleMap.put(article.link, article);
//                    }

                    List<Article> articlesNew = new ArrayList<>();
                    for (Article article : feedFromRemote.articles) {
                        if (!isArticleContains(feedFromDb.articles, article)) {
                            articlesNew.add(article);
                        }
                    }
                    feedFromDb.articles = articlesNew; //TODO !!!
                    return feedFromDb;
                });
    }

    private boolean isArticleContains(List<Article> fromDb, Article articleFromRemote) {
        for (Article article : fromDb) {
            if (article.link.equals(articleFromRemote.link))
                return true;
        }
        return false;
    }


    //TODO Zip example
    private Single<Integer> pullNewArticlesForFeed(Feed feed) {
        return Single.zip(
                feedRepository.getFeed(feed.id),
                feedRepository.fetchFeed(feed.url),
                (feedFromDb, feedFromRemote) -> {
                    //return zipUpdateFeed(feedFromRemote, feedFromDb);
                    List<Article> articlesNew = new ArrayList<>();

                    for (Article article : feedFromRemote.articles) {
                        if (!feedFromDb.articles.contains(article)) {
                            articlesNew.add(article);
                        }
                    }
                    feedRepository.saveArticlesForFeed(feed.id, articlesNew);
                    return articlesNew.size();
                }
        );
    }
}
