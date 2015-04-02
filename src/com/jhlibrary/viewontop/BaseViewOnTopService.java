package com.jhlibrary.viewontop;

import android.app.Service;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.os.IBinder;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewParent;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

import com.jhlibrary.R;
import com.jhlibrary.util.Util;

/**
 * @author jong-hyun.jeong
 * Floating View 를 생성한다.<br>
 * View는 Z-index 최상단에 위치한다.<br>
 * 이 클래스를 상속받고, onCreate에서 initialize(int layout)을 실행하도록 코딩해야한다.
 */
public class BaseViewOnTopService extends Service implements View.OnClickListener {

	public static final String VIEW_ID = "view_id";
	
	private WindowManager.LayoutParams mWindowLayoutParams; // layout params 객체. 뷰의 위치 및 크기를
												// 지정하는 객체
	private WindowManager mWindowManager; // 윈도우 매니저
	private SeekBar mSeekBar; // 투명도 조절 seek bar

	private float START_X, START_Y; // 움직이기 위해 터치한 시작 점
	private int PREV_X, PREV_Y; // 움직이기 이전에 뷰가 위치한 점
	private int MAX_X = -1, MAX_Y = -1; // 뷰의 위치 최대 값

	protected RelativeLayout mHeaderLayout;
	protected View mContainer;
	protected RelativeLayout mContentLayout; //컨텐츠가 표시될곳..
	protected View mInnerLayout;
	
	protected LinearLayout.LayoutParams mContainerParams;
	
	private OnTouchListener mViewTouchListener = new OnTouchListener() {
		
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN: // 사용자 터치 다운이면
				
				updateFloatingWindow( true );
				if (MAX_X == -1)
					setMaxPosition();
				START_X = event.getRawX(); // 터치 시작 점
				START_Y = event.getRawY(); // 터치 시작 점
				PREV_X = mWindowLayoutParams.x; // 뷰의 시작 점
				PREV_Y = mWindowLayoutParams.y; // 뷰의 시작 점
//				PREV_X = mContainerParams.leftMargin; // 뷰의 시작 점
//				PREV_Y = mContainerParams.topMargin; // 뷰의 시작 점
				break;
			case MotionEvent.ACTION_MOVE:
				int x = (int) (event.getRawX() - START_X); // 이동한 거리
				int y = (int) (event.getRawY() - START_Y); // 이동한 거리

				// 터치해서 이동한 만큼 이동 시킨다
				mWindowLayoutParams.x = PREV_X + x;
				mWindowLayoutParams.y = PREV_Y + y;
//				mContainerParams.leftMargin = PREV_X + x;
//				mContainerParams.topMargin = PREV_Y + y;

				optimizePosition(); // 뷰의 위치 최적화
				updateXY();
//				mWindowManager.updateViewLayout(mContainer, mParams); // 뷰 업데이트
				break;
			}

