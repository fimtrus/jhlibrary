package com.jhlibrary.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.jhlibrary.R;

/**
 * Dialog 클래스
 */
public class CommonDialogs extends Dialog {

	private Context mContext;
	private ProgressDialog progressDialog;
	protected boolean destroyed = false;

	public CommonDialogs(Context context) {
		super(context);
		mContext = context;
	}

	public void showLoadingProgressDialog(Activity activity) {
		this.showProgressDialog(activity, mContext.getResources().getString(R.string.loading));
	}

	public void showProgressDialog(final Activity activity, CharSequence message) {

		if (progressDialog == null) {
			progressDialog = new ProgressDialog(mContext);
			progressDialog.setIndeterminate(true);
		}

		progressDialog.setCancelable(true);
		progressDialog.setOnCancelListener(new OnCancelListener() {

			@Override
			public void onCancel(DialogInterface dialog) {
				destroyed = true;
				dialog.dismiss();
				activity.finish();
			}

		});

		progressDialog.setMessage(message);
		progressDialog.show();

	}

	public void dismissProgressDialog() {
		if (progressDialog != null && !destroyed) {
			progressDialog.dismiss();
		}
	}

	public void showNetworkFalseDialog() {
		if (!destroyed) {
			AlertDialog.Builder alertDialog = null;
			alertDialog = new AlertDialog.Builder(mContext);
			alertDialog.setMessage(mContext.getResources().getString(R.string.network_connect_error));
			alertDialog.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					((Activity) mContext).finish();
				}
			});
			alertDialog.show();
		}
	}

	public void showAlertDialog(Context context, String message) {

		AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
		alertDialog.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
			}
		});
		alertDialog.setMessage(message);
		alertDialog.show();
	}

	public void showAlertDialog(Context context, int stringId) {
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
		alertDialog.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
			}
		});
		alertDialog.setMessage(stringId);
		alertDialog.show();
	}

	public void showAlertDialog(Context context, int stringId, DialogInterface.OnClickListener okListener,
			DialogInterface.OnClickListener cancelListener) {
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
		alertDialog.setCancelable(false);
		if (okListener != null) {
			alertDialog.setPositiveButton(android.R.string.ok,okListener);
		}
		if (cancelListener != null) {
			alertDialog.setNegativeButton(android.R.string.cancel, cancelListener);
		}
		alertDialog.setMessage(stringId);
		alertDialog.show();
	}
	public void showAlertDialog(Context context, String message, DialogInterface.OnClickListener okListener,
			DialogInterface.OnClickListener cancelListener) {
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
		alertDialog.setCancelable(false);
		if (okListener != null) {
			alertDialog.setPositiveButton(android.R.string.ok,okListener);
		}
		if (cancelListener != null) {
			alertDialog.setNegativeButton(android.R.string.cancel, cancelListener);
		}
		alertDialog.setMessage(message);
		alertDialog.show();
	}

	@Override
	public boolean isShowing() {
		return super.isShowing();
	}
	
	public AlertDialog getConfirmDialog(int title, int stringId, DialogInterface.OnClickListener okListener,
			DialogInterface.OnClickListener cancelListener) {
		AlertDialog.Builder alertDialog;
		if (Util.isICS()) {
			alertDialog = new AlertDialog.Builder(mContext);
//			alertDialog = new AlertDialog.Builder(mContext, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
		} else {
			alertDialog = new AlertDialog.Builder(mContext);
		}
		if (okListener != null) {
			alertDialog.setPositiveButton(android.R.string.ok,okListener);
		}
		if (cancelListener != null) {
			alertDialog.setNegativeButton(android.R.string.cancel, cancelListener);
		}
		alertDialog.setCancelable(false);
		if (title > 0) {
			alertDialog.setTitle(title);
		}
		alertDialog.setMessage(stringId);
		return alertDialog.create();
	}
	public AlertDialog getConfirmDialog(String title, String message, DialogInterface.OnClickListener okListener,
			DialogInterface.OnClickListener cancelListener) {
		AlertDialog.Builder alertDialog;
		if (Util.isICS()) {
			alertDialog = new AlertDialog.Builder(mContext);
//			alertDialog = new AlertDialog.Builder(mContext, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
		} else {
			alertDialog = new AlertDialog.Builder(mContext);
		}
		if (okListener != null) {
			alertDialog.setPositiveButton(android.R.string.ok,okListener);
		}
		if (cancelListener != null) {
			alertDialog.setNegativeButton(android.R.string.cancel, cancelListener);
		}
		alertDialog.setCancelable(false);
		if (title != null) {
			alertDialog.setTitle(title);
		}
		alertDialog.setMessage(message);
		return alertDialog.create();
	}
	
	public AlertDialog showListDialog( String title, int stringArrayId, DialogInterface.OnClickListener listener ) {
		
		AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
//		List<String> list = Arrays.asList( mContext.getResources().getStringArray(stringArrayId) );
//		ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, list);
		if ( title != null ) {
			builder.setTitle(title);
		}
		builder.setItems(stringArrayId, listener);
		AlertDialog dialog = builder.create();
		dialog.show();
		return dialog;
	}
	public AlertDialog showListDialog( String title, String[] strings, DialogInterface.OnClickListener listener ) {
		
		AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
//		List<String> list = Arrays.asList( mContext.getResources().getStringArray(stringArrayId) );
//		ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, list);
		if ( title != null ) {
			builder.setTitle(title);
		}
		builder.setItems(strings, listener);
		AlertDialog dialog = builder.create();
		dialog.show();
		return dialog;
	}
	public AlertDialog showListDialog( int title, int stringArrayId, DialogInterface.OnClickListener listener ) {
		
		AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
//		List<String> list = Arrays.asList( mContext.getResources().getStringArray(stringArrayId) );
//		ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, list);
		if ( title > 0 ) {
			builder.setTitle(title);
		}
		builder.setItems(stringArrayId, listener);
		AlertDialog dialog = builder.create();
		dialog.show();
		return dialog;
	}
	
	/**
	 * InputDialog를 호출한다.
	 */
	public Dialog showInputDialog (int titleRes, final CommonDialogs.OnClickListener clickListener, TextWatcher watcher ) {
		
		LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View layout = inflater.inflate(R.layout.view_input_password, null);
		
		AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mContext);
		dialogBuilder.setTitle( titleRes );
		dialogBuilder.setCancelable(false);
		dialogBuilder.setView(layout);
		
		final EditText editText = (EditText) layout.findViewById(R.id.edittext_password);
		
		if ( watcher != null ) {
			editText.addTextChangedListener(watcher);
		}
		
		dialogBuilder.setPositiveButton(android.R.string.ok, null);
		dialogBuilder.setNegativeButton(android.R.string.cancel, null);
		
		final AlertDialog dialog = dialogBuilder.create();
		
		dialog.setOnShowListener(new DialogInterface.OnShowListener() {
			@Override
			public void onShow(DialogInterface arg0) {
				
				InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
		        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
		        
		        
				Button positiveButton = ( (AlertDialog) arg0 ).getButton(AlertDialog.BUTTON_POSITIVE);
				Button negativeButton = ( (AlertDialog) arg0 ).getButton(AlertDialog.BUTTON_NEGATIVE);
				//Or AlertDialog.BUTTON_NEGATIVE
				positiveButton.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						
						if ( clickListener != null ) {
							clickListener.onClick(true, editText.getText().toString(), dialog, v, editText);
						}
					}
				});
				negativeButton.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						clickListener.onClick(false, editText.getText().toString(), dialog, v, editText);
						dialog.dismiss();
					}
				});
			}
		});
		
		dialog.show();
		editText.requestFocus();
		return dialog;
	}
	/**
	 * InputDialog를 호출한다.
	 */
	public Dialog showImageDialog (int titleRes, int positiveButton, int negativeButton, Bitmap bitmap, final CommonDialogs.OnClickListener clickListener ) {
		
		LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View layout = inflater.inflate(R.layout.view_imageview_password, null);
		
		AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mContext);
		if ( titleRes > 0 ) {
			
			dialogBuilder.setTitle( titleRes );
		}
		dialogBuilder.setCancelable(false);
		dialogBuilder.setView(layout);
		
		final ImageView imageView = (ImageView) layout.findViewById(R.id.imageview_preview);
		
		imageView.setImageBitmap(bitmap);
		
		dialogBuilder.setPositiveButton(positiveButton, null);
		dialogBuilder.setNegativeButton(negativeButton, null);
		
		final AlertDialog dialog = dialogBuilder.create();
		
		
		dialog.setOnShowListener(new DialogInterface.OnShowListener() {
			@Override
			public void onShow(DialogInterface arg0) {
				Button positiveButton = ( (AlertDialog) arg0 ).getButton(AlertDialog.BUTTON_POSITIVE);
				Button negativeButton = ( (AlertDialog) arg0 ).getButton(AlertDialog.BUTTON_NEGATIVE);
				//Or AlertDialog.BUTTON_NEGATIVE
				positiveButton.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						
						if ( clickListener != null ) {
							clickListener.onClick(true, "", dialog, v, null);
						}
					}
				});
				negativeButton.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						clickListener.onClick(false, "", dialog, v, null);
						dialog.dismiss();
					}
				});
			}
		});
		dialog.show();
		
		return dialog;
	}
	/**
	 * InputDialog를 호출한다.
	 */
	public Dialog showInputAndImageDialog (int titleRes, Bitmap bitmap, String defaultText, final CommonDialogs.OnClickListener clickListener, TextWatcher watcher ) {
		
		LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View layout = inflater.inflate(R.layout.view_input_imageview_password, null);
		
		AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mContext);
		dialogBuilder.setTitle( titleRes );
		dialogBuilder.setCancelable(false);
		dialogBuilder.setView(layout);
		
		final EditText editText = (EditText) layout.findViewById(R.id.edittext_password);
		
		editText.setText(defaultText);
		
		final ImageView imageView = (ImageView) layout.findViewById(R.id.imageview_preview);
		
		imageView.setImageBitmap(bitmap);
		
		dialogBuilder.setPositiveButton(android.R.string.ok, null);
		dialogBuilder.setNegativeButton(android.R.string.cancel, null);
		
		final AlertDialog dialog = dialogBuilder.create();
		
		if ( watcher != null ) {
			editText.addTextChangedListener(watcher);
		}
		
		
		dialog.setOnShowListener(new DialogInterface.OnShowListener() {
			@Override
			public void onShow(DialogInterface arg0) {
				
				InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
		        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
		        
				Button positiveButton = ( (AlertDialog) arg0 ).getButton(AlertDialog.BUTTON_POSITIVE);
				Button negativeButton = ( (AlertDialog) arg0 ).getButton(AlertDialog.BUTTON_NEGATIVE);
				//Or AlertDialog.BUTTON_NEGATIVE
				positiveButton.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						
						if ( clickListener != null ) {
							clickListener.onClick(true, editText.getText().toString(), dialog, v, editText);
						}
					}
				});
				negativeButton.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						clickListener.onClick(false, editText.getText().toString(), dialog, v, editText);
						dialog.dismiss();
					}
				});
			}
		});
		
		dialog.show();
		
		editText.requestFocus();
		
		return dialog;
	}
	/**
	 * InputDialog를 호출한다.
	 */
	public Dialog showInputDialog (int titleRes, String defaultText, final CommonDialogs.OnClickListener clickListener, TextWatcher watcher ) {
		
		LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View layout = inflater.inflate(R.layout.view_input_password, null);
		
		AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mContext);
		dialogBuilder.setTitle( titleRes );
		dialogBuilder.setCancelable(false);
		dialogBuilder.setView(layout);
		
		final EditText editText = (EditText) layout.findViewById(R.id.edittext_password);
		
		editText.setText(defaultText);
		
		dialogBuilder.setPositiveButton(android.R.string.ok, null);
		dialogBuilder.setNegativeButton(android.R.string.cancel, null);
		
		final AlertDialog dialog = dialogBuilder.create();
		
		if ( watcher != null ) {
			editText.addTextChangedListener(watcher);
		}
		
		
		dialog.setOnShowListener(new DialogInterface.OnShowListener() {
			@Override
			public void onShow(DialogInterface arg0) {
				
				InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
		        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
				
				Button positiveButton = ( (AlertDialog) arg0 ).getButton(AlertDialog.BUTTON_POSITIVE);
				Button negativeButton = ( (AlertDialog) arg0 ).getButton(AlertDialog.BUTTON_NEGATIVE);
				//Or AlertDialog.BUTTON_NEGATIVE
				positiveButton.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						
						if ( clickListener != null ) {
							clickListener.onClick(true, editText.getText().toString(), dialog, v, editText);
						}
					}
				});
				negativeButton.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						clickListener.onClick(false, editText.getText().toString(), dialog, v, editText);
						dialog.dismiss();
					}
				});
			}
		});
		
		dialog.show();
		
		editText.requestFocus();
		
		return dialog;
	}
	
	
	public interface OnClickListener {
		void onClick( boolean isPositive, String text, AlertDialog dialog, View button, EditText editText );
	}
	
	/**
	 * 동그란 프로그래스바를 생성한다.
	 * 
	 * @param context
	 * @return Dialog
	 */
	public Dialog showDialog(DialogInterface.OnCancelListener cancelListener ) {
		Dialog dialog;
		dialog = new Dialog(mContext, R.style.Theme_CustomDialog);
		dialog.setCancelable(false);
		LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.dialog_activity, null);
		dialog.setContentView(view);
		if ( cancelListener != null) {
			
			dialog.setOnCancelListener(cancelListener);
		}
		dialog.show();
		return dialog;
	}
	
}



