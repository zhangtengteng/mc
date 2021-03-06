package com.chehui.maiche;

import cn.jpush.android.api.JPushInterface;
import android.app.Application;
import android.util.Log;

public class MyApplication extends Application {
	private static final String TAG = "JPush";

	@Override
	public void onCreate() {
		super.onCreate();
		Log.d(TAG, "+++++++++++++++++[ExampleApplication] onCreate");
		super.onCreate();
		JPushInterface.setDebugMode(true); // 设置开启日志,发布时请关闭日志
		JPushInterface.init(this); //
	}
}
