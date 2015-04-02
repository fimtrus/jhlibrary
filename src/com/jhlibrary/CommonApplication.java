package com.jhlibrary;


import android.app.Application;

/**
 * 공통적으로 사용하는 Application 클래스
 */
public class CommonApplication extends Application {

	private static CommonApplication mApplication;

	@Override
	public void onCreate() {
		super.onCreate();
//		DBHelper.getInstance(this).createDataBase();
		mApplication = this;
		

	}

	public static CommonApplication getApplication() {
		return mApplication;
	}
}