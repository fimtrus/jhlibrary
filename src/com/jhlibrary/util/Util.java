/**
 * Utils.java.java        May 21, 2012
 *
 */
package com.jhlibrary.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.ParseException;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.gc.android.market.api.MarketSession;
import com.gc.android.market.api.MarketSession.Callback;
import com.gc.android.market.api.model.Market.App;
import com.gc.android.market.api.model.Market.AppsRequest;
import com.gc.android.market.api.model.Market.AppsResponse;
import com.gc.android.market.api.model.Market.ResponseContext;
import com.google.android.gcm.GCMRegistrar;
import com.jhlibrary.CommonApplication;
import com.jhlibrary.R;
import com.jhlibrary.constant.Key;

/**
 * 
 * 
 * @author jong-hyun.jeong
 */
public class Util {

	private static String SENDER_ID = "sender_id";

	public static int TAKE_CAMERA = 1;					// 카메라 리턴 코드값 설정
	public static int TAKE_GALLERY = 2;				// 앨범선택에 대한 리턴 코드값 설정
	/**
	 * 동그란 프로그래스바를 생성한다.
	 * 
	 * @param context
	 * @return Dialog
	 */
	public static Dialog getDialog(final Activity context) {
		Dialog dialog;
		dialog = new Dialog(context, R.style.Theme_CustomDialog);
		dialog.setCancelable(false);
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.dialog_activity, null);
		dialog.setContentView(view);
		return dialog;
	}

	/**
	 * 동그란 프로그래스바를 생성한다.
	 * 
	 * @param context
	 * @return Dialog
	 */
	public static DialogFragment getFragmentDialog(final FragmentManager manager, final android.support.v4.app.FragmentActivity context) {
		DialogFragment dialog;
		dialog = new DialogFragment() {

			@Override
			public Dialog onCreateDialog(Bundle savedInstanceState) {
				Dialog dialog;
				dialog = new Dialog(context, R.style.Theme_CustomDialog);
				dialog.setCancelable(false);
				LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
				View view = inflater.inflate(R.layout.dialog_activity, null);
				dialog.setContentView(view);
				return dialog;
			}

		};
		dialog.setCancelable(false);
		dialog.show(manager, "dialog");
		// LayoutInflater inflater = (LayoutInflater)
		// context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		// View view = inflater.inflate(R.layout.dialog_activity, null);
		// dialog.set(view);
		return dialog;
	}

	/**
	 * 이미지 관련 처리를 수행한다. 회전을 시키며, 이미지를 화면에 맞게 줄인다.
	 */
	public static Bitmap getImageProcess(Bitmap bmp, int nRotate, int viewW, int viewH) {

		Matrix matrix = new Matrix();

		// 이미지의 해상도를 줄인다.
		/*
		 * float scaleWidth = ((float) newWidth) / width; float scaleHeight =
		 * ((float) newHeight) / height; matrix.postScale(scaleWidth,
		 * scaleHeight);
		 */

		matrix.postRotate(nRotate); // 회전
		// 이미지를 회전시킨다
		Bitmap resize = Bitmap.createBitmap(bmp, 0, 0, viewW, viewH, matrix, true);
		// View 사이즈에 맞게 이미지를 조절한다.
		// Bitmap resize = Bitmap.createScaledBitmap(rotateBitmap, viewW, viewH,
		// true);

		return resize;
	}

	/**
	 * 이미지 관련 처리를 수행한다. 회전을 시키며, 이미지를 화면에 맞게 줄인다.
	 */
	public static Bitmap getImageProcess(Bitmap bmp, int nRotate) {

		Matrix matrix = new Matrix();

		// 이미지의 해상도를 줄인다.
		/*
		 * float scaleWidth = ((float) newWidth) / width; float scaleHeight =
		 * ((float) newHeight) / height; matrix.postScale(scaleWidth,
		 * scaleHeight);
		 */

		matrix.postRotate(nRotate); // 회전
		// 이미지를 회전시킨다
		Bitmap resize = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, true);
		// View 사이즈에 맞게 이미지를 조절한다.
		// Bitmap resize = Bitmap.createScaledBitmap(rotateBitmap, viewW, viewH,
		// true);

		return resize;
	}

	/**
	 * Assets에 저장되어있는 데이터를 불러온다.
	 * 
	 * @param resourceName
	 * @return
	 */
	public static String getResourceBundle(String resourceName) {
		ResourceBundle resource = ResourceBundle.getBundle("assets.auth-config");
		String bundle = resource.getString(resourceName);
		return bundle;
	}

	/**
	 * Preference에 저장된 내용들을 모두 지운다.
	 */
	public static void clearPreference(Context context) {
		SharedPreferences pref = context.getSharedPreferences(context.getPackageName(), Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = pref.edit();
		editor.clear();
		editor.commit();
	}

	/**
	 * Preference에 저장된 내용들 얻어온다. Key가 필요하다.
	 * 
	 * @param name
	 *            : Key
	 * @return
	 */
	public static Object getPreference(Context context, String name) {

		SharedPreferences pref = context.getSharedPreferences(context.getPackageName(), Activity.MODE_PRIVATE);
		Map<String, ?> map = pref.getAll();
		Object obj = map.get(name);

		return obj;
	}
	

	/**
	 * Preference에 데이터를 저장한다.
	 */
	public static void setPreference(Context context, String name, Object value) {

		SharedPreferences pref = context.getSharedPreferences(context.getPackageName(), Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = pref.edit();

		if (value instanceof Boolean) {
			editor.putBoolean(name, (Boolean) value);
		} else if (value instanceof Float) {
			editor.putFloat(name, (Float) value);
		} else if (value instanceof Integer) {
			editor.putInt(name, (Integer) value);
		} else if (value instanceof String) {
			editor.putString(name, (String) value);
		} else if (value instanceof Long) {
			editor.putLong(name, (Long) value);
		}
		editor.commit();
	}
	/**
	 * Preference에 저장된 내용들 얻어온다. Key가 필요하다.
	 * 
	 * @param name
	 *            : Key
	 * @return
	 */
	public static String getCryptPreference(Context context, String name) {
		
		SharedPreferences pref = context.getSharedPreferences(context.getPackageName(), Activity.MODE_PRIVATE);
		
		String value = pref.getString(name, null);
		String decryptValue = null;
		
		if ( value != null ) {
			try {
				decryptValue = AesCrypto.decrypt(value);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return decryptValue;
	}
	
	
	/**
	 * Preference에 데이터를 저장한다.
	 */
	public static void setCryptPreference(Context context, String name, String value) {
		
		SharedPreferences pref = context.getSharedPreferences(context.getPackageName(), Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = pref.edit();
		
		if (value instanceof String) {
			
			String cryptValue = null;
			try {
				cryptValue = AesCrypto.encrypt(value);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if ( cryptValue != null ) {
				editor.putString(name, cryptValue );
				editor.commit();
			}
		} 

	}

	/**
	 * Pixel을 DP로 변경해주는 메서드
	 * 
	 * @param dp
	 * @return
	 */
	public static int getPxFromDp(float dp) {
		int px = 0;
		Context appContext = CommonApplication.getApplication();
		px = (int) (dp * appContext.getResources().getDisplayMetrics().density);
		return px;
	}

	/**
	 * 메일을 보낸다. 파라미터가 null일경우에는 해당 필드는 첨부하지 않는다.
	 * 
	 * @param context
	 * @param emails
	 * @param title
	 * @param message
	 * @param filePath
	 */
	public static void sendMail(Context context, String[] emails, String title, String message, String filePath) {
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
		intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
		intent.setType("image/jpg");
		
		// 수신인 주소 - tos배열의 값을 늘릴 경우 다수의 수신자에게 발송됨
		if (emails != null) {
			intent.putExtra(Intent.EXTRA_EMAIL, emails);
		}
		if (title != null) {
			intent.putExtra(Intent.EXTRA_SUBJECT, title);
		}
		if (message != null) {
			intent.putExtra(Intent.EXTRA_TEXT, message);
		}
		if (filePath != null) {
			// File객체로부터 Uri값 생성
			File file = new File(filePath);
			final Uri fileUri = Uri.fromFile(file);
			intent.putExtra(Intent.EXTRA_STREAM, fileUri);
		}
		context.startActivity(intent);
	}
	
	public static void registerGcm() {
		
		Context context = CommonApplication.getApplication();
		
		GCMRegistrar.checkDevice(context);
		GCMRegistrar.checkManifest(context);
		
		final String regId = GCMRegistrar.getRegistrationId(context);
		if (regId.equals("")) {
			String senderId = Util.getResourceBundle(SENDER_ID );
			if (senderId == null) return;
			GCMRegistrar.register(context, senderId);
		} else {
		}
	}
	
	public static void registerGcm(Context context) {
		
		GCMRegistrar.checkDevice(context);
		GCMRegistrar.checkManifest(context);
		
		final String regId = GCMRegistrar.getRegistrationId(context);
		if (regId.equals("")) {
			String senderId = Util.getResourceBundle(SENDER_ID );
			if (senderId == null) return;
			GCMRegistrar.register(context, senderId);
		} else {
		}
	}
	
	public static boolean isICS() {
		int SDK_INT = android.os.Build.VERSION.SDK_INT;
		if (SDK_INT > Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
			return true;
		}
		return false;
	}
	
	public static boolean isLite(Context context) {
		Object obj = getPreference(context, Key.IS_PERCHASED);
		
		if (!context.getPackageName().toLowerCase().contains("lite")) {
			return false;
		} else {
			if (obj == null || ((Boolean)obj) == false) {
				return true;
			} else {
				return false;
			}
		}
	}
	
	public static String requestPost(final String url, List<NameValuePair> params) {
		String response = null;
		HttpClient client = AndroidHttpClient.newInstance("Android");
		HttpParams httpParams = client.getParams();
        HttpPost post = new HttpPost(url); 
        
        UrlEncodedFormEntity entity = null;
		try {
			entity = new UrlEncodedFormEntity(params, HTTP.UTF_8);
			post.setEntity(entity);
	        HttpResponse httpRes;
			httpRes = client.execute(post);
	        HttpEntity httpEntity = httpRes.getEntity();
	        if (httpEntity != null)
	        {
					response = EntityUtils.toString(httpEntity);
	        }
		} catch (UnsupportedEncodingException e) {
		} catch (ClientProtocolException e1) {
		} catch (IOException e1) {
			Log.i("WLB", e1.toString());
		} catch (ParseException e) {
		}
		return response;
	}
	
	/**
	 * 내장카메라를 호출한다.
	 * @param context : activity의 context
	 * @param type : Util.TAKE_CAMERA, Util.TAKE_GALLERY
	 */
	public static void startCamera(Context context, int type) {
		Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        ((Activity) context).startActivityForResult(intent, type);
	}
	
	public static DisplayMetrics getMetrics () {
		DisplayMetrics matrix = new DisplayMetrics();
		((WindowManager)CommonApplication.getApplication().getSystemService(Context.WINDOW_SERVICE))
		.getDefaultDisplay().getMetrics(matrix); // 화면 정보를 가져와서
		return matrix;
	}
	
	/**
	 * 인터넷이 연결되어 있는지 확인한다.
	 * @param context
	 * @return
	 */
	public static boolean isOnline(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		
		if (ni != null) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean isRooted ( ) {
		try {
		    Runtime.getRuntime().exec("su");
		    return true;
		} catch ( Exception e) {
		    // 루팅 안되있으면 Exception
			return false;
		}
	}
	
	/**
	 * 버전 이름을 얻는다.
	 * @param context
	 * @return versionCode
	 */
	public static String getVersionName(Context context) {
		try {
			PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
			return packageInfo.versionName;
		} catch (NameNotFoundException e) {
			return null;
		}
	}
	/**
	 * 버전 코드를 얻는다.
	 * @param context
	 * @return versionCode
	 */
	public static int getVersionCode(Context context) {
		try {
			PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
			return packageInfo.versionCode;
		} catch (NameNotFoundException e) {
			return -1;
		}
	}

	public static void updateCheck(final Context context) {

		new AsyncTask<Void, Void, Boolean>() {

			@Override
			protected Boolean doInBackground(Void... params) {
				
				try {
					MarketSession session = new MarketSession();
					session.login("fimtrusmarket@gmail.com", "wjdwhdgus1"); // 구글 아무 계정이나 되는듯..?
					session.getContext().setAndroidId(getAndroidId(context));
					// session.getContext.setAndroidId(myAndroidId);
					String query = context.getPackageName(); // 앱 이름 또는 패키지 명 다 쿼리 되는듯.. 근데
													// 내꺼 안뜨냐 ?
					AppsRequest appsRequest = AppsRequest.newBuilder().setQuery(query).setStartIndex(0).setEntriesCount(1)
							.setWithExtendedInfo(true).build();
					session.append(appsRequest, new Callback<AppsResponse>() {
						@Override
						public void onResult(ResponseContext responseContext, AppsResponse response) {
							
							App app = response.getApp(0);
							int marketCode = app.getVersionCode();
							int currentCode = getVersionCode(context);
							
							if ( marketCode > currentCode ) {
								publishProgress(null);
//								Toast.makeText(context, "market :  "+ marketCode + " current : " + currentCode, Toast.LENGTH_SHORT).show();
							}
						}
					});
					session.flush();
				} catch ( Exception e ) {
					
				}
				
				return null;
			}

			@Override
			protected void onProgressUpdate(Void... values) {
				super.onProgressUpdate(values);
				
				AlertDialog.Builder builder = new AlertDialog.Builder(context); 
				builder.setCancelable(false);
				
				builder.setTitle(R.string.title_update);
				builder.setMessage(R.string.update_description);
				builder.setPositiveButton(android.R.string.ok, null);
				builder.setNegativeButton(android.R.string.cancel, null);
				
				final AlertDialog dialog = builder.create();
				
				dialog.setOnShowListener(new DialogInterface.OnShowListener() {
				    @Override
				    public void onShow(DialogInterface arg0) {
				        Button positiveButton = ( (AlertDialog) arg0 ).getButton(AlertDialog.BUTTON_POSITIVE);
				        Button negativeButton = ( (AlertDialog) arg0 ).getButton(AlertDialog.BUTTON_NEGATIVE);
				        
				        positiveButton.setOnClickListener(new View.OnClickListener() {
				            @Override
				            public void onClick(View view) {
				            	Intent intent = new Intent();
				            	intent.setData( Uri.parse("market://details?id=" + context.getPackageName() ) ); 
				            	context.startActivity(intent);
				            	dialog.dismiss();
				            }
				        });
				        negativeButton.setOnClickListener(new View.OnClickListener() {
				        	@Override
				        	public void onClick(View view) {
				        		dialog.dismiss();
				        	}
				        });
				    }
				});
				
				dialog.show();
			}
		}.execute();
	}
	
	private static final Uri URI = Uri.parse("content://com.google.android.gsf.gservices");
    private static final String ID_KEY = "android_id";
     
    /**
     * 안드로이드 아이디를 읽어온다.<br>
     * <uses-permission android:name="com.google.android.providers.gsf.permission.READ_GSERVICES" />
     * 퍼미션 추가 필요.
     * @param context
     * @return
     */
    public static String getAndroidId(Context context) {
        String[] params = { ID_KEY };
        Cursor c = context.getContentResolver()
                .query(URI, null, null, params, null);
     
        if (!c.moveToFirst() || c.getColumnCount() < 2)
            return null;
     
        try {
            return Long.toHexString(Long.parseLong(c.getString(1)));
        } catch (NumberFormatException e) {
            return null;
        }
    }
    
    /**
     * 해당 뷰의 스크린샷을 찍어 파일로 저장한다.
     * @param view
     * @param path
     */
    public static boolean saveScreen( Context context, View view, String path ) {
		
		Bitmap bitmap = snapScreen(view);
		
		try {
			File file = new File( path );
			FileOutputStream fos = new FileOutputStream( file );
			bitmap.compress( CompressFormat.JPEG, 100, fos );
			fos.flush();
			fos.close();
			context.sendBroadcast(new Intent( Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file)) );
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		
	}
    /**
     * 해당 뷰의 스크린샷을 찍어 파일로 저장한다.
     * @param view
     * @param path
     */
    public static boolean saveScreen( Context context, View view, File file ) {
    	
    	Bitmap bitmap = snapScreen(view);
    	
    	try {
    		FileOutputStream fos = new FileOutputStream( file );
    		bitmap.compress( CompressFormat.JPEG, 100, fos );
    		fos.flush();
    		fos.close();
    		context.sendBroadcast(new Intent( Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file)) );
    		return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
    	
    }
    /**
     * 해당 뷰의 스크린샷을 찍어 리턴한다.
     * @param view
     * @param path
     */
    public static Bitmap snapScreen( View view ) {
    	view.setDrawingCacheEnabled(true);
    	view.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
    	
    	Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
    	
    	view.setDrawingCacheEnabled(false);
    	
    	return bitmap;
    	
    }
    
    public static Bitmap resizeBitmap ( Context context, Uri uri, int resizeWidth, int resizeHeight ) {
    	
    	Bitmap bitmap = null;
    	Bitmap resizedBitmap = null;
    	
		try {
			bitmap = Images.Media.getBitmap(context.getContentResolver(), uri);
			
			resizedBitmap = Bitmap.createScaledBitmap(bitmap, resizeWidth, resizeHeight, true);
			bitmap.recycle();
		} catch (FileNotFoundException e) {
		} catch (Exception e) {
		}
		return resizedBitmap;
    }
    public static Bitmap resizeBitmap ( Context context, String path, int resizeWidth, int resizeHeight ) {
    	
    	Bitmap bitmap = null;
    	Bitmap resizedBitmap = null;
    	
    	try {
    		
    		bitmap = BitmapFactory.decodeFile(path);
    		
    		resizedBitmap = Bitmap.createScaledBitmap(bitmap, resizeWidth, resizeHeight, true);
    		bitmap.recycle();
    	}catch (Exception e) {
    	}
    	return resizedBitmap;
    }
    public static Bitmap resizeBitmap ( Context context, Bitmap bitmap, int resizeWidth, int resizeHeight ) {
    	
    	Bitmap resizedBitmap = null;
    	
    	try {
    		
    		resizedBitmap = Bitmap.createScaledBitmap(bitmap, resizeWidth, resizeHeight, true);
    		bitmap.recycle();
    	} catch (Exception e) {
    	}
    	return resizedBitmap;
    }
    
    /**
     * 하위 디렉토리 및 파일들을 모두 삭제한다.
     * @param path
     */
    public static void removeDirectory ( String path ) {
		
		File dir = new File(path);
		
    	File[] childDirectories = dir.listFiles();
    	
    	if ( childDirectories != null ) {
    		
    		for ( File d : childDirectories) {
    			
    			if ( d.isDirectory() ) {
    				removeDirectory(d.getAbsolutePath());
    			} else {
    				if ( d.exists() ) {
    					d.delete();
    				}
    			}
    		}
    	}
    	
    	dir.delete();
	}
    
    public static boolean isTablet(Context context) {
    	if ( (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_XLARGE
//    			|| (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE 
    			) {
    		return true;
    	}
    	return false;
    }
}
