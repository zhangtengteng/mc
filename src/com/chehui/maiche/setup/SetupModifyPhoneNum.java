package com.chehui.maiche.setup;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.chehui.maiche.ActivityManager;
import com.chehui.maiche.BaseActivity;
import com.chehui.maiche.R;
import com.chehui.maiche.SharedPreManager;
import com.chehui.maiche.comm.CommonData;
import com.chehui.maiche.httpserve.HttpService;
import com.chehui.maiche.utils.StringUtils;
import com.chehui.maiche.utils.ToastUtils;
import com.chehui.maiche.utils.Utils;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

/**
 * 
 * @author zzp
 *         <P>
 *         手机号修改界面
 *         <P>
 *
 */
public class SetupModifyPhoneNum extends BaseActivity {

	private EditText edt_newphonenum;
	private EditText edt_newvalicode;
	private Button btn_newgetvalidcode;
	private Button btn_complete;
	private String newPhoneNum;
	private String newValicode;
	private TimeCount timeCount;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup_modify_phonenum);
		initTitleView(-1, 255, R.string.set_safe_verification, 255, -1, 0);
		initWidgets();

	}

	/**
	 * 初始化组件
	 */
	private void initWidgets() {
		timeCount = new TimeCount(60000, 1000);

		edt_newphonenum = (EditText) findViewById(R.id.setup_modify_edt_newphonenum);
		edt_newvalicode = (EditText) findViewById(R.id.setup_modify_edt_newvalicode);
		btn_newgetvalidcode = (Button) findViewById(R.id.setup_modify_btn_newgetvalidcode);
		btn_complete = (Button) findViewById(R.id.setup_modify_safe_btn_complete);

		btn_newgetvalidcode.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				newPhoneNum = edt_newphonenum.getText().toString().trim();
				GetValidCode(newPhoneNum + "|" + "");

			}
		});

		btn_complete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				newValicode = edt_newvalicode.getText().toString().trim();
				ValidCode(newPhoneNum + "|" + newValicode);
			}
		});
	}

	/**
	 * 匹配验证码
	 * 
	 * @param conParams
	 */
	private void ValidCode(final String conParams) {

		if (StringUtils.isEmpty(newPhoneNum)) {
			ToastUtils.showShortToast(getApplicationContext(), "手机号不能为空！");
			return;
		}
		if (StringUtils.isEmpty(newValicode)) {
			ToastUtils.showShortToast(getApplicationContext(), "验证码不能为空！");
			return;
		}
		if (!Utils.isNetworkAvailable(this)) {
			ToastUtils.showShortToast(getApplicationContext(), R.string.common_network_unavalible);
			return;
		}

		new AsyncTask<Void, Integer, String>() {

			@Override
			protected String doInBackground(Void... params) {
				final List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
				parameters.add(new BasicNameValuePair("classname", "MessageService"));
				parameters.add(new BasicNameValuePair("methodname", "ValidCode"));
				parameters.add(new BasicNameValuePair("params", conParams));
				String response = HttpService.methodPost(CommonData.HTTP_URL, parameters);
				return response;
			}

			@Override
			protected void onPostExecute(String result) {
				super.onPostExecute(result);
				if (result != null) {
					String json = result.toString();
					try {
						JSONObject jsonObject = new JSONObject(json);
						Boolean data = jsonObject.getBoolean("Data");
						// 完成修改
						if (data) {

							SellerModifyPhoneNum(SharedPreManager.getInstance().getInt(CommonData.USER_ID, 0) + "|"
									+ "tel" + "|" + newPhoneNum);
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				} else {
					ToastUtils.showShortToast(SetupModifyPhoneNum.this, "加载网络失败!");
				}
			}

		}.execute();
	}

	/***
	 * 发送手机验证码
	 */
	private void GetValidCode(final String conParams) {
		if (StringUtils.isEmpty(newPhoneNum)) {
			ToastUtils.showShortToast(getApplicationContext(), "手机号不能为空！");
			return;
		}

		if (!Utils.isNetworkAvailable(this)) {
			ToastUtils.showShortToast(getApplicationContext(), R.string.common_network_unavalible);
			return;
		}

		// 发送验证码
		new AsyncTask<Void, Integer, String>() {

			@Override
			protected String doInBackground(Void... params) {
				// 开始倒计时
				timeCount.start();
				// 请求服务器
				final List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
				parameters.add(new BasicNameValuePair("classname", "MessageService"));
				parameters.add(new BasicNameValuePair("methodname", "GetValidCode"));
				parameters.add(new BasicNameValuePair("params", conParams));

				String response = HttpService.methodPost(CommonData.HTTP_URL, parameters);
				return response;
			}

			@Override
			protected void onPostExecute(String result) {
				super.onPostExecute(result);
				if (result != null) {
					String json = result.toString();
					try {
						JSONObject jsonObject = new JSONObject(json);
						Boolean data = jsonObject.getBoolean("Data");
						String mess = jsonObject.getString("Mess");
						if (!data) {
							ToastUtils.showShortToast(SetupModifyPhoneNum.this, mess);
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}

				} else {
					ToastUtils.showShortToast(SetupModifyPhoneNum.this, "加载网络失败!");
				}

			}
		}.execute();

	}

	/**
	 * 倒计时
	 * 
	 * @author zzp
	 */
	class TimeCount extends CountDownTimer {

		/***
		 * 
		 * @param millisInFuture
		 *            总时长
		 * @param countDownInterval
		 *            计时的时间间隔
		 */
		public TimeCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
		}

		@SuppressLint("ResourceAsColor")
		@Override
		public void onFinish() {// 计时完毕时触发
			btn_newgetvalidcode.setText("重新验证");
			btn_newgetvalidcode.setClickable(true);
		}

		@SuppressLint("ResourceAsColor")
		@Override
		public void onTick(long millisUntilFinished) {// 计时过程显示
			btn_newgetvalidcode.setClickable(false);
			btn_newgetvalidcode.setText(millisUntilFinished / 1000 + "秒");
		}
	}

	/**
	 * 修改手机号
	 * 
	 * @param conParams
	 */
	private void SellerModifyPhoneNum(final String conParams) {
		if (!Utils.isNetworkAvailable(this)) {
			ToastUtils.showShortToast(getApplicationContext(), R.string.common_network_unavalible);
			return;
		}

		new AsyncTask<Void, Integer, String>() {

			@Override
			protected String doInBackground(Void... params) {
				final List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
				parameters.add(new BasicNameValuePair("classname", "BaseInfoService"));
				parameters.add(new BasicNameValuePair("methodname", "SellerModify"));
				parameters.add(new BasicNameValuePair("params", conParams));
				String response = HttpService.methodPost(CommonData.HTTP_URL, parameters);
				return response;
			}

			@Override
			protected void onPostExecute(String result) {
				super.onPostExecute(result);
				if (result != null) {
					String json = result.toString();
					try {
						JSONObject jsonObject = new JSONObject(json);
						Boolean Success = jsonObject.getBoolean("Success");
						String mess = jsonObject.getString("Mess");
						// 修改成功
						if (Success) {
							ToastUtils.showShortToast(SetupModifyPhoneNum.this, mess);
							SharedPreManager.getInstance().setString(CommonData.USER_PHONE, newPhoneNum);
							ActivityManager.getInstance().startNextActivity(SetupManagerAccountPersion.class);
							finish();
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				} else {
					ToastUtils.showShortToast(SetupModifyPhoneNum.this, "加载网络失败!");
				}
			}

		}.execute();
	}

}
