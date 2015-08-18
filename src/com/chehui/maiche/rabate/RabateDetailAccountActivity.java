package com.chehui.maiche.rabate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.chehui.maiche.BaseActivity;
import com.chehui.maiche.R;
import com.chehui.maiche.SharedPreManager;
import com.chehui.maiche.comm.CommonData;
import com.chehui.maiche.httpserve.HttpService;
import com.chehui.maiche.utils.ToastUtils;
import com.chehui.maiche.utils.Utils;

/**
 * 明细账
 * 
 * @author zzp
 * 
 */
public class RabateDetailAccountActivity extends BaseActivity {
	private static final String TAG = "RabateDetailAccountActivity";

	private int sellerId;
	private String postParams;
	private String json;
	/** 奖金详情listView */
	private ListView listView;
	/** 存储返回数据的集合 */
	private List<Map<String, String>> list = new ArrayList<Map<String, String>>();
	private Map<String, String> map;
	/** 实例化adapter */
	private RabateDetailAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rabate_detail_account);
		initTitleView(-1, 255, R.string.set_account_money, 255, -1, 0);

		initData();
		initWidget();

	}

	private void initWidget() {
		listView = (ListView) findViewById(R.id.rabate_list_detailAccount);

	}

	private void initData() {
		sellerId = SharedPreManager.getInstance()
				.getInt(CommonData.USER_ID, 72);
		postParams = String.valueOf(sellerId);
		GetBurseList(postParams);
	}

	/**
	 * 获取详细信息
	 * 
	 * @param postParams
	 */
	private void GetBurseList(final String postParams) {
		if (!Utils.isNetworkAvailable(RabateDetailAccountActivity.this)) {
			ToastUtils.showShortToast(RabateDetailAccountActivity.this,
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
						"BankService"));
				parameters.add(new BasicNameValuePair("methodname",
						"GetBurseList"));
				parameters.add(new BasicNameValuePair("params", postParams));

				Log.d(TAG + "查看参数类型", parameters.toString());
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
					Log.d(TAG + "返回奖金明细", json);
					analysisJson();
				} else {
					ToastUtils.showShortToast(RabateDetailAccountActivity.this,
							"网络加载失败");
				}

			}

		}.execute();

	}

	/**
	 * 解析json数据
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
					String SellerID = item.optString("SellerID");
					String BusinessID = item.optString("BusinessID");
					String QueteID = item.optString("QueteID");
					String CreateDate = item.optString("CreateDate");
					String PayType = item.optString("PayType");
					String Cast = item.optString("Cast");
					String ReturnMoney = item.optString("ReturnMoney");
					String SellerGet = item.optString("SellerGet");

					map = new HashMap<String, String>();
					map.put("SellerID", SellerID);
					map.put("BusinessID", BusinessID);
					map.put("QueteID", QueteID);
					map.put("CreateDate", CreateDate);
					map.put("PayType", PayType);
					map.put("Cast", Cast);
					map.put("ReturnMoney", ReturnMoney);
					map.put("SellerGet", SellerGet);
					list.add(map);
				}
				if (adapter == null) {
					adapter = new RabateDetailAdapter(list);
					listView.setAdapter(adapter);
				} else {
					adapter.setData(list);
				}
			} else {
				ToastUtils.showShortToast(RabateDetailAccountActivity.this,
						mess);
			}
		} catch (JSONException e) {

			e.printStackTrace();
		}

	}

	/**
	 * 奖金详情adapter
	 * 
	 * @author zzp
	 * 
	 */
	private class RabateDetailAdapter extends BaseAdapter {
		private LayoutInflater inflater;
		private List<Map<String, String>> lists;

		public RabateDetailAdapter(List<Map<String, String>> list) {
			this.lists = list;
			inflater = LayoutInflater.from(RabateDetailAccountActivity.this);
		}

		public void setData(List<Map<String, String>> list) {
			lists = list;
			notifyDataSetChanged();
		}

		@Override
		public int getCount() {
			return lists.size();
		}

		@Override
		public Object getItem(int arg0) {
			return lists.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			return arg0;
		}

		@SuppressLint("ResourceAsColor")
		@Override
		public View getView(int position, View view, ViewGroup arg2) {
			ViewHolder holder;
			Map<String, String> map2 = lists.get(position);
			if (view == null) {
				holder = new ViewHolder();
				view = inflater.inflate(
						R.layout.activity_rabate_detail_account_item, null);
				holder.tvTime = (TextView) view
						.findViewById(R.id.rabate_txt_detail_list_date_item);
				holder.tvMoney = (TextView) view
						.findViewById(R.id.rabate_txt_detail_list_SellerGet);
				holder.tvPay = (TextView) view
						.findViewById(R.id.rabate_txt_detail_list_item);
				view.setTag(holder);
			} else {
				holder = (ViewHolder) view.getTag();
			}
			holder.tvTime.setText(map2.get("CreateDate").replace("T", " "));
			String str = map2.get("SellerGet");
			String substring = str.substring(1);
			if (str.indexOf("-") == -1) {
				holder.tvMoney.setTextColor(getResources()
						.getColor(R.color.red));
				holder.tvMoney.setText("+￥" + substring + ".00");
			} else {
				holder.tvMoney.setTextColor(getResources().getColor(
						R.color.green));
				holder.tvMoney.setText("-￥" + substring + ".00");
			}

			holder.tvPay.setText(map2.get("PayType"));
			return view;
		}

		class ViewHolder {
			private TextView tvTime;
			private TextView tvMoney;
			private TextView tvPay;
		}

	}
}
