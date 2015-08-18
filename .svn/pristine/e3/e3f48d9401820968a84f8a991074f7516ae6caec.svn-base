package com.chehui.maiche.custom;

public class BasePopuWindow {

	private static volatile BasePopuWindow instance;

	private BasePopuWindow() {
	}

	/**
	 * 创建单例类，提供静态方法调用
	 * 
	 * @return ActivityManager
	 */
	public static BasePopuWindow getInstance() {
		if (instance == null) {
			instance = new BasePopuWindow();
		}
		return instance;
	}
}
