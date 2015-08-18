package com.chehui.maiche.login;

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
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.chehui.maiche.BaseActivity;
import com.chehui.maiche.R;
import com.chehui.maiche.RegisterManager;
import com.chehui.maiche.comm.CommonData;
import com.chehui.maiche.custom.EditTextWithDel;
import com.chehui.maiche.httpserve.HttpService;
import com.chehui.maiche.utils.StringUtils;
import com.chehui.maiche.utils.ToastUtils;
import com.chehui.maiche.utils.Utils;

/**
 * 
 * @author zzp
 *         <P>
 *         注册发送手机验证码
 *         <P>
 * 
 */
public class RegGetValidCodeActivity extends BaseActivity {
	private static final String TAG = "RegGetValidCodeActivity";
	/** 发送验证码 */
	private Button btnSendCode;
	/** 验证完成下一步 */
	private Button btnNext;
	/** 输入手机号 */
	private EditTextWithDel edtPhone;
	/** 输入验证码 */
	private EditTextWithDel edtCode;
	/** 超时控制器 */
	private TimeCount timeCount;
	/***/
	@SuppressWarnings("unused")
	private TextView txtAgt;
	/** 查看用户协议 */
	private TextView txtAgreement;

	/** 手机号 */
	private String telNum;
	/** 验证码 */
	private String validecode;

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
				ToastUtils.showShortToast(RegGetValidCodeActivity.this,
						resultfalse);
				break;

			case CommonData.HTTP_HANDLE_SUCCESS:
				Bundle data = msg.getData();
				String result = data.getString("mess");
				ToastUtils.showShortToast(RegGetValidCodeActivity.this, result);
				startNextActivity();

				break;

			default:
				break;
			}
		}
	};

	/**
	 * 跳转下一个界面
	 */
	@SuppressWarnings("static-access")
	public void startNextActivity() {
		activityManager.getInstance().startNextActivity(
				RegSellerRegisterActivity.class);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reggetvalidcode);
		initTitleView(-1, 255, R.string.reg, 255, -1, 0);
		initWidgets();
	}

	/**
	 * 初始化组件
	 */
	private void initWidgets() {
		edtPhone = (EditTextWithDel) findViewById(R.id.et_phone);
		edtCode = (EditTextWithDel) findViewById(R.id.et_code);
		txtAgt = (TextView) findViewById(R.id.txt_agt);
		txtAgreement = (TextView) findViewById(R.id.txt_agreement);
		timeCount = new TimeCount(60000, 1000);
		btnSendCode = (Button) findViewById(R.id.btn_send_code);
		btnSendCode.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// 手机号
				telNum = edtPhone.getText().toString();
				if(!Utils.isMobileNO(telNum)){
					ToastUtils.showShortToast(RegGetValidCodeActivity.this, "手机号格式不正确");
				}

				// 将手机号存入实体类
				RegisterManager.getInstance().setTel(telNum);

				String type = "zc";

				String conParams = telNum + "|" + type;

				sendCode(conParams);

			}

		});

		btnNext = (Button) findViewById(R.id.btn_next0);
		btnNext.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
			/*	validecode = edtCode.getText().toString();
				String validParams = telNum + "|" + validecode;
				matchValicode(validParams);*/
				startNextActivity();

			}
		});
		txtAgreement.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(RegGetValidCodeActivity.this,
						LoginDealActivity.class));
			}
		});

	}

	/**
	 * 匹配短息和验证码
	 */
	private void matchValicode(final String validParams) {
		if (StringUtils.isEmpty(edtPhone.getText().toString())) {
			ToastUtils.showShortToast(getApplicationContext(), "手机号不能为空！");
			return;
		}

		if (StringUtils.isEmpty(validecode)) {
			ToastUtils.showShortToast(getApplicationContext(), "验证码不能为空！");
			return;
		}
		if (!Utils.isNetworkAvailable(this)) {
			ToastUtils.showShortToast(getApplicationContext(),
					R.string.common_network_unavalible);
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

				if (result != null) {
					// "Mess":"发送成功","Data":true
					String json = result.toString();
					analysisJson(json);
				} else {
					ToastUtils.showShortToast(RegGetValidCodeActivity.this,
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
		if (!json.isEmpty()) {
			try {
				Log.d(TAG + "匹配验证码", json);
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

		} else {
			ToastUtils.showShortToast(RegGetValidCodeActivity.this, "服务器无响应!");
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
					Log.d(TAG + "查看获得验证码", json);
				} else {
					ToastUtils.showShortToast(RegGetValidCodeActivity.this,
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

		@SuppressLint("ResourceAsColor") @Override
		public void onFinish() {// 计时完毕时触发
			btnSendCode.setText("重新验证");
			btnSendCode.setClickable(true);
		}

		@SuppressLint("ResourceAsColor") @Override
		public void onTick(long millisUntilFinished) {// 计时过程显示
			btnSendCode.setClickable(false);
			btnSendCode.setText(millisUntilFinished / 1000 + "秒");
		}
	}
}
