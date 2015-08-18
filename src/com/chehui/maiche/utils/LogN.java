package com.chehui.maiche.utils;

import android.util.Log;

import com.chehui.maiche.BuildConfig;

public class LogN {
	private static final boolean DEBUG = true & BuildConfig.DEBUG;

	private static final boolean WARNING = true & BuildConfig.DEBUG;

	private static final boolean INFO = true & BuildConfig.DEBUG;

	private static final boolean ERROR = true & BuildConfig.DEBUG;

	private static final String TAG = "CheHui";

	public static void i(Object nvsObject, String msg) {
		if (null == nvsObject) {
			return;
		}
		i(nvsObject.getClass(), msg);
	}

	public static void w(Object nvsObject, String msg) {
		if (null == nvsObject) {
			return;
		}
		w(nvsObject.getClass(), msg);
	}

	public static void d(Object nvsObject, String msg) {
		if (null == nvsObject) {
			return;
		}
		d(nvsObject.getClass(), msg);
	}

	public static void e(Object nvsObject, String msg) {
		if (null == nvsObject) {
			return;
		}
		e(nvsObject.getClass(), msg);
	}

	public static void i(Class<? extends Object> nvsObject, String msg) {
		if (INFO && null != nvsObject) {
			Log.i(TAG, nvsObject.getSimpleName() + " | " + msg);
		}
	}

	public static void w(Class<? extends Object> nvsObject, String msg) {
		if (WARNING && null != nvsObject) {
			Log.w(TAG, nvsObject.getSimpleName() + " | " + msg);
		}
	}

	public static void d(Class<? extends Object> nvsObject, String msg) {
		if (DEBUG && null != nvsObject) {
			Log.d(TAG, nvsObject.getSimpleName() + " | " + msg);
		}
	}

	public static void e(Class<? extends Object> nvsObject, String msg) {
		if (ERROR && null != nvsObject) {
			Log.e(TAG, nvsObject.getSimpleName() + " | " + msg);
		}
	}

}
