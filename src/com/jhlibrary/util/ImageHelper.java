package com.jhlibrary.util;

import java.lang.ref.WeakReference;
import java.util.HashMap;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

public class ImageHelper {

	/*
	 * 이미지 다운로드를 위한 FLAG
	 */
	public static final int IMGAE_CACHE_LIMIT_SIZE = 50;
	public static HashMap<String, Bitmap> mImageCache = new HashMap<String, Bitmap>();

	/**
	 * 비트맵에 라운드를 적용한다.
	 * 
	 * @param bitmap : 라운드 적용할 이미지
	 * @param pixels : 라운드 크기.
	 * @return
	 */
	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int pixels) {
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final int color = 0xffffffff;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);
		final float roundPx = pixels;

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);

		return output;
	}

	/**
	 * 서버에 있는 이미지를 다운로드하여 화면에 보여준다.
	 * 
	 * @param url : 파일 주소
	 * @param imageView : 이미지를 보여줄 뷰.
	 */
	public static void download(String url, ImageView imageView) {
		Bitmap cachedImage = mImageCache.get(url);
		if (cachedImage != null) {
			imageView.setImageBitmap(cachedImage);
		} else if (cancelPotentialDownload(url, imageView)) {
			if (mImageCache.size() > IMGAE_CACHE_LIMIT_SIZE) {
				mImageCache.clear();
			}

			ImageDownloaderTask task = new ImageDownloaderTask(url, imageView);
			DownloadedDrawable downloadedDrawable = new DownloadedDrawable(task);
			imageView.setImageDrawable(downloadedDrawable);
			task.execute(url);
		}
	}
	public static void download(String url, ImageView imageView, Drawable drawable) {
		Bitmap cachedImage = mImageCache.get(url);
		if (cachedImage != null) {
			imageView.setImageBitmap(cachedImage);
		} else if (cancelPotentialDownload(url, imageView)) {
			if (mImageCache.size() > IMGAE_CACHE_LIMIT_SIZE) {
				mImageCache.clear();
			}
			
			ImageDownloaderTask task = new ImageDownloaderTask(url, imageView);
			DownloadedDrawable downloadedDrawable = new DownloadedDrawable(task);
			imageView.setImageDrawable(downloadedDrawable);
			drawable = downloadedDrawable;
			task.execute(url);
		}
	}
	public static void download(String url, onDownloadCompleteListener listener) {
			
		ImageDownloaderTask task = new ImageDownloaderTask(url, listener);
		task.execute(url);
	}

	private static boolean cancelPotentialDownload(String url, ImageView imageView) {
		ImageDownloaderTask bitmapDownloaderTask = getBitmapDownloaderTask(imageView);

		if (bitmapDownloaderTask != null) {
			String bitmapUrl = bitmapDownloaderTask.url;
			if ((bitmapUrl == null) || (!bitmapUrl.equals(url))) {
				bitmapDownloaderTask.cancel(true);
			} else {
				return false;
			}
		}
		return true;
	}

	private static ImageDownloaderTask getBitmapDownloaderTask(ImageView imageView) {
		if (imageView != null) {
			Drawable drawable = imageView.getDrawable();
			if (drawable instanceof DownloadedDrawable) {
				DownloadedDrawable downloadedDrawable = (DownloadedDrawable) drawable;
				return downloadedDrawable.getBitmapDownloaderTask();
			}
		}
		return null;
	}

	static class DownloadedDrawable extends ColorDrawable {
		private final WeakReference<ImageDownloaderTask> bitmapDownloaderTaskReference;

		public DownloadedDrawable(ImageDownloaderTask bitmapDownloaderTask) {
			super(Color.TRANSPARENT);
			bitmapDownloaderTaskReference = new WeakReference<ImageDownloaderTask>(bitmapDownloaderTask);
		}

		public ImageDownloaderTask getBitmapDownloaderTask() {
			return bitmapDownloaderTaskReference.get();
		}
	}
	
	public static interface onDownloadCompleteListener {
		void onComplete( Bitmap bitmap );
	};
}
