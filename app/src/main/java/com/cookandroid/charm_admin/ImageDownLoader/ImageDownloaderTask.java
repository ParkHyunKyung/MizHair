package com.cookandroid.charm_admin.ImageDownLoader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;

public class ImageDownloaderTask extends AsyncTask<String, Void, Bitmap>
{
	public String url;
	public String targetUrl;
	private WeakReference<ImageView> imageViewReference;

	public ImageDownloaderTask(String url, ImageView imageView)
	{
		this.targetUrl = url;
		this.imageViewReference = new WeakReference<ImageView>(imageView);
	}

	@Override
	protected Bitmap doInBackground(String... params)
	{
		return downloadBitmap(params[0]);
	}

	@Override
	protected void onPostExecute(Bitmap bitmap)
	{
		if(isCancelled())
		{
			bitmap = null;
		}

		if(imageViewReference != null)
		{
			ImageView imageView = imageViewReference.get();
			ImageDownloaderTask bitmapDownloaderTask = getBitmapDownloaderTask(imageView);

			if(this == bitmapDownloaderTask)
			{
				ImageDownloader.mImageCache.put(targetUrl, bitmap);
				imageView.setImageBitmap(bitmap);
			}
		}
	}

	private ImageDownloaderTask getBitmapDownloaderTask(ImageView imageView)
	{
		if(imageView != null)
		{
			Drawable drawable = imageView.getDrawable();
			if(drawable instanceof ImageDownloader.DownloadedDrawable)
			{
				ImageDownloader.DownloadedDrawable downloadedDrawable = (ImageDownloader.DownloadedDrawable) drawable;
				return downloadedDrawable.getBitmapDownloaderTask();
			}
		}
		return null;
	}

	static Bitmap downloadBitmap(String url)
	{
		final HttpClient client = new DefaultHttpClient();
		final HttpGet getRequest = new HttpGet(url);

		try
		{
			HttpResponse response = client.execute(getRequest);
			final int statusCode = response.getStatusLine().getStatusCode();
			if(statusCode != HttpStatus.SC_OK)
			{
				Log.w("ImageDownloader", "Error " + statusCode + " while retrieving bitmap from " + url);
				return null;
			}

			final HttpEntity entity = response.getEntity();
			if(entity != null)
			{
				InputStream inputStream = null;
				//BitmapFactory.Options options = new BitmapFactory.Options();
				//options.inSampleSize = 2;

				try
				{
					inputStream = entity.getContent();
					final Bitmap bitmap = BitmapFactory.decodeStream(new FlushedInputStream(inputStream));
					//final Bitmap bitmap = BitmapFactory.decodeStream(new FlushedInputStream(inputStream), null, options);
					return setSizeBitmap(bitmap);
				}
				finally
				{
					if(inputStream != null)
					{
						inputStream.close();
					}
					entity.consumeContent();
				}
			}
		}
		catch(Exception e)
		{
			getRequest.abort();
		}
		return null;
	}

	static class FlushedInputStream extends FilterInputStream
	{
		public FlushedInputStream(InputStream inputStream)
		{
			super(inputStream);
		}

		@Override
		public long skip(long n) throws IOException
		{
			long totalBytesSkipped = 0L;
			while(totalBytesSkipped < n)
			{
				long bytesSkipped = in.skip(n - totalBytesSkipped);
				if(bytesSkipped == 0L)
				{
					int bytes = read();
					if(bytes < 0)
					{
						break; // we reached EOF
					}
					else
					{
						bytesSkipped = 1; // we read one byte
					}
				}
				totalBytesSkipped += bytesSkipped;
			}
			return totalBytesSkipped;
		}
	}
	public static Bitmap setSizeBitmap(Bitmap bitmap){
		Bitmap result;
		if(bitmap.getWidth()>bitmap.getHeight()){
			result = Bitmap.createBitmap(bitmap
					, (bitmap.getWidth()-bitmap.getHeight()) / 2 //X 시작위치
					, 0 //Y 시작위치
					, bitmap.getHeight() // 넓이
					, bitmap.getHeight()); // 높이
		}else{
			result = Bitmap.createBitmap(bitmap
					, 0 //X 시작위치
					, (bitmap.getHeight()-bitmap.getWidth()) / 2  //Y 시작위치
					, bitmap.getWidth() // 넓이
					, bitmap.getWidth()); // 높이
		}

		if (result != bitmap) {
			bitmap.recycle();
		}

		return resizeBitmap(result);
	}

	static public Bitmap resizeBitmap(Bitmap original) {

		int resizeWidth = 700;

		double aspectRatio = (double) original.getHeight() / (double) original.getWidth();
		int targetHeight = (int) (resizeWidth * aspectRatio);
		Bitmap result = Bitmap.createScaledBitmap(original, resizeWidth, targetHeight, false);
		if (result != original) {
			original.recycle();
		}
		return getCircleBitmap(result);
	}

	public static Bitmap getCircleBitmap(Bitmap bitmap) {
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(output);
		final int color = 0xff424242;
		final Paint paint = new Paint();
		Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		if(bitmap.getWidth()<bitmap.getHeight()){
			rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		}else {
			rect = new Rect(0, 0, bitmap.getHeight(), bitmap.getWidth());
		}
		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		int size = (bitmap.getWidth()/2);
		if(bitmap.getWidth()<bitmap.getHeight()){
			size = (bitmap.getWidth()/2);
		}else {
			size = (bitmap.getHeight()/2);
		}
		canvas.drawCircle(size, size, size, paint);
		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);
		return output;
	}
}
