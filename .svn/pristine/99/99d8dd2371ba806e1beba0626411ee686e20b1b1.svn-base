package com.chehui.maiche.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

/**
 * Created by hu on 2014/9/28.
 */
public class BitmapUtils {
	/**
	 * 
	 * @param imgPath
	 * @param bitmap
	 * @return
	 */
	public static String bitmapToBase64(Bitmap bitmap) {
		if (null == bitmap) {
			return null;
		}

		return Base64.encodeToString(Bitmap2Bytes(bitmap, 100), Base64.DEFAULT);
	}

	/**
	 * 
	 * @param base64Data
	 * @param imgName
	 */
	public static Bitmap base64ToBitmap(String base64Data) {
		if (StringUtils.isEmpty(base64Data)) {
			return null;
		}

		byte[] bytes = Base64.decode(base64Data, Base64.DEFAULT);
		Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
		return bitmap;
	}

	/**
	 * 图片转换成字节数�?
	 * 
	 * @param bm
	 *            图片对象
	 * @return
	 */
	public static byte[] Bitmap2Bytes(Bitmap bm, int rate) {
		byte[] imageBytes = null;
		ByteArrayOutputStream baos = null;

		try {
			if (null != bm) {
				baos = new ByteArrayOutputStream();
				bm.compress(Bitmap.CompressFormat.JPEG, rate, baos);
				imageBytes = baos.toByteArray();
			}
		} catch (Exception e) {
			LogN.e(BitmapUtils.class, "Bitmap2Bytes Exception" + e.getMessage());
		} finally {
			Utils.closeIO(baos);
		}

		return imageBytes;
	}

	/**
	 * 
	 * @param filePath
	 * @param width
	 * @param height
	 * @return
	 */
	public static Bitmap createImageThumbnail(String filePath, int width,
			int height) {
		Bitmap bitmap = null;
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filePath, opts);

		opts.inSampleSize = computeSampleSize(opts, -1, width * height);
		opts.inJustDecodeBounds = false;

		try {
			bitmap = BitmapFactory.decodeFile(filePath, opts);
		} catch (Exception e) {
			LogN.e(BitmapUtils.class,
					"createImageThumbnail Exception:" + e.getMessage());
		}
		return bitmap;
	}

	/**
	 * 
	 * @param options
	 * @param minSideLength
	 * @param maxNumOfPixels
	 * @return
	 */
	public static int computeSampleSize(BitmapFactory.Options options,
			int minSideLength, int maxNumOfPixels) {
		int initialSize = computeInitialSampleSize(options, minSideLength,
				maxNumOfPixels);
		int roundedSize;
		if (initialSize <= 8) {
			roundedSize = 1;
			while (roundedSize < initialSize) {
				roundedSize <<= 1;
			}
		} else {
			roundedSize = (initialSize + 7) / 8 * 8;
		}
		return roundedSize;
	}

	/**
	 * 
	 * @param options
	 * @param minSideLength
	 * @param maxNumOfPixels
	 * @return
	 */
	private static int computeInitialSampleSize(BitmapFactory.Options options,
			int minSideLength, int maxNumOfPixels) {
		double w = options.outWidth;
		double h = options.outHeight;
		int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math
				.sqrt(w * h / maxNumOfPixels));
		int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(
				Math.floor(w / minSideLength), Math.floor(h / minSideLength));
		if (upperBound < lowerBound) {
			// return the larger one when there is no overlapping zone.
			return lowerBound;
		}
		if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
			return 1;
		} else if (minSideLength == -1) {
			return lowerBound;
		} else {
			return upperBound;
		}
	}

	/***
	 * 保存bitmap到本地 返回路径
	 * @param bm
	 * @param picName
	 * @return
	 */
	public static String saveBitmap(Bitmap bm, String picName) {
		String innerSDCardPath = FilePathUtils.getInnerSDCardPath();
		File f = new File(innerSDCardPath+"/", picName);
		LogN.e(BitmapUtils.class, "innerSDCardPath is  "+innerSDCardPath);
		if (f.exists()) {
			f.delete();
		}
		try {
			FileOutputStream out = new FileOutputStream(f);
			bm.compress(Bitmap.CompressFormat.JPEG, 90, out);
			out.flush();
			out.close();
			LogN.e (BitmapUtils.class, "saveBitmap success");
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return f.getPath();
	}
}
