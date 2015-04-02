package com.jhlibrary.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.jhlibrary.R;

/**
 * 모든 Activity의 부모 클래스.
 */
public class BaseActivity extends android.support.v4.app.FragmentActivity {

	private boolean flag;
	private Handler mBackHandler;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initialize();
	}

	// Init
	protected void initialize() {

		initializeActionBar();
		initializeFragments();
		initializeFields();
		initializeListeners();
		initializeView();
	}

	protected void initializeFields() {
		mBackHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if (msg.what == 0) {
					flag = false;
				}
			}
		};
	}

	protected void initializeListeners() {
	}

	protected void initializeView() {
	}

	protected void initializeFragments() {
	}

	// Actionbar 설정
	private void initializeActionBar() {
	}

	@Override
	public void onBackPressed() {
		// Main 화면에서만 종료가 가능하도록...

		if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
			super.onBackPressed();
		} else {
			if (!flag) {
				Toast.makeText(this, R.string.exit_text, Toast.LENGTH_SHORT).show();
				flag = true;
				mBackHandler.sendEmptyMessageDelayed(0, 3000);
			} else {
				super.onBackPressed();
			}
		}
	}

	// protected void addLeftSlideMenu(int layout) {
	// mSlideMenu.setMode(SlidingMenu.LEFT);
	// mSlideMenu.setMenu(layout);
	//
	// }
	// protected void addRightSlideMenu(int layout) {
	// mSlideMenu.setMode(SlidingMenu.RIGHT);
	// mSlideMenu.setMenu(layout);
	//
	// }

	// @Override
	// public boolean onCreateOptionsMenu(Menu menu) {
	// getMenuInflater().inflate(R.menu.activity_main, menu);
	// return true;
	// }

}
