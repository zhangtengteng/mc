package com.chehui.maiche.rabate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.chehui.maiche.BaseActivity;
import com.chehui.maiche.R;
import com.chehui.maiche.SharedPreManager;
import com.chehui.maiche.comm.CommonData;
import com.chehui.maiche.httpserve.HttpService;
import com.chehui.maiche.utils.ToastUtils;
import com.chehui.maiche.utils.Utils;

/**
 * @author zzp
 *         <P>
 *         银行卡列表和添加银行卡
 *         <P>
 * 
 */
public class RabateListBankcardActivity extends BaseActivity {

	/** 用户id */
	private int sellerId;
	/** 添加银行卡 */
	private LinearLayout addBankcard;
	/** 银行卡列表 */
	private ListView listBankcard;
	/** 返回json数据 */
	private String json;

	private List<Map<String, String>> list = new ArrayList<Map<String, String>>();

	private Map<String, String> map;

	private SimpleAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rabate_bankcard_list);
		initTitleView(-1, 255, R.string.set_account_add_bank_top2, 255, -1, 0);
		initData();
		initWidget();
	}

	@Override
	protected void onStart() {
		super.onStart();
		// 再次进入界面进行刷新获取新的数据
		initData();
	}

	/**
	 * 初始化数据
	 */
	private void initData() {

		sellerId = SharedPreManager.getInstance()
				.getInt(CommonData.USER_ID, 72);

		getBankCardListRequest(String.valueOf(sellerId));

	}

	/** 初始化组件 */
	private void initWidget() {

		findViewById(R.id.iv).setVisibility(View.VISIBLE);
		addBankcard = (LinearLayout) findViewById(R.id.rabate_add_bank);

		listBankcard = (ListView) findViewById(R.id.rabate_list_bankcard);

		addBankcard.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(RabateListBankcardActivity.this,
						RabateAddBankcardActivity.class);
				startActivity(intent);

			}
		});

	}

	/***
	 * 获取银行卡列表
	 */
	private void getBankCardListRequest(final String postParams) {
		if (!Utils.isNetworkAvailable(RabateListBankcardActivity.this)) {
			ToastUtils.showShortToast(RabateListBankcardActivity.this,
					R.string.common_network_unavalible);
			return;
		}
		showWaitDialog(R.string.common_request_no_title);

		new AsyncTask<Void, Integer, String>() {

			@Override
			protected String doInBackground(Void... params) {
				// 请求服务器
				final List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
				parameters.add(new BasicNameValuePair("classname",
						"BankService"));
				parameters.add(new BasicNameValuePair("methodname",
						"GetBankCardList"));
				parameters.add(new BasicNameValuePair("params", postParams));

				Log.d("查看参数类型", parameters.toString());
				String response = HttpService.methodPost(CommonData.HTTP_URL,
						parameters);
				return response;
			}

			@Override
			protected void onPostExecute(String result) {
				super.onPostExecute(result);
				dismissWaitDialog();
				if (result != null) {
					json = result.toString();
					Log.d("返回银行卡列表", json);
					analysisJson();
				} else {
					ToastUtils.showShortToast(RabateListBankcardActivity.this,
							"网络加载失败!");
				}
			}

		}.execute();

	}

	/***
	 * 解析返回json数据
	 */
	private void analysisJson() {
		try {
			JSONObject jsonObject = new JSONObject(json);
			Boolean success = jsonObject.getBoolean("Success");
			String mess = jsonObject.getString("Mess");
			if (success == true) {
				// 解析data数据
				JSONArray jsonArray = jsonObject.getJSONArray("Data");
				list.clear();
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject item = jsonArray.getJSONObject(i);
					String Id = item.optString("Id");
					String BankCardNum = item.optString("BankCardNum");
					String BankPass = item.optString("BankPass");
					String CreateDate = item.optString("CreateDate");
					String BankName = item.optString("BankName");
					String CardAdmin = item.optString("CardAdmin");
					String Tel = item.optString("Tel");
					int State = item.optInt("State");

					map = new HashMap<String, String>();
					map.put("Id", String.valueOf(Id));
					map.put("BankPass", BankPass);
					map.put("CreateDate", CreateDate);
					map.put("CardAdmin", CardAdmin);
					map.put("Tel", Tel);
					map.put("State", String.valueOf(State));
					map.put("BankCardNum",
							BankCardNum.substring(0, 3)
									+ "****"
									+ BankCardNum.substring(
											BankCardNum.length() - 5,
											BankCardNum.length() - 1));
					map.put("BankName", BankName);
					list.add(map);

				}
				if (adapter == null) {
					adapter = new SimpleAdapter(
							RabateListBankcardActivity.this, list,
							R.layout.activity_rabate_bankcard_item,
							new String[] { "BankName", "BankCardNum" },
							new int[] { R.id.rabate_txt_bankName,
									R.id.rabate_txt_cardNum });
					listBankcard.setAdapter(adapter);
				} else {
					adapter.notifyDataSetChanged();
				}

			} else {
				ToastUtils
						.showShortToast(RabateListBankcardActivity.this, mess);

			}

		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

}