			return true;
		}
	};
	private OnTouchListener mRootLayoutListener = new OnTouchListener() {
		
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN: // 사용자 터치 다운이면
				Log.i("", "ROOT actionDown");
//				updateFloatingWindow( WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE );
				updateFloatingWindow( false );
				break;
			}
			
			return false;
		}
	};
	private OnTouchListener mContainerListener = new OnTouchListener() {
		
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN: // 사용자 터치 다운이면
				Log.i("", "CONTAINER actionDown");
				updateFloatingWindow( true );
//				updateFloatingWindow( WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE );
				break;
			}
			
			return true;
		}
	};
	private ImageView mCancelButton;

	private ViewParent mContainerParant;

	private LinearLayout mRootLayout;
	

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}
	
	/**
	 * floating시킬 컨텐츠를 추가한다.
	 * @param floatingContent
	 */
	public void initialize(int innerLayout) {

		initializeFields(innerLayout);
		initializeViews();
		initializeListeners();
		
		 
	}
	private void initializeFields (int innerLayout) {
		
		mRootLayout = new LinearLayout(getApplicationContext());
		ViewGroup.LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
		mContainerParams = new LinearLayout.LayoutParams( LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT );
		
		mContainer = LayoutInflater.from(getApplicationContext()).inflate(
				R.layout.view_base_view_on_top, null);
		// mPopupView = new WebView(this); // 뷰 생성
		mHeaderLayout = (RelativeLayout) mContainer.findViewById(R.id.header);
		mContentLayout = (RelativeLayout) mContainer.findViewById(R.id.layout_base);
		
		mInnerLayout = LayoutInflater.from(getApplicationContext()).inflate(innerLayout, null);
		mContentLayout.addView(mInnerLayout);
		
		DisplayMetrics matrix = com.jhlibrary.util.Util.getMetrics();
		
		RelativeLayout.LayoutParams innerLayoutParams = (RelativeLayout.LayoutParams) mInnerLayout.getLayoutParams();
		android.widget.LinearLayout.LayoutParams headerLayoutParams = (LinearLayout.LayoutParams) mHeaderLayout.getLayoutParams();
		
		headerLayoutParams.width = matrix.widthPixels - (int)(50 * matrix.density);
		innerLayoutParams.width = matrix.widthPixels - (int)(50 * matrix.density);
		innerLayoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;//(int)( ( matrix.widthPixels - (20 * matrix.density) ) * 0.75);
		mInnerLayout.setLayoutParams(innerLayoutParams);
		mHeaderLayout.setLayoutParams(headerLayoutParams);
		
		mCancelButton = (ImageView) mContainer.findViewById(R.id.button_cancel);
		
		mSeekBar = (SeekBar) mContainer.findViewById(R.id.seekbar_lucidity); // 투명도 조절 seek bar
		
		mRootLayout.setLayoutParams(layoutParams);
		mRootLayout.setBackgroundColor(Color.BLACK);
		mRootLayout.addView( mContainer );
	}
	private void initializeViews () {
		DisplayMetrics matrix = com.jhlibrary.util.Util.getMetrics();
		
		mWindowLayoutParams = new WindowManager.LayoutParams(
				WindowManager.LayoutParams.WRAP_CONTENT,
				WindowManager.LayoutParams.WRAP_CONTENT,
//				WindowManager.LayoutParams.MATCH_PARENT,
//				(int)( matrix.widthPixels - (50 * matrix.density) ),
//				 WindowManager.LayoutParams.WRAP_CONTENT,
//				matrix.widthPixels - (int)(50 * matrix.density), 
//				(int)( ( matrix.widthPixels - (50 * matrix.density) ) * 0.75),
//		mParams = new WindowManager.LayoutParams(matrix.widthPixels - (int)(50 * matrix.density), 
//				matrix.heightPixels/2,
//		 WindowManager.LayoutParams.WRAP_CONTENT,
//		 WindowManager.LayoutParams.WRAP_CONTENT,
				WindowManager.LayoutParams.TYPE_PRIORITY_PHONE, // 항상 최 상위에 있게. status
														// bar 밑에 있음. 터치 이벤트 받을
														// 수 있음.
//				0, // 이 속성을 안주면 터치 &
				WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH, // 이 속성을 안주면 터치 &
																// 키 이벤트도 먹게 된다.
																// 포커스를 안줘서 자기
																// 영역 밖터치는 인식
																// 안하고 키이벤트를
																// 사용하지 않게 설정
				PixelFormat.TRANSLUCENT); // 투명
		mWindowLayoutParams.gravity = Gravity.LEFT | Gravity.TOP; // 왼쪽 상단에 위치하게 함.

		mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE); // 윈도우
																			// 매니저
																			// 불러옴.
		mWindowManager.addView(mRootLayout, mWindowLayoutParams); // 최상위 윈도우에 뷰 넣기. *중요 : 여기에
														// permission을 미리 설정해
														// 두어야 한다. 매니페스트에
		
		mRootLayout.setDrawingCacheEnabled( true );
		
