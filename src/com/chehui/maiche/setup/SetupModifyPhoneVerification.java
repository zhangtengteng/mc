package com.chehui.maiche.setup;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.chehui.maiche.ActivityManager;
import com.chehui.maiche.BaseActivity;
import com.chehui.maiche.R;
import com.chehui.maiche.comm.CommonData;
import com.chehui.maiche.enquiry.OrderCheckFragment;
import com.chehui.maiche.httpserve.HttpService;
import com.chehui.maiche.myorder.MyOrderFragment.MessageReceiver;
import com.chehui.maiche.utils.StringUtils;
import com.chehui.maiche.utils.ToastUtils;
import com.chehui.maiche.utils.Utils;

/**
 * 
 * @author zzp
 *         <P>
 *         验证手机号——跳转信息修改界面——分别根据所选条目跳转
 *         <P>
 *
 */
public class SetupModifyPhoneVerification extends BaseActivity {

	/** 匹配验证码 */
	private Button safe_btn_verification;
	private EditText edt_phonenum;
	private EditText edt_valicode;
	/** 获取验证码 */
	private Button btn_getvalidcode;
	private String phoneNum;
	private TimeCount timeCount;
	private String valicode;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup_modify_phoneverification);
		initTitleView(-1, 255, R.string.set_safe_verification, 255, -1, 0);
		initWidgets();
	}

	/**
	 * 初始化组件
	 */
	private void initWidgets() {
		timeCount = new TimeCount(60000, 1000);

		safe_btn_verification = (Button) findViewById(R.id.setup_modify_safe_btn_verification);
		edt_phonenum = (EditText) findViewById(R.id.setup_modify_edt_phonenum);
		edt_valicode = (EditText) findViewById(R.id.setup_modify_edt_valicode);
		btn_getvalidcode = (Button) findViewById(R.id.setup_modify_btn_getvalidcode);

		btn_getvalidcode.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				phoneNum = edt_phonenum.getText().toString().trim();
				GetValidCode(phoneNum + "|" + "");
			}
		});

		safe_btn_verification.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				valicode = edt_valicode.getText().toString().trim();
				ValidCode(phoneNum + "|" + valicode);
			}
		});

	}

	/**
	 * 匹配验证码
	 * 
	 * @param conParams
	 */
	private void ValidCode(final String conParams) {

		if (StringUtils.isEmpty(phoneNum)) {
			ToastUtils.showShortToast(getApplicationContext(), "手机号不能为空！");
			return;
		}
		if (StringUtils.isEmpty(valicode)) {
			ToastUtils.showShortToast(getApplicationContext(), "验证码不能为空!");
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
						// 跳转下一个界面
						if (data) {

							ActivityManager.getInstance().startNextActivity(SetupModifyPhoneNum.class);

						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				} else {
					ToastUtils.showShortToast(SetupModifyPhoneVerification.this, "加载网络失败!");
				}
			}

		}.execute();
	}

	/***
	 * 发送手机验证码
	 */
	private void GetValidCode(final String conParams) {
		if (StringUtils.isEmpty(phoneNum)) {
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
							ToastUtils.showShortToast(SetupModifyPhoneVerification.this, mess);
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}

				} else {
					ToastUtils.showShortToast(SetupModifyPhoneVerification.this, "加载网络失败!");
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
			btn_getvalidcode.setText("重新验证");
			btn_getvalidcode.setClickable(true);
		}
		@SuppressLint("ResourceAsColor")
		@Override
		public void onTick(long millisUntilFinished) {// 计时过程显示
			btn_getvalidcode.setClickable(false);
			btn_getvalidcode.setText(millisUntilFinished / 1000 + "秒");
		}
	}
	
}
