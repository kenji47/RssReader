package com.kenji1947.rssreader.di.presenter;

import com.kenji1947.rssreader.di.application.AppComponent;
import com.kenji1947.rssreader.presentation.article_detail.ArticleDetailPresenter;

import dagger.Component;

/**
 * Created by chamber on 15.12.2017.
 */

@PresenterScope
@Component(dependencies = AppComponent.class)
public interface ArticleDetailPresenterComponent {
    ArticleDetailPresenter providePresenter();

    final class Initializer {

        public static ArticleDetailPresenterComponent init(AppComponent appComponent) {
            return DaggerArticleDetailPresenterComponent.builder()
                    .appComponent(appComponent)
                    .build();
        }

        private Initializer() {
        }
    }
}
