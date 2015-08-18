package com.chehui.maiche.rabate;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.chehui.maiche.BaseActivity;
import com.chehui.maiche.R;
import com.chehui.maiche.SharedPreManager;
import com.chehui.maiche.comm.CommonData;
import com.chehui.maiche.httpserve.HttpService;
import com.chehui.maiche.utils.StringUtils;
import com.chehui.maiche.utils.ToastUtils;
import com.chehui.maiche.utils.Utils;

/**
 * 提现验证手机号
 * 
 * @author gqy
 * 
 */
public class RabateVertifyActivity extends BaseActivity {
	private TextView vertify_phone_num;

	/** 发送验证码 */
	private Button btnSendCode;
	/** 输入验证码 */
	private EditText edtCode;
	/** 超时控制器 */
	private TimeCount timeCount;
	/** 手机号 */
	private String telNum;
	/** 验证码 */
	private String validecode;
	private Button btn_commit;
	private String txt_params = "";
	/** 发送验证码的参数 */
	private String txt_params_code = "";

	private String tel_num, name, bankName, cardNum;

	private double cash_num;
	private String json;

	private String money, s;

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
				ToastUtils.showShortToast(RabateVertifyActivity.this,
						resultfalse);
				break;

			case CommonData.HTTP_HANDLE_SUCCESS:
				commitBankMessage();
				break;
			default:
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vertify_phone);
		initTitleView(-1, 255, R.string.set_account_vertify_phone, 255, -1, 0);
		initWidgets();
	}

	/**
	 * 初始化组件
	 */
	private void initWidgets() {
		name = getIntent().getStringExtra("NAME");
		bankName = getIntent().getStringExtra("BANKNAME");
		cardNum = getIntent().getStringExtra("BANKNUM");
		vertify_phone_num = (TextView) findViewById(R.id.vertify_phone_num);
		tel_num = getIntent().getStringExtra("TEL");
		vertify_phone_num.setText(tel_num);
		edtCode = (EditText) findViewById(R.id.et_code);
		timeCount = new TimeCount(60000, 1000);
		btn_commit = (Button) findViewById(R.id.btn_myorder_next);
		btnSendCode = (Button) findViewById(R.id.btn_send_code);
		btnSendCode.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// 手机号
				telNum = vertify_phone_num.getText().toString().trim();
				txt_params_code = telNum + "|tx";
				sendCode();
			}
		});

		btn_commit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				validecode = edtCode.getText().toString().trim();
				String validParams = telNum + "|" + validecode;
				matchValicode(validParams);
			}
		});
	}

	/**
	 * 匹配短息和验证码
	 */
	private void matchValicode(final String validParams) {

		if (StringUtils.isEmpty(validecode)) {
			ToastUtils.showShortToast(getApplicationContext(), "验证码不能为空！");
			return;
		}
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
					String json_code = result.toString();
					analysisJson(json_code);
				} else {
					ToastUtils.showShortToast(RabateVertifyActivity.this,
							"网络加载失败");
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
               dismissLastAlertDialog();
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
	private void sendCode() {

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
				parameters
						.add(new BasicNameValuePair("params", txt_params_code));

				String response = HttpService.methodPost(CommonData.HTTP_URL,
						parameters);
				return response;
			}

			@Override
			protected void onPostExecute(String result) {
				super.onPostExecute(result);

				if (StringUtils.isEmpty(result.toString())) {
					ToastUtils.showShortToast(RabateVertifyActivity.this,
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

	/**
	 * 提现提交银行卡信息
	 */
	private void commitBankMessage() {
		int sellerid = SharedPreManager.getInstance().getInt(
				CommonData.USER_ID, 103);
		cash_num = getIntent().getDoubleExtra("CASH", 0);
		s = cash_num + "";
		money = s.substring(0, s.indexOf("."));

		txt_params = sellerid + "|" + money + "|" + cardNum;

		new AsyncTask<Void, Integer, String>() {

			@Override
			protected String doInBackground(Void... params) {
				// 请求服务器
				final List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
				parameters.add(new BasicNameValuePair("classname",
						"BankService"));
				parameters.add(new BasicNameValuePair("methodname",
						"OrderMyMoney"));
				parameters.add(new BasicNameValuePair("params", txt_params));

				String response = HttpService.methodPost(CommonData.HTTP_URL,
						parameters);
				return response;
			}

			@Override
			protected void onPostExecute(String result) {
				// "Mess":"发送成功","Data":true
				if (result != null) {
					try {
						json = result.toString();
						JSONObject jsonObject = new JSONObject(json);
						String data = jsonObject.getString("Data");
						String mess = jsonObject.getString("Mess");

						if (mess.equals("申请提现成功")) {
							Intent intent = new Intent(
									RabateVertifyActivity.this,
									RabateCommitFinishActivity.class);
							intent.putExtra("UNAME", name);
							intent.putExtra("UBANKNAME", bankName);
							intent.putExtra("UCARDNUM", cardNum);
							intent.putExtra("DATE", data);
							intent.putExtra("CASH", money);
							startActivity(intent);
							finish();
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				} else {
					ToastUtils.showShortToast(RabateVertifyActivity.this,
							"网络加载失败");
				}

			}
		}.execute();
	}
}
