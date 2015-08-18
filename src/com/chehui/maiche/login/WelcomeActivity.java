package com.chehui.maiche.login;

import java.util.ArrayList;
import java.util.List;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import cn.jpush.android.api.JPushInterface;

import com.chehui.maiche.ActivityManager;
import com.chehui.maiche.BaseActivity;
import com.chehui.maiche.MainActivity;
import com.chehui.maiche.R;
import com.chehui.maiche.SharedPreManager;
import com.chehui.maiche.comm.CommonData;
import com.chehui.maiche.httpserve.HttpService;
import com.chehui.maiche.utils.StringUtils;

/**
 * 
 * @author zzp
 *         <P>
 *         自动登陆
 *         <P>
 */
public class WelcomeActivity extends BaseActivity implements Runnable {

	/**
	 * 是否第一次使用
	 */
	private boolean isFirstUse;

	/** 返回json数据 */
	private String json;
	/** 用户名手机号 */
	private String username;
	/** 用户密码 */
	private String password;
	/** 登录参数 */
	private String loginParams;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);

		/*
		 * username = SharedPreManager.getInstance()
		 * .getString(CommonData.USER_PHONE, "15261825221").trim();
		 * 
		 * password = SharedPreManager.getInstance()
		 * .getString(CommonData.USER_PWD, "111111").trim();
		 */
		username = SharedPreManager.getInstance().getString(CommonData.USER_PHONE, "").trim();

		password = SharedPreManager.getInstance().getString(CommonData.USER_PWD, "").trim();

		if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
			/**
			 * 启动一个延时线程
			 */
			new Thread(WelcomeActivity.this).start();
		} else {
			autoLogin();
		}

	}

	/**
	 * 自动登陆
	 */
	private void autoLogin() {

		// 暂时将参数值默认为0
		String clientType = "android";
		String clientID = "0";
		String loginIp = "0";

		// 使用特殊形式组合参数
		loginParams = username + "|" + password + "|" + clientType + "|" + clientID + "|" + loginIp;

		loginByTel(loginParams);

	}

	/**
	 * 登录
	 * 
	 * @param loginParams
	 *            参数格式：0|0|0
	 */
	private void loginByTel(final String loginParams) {
		new AsyncTask<Void, Integer, String>() {

			@Override
			protected String doInBackground(Void... params) {
				// 请求服务器
				final List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
				parameters.add(new BasicNameValuePair("classname", "BaseInfoService"));
				parameters.add(new BasicNameValuePair("methodname", "SellerLoginByTel"));
				parameters.add(new BasicNameValuePair("params", loginParams));

				String response = HttpService.methodPost(CommonData.HTTP_URL, parameters);
				return response;
			}

			@Override
			protected void onPostExecute(String result) {
				if (result != null) {
					json = result.toString();
					Log.d("返回登陆数据", json);
					depotReturnData();
				} else {
					startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
					finish();
				}
			}

		}.execute();

	}

	/**
	 * 解析和存储返回数据
	 */
	private void depotReturnData() {
		try {
			// 返回的数据形式是一个Object类型，所以可以直接转换成一个Object
			JSONObject jsonObject = new JSONObject(json);
			Boolean success = jsonObject.getBoolean("Success");
			// String mess = jsonObject.getString("Mess");
			// ToastUtils.showShortToast(WelcomeActivity.this, mess);
			if (success == true) {
				// 获取对象中的对象
				JSONObject contentObject = jsonObject.getJSONObject("Data");

				SharedPreManager.getInstance().setInt(CommonData.USER_ID, contentObject.getInt("Id"));
				SharedPreManager.getInstance().setInt(CommonData.USER_STATE, contentObject.getInt("State"));

				SharedPreManager.getInstance().setString(CommonData.USER_NAME, contentObject.getString("Name"));

				SharedPreManager.getInstance().setString(CommonData.USER_PHONE, contentObject.getString("Tel"));

				SharedPreManager.getInstance().setString(CommonData.USER_EMAIL, contentObject.getString("Mail"));

				SharedPreManager.getInstance().setString(CommonData.USER_CITY,
						converterToSpell(contentObject.getString("CityName")));

				SharedPreManager.getInstance().setString(CommonData.USER_CITY_VIEW,
						contentObject.getString("CityName"));

				SharedPreManager.getInstance().setString(CommonData.SELLBRANDNAME1,
						contentObject.getString("SellBrandName1"));
				SharedPreManager.getInstance().setString(CommonData.BlandId1, contentObject.getString("SellBrand1"));

				SharedPreManager.getInstance().setString(CommonData.Bland2, contentObject.getString("SellBrandName2"));
				SharedPreManager.getInstance().setString(CommonData.BlandId2, contentObject.getString("SellBrand2"));

				SharedPreManager.getInstance().setString(CommonData.Bland3, contentObject.getString("SellBrandName3"));
				SharedPreManager.getInstance().setString(CommonData.BlandId3, contentObject.getString("SellBrand3"));

				SharedPreManager.getInstance().setInt(CommonData.VIPLEVEL, contentObject.getInt("VipLevel"));

				SharedPreManager.getInstance().setString(CommonData.SFZ, contentObject.getString("SFZ"));
				// 跳转
				enterMianActivity();

			} else {
				startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 进入主界面
	 */
	private void enterMianActivity() {
		Intent intent = new Intent(getApplicationContext(), MainActivity.class);
		SharedPreManager.getInstance().setString(CommonData.USER_PHONE, username);
		SharedPreManager.getInstance().setString(CommonData.USER_PWD, password);
		startActivity(intent);
		// 退出栈中的activity
		ActivityManager.getInstance().popAllActivity();
		finish();
	}

	/**
	 * 汉字转换位汉语拼音，英文字符不变
	 * 
	 * @param chines
	 *            汉字
	 * @return 拼音
	 */
	public static String converterToSpell(String chines) {
		String pinyinName = "";
		char[] nameChar = chines.toCharArray();
		HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
		defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		for (int i = 0; i < nameChar.length; i++) {
			if (nameChar[i] > 128) {
				try {
					pinyinName += PinyinHelper.toHanyuPinyinStringArray(nameChar[i], defaultFormat)[0];
				} catch (BadHanyuPinyinOutputFormatCombination e) {
					e.printStackTrace();
				}
			} else {
				pinyinName += nameChar[i];
			}
		}
		return pinyinName;
	}

	@Override
	public void run() {

		/**
		 * 延迟两秒时间
		 */
		try {
			Thread.sleep(2000);
			// 读取SharedPreferences中需要的数据
			@SuppressWarnings("deprecation")
			SharedPreferences preferences = getSharedPreferences("isFirstUse", MODE_WORLD_READABLE);

			isFirstUse = preferences.getBoolean("isFirstUse", true);

			/**
			 * 如果用户不是第一次使用则直接调转到显示界面,否则调转到引导界面
			 */
			if (isFirstUse) {
				startActivity(new Intent(WelcomeActivity.this, GuideActivity.class));
			} else {
				startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
			}

			finish();
			// 实例化Editor对象
			Editor editor = preferences.edit();
			// 存入数据
			editor.putBoolean("isFirstUse", false);
			// 提交修改
			editor.commit();

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	protected void onResume() {
		super.onResume();
		JPushInterface.onResume(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		JPushInterface.onPause(this);
	}

}
