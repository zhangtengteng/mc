package com.chehui.maiche.conifg;

import java.io.File;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class GlobalConfig {
	@SuppressWarnings("unused")
	private final String clientType = "android";

	public static DeviceInfo deviceInfo; // 设备信息
	private static GlobalConfig globalConfig;// 唯一配置实例
	// private String projectName = "business";
	// private String packageName = "com.moon.business";

	public String sdPath; // 用于存放日志，缓存等到外置SD卡上
	public String sdRootPath; // 外置存储根目录
	public String dataPath;// 用户存放数据等到私有目录下
	public String imagePath; // 图像目录
	public String sdcachePath; // 缓存目录
	public String sdlogPath; // 日志目录
	public String databasePath;// 数据库目录
	public String voicePath;// 订单录音目录
	public String dataFileName = "business.db"; // 数据库文件名称
	// ordertable数据表
	public String orderTableName = "ordertable";
	// 是否由push启动
	public boolean isPush_ = false;
	// 是否由order订单信息
	public boolean isOrder_ = false;
	// 保存程序所有已打开页面 需要在程序onCreate函数中调用
	private static ArrayList<Activity> actiList_ = null;

	/** 获取全局唯一实例 */
	public static GlobalConfig getGlobalConfig(Context context) {
		if (globalConfig == null) {
			globalConfig = new GlobalConfig();
			// globalConfig.init(context);
		}
		return globalConfig;
	}

	//
	// /** 加载配置信息 */
	// public void init(Context context) {
	// // 加载设备信息
	// deviceInfo = new DeviceInfo();
	// deviceInfo.initDeviceInfo(context);
	// // 初始化文件目录
	// String fileSeparator = System.getProperty("file.separator");
	// // sd卡目录
	// sdPath = android.os.Environment.getExternalStorageDirectory()
	// .getAbsolutePath();
	// // business根目录
	// sdRootPath = sdPath + fileSeparator + projectName;
	// // 普通数据
	// dataPath = sdRootPath + fileSeparator + "data" + fileSeparator;
	// // 数据库
	// databasePath = sdRootPath + fileSeparator + "db" + fileSeparator;
	// // 图片
	// imagePath = sdRootPath + fileSeparator + "image" + fileSeparator;
	// // 缓存
	// sdcachePath = sdRootPath + fileSeparator + "cache" + fileSeparator;
	// // 日志
	// sdlogPath = sdRootPath + fileSeparator + "log" + fileSeparator;
	// // 订单录音目录
	// voicePath = sdRootPath + fileSeparator + "voice" + fileSeparator;
	// //
	// // 初始化数据库，数据，图片文件夹
	// checkAndCreatePrivateDirectory();
	// // 初始化缓存，日志文件夹
	// checkAndCreateSdDirectory();
	// // 初始化保存页面列表
	// actiList_ = new ArrayList<Activity>(0);
	// // 初始化数据库及数据表
	// initDb();
	// return;
	// }

	// //初始化数据库及数据表
	// private void initDb(){
	// //查看business.db库是否存在，不存在则创建
	// BasicDataBase businessDb = new BasicDataBase();
	// businessDb.open(dataFileName,true, databasePath );
	// //判断ordertable是否存在，不存在则创建
	// boolean isOrderTableExist = businessDb.isTableExist(orderTableName);
	// if(!isOrderTableExist){
	// businessDb.executeUpdate(Constants.CREATE_ORDERTABLE);
	// }
	// businessDb.close();
	// }
	//
	@SuppressWarnings("unused")
	private void checkAndCreatePrivateDirectory() {
		// 创建私有文件夹
		String[] path = { databasePath, dataPath, imagePath, voicePath };
		for (String x : path) {
			File fi = new File(x);
			if (!fi.exists()) {
				fi.mkdir();
			}
		}
	}

	// /** 检查并创建SD文件夹 */
	// private void checkAndCreateSdDirectory() {
	// String sdpath = android.os.Environment.getExternalStorageDirectory()
	// .getAbsolutePath();
	//
	// if ((!TextUtils.isEmpty(sdpath) && new File(sdpath).canRead())) {
	// // SD卡可用时再创建
	// String[] path = { sdcachePath, sdlogPath };
	// for (String x : path) {
	// File fi = new File(x);
	// if (!fi.exists()) {
	// fi.mkdir();
	// } else {
	// FileUtil.DeleteFile(x);
	// fi.mkdir();
	// }
	// }
	// // 禁止系统Media搜索程序目录;
	// String nomediapath = sdRootPath + ".nomedia";
	// File nomedia = new File(nomediapath);
	// try {
	// if (!nomedia.exists())
	// nomedia.createNewFile();
	// } catch (IOException e) {
	// }
	// }
	// }
	//
	// /** 程序退出清空缓存数据 */
	// public void exit() {
	// FileUtil.DeleteFile(sdcachePath);
	// FileUtil.DeleteFile(sdlogPath);
	// }

	/** 添加Activity到列表，Activity页面构建onCreate函数内调用该函数 */
	public void addActivity(Activity activity) {
		if (activity != null) {
			if (actiList_ == null) {
				actiList_ = new ArrayList<Activity>(0);
			}
			actiList_.add(activity);
		}
		return;
	}

	/** 移除Activity从列表，Activity页面销毁onDestory函数内调用该函数 */
	public void removeActivity(Activity activity) {
		actiList_.remove(activity);
		if (!activity.isFinishing()) {
			activity.finish();
		}
		return;
	}

	/** 获取程序最上层Activity */
	public Activity getTopActivity() {
		if (actiList_ == null || actiList_.size() == 0) {
			return null;
		}
		return actiList_.get(actiList_.size() - 1);
	}

	// /** 获取MainActivity */
	// public Activity getMainActivity() {
	// if (actiList_ == null || actiList_.size() == 0)
	// {
	// return null;
	// }
	// for (Activity activity : actiList_)
	// {
	// if (activity instanceof MainActivity)
	// {
	// return activity;
	// }
	// }
	// return null;
	// }
	//
	// /** 跳转至主页面 关闭顶部所有页面*/
	// public void goMainActivity() {
	// if (actiList_ == null || actiList_.size() == 0)
	// {
	// return;
	// }
	// ArrayList<Activity> destoryList = new ArrayList<Activity>();
	// //过滤出需删除list
	// for (int i = (actiList_.size()-1); i >=0;i--)
	// {
	// Activity activity = actiList_.get(i);
	// if (activity instanceof MainActivity)
	// {
	// break;
	// }else{
	// destoryList.add(activity);
	// }
	// }
	// //删除数据
	// for(int i = 0 ;i<destoryList.size();i++){
	// removeActivity(destoryList.get(i));
	// }
	// }

	/** 获取程序页面列表 */
	public ArrayList<Activity> getActivitys() {
		return actiList_;
	}

	/** 设置token */
	public void setToken(Context context, String token) {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(context);
		Editor pEdit = prefs.edit();
		pEdit.putString("token", token);
		pEdit.commit();
	}

	/** 获取token */
	public String getToken(Context context) {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(context);
		String token = prefs.getString("token", "");
		return token;
		// return "1cc21eb231d81a0120df3cf908bcfd1a";

	}
}