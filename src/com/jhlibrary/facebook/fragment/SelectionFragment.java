package com.jhlibrary.facebook.fragment;

import android.support.v4.app.Fragment;

@Deprecated
public class SelectionFragment extends Fragment {
//	private View mRootLayout;
////	private ProfilePictureView profilePictureView;
////	private TextView userNameView;
//
//	private UiLifecycleHelper uiHelper;
//	private Session.StatusCallback callback;
//	private String mFilePath;
//	private EditText mMessageEditText;
//	private ImageView mImageView;
//	private Button mSendButton;
//
//	private Dialog mDialog;
//	
//	private ResultToast mResultToast;
//	
//	private static final int REAUTH_ACTIVITY_CODE = 100;
//
//	@Override
//	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//
//		mRootLayout = inflater.inflate(R.layout.fragment_selection, container, false);
//		initialize();
//		return mRootLayout;
//	}
//
//	private void initialize() {
//
//		initializeFields();
//		initializeListeners();
//		initializeView();
//
//	}
//
//	private void initializeFields() {
////		profilePictureView = (ProfilePictureView) mRootLayout.findViewById(R.id.selection_profile_pic);
////		profilePictureView.setCropped(true);
//
//		// Find the user's name view
////		userNameView = (TextView) mRootLayout.findViewById(R.id.selection_user_name);
//		
//		mFilePath = getActivity().getIntent().getStringExtra("filename");
//		mMessageEditText = (EditText) mRootLayout.findViewById(R.id.edittext_message);
//		mImageView = (ImageView) mRootLayout.findViewById(R.id.imageview_image);
//		mSendButton = (Button) mRootLayout.findViewById(R.id.button_send);
//	}
//
//	private void initializeListeners() {
//		callback = new Session.StatusCallback() {
//			@Override
//			public void call(final Session session, final SessionState state, final Exception exception) {
//				onSessionStateChange(session, state, exception);
//			}
//		};
//		
//		mSendButton.setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				upload(mMessageEditText.getText().toString());				
//			}
//		});
//	}
//
//	private void initializeView() {
//		// Check for an open session
//	    Session session = Session.getActiveSession();
//	    if (session != null && session.isOpened()) {
//	        // Get the user's data
////	        makeMeRequest(session);
//	    }
//	    
//	    mImageView.setImageBitmap(BitmapFactory.decodeFile(mFilePath));
//	}
//
////	private void makeMeRequest(final Session session) {
////		// Make an API call to get user data and define a
////		// new callback to handle the response.
////		Request request = Request.newMeRequest(session, new Request.GraphUserCallback() {
////			@Override
////			public void onCompleted(GraphUser user, Response response) {
////				// If the response is successful
////				if (session == Session.getActiveSession()) {
////					if (user != null) {
////						// Set the id for the ProfilePictureView
////						// view that in turn displays the profile picture.
////						profilePictureView.setProfileId(user.getId());
////						// Set the Textview's text to the user's name.
////						userNameView.setText(user.getName());
////					}
////				}
////				if (response.getError() != null) {
////					// Handle errors, will do so later.
////				}
////			}
////		});
////		request.executeAsync();
////	}
//
//	private void onSessionStateChange(final Session session, SessionState state, Exception exception) {
//		if (session != null && session.isOpened()) {
//			// Get the user's data.
////			makeMeRequest(session);
//		}
//	}
//
//	@Override
//	public void onResume() {
//		super.onResume();
//		uiHelper.onResume();
//	}
//
//	@Override
//	public void onSaveInstanceState(Bundle bundle) {
//		super.onSaveInstanceState(bundle);
//		uiHelper.onSaveInstanceState(bundle);
//	}
//
//	@Override
//	public void onPause() {
//		super.onPause();
//		uiHelper.onPause();
//	}
//
//	@Override
//	public void onDestroy() {
//		super.onDestroy();
//		uiHelper.onDestroy();
//	}
//	
//	@Override
//	public void onCreate(Bundle savedInstanceState) {
//	    super.onCreate(savedInstanceState);
//	    uiHelper = new UiLifecycleHelper(getActivity(), callback);
//	    uiHelper.onCreate(savedInstanceState);
//	}
//	
//	@Override
//	public void onActivityResult(int requestCode, int resultCode, Intent data) {
//	    super.onActivityResult(requestCode, resultCode, data);
//	    if (requestCode == REAUTH_ACTIVITY_CODE) {
//	        uiHelper.onActivityResult(requestCode, resultCode, data);
//	    }
//	}
//
//	public void upload(String message) {
//
//
//		try{
//			File file = new File(mFilePath);
////			FileInputStream inputStream = new FileInputStream(file);
////			BitmapFactory.decodeFile(mFilePath);
////			ByteArrayOutputStream fos = new ByteArrayOutputStream((int) file.length()); 
////			byte[] buffer = new byte[1024];
////			int readcount = 0;
////			  
////			while((readcount=inputStream.read(buffer)) != -1) {
////				fos.write(buffer, 0, readcount);    // 파일 복사 
////			}
////			
////			byte[] imgData = fos.toByteArray();
////					
////			
////			String url = file.getAbsolutePath().toString();
//			
//			if (file.exists()) {
//				final Session session = Session.getActiveSession();
//				Request request = Request.newUploadPhotoRequest(session, file, new Request.Callback() {
//					
//					@Override
//					public void onCompleted(Response response) {
//						mDialog.dismiss();
//						if (mResultToast != null) {
//							mResultToast.resultToast(response);
//						}
////						if (response.getError() == null) {
////							Toast.makeText(getActivity(), "Success", Toast.LENGTH_LONG).show();
////							getActivity().finish();
////						} else {
////							Toast.makeText(getActivity(), response.getError().getErrorMessage(), Toast.LENGTH_LONG).show();
////						}
//					}
//				});
//				Bundle params = request.getParameters();
////				request.setParameters(params);
////				params.putString("source",url);//Facebook API
//				params.putString("name", message);
//				params.putString("message", "description  goes here");
//				request.setParameters(params);
//				if (mDialog == null) {
//					mDialog = Util.getDialog(getActivity());
//				}
//				mDialog.show();
//				request.executeAsync();
//				
//			}
//			
//		}catch (Exception e) {
//		}
//	 }
//	
//	public void setResultToast(ResultToast resultToast) {
//		mResultToast = resultToast;
//	}
}
