package com.chehui.maiche.conifg;

import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;

public class DeviceInfo {
	
	
	private String deviceIMEI;
	private String deviceIMSI;
	private String deviceName;
	public String firmware;
	private String language;
	public String deviceId;
	private boolean netConnect;
	private String netExtraType;
	private int    netType = -1;
	public String resolution;
	public String sdkVersion;
	private String simId;

	public int screenWidth;
	public int screenHeight;
	private float density;

	@SuppressWarnings("deprecation")
	public void initDeviceInfo(Context context) {

		TelephonyManager tmManager = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		deviceIMEI = tmManager.getDeviceId();
		//firmware = tmManager.getDeviceSoftwareVersion();
		netType = tmManager.getNetworkType();
		deviceIMSI = tmManager.getSubscriberId();
		
		DisplayMetrics displayMetric = context.getResources().getDisplayMetrics();
		displayMetric = context.getResources().getDisplayMetrics();
		screenWidth = displayMetric.widthPixels;
		screenHeight = displayMetric.heightPixels;
		density = displayMetric.density;
		resolution = String.valueOf(screenWidth) + " * " + String.valueOf(screenHeight);
		firmware   = Build.VERSION.RELEASE;
		sdkVersion = Build.VERSION.SDK;
		deviceId   = Build.MODEL;
		
	}
	
	public float getDensity() {
		return density;
	}

	public String getDeviceIMEI() {
		return deviceIMEI;
	}

	public String getDeviceIMSI() {
		return deviceIMSI;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public String getFirmware() {
		return firmware;
	}

	public String getLanguage() {
		return language;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public String getNetExtraType() {
		return netExtraType;
	}

	public int getNetType() {
		return netType;
	}

	public String getResolution() {
		return resolution;
	}

	public String getSdkVersion() {
		return sdkVersion;
	}

	public String getSimId() {
		return simId;
	}

	public boolean isNetConnect() {
		return netConnect;
	}

	public int getScreenWidth() {
		return screenWidth;
	}

	public int getScreenHeight() {
		return screenHeight;
	}

}
