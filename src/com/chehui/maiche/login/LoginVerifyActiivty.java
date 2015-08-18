package com.chehui.maiche.login;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.chehui.maiche.ActivityManager;
import com.chehui.maiche.BaseActivity;
import com.chehui.maiche.R;
import com.chehui.maiche.SharedPreManager;
import com.chehui.maiche.comm.CommonData;
import com.chehui.maiche.httpserve.HttpService;
import com.chehui.maiche.utils.StringUtils;
import com.chehui.maiche.utils.ToastUtils;
import com.chehui.maiche.utils.Utils;

/**
 * 验证码找回密码
 * 
 * @author gqy
 *         <P>
 *         判断type：zc代表注册，tx代表提现，wjmm代表忘记密码
 *         <P>
 */
public class LoginVerifyActiivty extends BaseActivity {
	/** 发送验证码 */
	private Button btnSendCode;
	/** 输入手机号 */
	private EditText edtPhone;
	/** 输入验证码 */
	private EditText edtCode;
	/** 超时控制器 */
	private TimeCount timeCount;
	/** 手机号 */
	private String telNum;
	/** 验证码 */
	private String validecode;
	private Button btn_commit;

	/**
	 * 接收消息
	 */
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case CommonData.HTTP_HANDLE_FAILE:
				Bundle datafalse = msg.getData();
				String resultfalse = datafalse.getString("mess");
				ToastUtils
						.showShortToast(LoginVerifyActiivty.this, resultfalse);
				break;

			case CommonData.HTTP_HANDLE_SUCCESS:
				Bundle data = msg.getData();
				String result = data.getString("mess");
				ToastUtils.showShortToast(LoginVerifyActiivty.this, result);

				ActivityManager.getInstance().startNextActivity(
						LoginChangePwd.class);
				break;
			default:
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login_verify_actiivty);
		initTitleView(-1, 255, R.string.vertifycode, 255, -1, 0);
		initWidgets();
	}

	/**
	 * 初始化组件
	 */
	private void initWidgets() {
		edtPhone = (EditText) findViewById(R.id.et_phone);
		edtCode = (EditText) findViewById(R.id.et_code);
		timeCount = new TimeCount(60000, 1000);
		btnSendCode = (Button) findViewById(R.id.btn_send_code);
		btn_commit = (Button) findViewById(R.id.btn_commit);
		btnSendCode.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// 手机号
				telNum = edtPhone.getText().toString().trim();
				SharedPreManager.getInstance().setString(
						CommonData.CHANGE_PWD_PHONE, telNum);

				String conParams = telNum + "|" + "wjmm";

				sendCode(conParams);

			}
		});

		btn_commit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				validecode = edtCode.getText().toString().trim();
				String validParams = telNum + "|" + validecode;
				System.out.println(validParams);
				matchValicode(validParams);

			}
		});
	}

	/**
	 * 匹配短息和验证码
	 */
	private void matchValicode(final String validParams) {
		if (!Utils.isNetworkAvailable(this)) {
			ToastUtils.showShortToast(getApplicationContext(),
					R.string.common_network_unavalible);
			return;
		}
		if (StringUtils.isEmpty(edtPhone.getText().toString())) {
			ToastUtils.showShortToast(getApplicationContext(), "手机号不能为空！");
			return;
		}

		if (StringUtils.isEmpty(validecode)) {
			ToastUtils.showShortToast(getApplicationContext(), "验证码不能为空！");
			return;
		}
		new AsyncTask<Void, Integer, String>() {

			@Override
			protected String doInBackground(Void... params) {
				// 请求服务器
				final List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
				parameters.add(new BasicNameValuePair("classname",
						"MessageService"));
				parameters
						.add(new BasicNameValuePair("methodname", "ValidCode"));
				parameters.add(new BasicNameValuePair("params", validParams));

				String response = HttpService.methodPost(CommonData.HTTP_URL,
						parameters);
				return response;
			}

			@Override
			protected void onPostExecute(String result) {
				// "Mess":"发送成功","Data":true
				if (result != null) {
					String json = result.toString();
					analysisJson(json);
				} else {
					ToastUtils.showShortToast(LoginVerifyActiivty.this,
							"网络加载失败!");
				}
			}
		}.execute();
	}

	/**
	 * 解析返回数据
	 * 
	 * @param json
	 */
	private void analysisJson(String json) {
		try {
			JSONObject jsonObject = new JSONObject(json);
			Boolean Data = jsonObject.getBoolean("Data");
			String mess = jsonObject.getString("Mess");
			if (String.valueOf(Data).equals("true")) {
				Message msg = mHandler
						.obtainMessage(CommonData.HTTP_HANDLE_SUCCESS);
				Bundle data = msg.getData();
				data.putString("mess", mess);
				mHandler.sendMessage(msg);

			} else if (String.valueOf(Data).equals("false")) {
				Message msg = mHandler
						.obtainMessage(CommonData.HTTP_HANDLE_FAILE);
				Bundle datafalse = msg.getData();
				datafalse.putString("mess", mess);
				mHandler.sendMessage(msg);

			}

		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	/***
	 * 发送手机验证码
	 */
	private void sendCode(final String conParams) {
		if (StringUtils.isEmpty(edtPhone.getText().toString())) {
			ToastUtils.showShortToast(getApplicationContext(), "手机号不能为空！");
			return;
		}
		if (!Utils.isNetworkAvailable(this)) {
			ToastUtils.showShortToast(getApplicationContext(),
					R.string.common_network_unavalible);
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
				parameters.add(new BasicNameValuePair("classname",
						"MessageService"));
				parameters.add(new BasicNameValuePair("methodname",
						"GetValidCode"));
				parameters.add(new BasicNameValuePair("params", conParams));

				String response = HttpService.methodPost(CommonData.HTTP_URL,
						parameters);
				return response;
			}

			@Override
			protected void onPostExecute(String result) {
				super.onPostExecute(result);
				String json = result.toString();
				if (!json.isEmpty()) {
				} else {
					ToastUtils.showShortToast(LoginVerifyActiivty.this,
							"服务器无响应!");
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

		@Override
		public void onFinish() {// 计时完毕时触发
			btnSendCode.setText("重新验证");
			btnSendCode.setClickable(true);

		}

		@Override
		public void onTick(long millisUntilFinished) {// 计时过程显示

			btnSendCode.setClickable(false);
			btnSendCode.setText(millisUntilFinished / 1000 + "秒");
		}
	}
}
