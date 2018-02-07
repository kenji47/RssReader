package com.kenji1947.rssreader.fakes;

import com.kenji1947.rssreader.data.api.FeedApiService;
import com.kenji1947.rssreader.data.api.model.ApiArticle;
import com.kenji1947.rssreader.data.api.model.ApiConverter;
import com.kenji1947.rssreader.data.api.model.ApiConverterImpl;
import com.kenji1947.rssreader.data.api.model.ApiFeed;
import com.kenji1947.rssreader.domain.entities.Feed;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import io.reactivex.Single;
import timber.log.Timber;


/**
 * Created by chamber on 06.12.2017.
 */

public class FeedServiceFake implements FeedApiService {

    @Override
    public Single<Feed> fetchFeed(String feedUrl) {
        Timber.d("fetchFeed " + feedUrl);

        switch (AppSetup.feedApiServiceState) {

            case RETURN_NEW_ARTICLES: {
                Timber.d("RETURN_NEW_ARTICLES");
                return Single.just(DataLab.generateFeedWithNewArticles(feedUrl, 1000));
            }

            case RETURN_SAME_FEED: return Single.just(DataLab.generateFeed(feedUrl));

            case ERROR: return Single.error(new RuntimeException());

            default: return Single.just(DataLab.generateFeed(feedUrl));
        }
    }
























//TODO delete


    public static ApiConverter converter = new ApiConverterImpl();

    public final static String URL_XXX = "Xxx.com";
    public final static String URL_YYY = "Yyy.com";
    public final static String URL_ZZZ = "Zzz.com";

    private static ApiFeed xxx, yyy, zzz;

    static {
        xxx = generateFeed("Xxx");
        yyy = generateFeed("Yyy");
        zzz = generateFeed("Zzz");
    }

    public static Feed getXxx() {
        return converter.apiToDomain(FeedServiceFake.xxx);
    }

    public static Feed getYyy() {
        return converter.apiToDomain(FeedServiceFake.yyy);
    }

    public static Feed getZzz() {
        return converter.apiToDomain(FeedServiceFake.zzz);
    }

    private static ApiFeed generateFeed(String domain) {
        ApiFeed apiFeed = new ApiFeed(
                domain + " feed",
                domain + "/image",
                "pageLink/" + domain,
                "Description " + domain,
                domain + ".com",
                generateArticles(1, domain)
        );
        return apiFeed;
    }

    private static List<ApiArticle> generateArticles(int number, String domain) {
        List<ApiArticle> apiArticles = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            String articleTitle = "Article " + UUID.randomUUID().toString().substring(0, 3) + " " + domain;
            apiArticles.add(
                    new ApiArticle(articleTitle, "link/" + articleTitle, System.currentTimeMillis()
                    ));
        }
        return apiArticles;
    }



    //    @Override
//    public Single<ApiFeed> fetchFeed(String feedUrl) {
//
//        switch (feedUrl) {
//            case URL_XXX: return generateApiFeed(URL_XXX);
//            case URL_YYY: return generateApiFeed(URL_YYY);
//            case URL_ZZZ: return generateApiFeed(URL_ZZZ);
//            default: return generateApiFeed(URL_XXX);
//        }
//    }

//    @Override
//    public Single<Feed> fetchFeedNew(String feedUrl) {
//        return null;
//    }


    //create feed
//    @Override
//    public Single<ApiFeed> downloadNewFeed_Test(String feedUrl) throws NoNetworkException {
//        //return Single.error(new NoNetworkException("asd"));
//        //throw new NoNetworkException("asd");
//        //return Single.error(new RuntimeException());
//        switch (feedUrl) {
//            case URL_XXX: return Single.just(xxx);
//            case URL_YYY: return Single.just(yyy);
//            case URL_ZZZ: return Single.just(zzz);
//            default: return Single.just(xxx);
//        }
//    }

//    @Override
//    public Single<ApiFeed> updateFeed_Test(String feedUrl, boolean update) {
//        //выдать тот же фид
//        //сгенерировать одну новую статью
//        switch (feedUrl) {
//            case URL_XXX: return updateFeed(xxx, update);
//            case URL_YYY: return updateFeed(yyy, update);
//            case URL_ZZZ: return updateFeed(zzz, update);
//            default: return updateFeed(xxx, update);
//        }
//    }

//    private Single<ApiFeed> updateFeed(ApiFeed apiFeed, boolean update) {
//        ApiFeed apiFeedNew = new ApiFeed(
//                apiFeed.title,
//                apiFeed.imageUrl,
//                apiFeed.pageLink,
//                apiFeed.description,
//                apiFeed.url,
//                new ArrayList<>()
//        );
//
//        if (!update) {
//            return Single.just(apiFeedNew);
//        }
//        apiFeedNew.articles.addAll(generateArticlesForFeed(1, apiFeed.url));
//        return Single.just(apiFeed);
//    }




    private Single<ApiFeed> generateApiFeed(String feedUrl) {
        String feedTitle = feedUrl + " Feed " + UUID.randomUUID().toString().substring(0, 3);

        List<ApiArticle> articles = new ArrayList<>();
        String articleTitle;

        for (int i = 0; i < 3; i++) {
            articleTitle = "Article " + UUID.randomUUID().toString().substring(0, 3) + " " + feedTitle;
            articles.add(
                    new ApiArticle(articleTitle, feedUrl + "/" + articleTitle, System.currentTimeMillis()
                    ));
        }
        return Single.just(new ApiFeed(
                feedTitle,
                "image_url/" + feedTitle,
                "page_link/" + feedTitle,
                "Description " + feedTitle,
                feedUrl,
                articles)
        );
    }
}
