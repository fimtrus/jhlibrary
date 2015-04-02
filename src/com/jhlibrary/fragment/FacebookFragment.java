package com.jhlibrary.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FacebookFragment extends Fragment {
	private ViewGroup mRootLayout;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

//		mRootLayout = (ViewGroup) inflater.inflate(R.layout.fragment_camera, container, false);

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
