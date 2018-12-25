package com.moneytap.wikisearch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.moneytap.utils.Utils;

public class WebviewActivity extends AppCompatActivity {
    final String TAG = WebviewActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        Log.d(TAG, "onCreate: " +url);
        if(url!=null & Utils.isNetworkAvailable(WikiApplication.getAppContext())){
            WebView webView = findViewById(R.id.webview);
            webView.setWebViewClient(new WebViewClient(){
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    view.loadUrl(url);
                    return true;
                }
            });
            webView.loadUrl(url);
        }else{
            Toast.makeText(this,getResources().getString(R.string.webview_error_message),Toast.LENGTH_SHORT).show();
        }

    }
}
