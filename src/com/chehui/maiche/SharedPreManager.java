package com.chehui.maiche;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.chehui.maiche.utils.LogN;

/**
 * 
 * @author zzp
 * 
 */
public class SharedPreManager extends BaseManager {
	private SharedPreferences sp = null;

	// 远程服务器IP
	public static final String HOST_IP = "host";

	// 远程服务器端口
	public static final String HOST_PORT = "port";

	/** 用户姓名 */
	public static final String USER_NAME = "name";

	/** 用户密码 */
	public static final String USER_PWD = "pwd";

	private static String CONFIG_FILE_NAME = "chehui_maiche.config";

	private volatile static SharedPreManager instance = null;

	public SharedPreManager() {

	}

	public static SharedPreManager getInstance() {
		if (instance == null) {
			instance = new SharedPreManager();
		}

		return instance;
	}

	@Override
	public void init(Context context) {
		super.init(context);
		sp = context.getSharedPreferences(CONFIG_FILE_NAME,
				Context.MODE_PRIVATE);
	}

	/**
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public String getString(String key, String defaultValue) {
		String ret = defaultValue;

		if (isInit && sp != null) {
			ret = sp.getString(key, defaultValue);
		} else {
			LogN.e(this, "init() method should call first or sp is null");
		}

		return ret;
	}

	/**
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public int getInt(String key, int defaultValue) {
		int ret = defaultValue;

		if (isInit && sp != null) {
			ret = sp.getInt(key, defaultValue);
		} else {
			LogN.e(this, "init() method should call first or sp is null");
		}

		return ret;
	}

	/**
	 * 
	 * @param key
	 * @param value
	 */
	public void setString(String key, String value) {
		if (isInit && sp != null) {
			Editor ed = sp.edit();
			ed.putString(key, value);
			ed.commit();
		} else {
			LogN.e(this, "init() method should call first or sp is null");
		}
	}

	/**
	 * 
	 * @param key
	 * @param value
	 */
	public void setInt(String key, int value) {
		if (isInit && sp != null) {
			Editor ed = sp.edit();
			ed.putInt(key, value);
			ed.commit();
		} else {
			LogN.e(this, "init() method should call first or sp is null");
		}
	}
}
