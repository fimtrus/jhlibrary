package com.jhlibrary.facebook.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jhlibrary.R;

public class SplashFragment extends Fragment {
	private View mRootLayout;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		mRootLayout = inflater.inflate(R.layout.fragment_splash, container, false);
		//TODO : 
//		LoginButton authButton = (LoginButton) mRootLayout.findViewById(R.id.login_button);
//		authButton.setFragment(this);
//		authButton.setReadPermissions(Arrays.asList("user_likes", "user_status"));
		initialize();
		return mRootLayout;
	}

	private void initialize() {

		initializeFields();
		initializeListeners();
		initializeView();

	}

	private void initializeFields() {
	}

	private void initializeListeners() {

	}

	private void initializeView() {
	}
}
