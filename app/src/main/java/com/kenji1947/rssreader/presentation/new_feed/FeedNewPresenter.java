package com.kenji1947.rssreader.presentation.new_feed;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.kenji1947.rssreader.data.worker.error_handler.ErrorHandler;
import com.kenji1947.rssreader.domain.interactors.feed.CreateNewFeedInteractor;
import com.kenji1947.rssreader.domain.util.SchedulersProvider;
import com.kenji1947.rssreader.presentation.Screens;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import ru.terrakok.cicerone.Router;

/**
 * Created by chamber on 13.12.2017.
 */

@InjectViewState
public class FeedNewPresenter extends MvpPresenter<FeedNewView> {

    private SchedulersProvider schedulersProvider;
    private CreateNewFeedInteractor createFeedInteractor;
    private ErrorHandler errorHandler;
    private Router router;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Inject
    public FeedNewPresenter(CreateNewFeedInteractor createFeedInteractor,
                            SchedulersProvider schedulersProvider,
                            ErrorHandler errorHandler,
                            Router router) {
        this.schedulersProvider = schedulersProvider;
        this.createFeedInteractor = createFeedInteractor;
        this.errorHandler = errorHandler;
        this.router = router;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }

    //TODO Ошибки:
    // урл не валиден
    // нет интернета
    // фид уже есть в бд
    // нет интернета во время запроса
    // ресурса с данным урл не существует

    public void addNewFeed(String feedUrl) {
        compositeDisposable.add(createFeedInteractor
                .createNewFeed(feedUrl)
                .observeOn(schedulersProvider.getMain())
                .doOnSubscribe(disposable -> {getViewState().showProgress(true);})
//                .doAfterTerminate(() -> {getViewState().showProgress(false);})
                .subscribe(this::onCreateFeedCompletion, this::onCreateFeedError)
        );
    }

    private void onCreateFeedCompletion() {
        getViewState().showProgress(false);
        getViewState().closeDialog();

        //TODO Not used
        //router.exitWithResult(Screens.RESULT_NEW_FEED_SCREEN_UPDATE, true);
    }

    private void onCreateFeedError(Throwable throwable) {
        getViewState().showProgress(false);
        errorHandler.handleErrorScreenNewFeed(throwable, s -> getViewState().showMessage(s));
    }

    //TODO Not used
    public void back() {
        router.backTo(Screens.FEED_LIST_SCREEN);
    }
}
