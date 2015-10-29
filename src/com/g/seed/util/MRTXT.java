package com.g.seed.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

import android.annotation.SuppressLint;

public class MRTXT
{
	public static String phoneFormat(String phone)
	{
		return phone.length() == 11 ? phone.replace(phone.substring(3, 7), "****") : phone;
		
	}
	
	@SuppressLint({ "SimpleDateFormat" })
	public static String date() {
		return new SimpleDateFormat("yy-MM-dd HH:mm:ss").format(new Date());
	}
	
	@SuppressLint({ "SimpleDateFormat" })
	public static Date date(String date) {
		try {
			return new SimpleDateFormat("yy-MM-dd HH:mm:ss").parse(date);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static String holdC(Object number, int holdCount)
	{
		String strPercentTemp = number.toString();
		if (strPercentTemp.indexOf(".") == -1)
			return holdC(strPercentTemp + ".", holdCount);
		do
			strPercentTemp = strPercentTemp + "0";
		while (holdCount - strPercentTemp.substring(strPercentTemp.indexOf(".") + 1).length() > 0);
		
		strPercentTemp = strPercentTemp.substring(0, strPercentTemp.indexOf(".") + 1 + holdCount);
		return strPercentTemp;
	}
	
	public static boolean checkCellphoneNumber(String mobile)
	{
		return Pattern.matches("(\\+\\d+)?(852\\d{8}$)|(1[3458]\\d{9}$)", mobile);
	}
	
}
