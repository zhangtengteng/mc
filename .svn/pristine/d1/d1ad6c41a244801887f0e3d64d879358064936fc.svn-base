package com.chehui.maiche.setup;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chehui.maiche.BaseActivity;
import com.chehui.maiche.R;
import com.chehui.maiche.SharedPreManager;
import com.chehui.maiche.comm.CommonData;
import com.chehui.maiche.httpserve.HttpService;
import com.chehui.maiche.utils.ToastUtils;
import com.chehui.maiche.utils.Utils;

/**
 * 实名认证
 * 
 * @author gqy
 * 
 */
public class SetupManagerAuthentication extends BaseActivity {

	private Button btn_authentication;
	private LinearLayout authened;
	private EditText et_userName, et_identifyNumber;
	private TextView tv_userName, tv_identifyNumber;
	private ImageView image_authen;
	private TextView certified_progress;

	/** 用户认证状态 */
	private int vipLevel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_certified_merchants);
		initTitleView(-1, 255, R.string.set_account_authentication, 255, -1, 0);
		initData();
		initWidgets();
	}

	private void initData() {
		// 暂时将参数值默认为0
		String clientType = "android";
		String clientID = "0";
		String loginIp = "0";

		getVIPlevel(SharedPreManager.getInstance().getString(
				CommonData.USER_PHONE, "")
				+ "|"
				+ SharedPreManager.getInstance().getString(CommonData.USER_PWD,
						"") + "|" + clientType + "|" + clientID + "|" + loginIp);

	}

	/***
	 * 获取viplevel
	 * 
	 * @param username
	 * @param password
	 */
	private void getVIPlevel(final String postParams) {
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
				parameters.add(new BasicNameValuePair("params", postParams));

				String response = HttpService.methodPost(CommonData.HTTP_URL,
						parameters);
				return response;
			}

			@Override
			protected void onPostExecute(String result) {
				dismissWaitDialog();
				if (result != null) {
					String json = result.toString();
					try {
						// 返回的数据形式是一个Object类型，所以可以直接转换成一个Object
						JSONObject jsonObject = new JSONObject(json);
						Boolean success = jsonObject.getBoolean("Success");

						if (success == true) {
							// 获取对象中的对象
							JSONObject contentObject = jsonObject
									.getJSONObject("Data");

							vipLevel = contentObject.getInt("VipLevel");
							SharedPreManager.getInstance().setString(
									CommonData.SFZ,
									contentObject.getString("SFZ"));

						}

						// 判断是否已认证，1为已认证，0为未认证,2为待审核

						if (vipLevel == 1) {
							image_authen
									.setBackgroundResource(R.drawable.authened);
							btn_authentication.setVisibility(View.GONE);
							authened.setVisibility(View.VISIBLE);
							tv_userName.setText(SharedPreManager.getInstance()
									.getString(CommonData.USER_NAME, ""));
							tv_identifyNumber.setText(SharedPreManager
									.getInstance()
									.getString(CommonData.SFZ, ""));
							et_userName.setVisibility(View.GONE);
							et_identifyNumber.setVisibility(View.GONE);
							certified_progress.setText("通过认证");

						} else if (vipLevel == 2) {
							image_authen
									.setBackgroundResource(R.drawable.authened);
							btn_authentication.setVisibility(View.GONE);
							authened.setVisibility(View.VISIBLE);
							tv_userName.setText(SharedPreManager.getInstance()
									.getString(CommonData.USER_NAME, ""));
							tv_identifyNumber.setText(SharedPreManager
									.getInstance()
									.getString(CommonData.SFZ, ""));
							et_userName.setVisibility(View.GONE);
							et_identifyNumber.setVisibility(View.GONE);
							certified_progress.setText("待审核");
						} else if (vipLevel == 0) {
							image_authen
									.setBackgroundResource(R.drawable.unauthen);
							btn_authentication.setVisibility(View.VISIBLE);
							authened.setVisibility(View.GONE);
						}

					} catch (JSONException e) {
						e.printStackTrace();
					}

				} else {
					ToastUtils.showShortToast(SetupManagerAuthentication.this,
							"网络加载失败!");
				}
			}

		}.execute();

	}

	private void initWidgets() {
		btn_authentication = (Button) findViewById(R.id.btn_next3);
		et_userName = (EditText) findViewById(R.id.et_userName);
		et_identifyNumber = (EditText) findViewById(R.id.et_identity_number);
		tv_userName = (TextView) findViewById(R.id.tv_userName);
		tv_identifyNumber = (TextView) findViewById(R.id.tv_identity_number);
		authened = (LinearLayout) findViewById(R.id.authened);
		image_authen = (ImageView) findViewById(R.id.img_authen);
		certified_progress = (TextView) findViewById(R.id.txt_certified_progress);

		btn_authentication.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (et_userName.getText().toString().equals("")
						&& et_userName.getText().toString().trim() != null) {
					ToastUtils.showLongToast(getApplicationContext(), "姓名不能为空");
				} else if (et_identifyNumber.getText().toString().equals("")) {
					ToastUtils.showLongToast(getApplicationContext(),
							"身份证号不能为空");
				} else if (et_identifyNumber.getText().length() != 18) {
					ToastUtils.showLongToast(getApplicationContext(),
					 		"身份证号格式不正确");
				} else {
					Intent intent = new Intent(SetupManagerAuthentication.this,
							SetupUploadPic.class);
					intent.putExtra("USERNAME", et_userName.getText()
							.toString());
					intent.putExtra("IDETIFY", et_identifyNumber.getText()
							.toString());
					startActivity(intent);
				}
			}
		});
	}
}
