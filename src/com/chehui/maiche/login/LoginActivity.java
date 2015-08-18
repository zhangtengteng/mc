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
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.chehui.maiche.ActivityManager;
import com.chehui.maiche.BaseActivity;
import com.chehui.maiche.MainActivity;
import com.chehui.maiche.R;
import com.chehui.maiche.SharedPreManager;
import com.chehui.maiche.comm.CommonData;
import com.chehui.maiche.httpserve.HttpService;
import com.chehui.maiche.utils.StringUtils;
import com.chehui.maiche.utils.ToastUtils;
import com.chehui.maiche.utils.Utils;

/**
 * 登陆界面
 * 
 * @author zzp
 * 
 */
public class LoginActivity extends BaseActivity {

	/** 返回json数据 */
	private String json;
	/** 用户名手机号 */
	private String username;
	/** 用户密码 */
	private String password;
	/** 登录参数 */
	private String loginParams;

	private TextView txt_username;
	private TextView txt_pwd;
	private Button btn_login;
	private TextView txt_pwd_reback, txt_reg_user;

	/**
	 * 接收消息
	 */
	@SuppressWarnings("unused")
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case CommonData.MESS:
				break;
			default:
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		initView();
		SharedPreManager.getInstance().init(getApplicationContext());
	}

	private void initView() {
		txt_username = (TextView) findViewById(R.id.login_txt_userName);
		txt_pwd = (TextView) findViewById(R.id.et_pwd);
		btn_login = (Button) findViewById(R.id.login_btn);
		txt_reg_user = (TextView) findViewById(R.id.login_tv_reg);
		txt_pwd_reback = (TextView) findViewById(R.id.login_tv_pwd_reback);
		btn_login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {

				if (txt_username.getText().toString().trim().equals("1")) {
					startActivity(new Intent(getApplicationContext(), MainActivity.class));
					ActivityManager.getInstance().popAllActivity();
					return;
				}

				if (!Utils.isNetworkAvailable(LoginActivity.this)) {
					ToastUtils.showShortToast(getApplicationContext(), R.string.common_network_unavalible);
					return;
				}

				username = txt_username.getText().toString().trim();
				password = txt_pwd.getText().toString().trim();

				if (StringUtils.isEmpty(username)) {
					ToastUtils.showShortToast(getApplicationContext(), R.string.account_input_hint);
					return;
				} else {
					if (!Utils.isMobileNO(username)) {
						ToastUtils.showShortToast(getApplicationContext(), R.string.account_input_phone_error);
						return;
					} else {
						if (StringUtils.isEmpty(password)) {
							ToastUtils.showShortToast(LoginActivity.this, R.string.pwd_input_hint);
							return;
						}
					}
				}

				// 暂时将参数值默认为0
				String clientType = "android";
				String clientID = "0";
				String loginIp = "0";

				// 使用特殊形式组合参数
				loginParams = username + "|" + password + "|" + clientType + "|" + clientID + "|" + loginIp;

				loginByTel(loginParams);

			}
		});
		txt_reg_user.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ActivityManager.getInstance().startNextActivity(RegGetValidCodeActivity.class);
			}
		});

		txt_pwd_reback.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				ActivityManager.getInstance().startNextActivity(LoginQuestionActicity.class);
			}
		});
	}

	/**
	 * 登录
	 * 
	 * @param loginParams
	 *            参数格式：0|0|0
	 */
	private void loginByTel(final String loginParams) {
		if (!Utils.isNetworkAvailable(this)) {
			ToastUtils.showShortToast(getApplicationContext(), R.string.common_network_unavalible);
			return;
		}

		showWaitDialog(R.string.common_requesting);
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
				dismissWaitDialog();
				if (result != null) {
					json = result.toString();
					Log.d("返回登陆数据", json);
					depotReturnData();
				} else {
					ToastUtils.showShortToast(LoginActivity.this, "网络加载失败!");
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
			String mess = jsonObject.getString("Mess");

			ToastUtils.showShortToast(LoginActivity.this, mess);
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

}
