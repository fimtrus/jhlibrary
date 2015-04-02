package com.jhlibrary.twitter.activity;

import java.io.File;
import java.util.List;

import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;
import twitter4j.media.ImageUpload;
import twitter4j.media.ImageUploadFactory;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.jhlibrary.R;
import com.jhlibrary.constant.Key;
import com.jhlibrary.util.Util;

public class TwitterActivity extends Activity {

	/**
	 * Twitter instance object
	 */
	private Twitter twitter;
	private AccessToken acToken = null;
	private RequestToken rqToken = null;
	private Status status = null;
	String access_token = "";
	String access_token_secret = "";
	private Button mSendButton;
	private String mFilePath;
	private EditText mMessageEditText;
	private Dialog mDialog;
	private ImageView mImageView;

	protected boolean isAuth = false;
	
	private static final String CONSUMER_KEY = "twitter.consumer_key";
	private static final String CONSUMER_SECRET = "twitter.consumer_secret";
	private static final String ACCESS_TOKEN = "ACCESS_TOKEN";
	private static final String ACCESS_TOKEN_SECRET = "ACCESS_TOKEN_SECRET";
	public static Uri CALLBACK_URL = Uri.parse("wefu://twitter");

	/** 생성. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_twitter_main);
		initialize();
		try {
			getToken();
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void initialize() {
		initializeFields();
		initializeListeners();
		initializeView();
	}

	private void initializeFields() {
		// 트위터 인스턴스 얻기
		twitter = new TwitterFactory().getInstance();
		// 컨슈머 키 대입
		twitter.setOAuthConsumer(Util.getResourceBundle(CONSUMER_KEY), Util.getResourceBundle(CONSUMER_SECRET));
		mSendButton = (Button) findViewById(R.id.button_send);
		mFilePath = getIntent().getStringExtra(Key.FILE_NAME);
		mMessageEditText = (EditText) findViewById(R.id.edittext_message);
		mImageView = (ImageView) findViewById(R.id.imageview_image);
		
	}

	private void initializeView() {
		mImageView.setImageBitmap(BitmapFactory.decodeFile(mFilePath));
	}

	private void initializeListeners() {
		// 버튼 리스너 등록

		mSendButton.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				try {
					File file = new File(mFilePath);
					if (file.exists())
						writePicTwitter(mMessageEditText.getText().toString(), file);
					// if (null != acToken) getList();
				} catch (TwitterException e) {
					e.printStackTrace();
				} // 토큰 발행
			}
		});

	}

//	/**
//	 * 트위터 글 쓰기
//	 * 
//	 * @return
//	 * @throws TwitterException
//	 */
//	private boolean writeTwitter(String content) throws TwitterException {
//		boolean res = false;
//
//		getToken(); // 토큰 읽기
//
//		if (null != acToken) {
//			// 트위터 글쓰기
//			// SimpleDateFormat dateFormat = new
//			// SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//			// dateFormat.format(new
//			// Date(System.currentTimeMillis())).toString();
//			status = twitter.updateStatus(content);
//		}
//
//		return res;
//	}

	/**
	 * 트위터 글 쓰기
	 * 
	 * @return
	 * @throws TwitterException
	 */
	private boolean writePicTwitter(final String content, final File file) throws TwitterException {
		boolean res = false;

		getToken(); // 토큰 읽기

		if (null != acToken) {
			if (mDialog == null) {
				mDialog = Util.getDialog(TwitterActivity.this);
			}
			mDialog.show();
			AsyncTask<Void, Void, Boolean> task = new AsyncTask<Void, Void, Boolean>() {

				@Override
				protected Boolean doInBackground(Void... params) {
					// TODO Auto-generated method stub
					ConfigurationBuilder builder = new ConfigurationBuilder();
					builder.setOAuthConsumerKey(Util.getResourceBundle(CONSUMER_KEY));
					builder.setOAuthConsumerSecret(Util.getResourceBundle(CONSUMER_SECRET));
					builder.setOAuthAccessToken(access_token);
					builder.setOAuthAccessTokenSecret(access_token_secret);
					builder.setMediaProvider("TWITTER");

					Configuration conf = builder.build();
					ImageUpload imageUpload = new ImageUploadFactory(conf).getInstance();
					try {
						imageUpload.upload(file, content);
						return true;
					} catch (TwitterException e) {
						e.printStackTrace();
						return false;
					}
				}

				@Override
				protected void onPostExecute(Boolean result) {
					super.onPostExecute(result);
					mDialog.dismiss();
					resultToast(result);
				}

				@Override
				protected void onPreExecute() {
					// TODO Auto-generated method stub
					super.onPreExecute();
				}

			};
			task.execute();
		}

		return res;
	}

