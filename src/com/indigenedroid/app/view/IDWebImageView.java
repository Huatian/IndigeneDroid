package com.indigenedroid.app.view;

import java.io.File;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.v4.util.LruCache;
import android.util.AttributeSet;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.indigenedroid.app.util.BitmapUtil;

public class IDWebImageView extends NetworkImageView {

	public IDWebImageView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public IDWebImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public IDWebImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public static void fetchBitmap(String paramString, ImageLoader.ImageListener paramImageListener) {
		VolleyImageCaceh.instance(null).getImageLoader().get(paramString, paramImageListener);
	}

	public void setCircleImageUrl(String paramString, int defaultImage) {
		setTransform(new CircleTransform());
		setImageUrl(paramString, VolleyImageCaceh.instance(null).getImageLoader());
		setDefaultImageResId(defaultImage);
	}

	public void setImageUrl(String paramString, int defaultImage) {
		if ((paramString == null) || (paramString.length() == 0)) {
			if (defaultImage != 0) {
				setImageResource(defaultImage);
				return;
			}
		}
		setImageUrl(paramString, VolleyImageCaceh.instance(null).getImageLoader());
		setDefaultImageResId(defaultImage);
	}

	public static class BitmapCache implements ImageLoader.ImageCache {
		private LruCache<String, Bitmap> mCache = new LruCache<String, Bitmap>(5242880) {
			protected int sizeOf(String paramAnonymousString, Bitmap paramAnonymousBitmap) {
				return paramAnonymousBitmap.getRowBytes() * paramAnonymousBitmap.getHeight();
			}
		};

		public Bitmap getBitmap(String paramString) {
			return (Bitmap) this.mCache.get(paramString);
		}

		public void putBitmap(String paramString, Bitmap paramBitmap) {
			this.mCache.put(paramString, paramBitmap);
		}
	}

	/*
	 * Extends from DisckBasedCache --> Utility from volley toolbox. Also
	 * implements ImageCache, so that we can pass this custom implementation to
	 * ImageLoader.
	 */
	public static class DiskBitmapCache extends DiskBasedCache implements ImageCache {

		public DiskBitmapCache(File rootDirectory, int maxCacheSizeInBytes) {
			super(rootDirectory, maxCacheSizeInBytes);
		}

		public DiskBitmapCache(File cacheDir) {
			super(cacheDir);
		}

		public Bitmap getBitmap(String url) {
			final Entry requestedItem = get(url);

			if (requestedItem == null)
				return null;

			return BitmapFactory.decodeByteArray(requestedItem.data, 0, requestedItem.data.length);
		}

		public void putBitmap(String url, Bitmap bitmap) {

			final Entry entry = new Entry();

			/*
			 * //Down size the bitmap.If not done, OutofMemoryError occurs while
			 * decoding large bitmaps. // If w & h is set during image request (
			 * using ImageLoader ) then this is not required.
			 * ByteArrayOutputStream baos = new ByteArrayOutputStream(); Bitmap
			 * downSized = BitmapUtil.downSizeBitmap(bitmap, 50);
			 * 
			 * downSized.compress(Bitmap.CompressFormat.JPEG, 100, baos); byte[]
			 * data = baos.toByteArray();
			 * 
			 * System.out.println("####### Size of bitmap is ######### "+url+" : "
			 * +data.length); entry.data = data ;
			 */

			entry.data = BitmapUtil.convertBitmapToBytes(bitmap);
			put(url, entry);
		}
	}

	static class CircleTransform extends NetworkImageView.VolleyTransform {

		@Override
		public Bitmap transform(Bitmap paramBitmap) {
			if (paramBitmap == null)
				return null;
			int i = paramBitmap.getWidth();
			int j = paramBitmap.getHeight();
			if ((i <= 0) || (j <= 0))
				return null;
			int k = 0;
			if (i <= j) {
				k = i / 2;
				
			} else {
				k = j / 2;
			}

			Bitmap localBitmap1 = Bitmap.createBitmap(k * 2, k * 2, Bitmap.Config.ARGB_8888);
			Canvas localCanvas1 = new Canvas(localBitmap1);
			Paint localPaint = new Paint(1);
			localPaint.setColor(-65536);
			//localPaint.setColor(Color.TRANSPARENT);
			localCanvas1.drawCircle(k, k, k, localPaint);
			localPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
			Bitmap localBitmap2 = Bitmap.createBitmap(k * 2, k * 2, Bitmap.Config.ARGB_8888);
			Canvas localCanvas2 = new Canvas(localBitmap2);
			localCanvas2.drawBitmap(paramBitmap, 0.0F, 0.0F, null);
			localCanvas2.drawBitmap(localBitmap1, 0.0F, 0.0F, localPaint);
			localBitmap1.recycle();
			return localBitmap2;
		}
	}

	public static class VolleyImageCaceh {
		static VolleyImageCaceh sCache;
		private ImageLoader mImageLoader;
		protected RequestQueue mQueue;

		public VolleyImageCaceh(Context paramContext) {
			this.mQueue = Volley.newRequestQueue(paramContext.getApplicationContext());
			this.mImageLoader = new ImageLoader(this.mQueue, new IDWebImageView.BitmapCache());

			/*this.mImageLoader = new ImageLoader(this.mQueue, new
			DiskBitmapCache(rootDirectory, 1000000));*/
		}

		public static VolleyImageCaceh instance(Context paramContext) {
			if (sCache == null) {
				sCache = new VolleyImageCaceh(paramContext);
				// rootDirectory = paramContext.getCacheDir();
			}

			return sCache;
		}

		public ImageLoader getImageLoader() {
			return this.mImageLoader;
		}
	}

}
