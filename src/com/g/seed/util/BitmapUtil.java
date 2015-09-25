package com.g.seed.util;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.Drawable;

public class BitmapUtil {

	public static final int ALL = 347120;
	public static final int TOP = 547120;
	public static final int LEFT = 647120;
	public static final int RIGHT = 747120;
	public static final int BOTTOM = 847120;

	/**
	 * 将图片转为黑白
	 * 
	 * @param original
	 * @return
	 */
	public static Bitmap toGrayscale(Bitmap original) {
		int width, height;
		height = original.getHeight();
		width = original.getWidth();
		final Bitmap bmpGrayscale = Bitmap.createBitmap(width, height, Config.RGB_565);
		final Canvas canvas = new Canvas(bmpGrayscale);
		final Paint paint = new Paint();
		final ColorMatrix cm = new ColorMatrix();
		cm.setSaturation(0);
		final ColorMatrixColorFilter cmf = new ColorMatrixColorFilter(cm);
		paint.setColorFilter(cmf);
		canvas.drawBitmap(original, 0, 0, paint);
		return bmpGrayscale;
	}
	
	public static Bitmap aa(int width, int height, RectF rectF, float round,
			float radius) {
		Bitmap bmp = Bitmap
				.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		final Canvas canvas = new Canvas(bmp);
		Paint paint = new Paint();
		paint.setColor(0x00000000);
		paint.setAntiAlias(true);
		canvas.drawRoundRect(rectF, round, round, paint);

		paint.setColor(0xffcccccc);
		paint.setMaskFilter(new BlurMaskFilter(radius,
				BlurMaskFilter.Blur.OUTER));
		canvas.drawRoundRect(rectF, round, round, paint);
		return bmp;
	}

	/**
	 * 该函数实现对图像进行二值化处理
	 * 
	 * @param graymap
	 * @return
	 */
	public static Bitmap gray2Binary(Bitmap graymap) {
		// 得到图形的宽度和长度
		final int width = graymap.getWidth();
		final int height = graymap.getHeight();
		// 创建二值化图像
		Bitmap binarymap = null;
		binarymap = graymap.copy(Config.ARGB_8888, true);
		// 依次循环，对图像的像素进行处理
		for (int i = 0; i < width; i++)
			for (int j = 0; j < height; j++) {
				// 得到当前像素的值
				final int col = binarymap.getPixel(i, j);
				// 得到alpha通道的值
				final int alpha = col & 0xFF000000;
				// 得到图像的像素RGB的值
				final int red = (col & 0x00FF0000) >> 16;
				final int green = (col & 0x0000FF00) >> 8;
				final int blue = (col & 0x000000FF);
				// 用公式X = 0.3×R+0.59×G+0.11×B计算出X代替原来的RGB
				int gray = (int) (red * 0.3 + green * 0.59 + blue * 0.11);
				// 对图像进行二值化处理
				if (gray <= 95)
					gray = 0;
				else
					gray = 255;
				// 新的ARGB
				final int newColor = alpha | (gray << 16) | (gray << 8) | gray;
				// 设置新图像的当前像素值
				binarymap.setPixel(i, j, newColor);
			}
		return binarymap;
	}

	/**
	 * 放大缩小图片
	 * 
	 * @param bitmap
	 * @param w
	 * @param h
	 * @return
	 */
	public static Bitmap zoomBitmap(Bitmap bitmap, int w, int h) {
		final int width = bitmap.getWidth();
		final int height = bitmap.getHeight();
		final Matrix matrix = new Matrix();
		final float scaleWidht = ((float) w / width);
		final float scaleHeight = ((float) h / height);
		matrix.postScale(scaleWidht, scaleHeight);
		final Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
		return newbmp;
	}

