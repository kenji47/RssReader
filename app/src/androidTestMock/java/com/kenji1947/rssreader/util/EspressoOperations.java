package com.kenji1947.rssreader.util;

import android.content.Intent;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.v7.app.AlertDialog;

import com.kenji1947.rssreader.R;
import com.kenji1947.rssreader.domain.entities.Article;
import com.kenji1947.rssreader.domain.entities.Feed;

import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by chamber on 20.12.2017.
 */

public class EspressoOperations {

    public static void startActivityDefaultIntent(ActivityTestRule activityTestRule) {
        //activityTestRule.launchActivity(new Intent(Intent.ACTION_MAIN));
        activityTestRule.launchActivity(new Intent());
    }

    public static void addNewFeedThroughDialog(String url) {
        onView(withId(R.id.fab)).perform(click());
        onView(withId(R.id.textInputEditText_feed_url)).perform(typeText(url));
        onView(withText(R.string.dialog_new_feed_button_positive)).perform(click());
    }

    public static String getString(int res, ActivityTestRule activityTestRule) {
        return activityTestRule.getActivity().getString(res);
    }
    //---

    public static void checkFeedListRecyclerView(List<Feed> feedList) {
        onView(withId(R.id.recyclerView_feeds))
                .check(MyAssertions.checkFeedList(feedList).assertFeedList());
    }

    public static void checkArticleListRecyclerView(List<Article> articleList) {
        onView(withId(R.id.recyclerView_articles))
                .check(MyAssertions.checkArticleList(articleList).assertArticleList());
    }


    //Old
    public static void checkFeedListRecyclerViewByScrollToHolder(List<Feed> feedList) {
        for (Feed feed : feedList) {
            onView(withId(R.id.recyclerView_feeds))
                    .perform(RecyclerViewActions.scrollToHolder(MyMatcher.holderFeedListWithFeed(feed)));
        }
    }

    public static void checkArticleListRecyclerViewByScrollToHolder(List<Article> articleList) {
        for (Article article : articleList) {
            onView(withId(R.id.recyclerView_articles))
                    .perform(RecyclerViewActions.scrollToHolder(MyMatcher.holderArticleListWithArticle(article)));
        }
    }
}
