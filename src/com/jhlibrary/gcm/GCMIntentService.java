package com.jhlibrary.gcm;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.android.gcm.GCMBaseIntentService;
import com.jhlibrary.util.Util;


public class GCMIntentService extends GCMBaseIntentService {
	private static final String tag = "GCMIntentService";
	public static final String ME = "GCMReceiver";

	public GCMIntentService() {
		super(Util.getResourceBundle("sender_id"));
	}

	/** 푸시로 받은 메시지 */
	@Override
	protected void onMessage(Context context, Intent intent) {
		
	}

	/** 에러 발생시 */
	@Override
	protected void onError(Context context, String errorId) {
		Log.d(tag, "onError. errorId : " + errorId);
	}

	/** 단말에서 GCM 서비스 등록 했을 때 등록 id를 받는다 */
	@Override
	protected void onRegistered(Context context, String regId) {
		Log.d(tag, "onRegistered. regId : " + regId);
	}

	/** 단말에서 GCM 서비스 등록 해지를 하면 해지된 등록 id를 받는다 */
	@Override
	protected void onUnregistered(Context context, String regId) {
		Log.d(tag, "onUnregistered. regId : " + regId);
	}
}
