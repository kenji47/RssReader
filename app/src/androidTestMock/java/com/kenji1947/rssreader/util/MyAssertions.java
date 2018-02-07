package com.kenji1947.rssreader.util;

import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.ViewAssertion;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.kenji1947.rssreader.R;
import com.kenji1947.rssreader.domain.entities.Article;
import com.kenji1947.rssreader.domain.entities.Feed;
import com.kenji1947.rssreader.presentation.article_list.ArticleListAdapter;
import com.kenji1947.rssreader.presentation.feed_list.FeedListAdapter;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Created by chamber on 21.12.2017.
 */

public class MyAssertions {


    public static WithArticleListAssertion checkArticleList(List<Article> articleList) {
        return new WithArticleListAssertion(articleList);
    }

    public static class WithArticleListAssertion {
        private List<Article> articleList;

        public WithArticleListAssertion(List<Article> articleList) {
            this.articleList = articleList;
        }

        public ViewAssertion assertArticleList() {
            return new ViewAssertion() {
                @Override
                public void check(View view, NoMatchingViewException noViewFoundException) {
                    if (noViewFoundException != null) {
                        throw noViewFoundException;
                    }
                    RecyclerView recyclerView = (RecyclerView) view;
                    assertThat(articleList.size(), is(recyclerView.getAdapter().getItemCount()));

                    for (int i = 0; i < articleList.size(); i++) {
                        ArticleListAdapter.ArticleHolder holder =
                                (ArticleListAdapter.ArticleHolder) recyclerView.findViewHolderForAdapterPosition(i);
                        if (holder != null) {
                            Article article = articleList.get(i);

                            assertThat(article.title, is(holder.textView_article_title.getText().toString()));
                            assertThat(article.publicationDate + "", is(holder.textView_article_date.getText().toString()));

                            int actualFavDrawableId = (int) holder.imageView_article_favourite_indicator.getTag();
                            int excpectedFavDrawableId = article.isFavourite
                                    ? R.drawable.ic_favorite
                                    : R.drawable.ic_not_favorite;
                            assertThat(actualFavDrawableId, is(excpectedFavDrawableId));

                            int actualIsNewVisibility = holder.textView_article_new_indicator.getVisibility();
                            int excpectedIsNewVisibility = article.isNew ? View.VISIBLE : View.GONE;
                            assertThat(actualIsNewVisibility, is(excpectedIsNewVisibility));

                        }
                    }

                }
            };
        }
    }
    public static WithFeedListAssertion checkFeedList(List<Feed> feedList) {
        return new WithFeedListAssertion(feedList);
    }
    public static class WithFeedListAssertion {
        private List<Feed> feedList;

        public WithFeedListAssertion(List<Feed> feedList) {
            this.feedList = feedList;
        }

        public ViewAssertion assertFeedList() {
            return new ViewAssertion() {
                @Override
                public void check(View view, NoMatchingViewException noViewFoundException) {
                    if (noViewFoundException != null) {
                        throw noViewFoundException;
                    }
                    RecyclerView recyclerView = (RecyclerView) view;
                    assertThat(feedList.size(), is(recyclerView.getAdapter().getItemCount()));

                    for (int i = 0; i < feedList.size(); i++) {
                        FeedListAdapter.FeedViewHolder holder =
                                (FeedListAdapter.FeedViewHolder) recyclerView.findViewHolderForAdapterPosition(i);
                        if (holder != null) {
                            Feed feed = feedList.get(i);
                            assertThat(feed.title, is(holder.feedTitle.getText().toString()));
                            assertThat(feed.description, is(holder.feedDescription.getText().toString()));
                        }
                    }

                }
            };
        }
    }

    public static ViewAssertion checkToolbarTitle(String title) {
        return (view, noViewFoundException) -> {
            if (view instanceof Toolbar) {
                Toolbar toolbar = (Toolbar) view;
                String actualTitle = toolbar.getTitle().toString();
                assertThat(actualTitle, is(title));
            }
        };
    }

}
