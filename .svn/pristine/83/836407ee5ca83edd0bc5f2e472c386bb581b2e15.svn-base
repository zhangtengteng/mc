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
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.chehui.maiche.BaseActivity;
import com.chehui.maiche.MainActivity;
import com.chehui.maiche.R;
import com.chehui.maiche.RegisterManager;
import com.chehui.maiche.SharedPreManager;
import com.chehui.maiche.comm.CommonData;
import com.chehui.maiche.httpserve.HttpService;
import com.chehui.maiche.utils.StringUtils;
import com.chehui.maiche.utils.ToastUtils;
import com.chehui.maiche.utils.Utils;

/**
 * 
 * @author zzp
 *         <P>
 *         注册完成启动app,将登陆信息存入sharePreference
 *         <P>
 * 
 */
public class RegCompleteActivity extends BaseActivity {

	private static final String TAG = "RegCompleteActivity";
	private TextView btn_start;
	private String username;
	private String password;
	/** 返回json数据 */
	private String json;

	/** 实名认证 */
	private Button btn_startAuthentication;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_regcomplete_layout);
		SharedPreManager.getInstance().init(getApplicationContext());
		initWidgets();
	}

	private void initWidgets() {
		btn_start = (TextView) findViewById(R.id.reg_btn_startapp);
		btn_startAuthentication = (Button) findViewById(R.id.reg_btn_startAuthentication);
		btn_startAuthentication.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				CommonData.toAuthon = true;
				// 暂时将参数值默认为0
				String clientType = "android";
				String clientID = "0";
				String loginIp = "0";

				username = RegisterManager.getInstance().getTel();
				password = RegisterManager.getInstance().getPassWord();

				// 使用特殊形式组合参数
				String loginParams = username + "|" + password + "|"
						+ clientType + "|" + clientID + "|" + loginIp;

				loginByTel(loginParams);
			}
		});
		btn_start.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				// 暂时将参数值默认为0
				String clientType = "android";
				String clientID = "0";
				String loginIp = "0";

				username = RegisterManager.getInstance().getTel();
				password = RegisterManager.getInstance().getPassWord();

				// 使用特殊形式组合参数
				String loginParams = username + "|" + password + "|"
						+ clientType + "|" + clientID + "|" + loginIp;

				loginByTel(loginParams);

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
			ToastUtils.showShortToast(getApplicationContext(),
					R.string.common_network_unavalible);
			return;
		}

		showWaitDialog(R.string.common_requesting);
		new AsyncTask<Void, Integer, String>() {

			@Override
			protected String doInBackground(Void... params) {
				// 请求服务器
				final List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
				parameters.add(new BasicNameValuePair("classname",
						"BaseInfoService"));
				parameters.add(new BasicNameValuePair("methodname",
						"SellerLoginByTel"));
				parameters.add(new BasicNameValuePair("params", loginParams));

				String response = HttpService.methodPost(CommonData.HTTP_URL,
						parameters);
				return response;
			}

			@Override
			protected void onPostExecute(String result) {
				dismissWaitDialog();
				if (result != null) {
					json = result.toString();
					Log.d(TAG + "返回登陆数据", json);
					depotReturnData();
				} else {
					ToastUtils.showShortToast(RegCompleteActivity.this,
							"网络加载失败!");
				}

			}

		}.execute();

	}

	/**
	 * 解析和存储返回数据
	 */
	private void depotReturnData() {
		try {
			if (StringUtils.isEmpty(json))
				return;
			// 返回的数据形式是一个Object类型，所以可以直接转换成一个Object
			JSONObject jsonObject = new JSONObject(json);
			Boolean success = jsonObject.getBoolean("Success");
			String mess = jsonObject.getString("Mess");

			if (success == true) {
				// 获取对象中的对象
				JSONObject contentObject = jsonObject.getJSONObject("Data");

				SharedPreManager.getInstance().setInt(CommonData.USER_ID,
						contentObject.getInt("Id"));
				SharedPreManager.getInstance().setInt(CommonData.USER_STATE,
						contentObject.getInt("State"));

				SharedPreManager.getInstance().setString(CommonData.USER_NAME,
						contentObject.getString("Name"));

				SharedPreManager.getInstance().setString(CommonData.USER_PHONE,
						contentObject.getString("Tel"));

				SharedPreManager.getInstance().setString(CommonData.USER_EMAIL,
						contentObject.getString("Mail"));

				SharedPreManager.getInstance().setString(CommonData.USER_CITY,
						converterToSpell(contentObject.getString("CityName")));

				SharedPreManager.getInstance().setString(
						CommonData.USER_CITY_VIEW,
						contentObject.getString("CityName"));

				SharedPreManager.getInstance().setString(
						CommonData.SELLBRANDNAME1,
						contentObject.getString("SellBrandName1"));
				SharedPreManager.getInstance().setString(CommonData.BlandId1,
						contentObject.getString("SellBrand1"));

				SharedPreManager.getInstance().setString(CommonData.Bland2,
						contentObject.getString("SellBrandName2"));
				SharedPreManager.getInstance().setString(CommonData.BlandId2,
						contentObject.getString("SellBrand2"));

				SharedPreManager.getInstance().setString(CommonData.Bland3,
						contentObject.getString("SellBrandName3"));
				SharedPreManager.getInstance().setString(CommonData.BlandId3,
						contentObject.getString("SellBrand3"));

				SharedPreManager.getInstance().setInt(CommonData.VIPLEVEL,
						contentObject.getInt("VipLevel"));

				SharedPreManager.getInstance().setString(CommonData.SFZ,
						contentObject.getString("SFZ"));
				// 跳转
				enterMianActivity();

			} else {
				ToastUtils.showShortToast(RegCompleteActivity.this, mess);
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 进入主界面
	 */
	private void enterMianActivity() {
		Intent intent = new Intent(RegCompleteActivity.this, MainActivity.class);
		SharedPreManager.getInstance().setString(CommonData.USER_PHONE,
				username);
		SharedPreManager.getInstance().setString(CommonData.USER_PWD, password);
		startActivity(intent);
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
					pinyinName += PinyinHelper.toHanyuPinyinStringArray(
							nameChar[i], defaultFormat)[0];
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
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		return false;
	}
}
