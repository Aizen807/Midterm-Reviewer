package com.aizen807.midtermreviewer;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Insets;
import android.os.Build;
import android.os.Bundle;
import android.print.PrintAttributes;
import android.print.PrintManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.view.View;
import android.view.WindowInsets;
import android.widget.FrameLayout;

public class MainActivity extends Activity {
    private static final String START_PAGE = "file:///android_asset/index.html";
    private WebView reviewer;

    private final class ReviewerBridge {
        @JavascriptInterface
        public void printPage() {
            runOnUiThread(MainActivity.this::printCurrentPage);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(Color.rgb(246, 241, 230));
        getWindow().setNavigationBarColor(Color.rgb(246, 241, 230));
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR | View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
        }

        FrameLayout root = new FrameLayout(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            root.setOnApplyWindowInsetsListener((view, windowInsets) -> {
                Insets bars = windowInsets.getInsets(WindowInsets.Type.systemBars());
                view.setPadding(bars.left, bars.top, bars.right, bars.bottom);
                return windowInsets;
            });
        } else {
            root.setFitsSystemWindows(true);
        }

        reviewer = new WebView(this);
        reviewer.setBackgroundColor(Color.rgb(246, 241, 230));
        reviewer.setOverScrollMode(View.OVER_SCROLL_NEVER);

        WebSettings settings = reviewer.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setAllowFileAccess(true);
        settings.setAllowContentAccess(false);
        settings.setAllowFileAccessFromFileURLs(false);
        settings.setAllowUniversalAccessFromFileURLs(false);
        settings.setBuiltInZoomControls(false);
        settings.setDisplayZoomControls(false);
        settings.setSupportZoom(false);
        settings.setLoadsImagesAutomatically(true);

        reviewer.addJavascriptInterface(new ReviewerBridge(), "ReviewerBridge");
        reviewer.setWebChromeClient(new WebChromeClient());
        reviewer.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (START_PAGE.equals(url)) {
                    view.evaluateJavascript(
                            "window.print=function(){if(window.ReviewerBridge){window.ReviewerBridge.printPage();}};",
                            null);
                }
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return request.isForMainFrame()
                        && !request.getUrl().toString().startsWith("file:///android_asset/");
            }

            @SuppressWarnings("deprecation")
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return !url.startsWith("file:///android_asset/");
            }
        });

        root.addView(reviewer, new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        setContentView(root);

        if (savedInstanceState == null || reviewer.restoreState(savedInstanceState) == null) {
            reviewer.loadUrl(START_PAGE);
        }
    }

    private void printCurrentPage() {
        if (reviewer == null || Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return;
        }
        PrintManager printManager = (PrintManager) getSystemService(Context.PRINT_SERVICE);
        if (printManager == null) {
            return;
        }
        PrintAttributes attributes = new PrintAttributes.Builder()
                .setMediaSize(PrintAttributes.MediaSize.ISO_A4)
                .setResolution(new PrintAttributes.Resolution(
                        "midterm_reviewer", "Midterm Reviewer", 300, 300))
                .build();
        printManager.print(
                "Midterm Reviewer",
                reviewer.createPrintDocumentAdapter("Midterm Reviewer"),
                attributes);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onBackPressed() {
        if (reviewer == null) {
            super.onBackPressed();
            return;
        }
        reviewer.evaluateJavascript(
                "(function(){var hub=document.getElementById('hub-view');"
                        + "if(!hub||getComputedStyle(hub).display!=='none'){return 'exit';}"
                        + "if(typeof showHub==='function'){showHub();return 'hub';}return 'exit';})()",
                result -> {
                    if ("\"exit\"".equals(result)) {
                        finish();
                    }
                });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (reviewer != null) {
            reviewer.saveState(outState);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onPause() {
        if (reviewer != null) {
            reviewer.onPause();
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (reviewer != null) {
            reviewer.onResume();
        }
    }

    @Override
    protected void onDestroy() {
        if (reviewer != null) {
            reviewer.removeJavascriptInterface("ReviewerBridge");
            reviewer.stopLoading();
            reviewer.loadUrl("about:blank");
            reviewer.removeAllViews();
            reviewer.destroy();
            reviewer = null;
        }
        super.onDestroy();
    }
}