//		mRootLayout.getParent().focusableViewAvailable(mContainer);
		
	}
	
	public void updateFloatingWindow ( boolean isTouchable ) {
		
		if ( isTouchable ) {
			
			if ( mWindowLayoutParams.flags == WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH ) {
//			if ( mWindowLayoutParams.flags == WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL ) {
				return;
			}
			
			mWindowLayoutParams.flags = WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH;
//			mWindowLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
//			mWindowLayoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
//			mWindowLayoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
//			mContainerParams.leftMargin = mWindowLayoutParams.x;
//			mContainerParams.topMargin = mWindowLayoutParams.y;
//			mWindowLayoutParams.x = 0;
//			mWindowLayoutParams.y = 0;
		} else {
			
			if ( mWindowLayoutParams.flags == WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE ) {
				return;
			}
			
			mWindowLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
//			mWindowLayoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
//			mWindowLayoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
//			mWindowLayoutParams.x = mContainerParams.leftMargin;
//			mWindowLayoutParams.y = mContainerParams.topMargin;
//			mContainerParams.leftMargin = 0;
//			mContainerParams.topMargin = 0;
			
		}
//		//백그라운드
////		mWindowLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
//		//포그라운드
////		WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
		mWindowManager.updateViewLayout( mRootLayout, mWindowLayoutParams );
//		mContainer.setLayoutParams( mContainerParams );
		
	}
	
	private void initializeListeners () {
		mHeaderLayout.setOnTouchListener(mViewTouchListener);
		mCancelButton.setOnClickListener(this);
		mRootLayout.setOnTouchListener(mRootLayoutListener);
		mContainer.setOnTouchListener(mContainerListener);
		
		addOpacityController(); //팝업 뷰의 투명도 조절하는 컨트롤러 추가
	}
	
	private void updateXY () {
		
		mWindowManager.updateViewLayout(mRootLayout, mWindowLayoutParams);
//		mContainer.setLayoutParams( mContainerParams );
	}

	@Override
	public void onCreate() {
		super.onCreate();
	}

	/**
	 * 뷰의 위치가 화면 안에 있게 최대값을 설정한다
	 */
	private void setMaxPosition() {
		DisplayMetrics matrix = new DisplayMetrics();
		mWindowManager.getDefaultDisplay().getMetrics(matrix); // 화면 정보를 가져와서

		MAX_X = matrix.widthPixels - mContainer.getWidth(); // x 최대값 설정
		MAX_Y = matrix.heightPixels - mContainer.getHeight() - Util.getPxFromDp(30); // y 최대값 설정
	}

	/**
	 * 뷰의 위치가 화면 안에 있게 하기 위해서 검사하고 수정한다.
	 */
	private void optimizePosition() {
		// 최대값 넘어가지 않게 설정
		if (mWindowLayoutParams.x > MAX_X)
			mWindowLayoutParams.x = MAX_X;
		if (mWindowLayoutParams.y > MAX_Y)
			mWindowLayoutParams.y = MAX_Y;
		if (mWindowLayoutParams.x < 0)
			mWindowLayoutParams.x = 0;
		if (mWindowLayoutParams.y < 0)
			mWindowLayoutParams.y = 0;
//		if (mContainerParams.leftMargin > MAX_X)
//			mContainerParams.leftMargin = MAX_X;
//		if (mContainerParams.topMargin > MAX_Y)
//			mContainerParams.topMargin = MAX_Y;
//		if (mContainerParams.leftMargin < 0)
//			mContainerParams.leftMargin = 0;
//		if (mContainerParams.topMargin < 0)
//			mContainerParams.topMargin = 0;
	}

	/**
	 * 알파값 조절하는 컨트롤러를 추가한다
	 */
	private void addOpacityController() {
		mSeekBar.setMax(100); // 맥스 값 설정.
		mSeekBar.setProgress(100); // 현재 투명도 설정. 100:불투명, 0은 완전 투명
		mSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				mWindowLayoutParams.alpha = ( progress * 0.7f + 30 ) /100.0f; // 알파값 설정
				mWindowManager.updateViewLayout( mRootLayout, mWindowLayoutParams); // 팝업 뷰
																		// 업데이트
			}
		});
	}

	/**
	 * 가로 / 세로 모드 변경 시 최대값 다시 설정해 주어야 함.
	 */
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		setMaxPosition(); // 최대값 다시 설정
		optimizePosition(); // 뷰 위치 최적화
	}

	@Override
	public void onDestroy() {
		if (mWindowManager != null) { // 서비스 종료시 뷰 제거. *중요 : 뷰를 꼭 제거 해야함.
			if (mContainer != null)
				mWindowManager.removeView( mRootLayout );
//			if (mSeekBar != null)
//				mWindowManager.removeView(mSeekBar);
		}
		super.onDestroy();
	}

	@Override
	public void onClick(View v) {
		
		if ( v.getId() == R.id.button_cancel ) {
			stopSelf();
		}
	}
}
