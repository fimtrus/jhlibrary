package com.jhlibrary.slidingmenu.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;

import com.jhlibrary.R;
import com.jhlibrary.activity.BaseActivity;
import com.slidingmenu.lib.SlidingMenu;
import com.slidingmenu.lib.app.SlidingActivityBase;
import com.slidingmenu.lib.app.SlidingActivityHelper;

/**
 * Sliding Activity의 부모 클래스.
 */
public class SlidingBaseActivity extends BaseActivity implements SlidingActivityBase {

	private SlidingActivityHelper mHelper;
	private Fragment mFrag;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.FragmentActivity#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mHelper = new SlidingActivityHelper(this);
		mHelper.onCreate(savedInstanceState);
//		mHelper.setSlidingActionBarEnabled(false);
//		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onPostCreate(android.os.Bundle)
	 */
	@Override
	public void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mHelper.onPostCreate(savedInstanceState);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#findViewById(int)
	 */
	@Override
	public View findViewById(int id) {
		View v = super.findViewById(id);
		if (v != null)
			return v;
		return mHelper.findViewById(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.support.v4.app.FragmentActivity#onSaveInstanceState(android.os
	 * .Bundle)
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		mHelper.onSaveInstanceState(outState);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#setContentView(int)
	 */
	@Override
	public void setContentView(int id) {
		setContentView(getLayoutInflater().inflate(id, null));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#setContentView(android.view.View)
	 */
	@Override
	public void setContentView(View v) {
		setContentView(v, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#setContentView(android.view.View,
	 * android.view.ViewGroup.LayoutParams)
	 */
	@Override
	public void setContentView(View v, LayoutParams params) {
		super.setContentView(v, params);
		mHelper.registerAboveContentView(v, params);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.slidingmenu.lib.app.SlidingActivityBase#setBehindContentView(int)
	 */
	public void setBehindContentView(int id) {
		setBehindContentView(getLayoutInflater().inflate(id, null));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.slidingmenu.lib.app.SlidingActivityBase#setBehindContentView(android
	 * .view.View)
	 */
	public void setBehindContentView(View v) {
		setBehindContentView(v, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.slidingmenu.lib.app.SlidingActivityBase#setBehindContentView(android
	 * .view.View, android.view.ViewGroup.LayoutParams)
	 */
	public void setBehindContentView(View v, LayoutParams params) {
		mHelper.setBehindContentView(v, params);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.slidingmenu.lib.app.SlidingActivityBase#getSlidingMenu()
	 */
	public SlidingMenu getSlidingMenu() {
		return mHelper.getSlidingMenu();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.slidingmenu.lib.app.SlidingActivityBase#toggle()
	 */
	public void toggle() {
		mHelper.toggle();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.slidingmenu.lib.app.SlidingActivityBase#showAbove()
	 */
	public void showContent() {
		mHelper.showContent();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.slidingmenu.lib.app.SlidingActivityBase#showBehind()
	 */
	public void showMenu() {
		mHelper.showMenu();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.slidingmenu.lib.app.SlidingActivityBase#showSecondaryMenu()
	 */
	public void showSecondaryMenu() {
		mHelper.showSecondaryMenu();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.slidingmenu.lib.app.SlidingActivityBase#setSlidingActionBarEnabled
	 * (boolean)
	 */
	public void setSlidingActionBarEnabled(boolean b) {
		mHelper.setSlidingActionBarEnabled(b);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onKeyUp(int, android.view.KeyEvent)
	 */
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		boolean b = mHelper.onKeyUp(keyCode, event);
		if (b)
			return b;
		return super.onKeyUp(keyCode, event);
	}

	/**
	 * 왼쪽과 오른쪽에 SlidingMenu를 추가한다.</br>
	 * @param savedInstanceState 
	 * @param leftLayout
	 * @param rightLayout
	 * @param leftId
	 * @param rightId
	 * @param leftFragment
	 * @param rightFragment
	 */
	protected void addLeftRightSlidingMenu(Bundle savedInstanceState, int leftLayout, int rightLayout, int leftId,
			int rightId, Fragment leftFragment, Fragment rightFragment) {

		setBehindContentView(leftLayout);
		if (savedInstanceState == null) {
			FragmentTransaction t = this.getSupportFragmentManager().beginTransaction();
			mFrag = leftFragment;
			t.replace(leftId, mFrag);
			t.commit();
		} else {
			mFrag = this.getSupportFragmentManager().findFragmentById(leftId);
		}

		// customize the SlidingMenu
		SlidingMenu sm = getSlidingMenu();
		sm.setShadowWidthRes(R.dimen.shadow_width);
		sm.setShadowDrawable(R.drawable.shadow);
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		sm.setFadeDegree(0.35f);
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);

		getSlidingMenu().setMode(SlidingMenu.LEFT_RIGHT);
		getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);

		getSlidingMenu().setSecondaryMenu(rightLayout);
		getSlidingMenu().setSecondaryShadowDrawable(R.drawable.shadowright);
		getSupportFragmentManager().beginTransaction().replace(rightId, rightFragment).commit();
	}

	/**
	 * 왼쪽에 SlidingMenu를 추가한다.
	 * @param savedInstanceState
	 * @param leftLayout
	 * @param leftId
	 * @param leftFragment
	 */
	protected void addLeftSlidingMenu(Bundle savedInstanceState, int leftLayout, int leftId, Fragment leftFragment) {

		setBehindContentView(leftLayout);
		if (savedInstanceState == null) {
			FragmentTransaction t = this.getSupportFragmentManager().beginTransaction();
			mFrag = leftFragment;
			t.replace(leftId, mFrag);
			t.commit();
		} else {
			mFrag = this.getSupportFragmentManager().findFragmentById(leftId);
		}

		// customize the SlidingMenu
		SlidingMenu sm = getSlidingMenu();
		sm.setShadowWidthRes(R.dimen.shadow_width);
		sm.setShadowDrawable(R.drawable.shadow);
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		sm.setFadeDegree(0.35f);
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);

		getSlidingMenu().setMode(SlidingMenu.LEFT);
		getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			mHelper.showMenu();
		}
		return super.onOptionsItemSelected(item);
	}

}
