package com.franmontiel.networkcalls;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.franmontiel.commons.navigation.NavigationHelper;

public class WebViewActivity extends AppCompatActivity {
    private static final String TAG = WebViewActivity.class.getSimpleName();

    public static final String EXTRA_TITLE = "EXTRA_TITLE";
    public static final String EXTRA_URL = "EXTRA_URL";

    public static void open(Fragment fromFragment, @NonNull String url, @Nullable String title) {
        Bundle extras = new Bundle();
        extras.putString(EXTRA_URL, url);
        if (title != null) {
            extras.putString(EXTRA_TITLE, title);
        }

        NavigationHelper.open(fromFragment, WebViewActivity.class, extras, null);
    }

    ProgressBar progressBar;
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        progressBar = (ProgressBar) findViewById(R.id.webProgressBar);
        webView = (WebView) findViewById(R.id.webview);

        if (getIntent().getExtras().containsKey(EXTRA_TITLE)) {
            if (getSupportActionBar() != null)
                getSupportActionBar().setTitle(getIntent().getExtras().getString(EXTRA_TITLE));
        }

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        webView.setWebViewClient(
                new WebViewClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        view.loadUrl(url);
                        return true;
                    }

                    @Override
                    public void onPageStarted(WebView view, String url, Bitmap favicon) {
                        super.onPageStarted(view, url, favicon);
                        progressBar.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onPageFinished(WebView view, String url) {
                        super.onPageFinished(view, url);
                        progressBar.setVisibility(View.GONE);
                        if (!getIntent().getExtras().containsKey(EXTRA_TITLE))
                            if (getSupportActionBar() != null)
                                getSupportActionBar().setTitle(view.getTitle());
                    }
                }
        );
        String url = getIntent().getExtras().getString(EXTRA_URL);
        if (!url.contains("http")) {
            url = "http://" + url;
        }



        webView.loadUrl(url);

    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