	/**
	 * 토큰 얻기
	 * 
	 * @return
	 * @throws TwitterException
	 */
	private void getToken() throws TwitterException {
		// 토큰이 발행 되지 않았으면
		if (null == acToken) {
			// 최근 사용한 토큰 읽기
			access_token = (String) Util.getPreference(this, ACCESS_TOKEN);
			access_token_secret = (String) Util.getPreference(this, ACCESS_TOKEN_SECRET);

			// 토큰 값이 모두 있으면
			if ((access_token != null) && (access_token_secret != null)) {
				// 기존 토큰 값으로 토큰 발행
				acToken = new AccessToken(access_token, access_token_secret);
				twitter.setOAuthAccessToken(acToken);
			} else {
				// 인증 시작
				startAuth();
			}
		} else {
		}
	}

	/**
	 * 인증 시작
	 * 
	 * @throws TwitterException
	 */
	private void startAuth() throws TwitterException {
		// rqToken = twitter.getOAuthRequestToken(CALLBACK_URL.toString());
		if (rqToken == null) {
			rqToken = twitter.getOAuthRequestToken();
		}
		isAuth = true;
		Intent intent = new Intent(this, TwitterWebViewActivity.class);
		intent.putExtra(Key.OAUTH_URL, rqToken.getAuthorizationURL());
		startActivityForResult(intent, 0);
	}

	/**
	 * @throws TwitterException
	 */
	private void getList() throws TwitterException {
		List<Status> statuses;
		Paging page = new Paging();
		page.count(20);
		page.setPage(1);
		statuses = twitter.getHomeTimeline(page);
		for (Status status : statuses) {
			Log.i("coolsharp", status.getText());
		}
	}

	/*
	 * (non-Javadoc) 인텐트 생성
	 * 
	 * @see android.app.Activity#onNewIntent(android.content.Intent)
	 */
	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		Uri uri = intent.getData();
		if (uri != null && CALLBACK_URL.getScheme().equals(uri.getScheme())) {
			String oauth_verifier = uri.getQueryParameter("oauth_verifier");
			try {
				acToken = twitter.getOAuthAccessToken(rqToken, oauth_verifier);
				access_token = acToken.getToken();
				access_token_secret = acToken.getTokenSecret();
				Util.setPreference(this, ACCESS_TOKEN, access_token);
				Util.setPreference(this, ACCESS_TOKEN_SECRET, access_token_secret);
				// Util.setSharedPreferencesValue(this, "coolsharp",
				// MODE_PRIVATE, ACCESS_TOKEN, access_token);
				// Util.setSharedPreferencesValue(this, "coolsharp",
				// MODE_PRIVATE, ACCESS_TOKEN_SECRET,
				// access_token_secret);
			} catch (TwitterException e) {
				Log.e("coolsharp", e.getMessage());
			}
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == 0) {
			if (resultCode == RESULT_OK) {
				String oauth_verifier = data.getStringExtra(Key.OAUTH_VERIFIER);
				try {
					acToken = twitter.getOAuthAccessToken(rqToken, oauth_verifier);
					access_token = acToken.getToken();
					access_token_secret = acToken.getTokenSecret();
					Util.setPreference(this, ACCESS_TOKEN, access_token);
					Util.setPreference(this, ACCESS_TOKEN_SECRET, access_token_secret);
					// Util.setSharedPreferencesValue(this, "coolsharp",
					// MODE_PRIVATE, ACCESS_TOKEN, access_token);
					// Util.setSharedPreferencesValue(this, "coolsharp",
					// MODE_PRIVATE, ACCESS_TOKEN_SECRET,
					// access_token_secret);
				} catch (TwitterException e) {
					Log.e(getPackageName(), e.getMessage());
				}
			}
		}
	}
	
	public void resultToast(boolean result) {
		if (result) {
			Toast.makeText(TwitterActivity.this, "Success", Toast.LENGTH_SHORT).show();
			onBackPressed();
		} else {
			Toast.makeText(TwitterActivity.this, "Failed", Toast.LENGTH_SHORT).show();
		}
	}
}