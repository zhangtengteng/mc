package com.chehui.maiche.rabate;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.chehui.maiche.R;
import com.chehui.maiche.SharedPreManager;
import com.chehui.maiche.comm.CommonData;
import com.chehui.maiche.custom.BaseFragment;
import com.chehui.maiche.httpserve.HttpService;
import com.chehui.maiche.utils.ToastUtils;
import com.chehui.maiche.utils.Utils;

/***
 * 我的奖金
 * 
 * @author zzp
 * 
 */
public class RabateFragment extends BaseFragment implements OnClickListener {

	/** 返回的json */
	private String json;
	/** 提交的参数 */
	private String postParams;

	private View mInflater;
	/** 用户ID */
	private int sellerId;

	/** 账户余额 */
	private TextView txt_balance;
	/** 提现 */
	private Button btnBalance;
	/** 账户明细 */
	private Button btnDetail;

	/** 我的奖金金额 */
	private String MyMoney;

	private Intent intent;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		mInflater = inflater
				.inflate(R.layout.fragment_rabate, container, false);

		initWidget();

		return mInflater;
	}

	@Override
	public void onStart() {
		super.onStart();

		initData();
		getMyBalanceRequest(postParams);

	}

	/***
	 * 初始化数据
	 */
	private void initData() {
		sellerId = SharedPreManager.getInstance()
				.getInt(CommonData.USER_ID, 72);
		postParams = String.valueOf(sellerId);

	}

	/** 初始化组件 */
	private void initWidget() {
		txt_balance = (TextView) mInflater
				.findViewById(R.id.rabate_txt_balance);
		btnBalance = (Button) mInflater.findViewById(R.id.rabate_btn_balance);
		btnDetail = (Button) mInflater.findViewById(R.id.rabate_btn_detail);
		btnBalance.setOnClickListener(this);
		btnDetail.setOnClickListener(this);
	}

	/**
	 * 获取我余额
	 */
	private void getMyBalanceRequest(final String postParams) {
		if (!Utils.isNetworkAvailable(RabateFragment.this.getActivity())) {
			ToastUtils.showShortToast(getActivity(),
					R.string.common_network_unavalible);
			return;
		}
//		showWaitDialog(R.string.common_request_no_title);

		new AsyncTask<Void, Integer, String>() {

			@Override
			protected String doInBackground(Void... params) {
				// 请求服务器
				final List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
				parameters.add(new BasicNameValuePair("classname",
						"BankService"));
				parameters.add(new BasicNameValuePair("methodname",
						"GetSellerSum"));
				parameters.add(new BasicNameValuePair("params", postParams));
				// Log.d("查看参数类型", parameters.toString());
				String response = HttpService.methodPost(CommonData.HTTP_URL,
						parameters);
				return response;
			}

			@Override
			protected void onPostExecute(String result) {
				super.onPostExecute(result);
//				dismissWaitDialog();
				if (result != null) {
					json = result.toString();
					analysisJson();
				} else {
					ToastUtils.showShortToast(getActivity(), "数据加载失败!");
				}

			}
		}.execute();

	}

	/***
	 * 解析数据
	 */
	private void analysisJson() {
		try {
			JSONObject jsonObject = new JSONObject(json);
			Boolean success = jsonObject.getBoolean("Success");
			String mess = jsonObject.getString("Mess");
			if (success == true) {
				JSONObject contentObject = jsonObject.getJSONObject("Data");
				MyMoney = contentObject.getString("MyMoney");
				txt_balance.setText("￥" + MyMoney);
			} else {

				ToastUtils.showShortToast(getActivity(), mess);

			}
		} catch (JSONException e) {

			e.printStackTrace();
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		// 提现
		case R.id.rabate_btn_balance:
			// 判断是否拥有银行卡
			isPossessBankCard();
			break;
		// 明细账
		case R.id.rabate_btn_detail:
			intent = new Intent(getActivity(),
					RabateDetailAccountActivity.class);
			startActivity(intent);

			break;

		default:
			break;
		}
	}

	/***
	 * 判断是否拥有银行卡
	 */
	private void isPossessBankCard() {
		new AsyncTask<Void, Integer, String>() {
			@Override
			protected String doInBackground(Void... params) {
				// 请求服务器
				final List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
				parameters.add(new BasicNameValuePair("classname",
						"BankService"));
				parameters.add(new BasicNameValuePair("methodname",
						"GetBankCardList"));
				parameters.add(new BasicNameValuePair("params", sellerId + ""));
				String response = HttpService.methodPost(CommonData.HTTP_URL,
						parameters);
				return response;
			}

			@Override
			protected void onPostExecute(String result) {
				super.onPostExecute(result);
				if (result != null) {
					json = result.toString();
					try {
						JSONObject jsonObject = new JSONObject(json);
						Boolean success = jsonObject.getBoolean("Success");
						if (success == true) {
							// 解析data数据
							JSONArray jsonArray = jsonObject
									.getJSONArray("Data");
							if (jsonArray.length() == 0) {
								intent = new Intent(getActivity(),
										RabateAddBankcardActivity.class);
								intent.putExtra("MONEY", MyMoney);
								// 区分直接添加银行卡的跳转方式
								CommonData.ISPOSSESSBANKCARD = true;
								startActivity(intent);
							} else {
								intent = new Intent(getActivity(),
										RabateWithdrawDepositActivity.class);
								intent.putExtra("MONEY", MyMoney);
								startActivity(intent);
							}
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				} else {
					ToastUtils.showShortToast(getActivity(), "网络请求失败!");
				}

			}
		}.execute();
	}
}
