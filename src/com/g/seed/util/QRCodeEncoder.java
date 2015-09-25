package com.g.seed.util;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

public class QRCodeEncoder {
	private String value = null;
	
	public Bitmap change() throws WriterException{
		return change(180, 180);
	}
	
	public Bitmap change(String value) throws WriterException{
		return this.setValue(value).change();
	}
	
	public Bitmap change(String value, int width) throws WriterException{
		return this.setValue(value).change(width, width);
	}

	public Bitmap change(int width, int height) throws WriterException {
		if(value == null || "".equals(value)) return null;
		
		Bitmap bitmap = 
				Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		
		int[] pixels = createPixels(width, height);
		bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
		return bitmap;
	}
	
	public void putOn(ImageView imageView) throws WriterException{
		Bitmap qrcode = change(imageView.getWidth(), imageView.getHeight());
		imageView.setImageBitmap(qrcode);
	}

	private int[] createPixels(int width, int height) throws WriterException {
		BitMatrix matrix = new MultiFormatWriter()
			.encode(value, BarcodeFormat.QR_CODE, width, height);
				
		int[] pixels = new int[width * height];
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (matrix.get(x, y)) {
					pixels[y * width + x] = 0xff000000;
				}
			}
		}
		
		return pixels;
	}

	public String getValue() {
		return value;
	}

	public QRCodeEncoder setValue(String value) {
		this.value = value;
		return this;
	}
	
}