	/**
	 * 将Drawable转化为Bitmap
	 * 
	 * @param drawable
	 * @return
	 */
	public static Bitmap drawableToBitmap(Drawable drawable) {
		final int width = drawable.getIntrinsicWidth();
		final int height = drawable.getIntrinsicHeight();
		final Bitmap bitmap = Bitmap.createBitmap(width, height,
				drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
						: Bitmap.Config.RGB_565);
		final Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, width, height);
		drawable.draw(canvas);
		return bitmap;

	}

	public static Bitmap roundedCorner(Drawable drawable, float roundPx) {
		return roundedCorner(drawableToBitmap(drawable), roundPx);
	}

	/**
	 * 获得圆角图片的方法
	 * 
	 * @param bitmap
	 * @param roundPx
	 * @return
	 */
	public static Bitmap roundedCorner(Bitmap bitmap, float roundPx) {

		final Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(),
				Config.ARGB_8888);
		final Canvas canvas = new Canvas(output);

		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);

		return output;
	}

	public static Bitmap reflection(Drawable drawable) {
		return createReflectionImageWithOrigin(drawableToBitmap(drawable));
	}

	/**
	 * 获得带倒影的图片方法
	 * 
	 * @param bitmap
	 * @return
	 */
	public static Bitmap createReflectionImageWithOrigin(Bitmap bitmap) {
		final int reflectionGap = 4;
		final int width = bitmap.getWidth();
		final int height = bitmap.getHeight();

		final Matrix matrix = new Matrix();
		matrix.preScale(1, -1);

		final Bitmap reflectionImage = Bitmap.createBitmap(bitmap, 0, height / 2, width,
				height / 2, matrix, false);

		final Bitmap bitmapWithReflection = Bitmap.createBitmap(width, (height + height / 2),
				Config.ARGB_8888);

		final Canvas canvas = new Canvas(bitmapWithReflection);
		canvas.drawBitmap(bitmap, 0, 0, null);
		final Paint deafalutPaint = new Paint();
		canvas.drawRect(0, height, width, height + reflectionGap, deafalutPaint);

		canvas.drawBitmap(reflectionImage, 0, height + reflectionGap, null);

		final Paint paint = new Paint();
		final LinearGradient shader = new LinearGradient(0, bitmap.getHeight(), 0,
				bitmapWithReflection.getHeight() + reflectionGap, 0x70ffffff, 0x00ffffff,
				TileMode.CLAMP);
		paint.setShader(shader);
		// Set the Transfer mode to be porter duff and destination in
		paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
		// Draw a rectangle using the paint with our linear gradient
		canvas.drawRect(0, height, width, bitmapWithReflection.getHeight() + reflectionGap, paint);

		return bitmapWithReflection;
	}

	public static Bitmap drawShadow(Drawable drawable) {
		return drawShadow(drawableToBitmap(drawable));
	}

	public static Bitmap drawShadow(Bitmap bitmap) {
		final BlurMaskFilter blurFilter = new BlurMaskFilter(20, BlurMaskFilter.Blur.OUTER);

		final Paint paint = new Paint();
		paint.setMaskFilter(blurFilter);

		final Bitmap originalBitmap = bitmap;
		final Bitmap shadowBitmap = originalBitmap.extractAlpha(paint, null);
		final Bitmap shadowImage32 = shadowBitmap.copy(Bitmap.Config.ARGB_8888, true);

		final Canvas c = new Canvas(shadowImage32);

		c.drawBitmap(originalBitmap, 20, 20, null);
		return shadowImage32;
	}

	public static Bitmap round(int type, Bitmap bitmap, int roundPx) {
		try {
			final int width = bitmap.getWidth();
			final int height = bitmap.getHeight();

			final Bitmap paintingBoard = Bitmap.createBitmap(width, height, Config.ARGB_8888);
			final Canvas canvas = new Canvas(paintingBoard);
			canvas.drawARGB(Color.TRANSPARENT, Color.TRANSPARENT, Color.TRANSPARENT,
					Color.TRANSPARENT);

			final Paint paint = new Paint();
			paint.setAntiAlias(true);
			paint.setColor(Color.BLACK);

			if (TOP == type)
				clipTop(canvas, paint, roundPx, width, height);
			else if (LEFT == type)
				clipLeft(canvas, paint, roundPx, width, height);
			else if (RIGHT == type)
				clipRight(canvas, paint, roundPx, width, height);
			else if (BOTTOM == type)
				clipBottom(canvas, paint, roundPx, width, height);
			else
				clipAll(canvas, paint, roundPx, width, height);

			paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
			final Rect src = new Rect(0, 0, width, height);
			final Rect dst = src;
			canvas.drawBitmap(bitmap, src, dst, paint);
			return paintingBoard;
		} catch (final Exception exp) {
			return bitmap;
		}
	}

	private static void clipLeft(final Canvas canvas, final Paint paint, int offset, int width,
			int height) {
		final Rect block = new Rect(offset, 0, width, height);
		canvas.drawRect(block, paint);
		final RectF rectF = new RectF(0, 0, offset * 2, height);
		canvas.drawRoundRect(rectF, offset, offset, paint);
	}

	private static void clipRight(final Canvas canvas, final Paint paint, int offset, int width,
			int height) {
		final Rect block = new Rect(0, 0, width - offset, height);
		canvas.drawRect(block, paint);
		final RectF rectF = new RectF(width - offset * 2, 0, width, height);
		canvas.drawRoundRect(rectF, offset, offset, paint);
	}

	private static void clipTop(final Canvas canvas, final Paint paint, int offset, int width,
			int height) {
		final Rect block = new Rect(0, offset, width, height);
		canvas.drawRect(block, paint);
		final RectF rectF = new RectF(0, 0, width, offset * 2);
		canvas.drawRoundRect(rectF, offset, offset, paint);
	}

	private static void clipBottom(final Canvas canvas, final Paint paint, int offset, int width,
			int height) {
		final Rect block = new Rect(0, 0, width, height - offset);
		canvas.drawRect(block, paint);
		final RectF rectF = new RectF(0, height - offset * 2, width, height);
		canvas.drawRoundRect(rectF, offset, offset, paint);
	}

	private static void clipAll(final Canvas canvas, final Paint paint, int offset, int width,
			int height) {
		final RectF rectF = new RectF(0, 0, width, height);
		canvas.drawRoundRect(rectF, offset, offset, paint);
	}

}