package com.jhlibrary.util;

import android.content.Context;
import android.media.MediaScannerConnection;
import android.media.MediaScannerConnection.MediaScannerConnectionClient;
import android.net.Uri;

public class MediaScanner {
	private Context mContext;

	private String mPath;

	private MediaScannerConnection mMediaScanner;
	private MediaScannerConnectionClient mMediaScannerClient;

	public static MediaScanner newInstance(Context context) {
		return new MediaScanner(context);
	}

	private MediaScanner(Context context) {
		mContext = context;
	}

	public void mediaScanning(final String path) {

		if (mMediaScanner == null) {
			mMediaScannerClient = new MediaScannerConnectionClient() {

				@Override
				public void onMediaScannerConnected() {
					mMediaScanner.scanFile(mPath, null); // 디렉토리
					// 가져옴
				}

				@Override
				public void onScanCompleted(String path, Uri uri) {

				}
			};
			mMediaScanner = new MediaScannerConnection(mContext, mMediaScannerClient);
		}

		mPath = path;
		mMediaScanner.connect();
	}

//	public void scanKeyFiles() {
//
//		mMediaScannerClient = new MediaScannerConnectionClient() {
//
//			public void onMediaScannerConnected() {// 미디어 스케너와 연결되면
//				FilenameFilter FF;
//				File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/NPKI");// SD카드의
//				// 디렉토리를
//				// 얻어오고
//				file.listFiles(FF);// 파일 탐색을 시작한다. 원래는 리턴형이 File[] 형이지만
//				// 하나의 디렉토리만 조사하는것이아니라 모든 SD카드의 파일을 읽어야함으로,
//				// ArrayList에 파일 객체를 담아둔다.
//
//				if (files.size() != 0) {
//					for (int i = 0; i < files.size(); i++) {
//						msc.scanFile(files.get(i).getAbsolutePath(), null);
//					}
//
//					Log.e(TAG, "findFile Count" + files.size() + ", FileName : " + files.get(0).getName());
//
//					File f = files.get(0);
//					new xmlParser(f);
//
//				}
//			}
//
//			public void onScanCompleted(String path, Uri uri) {
//				Log.e(TAG, "onScanCompleted");
//
//			}
//
//		};
//
//		FF = new FilenameFilter() {
//			public boolean accept(File dir, String name) {
//				if (new File(dir, name).isDirectory()) {
//					new File(dir, name).listFiles(FF);// SD메모리 전체를 탐색하기위해서 재귀형태로
//														// 탐색을 한다.
//				} else if (name.endsWith(".DEV")) {// 파일필터 확장자 .DEV로 필터하여 리스트에
//													// 저장한다.
//					files.add(new File(dir + "/" + name));
//				}
//				return true;
//			}
//		};
//
//		msc = new MediaScannerConnection(context, mScanClient);
//	}
}
