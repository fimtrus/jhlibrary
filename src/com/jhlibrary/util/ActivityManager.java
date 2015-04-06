package com.jhlibrary.util;

import java.util.ArrayList;

import android.app.Activity;

public class ActivityManager {
	 
	public static ArrayList<Activity> mActivityList = null;
	
	public static final Activity get( int i ) {
		
		if ( mActivityList == null || mActivityList.size() < i + 1 ) {
			return null;
		}
		
		return mActivityList.get(i);
	}
	public static final void add( Activity activity ) {
		
		if ( mActivityList == null ) {
			mActivityList = new ArrayList<Activity>();
		}
	}
	public static final void kill( int i ) {
		if ( mActivityList == null || mActivityList.size() < i + 1 ) {
			return;
		}
		
		mActivityList.get(i).finish();
	}
	
	public static final void killAll() {
		if ( mActivityList == null) {
			return;
		}
		
		while ( mActivityList.size() != 0 ) {
			mActivityList.get(0).finish();
		}
	}
	
}
