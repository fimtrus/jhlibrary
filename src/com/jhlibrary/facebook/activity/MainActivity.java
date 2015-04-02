package com.jhlibrary.facebook.activity;

import android.support.v4.app.FragmentActivity;


@Deprecated
public class MainActivity extends FragmentActivity {

//	private static final int SPLASH = 0;
//	private static final int SELECTION = 1;
//	private static final int SETTINGS = 2;
//
//	private static final int FRAGMENT_COUNT = SETTINGS + 1;
//
//	private Fragment[] fragments = new Fragment[FRAGMENT_COUNT];
//
//	private boolean isResumed = false;
//
//	private MenuItem settings;
//
//	private ResultToast mResultToast;
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_facebook_main);
//		uiHelper = new UiLifecycleHelper(this, callback);
//
//		FragmentManager fm = getSupportFragmentManager();
//		fragments[SPLASH] = fm.findFragmentById(R.id.splashFragment);
//		fragments[SELECTION] = fm.findFragmentById(R.id.selectionFragment);
//		fragments[SETTINGS] = fm.findFragmentById(R.id.userSettingsFragment);
//		
//		FragmentTransaction transaction = fm.beginTransaction();
//		for (int i = 0; i < fragments.length; i++) {
//			transaction.hide(fragments[i]);
//		}
//		transaction.commit();
//		// Session.openActiveSession(this, true, callback);
//
//		// start Facebook Login
////		Session.openActiveSession(this, true, new Session.StatusCallback() {
////
////			// callback when session changes state
////			@Override
////			public void call(Session session, SessionState state, Exception exception) {
////
////			}
////		});
//		try {
////			Toast.makeText(this, getPackageName(), Toast.LENGTH_SHORT).show();
//		    PackageInfo info = getPackageManager().getPackageInfo(
//		    		getPackageName(), 
//		            PackageManager.GET_SIGNATURES);
//		    for (Signature signature : info.signatures) {
//		        MessageDigest md = MessageDigest.getInstance("SHA");
//		        md.update(signature.toByteArray());
//		        Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
//		        }
//		} catch (NameNotFoundException e) {
//
//		} catch (NoSuchAlgorithmException e) {
//
//		}
//	}
//
//	private void showFragment(int fragmentIndex, boolean addToBackStack) {
//		FragmentManager fm = getSupportFragmentManager();
//		FragmentTransaction transaction = fm.beginTransaction();
//		for (int i = 0; i < fragments.length; i++) {
//			if (i == fragmentIndex) {
//				transaction.show(fragments[i]);
//			} else {
//				transaction.hide(fragments[i]);
//			}
//		}
//		if (addToBackStack) {
//			transaction.addToBackStack(null);
//		}
//		transaction.commit();
//	}
//
//	private void onSessionStateChange(Session session, SessionState state, Exception exception) {
//		// Only make changes if the activity is visible
//		if (isResumed) {
//			FragmentManager manager = getSupportFragmentManager();
//			// Get the number of entries in the back stack
//			int backStackSize = manager.getBackStackEntryCount();
//			// Clear the back stack
//			for (int i = 0; i < backStackSize; i++) {
//				manager.popBackStack();
//			}
//			if (state.isOpened()) {
//				// If the session state is open:
//				// Show the authenticated fragment
//				showFragment(SELECTION, false);
//			} else if (state.isClosed()) {
//				// If the session state is closed:
//				// Show the login fragment
//				showFragment(SPLASH, false);
//				Session.getActiveSession().closeAndClearTokenInformation();
//			}
//		}
//	}
//
//	@Override
//	protected void onResumeFragments() {
//		super.onResumeFragments();
//		Session session = Session.getActiveSession();
//
//		if (session != null && session.isOpened()) {
//			// if the session is already open,
//			// try to show the selection fragment
//			showFragment(SELECTION, false);
//		} else {
//			// otherwise present the splash screen
//			// and ask the user to login.
//			showFragment(SPLASH, false);
//		}
//	}
//
//	private UiLifecycleHelper uiHelper;
//	private Session.StatusCallback callback = new Session.StatusCallback() {
//		@Override
//		public void call(Session session, SessionState state, Exception exception) {
////			Toast.makeText(MainActivity.this, "session change : " + state.isOpened(), Toast.LENGTH_LONG).show();
//			onSessionStateChange(session, state, exception);
//		}
//	};
//
//	@Override
//	public void onResume() {
//		super.onResume();
//		uiHelper.onResume();
//		isResumed = true;
//	}
//
//	@Override
//	public void onPause() {
//		super.onPause();
//		uiHelper.onPause();
//		isResumed = false;
//	}
//
//	@Override
//	public void onActivityResult(int requestCode, int resultCode, Intent data) {
//		Log.i("MainActivity", "RESULT : " + resultCode);
//		super.onActivityResult(requestCode, resultCode, data);
//		uiHelper.onActivityResult(requestCode, resultCode, data);
//	}
//
//	@Override
//	public void onDestroy() {
//		super.onDestroy();
//		uiHelper.onDestroy();
//	}
//
//	@Override
//	protected void onSaveInstanceState(Bundle outState) {
//		super.onSaveInstanceState(outState);
//		uiHelper.onSaveInstanceState(outState);
//	}
//
//	@Override
//	public boolean onPrepareOptionsMenu(Menu menu) {
//		// only add the menu when the selection fragment is showing
//		if (fragments[SELECTION].isVisible()) {
//			if (menu.size() == 0) {
//				settings = menu.add(R.string.menu_settings);
//			}
//			return true;
//		} else {
//			menu.clear();
//			settings = null;
//		}
//		return false;
//	}
//
//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//		if (item.equals(settings)) {
//			showFragment(SETTINGS, true);
//			return true;
//		}
//		return false;
//	}
//	public interface ResultToast {
//		void resultToast(Response response);
//	}
//	
//	public void setResultToast(ResultToast resultToast) {
//		mResultToast = resultToast;
//		((SelectionFragment)fragments[SELECTION]).setResultToast(resultToast);
//	}
}
