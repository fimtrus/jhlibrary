package com.jhlibrary.twitter.activity;

import twitter4j.TwitterException;
import twitter4j.auth.AccessToken;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.jhlibrary.R;
import com.jhlibrary.constant.Key;

public class TwitterWebViewActivity extends Activity {

	private WebView mWebView;
	private Intent mIntent;
	private ProgressBar mProgressBar;

	/** 생성. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_twitter_webview);
		initialize();
	}

	private void initialize() {
		initializeFields();
		initializeListeners();
		initializeView();
	}

	private void initializeFields() {

		mIntent = getIntent();
		mProgressBar = (ProgressBar) findViewById(R.id.progressBar1);
		// WebView에서 트위터 인증 페이지 로드
		mWebView = (WebView) findViewById(R.id.webview);
		String url = mIntent.getStringExtra(Key.OAUTH_URL);
		mWebView.loadUrl(url);
		mWebView.getSettings().setJavaScriptEnabled(true);
		mWebView.addJavascriptInterface(new MyJavascriptInterface(), "HTMLOUT");
		mWebView.setWebChromeClient(new WebChromeClient() {

			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				// TODO Auto-generated method stub
				super.onProgressChanged(view, newProgress);
				if (newProgress >= 100) {
					mProgressBar.setVisibility(View.GONE);
				} else {
					if (mProgressBar.getVisibility() == View.GONE) {
						mProgressBar.setVisibility(View.VISIBLE);
					}
					mProgressBar.setProgress(newProgress);
				}
			}
			
		});
		mWebView.setWebViewClient(new WebViewClient() {
			// @Override
			// // Page가 redirect 될 때마다 호출된다.
			// public void onLoadResource(WebView view, String url) {
			// super.onLoadResource(view, url);
			// }

			@Override
			public void onPageFinished(WebView view, String url) {
				// TODO Auto-generated method stub
				super.onPageFinished(view, url);
				// App 등록시에 등록한 Callback URL
				// if (url.startsWith("https://api.twitter.com/oauth/authorize")
				// == true) {
				// url에 pincode 정보가 담겨있다. 이를 얻어오면 된다.
				// int idx = url.indexOf("&amp;oauth_verifier=");
				// String oauth_verifier = url.substring(idx +
				// "&amp;oauth_verifier".length() + 1, url.length());
				// i.putExtra(Key.OAUTH_VERIFIER, oauth_verifier);
				// setResult(RESULT_OK, i);
				// finish();
				view.loadUrl("javascript:window.HTMLOUT.showHTML(document.getElementsByTagName('code')[0].innerHTML);");
				// }
			}
		});
	}

	private void initializeView() {
	}

	private void initializeListeners() {
		// 버튼 리스너 등록

	}

	class MyJavascriptInterface {
		public void showHTML(String pin) throws TwitterException {
			if (pin.length() > 0) {
				Log.i("Javascript Interface", "pin = " + pin);
				mIntent.putExtra(Key.OAUTH_VERIFIER, pin);
				setResult(RESULT_OK, mIntent);
				finish();
				// AccessToken accessToken =
				// twitter.getOAuthAccessToken(requestToken, pin);
				Log.i("Javascript Interface", "AccessToken = " + pin);
			} else {
				Log.i("Javascript Interface", "get pin failed...");
			}
		}
	}
	// webview.addJavascriptInterface(new MyJavascriptInterface(), "HTMLOUT");
}