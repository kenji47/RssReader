package com.kenji1947.rssreader.domain.interactors.feed;

import com.kenji1947.rssreader.data.connectivity.ConnectivityReceiver;
import com.kenji1947.rssreader.domain.exceptions.FeedAlreadySubscribedException;
import com.kenji1947.rssreader.domain.exceptions.NoNetworkException;
import com.kenji1947.rssreader.domain.repository.FeedRepository;
import com.kenji1947.rssreader.domain.util.SchedulersProvider;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;

/**
 * Created by chamber on 18.12.2017.
 */

public class CreateNewFeedInteractor {
    private FeedRepository feedRepository;
    private ConnectivityReceiver connectivityReceiver;
    private SchedulersProvider schedulersProvider;

    @Inject
    public CreateNewFeedInteractor(FeedRepository feedRepository,
                                   ConnectivityReceiver connectivityReceiver,
                                   SchedulersProvider schedulersProvider) {
        this.feedRepository = feedRepository;
        this.connectivityReceiver = connectivityReceiver;
        this.schedulersProvider = schedulersProvider;
    }

    //TODO Валидация урла
    public Completable createNewFeed(String feedUrl) {
        return connectivityReceiver.isConnected()
                .flatMap(aBoolean -> aBoolean
                        ? feedRepository.feedExists(feedUrl)
                        : Single.error(new NoNetworkException()))
                .flatMapCompletable(aBoolean -> aBoolean
                        ? Completable.error(new FeedAlreadySubscribedException())
                        : feedRepository.createNewFeed(feedUrl));
    }
}
