package com.kenji1947.rssreader.presentation.article_detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.kenji1947.rssreader.App;
import com.kenji1947.rssreader.R;
import com.kenji1947.rssreader.di.presenter.ArticleDetailPresenterComponent;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chamber on 15.12.2017.
 */

public class ArticleDetailFragment extends MvpAppCompatFragment implements ArticleDetailView{
    public static final String TAG = ArticleDetailFragment.class.getSimpleName();
    private static final String ARG_ARTICLE_CONTENT_URL = "article_content_url";

    @BindView(R.id.webView_article_content) WebView webView_article_content;

    @InjectPresenter
    ArticleDetailPresenter presenter;
    @ProvidePresenter
    ArticleDetailPresenter providePresenter() {
        return ArticleDetailPresenterComponent.Initializer.init(App.INSTANCE.getAppComponent()).providePresenter();
    }

    public static ArticleDetailFragment newInstance(final String articleContentUrl) {
        final ArticleDetailFragment fragment = new ArticleDetailFragment();
        final Bundle arguments = new Bundle();
        arguments.putString(ARG_ARTICLE_CONTENT_URL, articleContentUrl);
        fragment.setArguments(arguments);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        final View fragmentView = inflater.inflate(R.layout.fragment_article_detail, container, false);
        ButterKnife.bind(this, fragmentView);
        return fragmentView;
    }

    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        extractArguments(getArguments());
    }

    private void extractArguments(final Bundle arguments) {
        setContentUrl(arguments.getString(ARG_ARTICLE_CONTENT_URL));
    }

    private void setupWebView(final String url) {
        WebSettings settings = webView_article_content.getSettings();
        webView_article_content.setWebViewClient(new ArticleWebClient(url));
        webView_article_content.getSettings().setJavaScriptEnabled(true);

        String contentFeedly = "<p>Huawei’s premier smartphone is let down by bad software</p> <p>We’re less than two months into 2018, and so far, the year hasn’t been very good for Huawei. </p>\n<p>The Chinese company had hoped that this would be the year when it would finally be able to break into the US market and compete with Apple and Samsung. But days before it was due to make its big partnership announcement with AT&amp;T, the <a href=\"http://www.theverge.com/2018/1/8/16865592/att-huawei-mate-10-pro-ces-deal-off\">deal fell apart</a> due to pressure from the US government. A rumored deal with Verizon that was slated for later in the year <a href=\"https://www.theverge.com/2018/1/30/16950122/verizon-refuses-huawei-phone-att-espionage-cybersecurity-fears?mid=1\">reportedly fell apart for similar reasons</a>. Then, last week, the heads of the FBI, CIA, and NSA told a Senate Intelligence Committee that American citizens <a href=\"https://www.theverge.com/2018/2/14/17011246/huawei-phones-safe-us-intelligence-chief-fears\">shouldn’t use Huawei products or services</a> on account of suspected ties to the Chinese government.</p>\n<p>The phone that Huawei hoped would carry it into the US market is its flagship <a href=\"https://www.theverge.com/circuitbreaker/2017/10/16/16481242/huawei-mate-10-pro-announcement-specs-price-ai-features\">Mate 10 Pro</a>, released in other parts of the world last fall. The Mate 10 Pro has been the focus of all Huawei’s marketing efforts in Western markets, from influencer partnerships to billboards to ad placements on popular websites (including <em>The Verge, </em>which our editorial team has had zero involvement in). Though Huawei lost its carrier partnerships and clearly doesn’t have any fans in the American government, it started selling the Mate 10 Pro unlocked in the US this week, and it can now be purchased for $799 from Amazon, Best Buy, and other retailers.</p>\n<p>The Mate 10 Pro is a flagship phone with flagship specs and flagship pricing. But there are a lot of flagship phones with flagship specs and flagship pricing here in the US, which means that the Mate 10 Pro is playing in an intensely competitive field. Without a large carrier partner and its massive marketing budget and retail footprint, it’s not likely that the Mate 10 Pro will be a popular phone in the US. But it got me wondering: now that Americans can buy it, is the Mate 10 Pro a phone anyone should buy, regardless of what the government says? Even if Huawei had been able to get it on AT&amp;T or Verizon or any other US carrier, would anyone have bought it?</p>\n<div> <figure>\n<img alt=\" \" src=\"https://cdn.vox-cdn.com/thumbor/SyTsQW_XA3zgPzyOkWHbn08v9UM=/400x0/filters:no_upscale()/cdn.vox-cdn.com/uploads/chorus_asset/file/10236185/akrales_180215_2287_0186.jpg\">\n</figure>\n</div>\n<p>In terms of hardware, the Mate 10 Pro has almost everything you’d expect from a flagship phone launched within the past six months. Its metal-and-glass body has excellent build quality, polished finishes, and tight tolerances. It’s water resistant. Its display has a now-fashionable wider 18:9 aspect ratio, with minimal bezels. It has stereo speakers and a dual rear camera system.</p>\n<div><aside><q>Few things are missing from the Mate 10 Pro’s spec sheet</q></aside></div>\n<p>Inside is Huawei’s own top-of-the-line Kirin 970 processor, 6GB of RAM, and 128GB of storage. There’s a huge, quick-charging 4,000mAh battery that can keep it going all day and then some. The phone even has an IR blaster so you can use it to control your TV or other electronics.</p>\n<p>Like many high-end phones, it doesn’t have a headphone jack, though Huawei does provide both EarPod knockoff headphones and a USB-C to 3.5mm adapter in the box. It also doesn’t have wireless charging, despite the glass back.</p>\n<p>But those are fairly minor complaints. If there’s a hardware feature you want, the Mate 10 Pro probably has it.</p>\n<div><div>\n<div> <figure>\n<img alt=\" \" src=\"https://cdn.vox-cdn.com/thumbor/g_VcSu5IacBUqYYRtFvsM1iDUfk=/400x0/filters:no_upscale()/cdn.vox-cdn.com/uploads/chorus_asset/file/10236193/akrales_180215_2287_0143.jpg\">\n</figure>\n</div>\n<div> <figure>\n<img alt=\" \" src=\"https://cdn.vox-cdn.com/thumbor/y_7ExDvkg8UZgmTy1738XB_r44U=/400x0/filters:no_upscale()/cdn.vox-cdn.com/uploads/chorus_asset/file/10236189/akrales_180215_2287_0127.jpg\">\n</figure>\n</div>\n<div> <figure>\n<img alt=\" \" src=\"https://cdn.vox-cdn.com/thumbor/A_9Y5dXb-VFD5lMuIKDFo8WDWYM=/400x0/filters:no_upscale()/cdn.vox-cdn.com/uploads/chorus_asset/file/10236179/akrales_180215_2287_0081.jpg\">\n</figure>\n</div>\n<div> <figure>\n<img alt=\" \" src=\"https://cdn.vox-cdn.com/thumbor/0xORjc4sdbSzm9Tg_UYDQGTRqIo=/400x0/filters:no_upscale()/cdn.vox-cdn.com/uploads/chorus_asset/file/10236183/akrales_180215_2287_0084.jpg\">\n</figure>\n</div>\n</div></div>\n<p>For the most part, that hardware works really well. I found the Mate 10 Pro to be as fast as any recent Android flagship. The hardware is nice to hold, and thanks to that enormous battery, it has terrific battery life. I didn’t find any obvious annoyances, like a poorly placed fingerprint sensor, an extra button for a voice assistant you’ll never use, or a notch that covers up part of the top of the screen, either.</p>\n<p>The 6-inch display has a lower resolution than many of its peers, at “only” 1080p, but I don’t think it’s a cause for concern, as both text and images are sharp. It’s OLED, which provides deep blacks and vibrant colors. It has excellent viewing angles and gets bright enough for use outdoors while I’m walking to the office. And, like a lot of newer Android phones, it has an always-on mode that will show you the time and date without having to pick up the phone.</p>\n<p>Perhaps my favorite feature is the Mate 10 Pro’s speakers, which are loud, clear, and automatically switch from mono to stereo when you turn the phone from portrait to landscape orientation.</p>\n<div> <figure>\n<img alt=\" \" src=\"https://cdn.vox-cdn.com/thumbor/4438XEndR-Wmo-FD_NmTy0Xn7Zo=/400x0/filters:no_upscale()/cdn.vox-cdn.com/uploads/chorus_asset/file/10236181/akrales_180215_2287_0068.jpg\">\n</figure>\n</div>\n<p>What often separates top-tier phones from the rest are the cameras, and while the Leica-branded dual rear camera isn’t as good as a Pixel 2 or an iPhone X camera, it’s certainly a capable shooter. It has fast focus and performance, preserves a good amount of detail, and produces pleasing, if a little bit oversaturated, colors.</p>\n<div><aside><q>The Mate 10 Pro’s camera takes lovely black-and-white images</q></aside></div>\n<p>The camera app has a ton of features and options, including manual controls, various portrait and fake blurring modes, and automatic scene functions that attempt to optimize the camera for whatever your subject is. Point the camera at a person, and it quickly switches to portrait mode; point it at a plate of food, and it switches to the food preset, and so on. It’s certainly easier to use than manually changing modes for each subject.</p>\n<p>My favorite camera mode is the monochrome option, which uses data from one of the Mate 10 Pro’s camera sensors to produce some of the nicest black-and-white images I’ve ever seen from a smartphone. Unlike the iPhone X’s studio lighting mode, it doesn’t overreach and try to duplicate a studio effect; it just produces nice-looking black and whites. The images are rich and contrasty, with lovely tones that I find hard to replicate with even my dedicated stills camera.</p>\n<div> <figure>\n<img alt=\" \" src=\"https://cdn.vox-cdn.com/thumbor/Rk9qu4-bwKfrLhMpAIU4TzRGzRQ=/400x0/filters:no_upscale()/cdn.vox-cdn.com/uploads/chorus_asset/file/10236175/akrales_180215_2287_0044.jpg\">\n</figure>\n</div>\n<p>But as with any phone, the hardware is only half the story, and software is generally what makes or breaks an experience. In the case of the Mate 10 Pro, Huawei’s software breaks it.</p>\n<p>The Mate 10 Pro runs Android 8.0 Oreo with Huawei’s EMUI user interface on top of it, and it’s wildly different from the version of Android you find on a Pixel or other modern phones. The best way I can describe it is a poorly made knockoff of iOS.</p>\n<p>Huawei has customized almost everything about Android, and often, not in a good way. For example, you can’t expand notifications on the lock screen, so deleting an email or marking a to-do complete can’t be done without unlocking the phone. The settings menu, messaging app, and share sheet have been lifted right out of iOS and shoehorned onto Android. For some reason, most of the apps in the share sheet are hidden by default, forcing extra taps and swipes just to see them all.</p>\n<div> <figure>\n<img alt=\" \" src=\"https://cdn.vox-cdn.com/thumbor/GeiILsTY80aYwUVwI2R_mCkFJDc=/400x0/filters:no_upscale()/cdn.vox-cdn.com/uploads/chorus_asset/file/10242081/huawei_mate10pro_software.jpg\">\n<figcaption><em>The story of Huawei’s software is poor design mixed with iOS influences.</em></figcaption>\n</figure>\n</div>\n<p>Sure, you can change some of these things by downloading a different launcher or messaging app, but you can’t change things like the quick settings menu that doesn’t match the rest of the notification shade or that awful share sheet. You can’t turn on an option to make notifications on the lock screen more useful. On top of that, there are frustrating bugs — even when I downloaded another launcher and attempted to use that, the Mate 10 would frequently reset itself to Huawei’s own launcher.</p>\n<div><aside><q>The Mate 10 Pro’s software is bad enough to prevent recommending the phone</q></aside></div>\n<p>This isn’t the kind of software experience anyone should have on an $800 phone, especially when there are already so many better options available. It’s bad enough that I honestly think nobody should buy the Mate 10 Pro because of its software, especially not at this price. You can get all of the hardware performance with none of the software headaches from any number of other Android phones available unlocked or through carriers.</p>\n<p>That’s just on an unlocked version of the phone, too. I’m curious what would have happened had Huawei been able to launch this phone with AT&amp;T, which inevitably would have loaded its own bloatware on top of it. Huawei doesn’t have the clout of Apple, Google, or even Samsung when it comes to negotiating with carriers, and it certainly would have had to bend to their demands for customization. (Just look at the <a href=\"https://www.theverge.com/2017/11/17/16667508/zte-axon-m-dual-screen-smartphone-review\">ZTE Axon M</a>, which is exclusive to AT&amp;T and loaded with as much bloatware as AT&amp;T could manage to cram on it.) In this regard, having a carrier deal probably wouldn’t have made much of a difference for Huawei. It’s hard to see anyone buying this phone as it is right now, carrier support or not.</p>\n<div> <figure>\n<img alt=\" \" src=\"https://cdn.vox-cdn.com/thumbor/qOmm5eSMz5-QSegK3Op6dHlfRaA=/400x0/filters:no_upscale()/cdn.vox-cdn.com/uploads/chorus_asset/file/10236177/akrales_180215_2287_0040.jpg\">\n</figure>\n</div>\n<p>I can’t deny that Huawei has done a lot right with the Mate 10 Pro’s hardware, even if it doesn’t necessarily break any new ground. It’s well-made, has all of the top-tier features, and is just as fast as any other phone in this class. If it wasn’t so expensive, I could perhaps excuse some of the software issues. But this is a premium-priced phone, and you should get a premium experience, which the Mate 10 Pro doesn’t provide.</p>\n<p>Following the collapse of Huawei’s deal with AT&amp;T, Richard Yu, the CEO of Huawei’s consumer products division, <a href=\"http://www.theverge.com/2018/1/9/16871538/huawei-ces-2018-event-ceo-richard-yu-keynote-speech\">made the case</a> that because the US government meddled in its business, American consumers are being robbed of the widest choice of smartphones that people in others parts of the world enjoy. He’s certainly not wrong; more choice is generally considered a good thing, as it provides more options for us and promotes more competition among manufacturers.</p>\n<p>But it’s hard for me to see anyone taking advantage of that choice even if they had it and opted to spend $800 for the Mate 10 Pro instead of a phone from Google, Samsung, or even Apple. If Huawei really wants to play in the US, it not only has to work with carriers and make the US government happy, but it also has to make and sell a phone that people will want to buy here — and frankly, the Mate 10 Pro isn’t it.</p>\n<div><div data-anthem-component=\"scorecard:897915\"></div></div>\n<p></p>";


//        String contentOriginal = "&lt;img alt=\"\" src=\"https://cdn.vox-cdn.com/thumbor/l1hjShzLh4vxdTztmijpFuqt98M=/0x0:2040x1360/1310x873/cdn.vox-cdn.com/uploads/chorus_image/image/58740231/eater1_2040.0.0.jpg\" /&gt;\n" +
//                "\n" +
//                "\n" +
//                "\n" +
//                "  &lt;p id=\"cMSwjX\"&gt;Microsoft and Google have been bitter rivals for at least a decade, and the pair have had several &lt;a href=\"https://www.theverge.com/2017/10/19/16502624/microsoft-google-security-patches-chrome-bug\"&gt;disagreements over security vulnerability disclosure&lt;/a&gt; in recent years. Google is stoking those disagreements again this week by disclosing a Microsoft Edge security flaw before a patch is available. &lt;a href=\"https://www.neowin.net/news/google-exposes-security-flaw-in-microsoft-edge\"&gt;&lt;em&gt;Neowin&lt;/em&gt;&lt;/a&gt; spotted that &lt;a href=\"https://bugs.chromium.org/p/project-zero/issues/detail?id=1435\"&gt;Google disclosed the security flaw to Microsoft back in November&lt;/a&gt;, and the company provided 90 days for Microsoft to fix it before going public as it’s rated “medium” in terms of severity.&lt;/p&gt;\n" +
//                "&lt;p id=\"99nVwO\"&gt;Google also provided Microsoft with an additional 14-day grace period to have a fix available for its monthly Patch Tuesday release in February, but Microsoft missed this goal because “the fix is more complex than initially anticipated.” It’s not clear when Microsoft will have a fix available, and the Google engineer responsible for reporting the security flaw says because of the complexity of the fix Microsoft “do not yet have a fixed date set as of yet.”&lt;/p&gt;\n" +
//                "&lt;div class=\"c-float-right\"&gt;&lt;aside id=\"ZSkkzn\"&gt;&lt;q&gt;Google has a history of disclosing Microsoft bugs before patches are ready&lt;/q&gt;&lt;/aside&gt;&lt;/div&gt;\n" +
//                "&lt;p id=\"iO3pwi\"&gt;The public disclosure will likely anger Microsoft, once again. The software giant &lt;a href=\"https://www.theverge.com/2017/10/19/16502624/microsoft-google-security-patches-chrome-bug\"&gt;hit back at Google’s approach to security patches&lt;/a&gt; last October, after discovering a Chrome flaw and “responsibly” disclosed it to Google so the company had enough time to patch. At the heart of the issue is whether Google’s policy to disclose after 90 days without a patch is reasonable. Google makes exceptions to this hard rule, with grace periods, and can even disclose much sooner if the vulnerability is being actively exploited. &lt;a href=\"https://www.theverge.com/2016/10/31/13481502/windows-vulnerability-sandbox-google-microsoft-disclosure\"&gt;Google disclosed a major Windows bug back in 2016&lt;/a&gt; just 10 days after reporting it to Microsoft, and the company has &lt;a href=\"https://www.theverge.com/2013/5/23/4358400/google-engineer-bashes-microsoft-discloses-windows-flaw\"&gt;revealed zero-day bugs in Windows&lt;/a&gt; in the past before patches are available.&lt;/p&gt;\n" +
//                "&lt;p id=\"GRul20\"&gt;Two big and obvious exceptions to Google’s security disclosure rules were the recent &lt;a href=\"https://www.theverge.com/2018/1/4/16850516/intel-meltdown-spectre-bug-patch-cpu-security-flaw-news\"&gt;Meltdown and Spectre bugs&lt;/a&gt;. Google engineers discovered the CPU flaws and Intel, AMD, and others had around six months to fix the problems before the flaws were publicly disclosed earlier this year. Chrome OS and Android devices were also affected by the processor flaws, along with Windows, Linux, macOS, and iOS.&lt;/p&gt;\n" +
//                "&lt;p id=\"LTwK54\"&gt;Google wants the industry to adopt its aggressive disclosure policies, but Microsoft has so far resisted rather publicly. This latest episode isn’t as critical as some of the past disclosures, but it will likely reignite the debate over whether Google, a company with competitive commercial interests, should be leading the way security flaws in rival operating systems are disclosed in the public interest.&lt;/p&gt;";


        webView_article_content.loadData(decodeFeedlyFeed(contentFeedly),"text/html; charset=utf-8", "utf-8");
        settings.setJavaScriptEnabled(true);
        settings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
    }

    private void setupWebView() {
        //        webView_article_content.setPadding(0, 0, 0, 0);
//        webView_article_content.setInitialScale(100);


        //webView_article_content.addJavascriptInterface(this, "");

        //settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        //settings.setLoadWithOverviewMode(true);
        //settings.setUseWideViewPort(true);
        //webView_article_content.loadUrl(url);
    }

    private String decodeFeedlyFeed(String html) {

//        html = html.replaceAll("\"", "\"");
//        html = html.replaceAll("\n", "");
        try {
            html = URLDecoder.decode(html, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //html = Html.fromHtml(html).toString(); Removes images
        return html;
    }

    private String decodeOriginalFeed(String html) {
        html = html.replaceAll("&lt;", "<");
        html = html.replaceAll("&gt;", ">");

        //html = Html.fromHtml(html).toString(); //Working

        return html;
    }

    @JavascriptInterface
    public void resize(final float height) {
        ArticleDetailFragment.this.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                webView_article_content.setLayoutParams(new LinearLayout.LayoutParams(getResources()
                        .getDisplayMetrics().widthPixels, (int) (height * getResources().getDisplayMetrics().density)));
            }
        });
    }
    public void setContentUrl(final String url) {
        setupWebView(url);
    }
}
