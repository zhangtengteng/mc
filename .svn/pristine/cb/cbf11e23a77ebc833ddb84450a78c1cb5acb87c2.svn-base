package com.chehui.maiche.login;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
 * 修改密码
 * 
 * @author gqy
 * 
 */
public class LoginChangePwd extends BaseActivity implements OnClickListener {
	private Button btn_finish;
	/** 输入框 */
	private EditText et_pwd, et_pwd_again;
	private TextView tv_phone;
	/** 输入框文本 */
	private String phone_num, pwd, pwd_again;
	/** 用户进行验证的手机号 */
	private String phone_real;
	/** 拼接参数 */
	private String validParams;

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
				ToastUtils.showShortToast(LoginChangePwd.this, resultfalse);
				break;

			case CommonData.HTTP_HANDLE_SUCCESS:
				Bundle data = msg.getData();
				String result = data.getString("mess");
				ToastUtils.showShortToast(LoginChangePwd.this, result);

				ActivityManager.getInstance().startNextActivity(
						LoginActivity.class);
				break;
			default:
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login_change_pwd);
		initTitleView(-1, 255, R.string.newpwd, 255, -1, 0);
		initwidget();
	}

	private void initwidget() {
		btn_finish = (Button) findViewById(R.id.btn_change_finish);
		tv_phone = (TextView) findViewById(R.id.tv_change_phone);
		tv_phone.setText(SharedPreManager.getInstance().getString(
				CommonData.CHANGE_PWD_PHONE, "186*****200"));
		et_pwd = (EditText) findViewById(R.id.et_change_pwd);
		et_pwd_again = (EditText) findViewById(R.id.et_change_pwd_again);
		btn_finish.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		phone_real = SharedPreManager.getInstance().getString(
				CommonData.CHANGE_PWD_PHONE, "010");
		phone_num = tv_phone.getText().toString().trim();
		pwd = et_pwd.getText().toString().trim();
		pwd_again = et_pwd_again.getText().toString().trim();
		if (StringUtils.isEmpty(phone_num)) {
			ToastUtils.showShortToast(LoginChangePwd.this, "请输入手机号");
		} else if (!phone_num.equals(phone_real)) {
			ToastUtils.showShortToast(LoginChangePwd.this, "请输入正确的手机号");
		} else if (StringUtils.isEmpty(pwd)) {
			ToastUtils.showShortToast(LoginChangePwd.this, "请输入密码");
		} else if (StringUtils.isEmpty(pwd_again)) {
			ToastUtils.showShortToast(LoginChangePwd.this, "请再次输入密码");
		} else if (!pwd.equals(pwd_again)) {
			ToastUtils.showLongToast(LoginChangePwd.this, "两次输入密码不同");
		} else {
			validParams = phone_num + "|" + pwd;

		}

		sellerChangePassByTel(validParams);
	}

	/**
	 * 更改密码
	 * 
	 * @param conParams
	 * 
	 */
	private void sellerChangePassByTel(final String conParams) {
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
						"BaseInfoService"));
				parameters.add(new BasicNameValuePair("methodname",
						"SellerChangePassByTel"));
				parameters.add(new BasicNameValuePair("params", validParams));

				String response = HttpService.methodPost(CommonData.HTTP_URL,
						parameters);
				return response;
			}

			@Override
			protected void onPostExecute(String result) {
				if (result != null) {
					String json = result.toString();
					analysisJson(json);
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
			Boolean Success = jsonObject.getBoolean("Success");
			String mess = jsonObject.getString("Mess");

			if (String.valueOf(Success).equals("true")) {
				Message msg = mHandler
						.obtainMessage(CommonData.HTTP_HANDLE_SUCCESS);
				Bundle data = msg.getData();
				data.putString("mess", mess);
				mHandler.sendMessage(msg);

			} else if (String.valueOf(Success).equals("false")) {
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
}
