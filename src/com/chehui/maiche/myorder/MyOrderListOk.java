package com.chehui.maiche.myorder;

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
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

import com.chehui.maiche.BaseActivity;
import com.chehui.maiche.R;
import com.chehui.maiche.SharedPreManager;
import com.chehui.maiche.comm.CommonData;
import com.chehui.maiche.custom.AutoListView;
import com.chehui.maiche.custom.AutoListView.OnLoadListener;
import com.chehui.maiche.custom.AutoListView.OnRefreshListener;
import com.chehui.maiche.httpserve.HttpService;
import com.chehui.maiche.utils.LogN;
import com.chehui.maiche.utils.ToastUtils;
import com.chehui.maiche.utils.Utils;

/***
 * 已经完成的订单
 * 
 * @author Administrator
 * 
 */
public class MyOrderListOk extends BaseActivity implements OnRefreshListener,
		OnLoadListener, OnItemClickListener {
	private AutoListView autoListView;
	private String state;
	private int currentPage = 1;
	/** 需要提交的参数 */
	private String params;
	/** 返回的json数据 */
	private String json;
	/** 存储返回数据 */
	private List<Map<String, String>> list = new ArrayList<Map<String, String>>();
	/** id值 */
	private String sellerid;
	private Map<String, String> map;
	private MyOrderListAdapter adapter;
	private TextView tv_empty;
	/** 定义一个变量，来标识是否退出 */
	private static boolean isExit = false;
	/***
	 * 接收消息
	 */
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case CommonData.HTTP_HANDLE_FAILE:
				Bundle data = msg.getData();
				String result = data.getString("mess");
				ToastUtils.showShortToast(getApplicationContext(), result);
				break;
			default:
				break;
			}

		}
	};
	Handler mHandler2 = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			isExit = false;
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_list);
		initViews();
		initData();
	}

	private void initViews() {
		autoListView = (AutoListView) findViewById(R.id.listview_order);
		autoListView.setOnRefreshListener(this);
		autoListView.setOnLoadListener(this);
	autoListView.setOnItemClickListener(this);		tv_empty = (TextView) findViewById(R.id.tv_empty);
		tv_empty.setText(getResources().getString(R.string.main_order_pass));
		autoListView.setEmptyView(tv_empty);	}

	/**
	 * 初始化数据
	 */
	private void initData() {
		int id = SharedPreManager.getInstance().getInt(CommonData.USER_ID, 71);
		int userState = SharedPreManager.getInstance().getInt(
				CommonData.USER_STATE, 0);
		state = String.valueOf(userState);
		sellerid = String.valueOf(id);
		params = sellerid + "|" + state + "|" + String.valueOf(currentPage);
		getMyOrderData(params, AutoListView.FIRSTLOAD);
	}

	/***
	 * 获得请求数据
	 * 
	 * @param conParams
	 *            请求参数
	 */
	private void getMyOrderData(final String conParams, final int type) {
		if (!Utils.isNetworkAvailable(this)) {
			ToastUtils.showShortToast(this, R.string.common_network_unavalible);
			return;
		}
		if (type == AutoListView.FIRSTLOAD) {
			showWaitDialog(R.string.common_requesting);
		}
		new AsyncTask<Void, Integer, String>() {

			@Override
			protected String doInBackground(Void... params) {
				// 请求服务器
				final List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
				parameters.add(new BasicNameValuePair("classname",
						"SellerOperationService"));
				parameters.add(new BasicNameValuePair("methodname",
						"GetQueteByID"));
				parameters.add(new BasicNameValuePair("params", conParams));

				LogN.e(MyOrderListOk.this, "查看参数类型" + parameters.toString());
				String response = HttpService.methodPost(CommonData.HTTP_URL,
						parameters);

				return response;
			}

			@Override
			protected void onPostExecute(String result) {
				dismissWaitDialog();
				if (result != null) {
					json = result.toString();
					LogN.e(this, "我的报价界面所有数据===" + json);
					analysisJson(type);
				} else {
					ToastUtils.showShortToast(MyOrderListOk.this, "暂无数据!");
				}
			}

		}.execute();

	}

	/***
	 * 解析返回的数据
	 */
	private void analysisJson(int type) {
		JSONObject jsonObject = null;
		try {
			jsonObject = new JSONObject(json);
			Boolean success = jsonObject.getBoolean("Success");
			String mess = jsonObject.getString("Mess");
			if (success != true) {
				Message msg = handler
						.obtainMessage(CommonData.HTTP_HANDLE_FAILE);
				Bundle data = msg.getData();
				data.putString("mess", mess);
				handler.sendMessage(msg);
				return;
			}
			// 解析data数据
			JSONArray jsonArray = jsonObject.getJSONArray("Data");
			ArrayList tempList = new ArrayList();
			if (jsonArray.length() != 0) {
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject item = jsonArray.getJSONObject(i);
					// 获取对象对应的值
					int QuoteID = item.optInt("QuoteID");
					int SellerID = item.optInt("SellerID");
					int CarID = item.optInt("CarID");

					// 根据state【0,1,2】判断订单的状态，待接受，已接受，已支付
					int State = item.optInt("State");
					String CarDetail = item.optString("CarDetail");
					String Cityname = item.optString("Cityname");
					String FloorPrice = item.optString("FloorPrice");
					String FloorPriceCN = item.optString("FloorPriceCN");
					String InsurancePrice = item.optString("InsurancePrice");
					String LicensePrice = item.optString("LicensePrice");
					String PurchaseTax = item.optString("PurchaseTax");
					String Prize = item.optString("Prize");
					String Guideprice = item.optString("Guideprice");
					String BeginDate = item.optString("BeginDate");
					String BegindateStr = item.optString("BegindateStr");
					String EndDate = item.optString("EndDate");
					String EffectiveTime = item.optString("EffectiveTime");
					String CreateDate = item.optString("CreateDate");
					String CreateDateCN = item.optString("CreateDateCN");
					// String StateCN = item.optString("StateCN");
					String DingPrice = item.optString("DingPrice");
					String RegistrationFee = item.optString("RegistrationFee");
					String CarDecoration = item.optString("CarDecoration");
					String CarColor = item.optString("CarColor");
					String CarGift = item.optString("CarGift");
					String CarPlan = item.optString("CarPlan");
					String PayMode = item.optString("PayMode");
					String CarAddress = item.optString("CarAddress");
					String Sellername = item.optString("Sellername");
					String BrandName = item.optString("BrandName");
					String SeriesName = item.optString("SeriesName");
					String CarName = item.optString("CarName");
					String Tel = item.optString("Tel");
					String Ddbh = item.optString("Ddbh");
					String UserID = item.optString("UserID");
					String OrderNumber = item.optString("OrderNumber");
					String ZongJia = item.optString("ZongJia");
					String QitaZafei = item.optString("QitaZafei");
					String CarYear = item.optString("CarYear");
					String ClientID = item.optString("ClientID");
					String ClientType = item.optString("ClientType");
					String SeriesFace = item.optString("SeriesFace");
					int SellerVipLevel = item.optInt("SellerVipLevel");
					String CarImage = item.optString("CarImage");
					String OrderID = item.optString("OrderID");
					String UserName = item.optString("UserName");
					boolean IsUserPay = item.optBoolean("IsUserPay");
					String OrderState = item.optString("OrderState");
					
					
					map = new HashMap<String, String>();
					map.put("QuoteID", String.valueOf(QuoteID));
					map.put("SellerID", String.valueOf(SellerID));
					map.put("CarID", String.valueOf(CarID));
					map.put("CarDetail", CarDetail);
					map.put("Cityname", Cityname);
					map.put("FloorPrice", FloorPrice);
					map.put("FloorPriceCN", FloorPriceCN);
					System.out.println(FloorPrice + "++++++++++++++++++++"
							+ FloorPriceCN);
					map.put("InsurancePrice", InsurancePrice);
					map.put("LicensePrice", LicensePrice);
					map.put("PurchaseTax", PurchaseTax);
					map.put("Prize", Prize);
					map.put("Guideprice", Guideprice);
					map.put("BeginDate", BeginDate);
					map.put("BegindateStr", BegindateStr);
					map.put("EndDate", EndDate);
					map.put("EffectiveTime", EffectiveTime);
					map.put("CreateDate", CreateDate);
					map.put("CreateDateCN", CreateDateCN);
					map.put("DingPrice", DingPrice);
					map.put("RegistrationFee", RegistrationFee);
					map.put("CarDecoration", CarDecoration);
					map.put("CarColor", CarColor);
					map.put("CarGift", CarGift);
					map.put("CarPlan", CarPlan);
					map.put("PayMode", PayMode);
					map.put("CarAddress", CarAddress);
					map.put("Sellername", Sellername);
					map.put("BrandName", BrandName);
					map.put("SeriesName", SeriesName);
					map.put("CarName", CarName);
					map.put("Tel", Tel);
					map.put("Ddbh", Ddbh);
					map.put("UserID", UserID);
					map.put("OrderNumber", OrderNumber);
					map.put("ZongJia", ZongJia);
					map.put("QitaZafei", QitaZafei);
					map.put("CarYear", CarYear);
					map.put("ClientID", ClientID);
					map.put("ClientType", ClientType);
					map.put("SeriesFace", SeriesFace);
					map.put("SellerVipLevel", String.valueOf(SellerVipLevel));
					map.put("CarImage", CarImage);
					map.put("OrderID", OrderID);
					map.put("UserName", UserName);
					map.put("State", String.valueOf(State));
					map.put("IsUserPay", String.valueOf(IsUserPay));
					map.put("OrderState", OrderState);
					if (State == 2) {
						tempList.add(map);
					}
					// 根据state【0,1,2】判断订单的状态，待接受，已接受，已支付
				}
				if (AutoListView.REFRESH == type) {
					list.clear();
				}
				list.addAll(tempList);
				LogN.e(MyOrderListOk.this, "jsonArray=" + jsonArray.length());
				LogN.e(MyOrderListOk.this, "tempList=" + tempList.size());
				LogN.e(MyOrderListOk.this, "list.size=" + list.size());
				if (adapter == null) {
					adapter = new MyOrderListAdapter(MyOrderListOk.this, list);
					autoListView.setAdapter(adapter);
				} else {
					adapter.setData(list);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
			list.clear();
			if (adapter == null) {
				adapter = new MyOrderListAdapter(this, list);
				autoListView.setAdapter(adapter);
			} else {
				adapter.setData(list);
			}
		} finally {

			if (type == AutoListView.REFRESH) {
				autoListView.onRefreshComplete();
			} else if (type == AutoListView.LOAD) {
				autoListView.onLoadComplete();
			}
			if (jsonObject != null) {
				autoListView.setResultSize(jsonObject.length());
			}
		}

	}

	private void toOrderDetailActivity(int position) {
		Intent intent = new Intent(this, MyOrderDetialActtivity.class);
		Map<String, String> myOrder = list.get(position - 1);
		// 根据state【0,1,2】判断订单的状态，待接受，已接受，已支付
		String State = myOrder.get("State");
		// if (state.equals("1") || state.equals("2"))
		// return;
		String QuoteID = myOrder.get("QuoteID");
		String SellerID = myOrder.get("SellerID");
		String Tel = myOrder.get("Tel");
		String CarDetail = myOrder.get("CarDetail");
		String CarImage = myOrder.get("CarImage");
		String Guideprice = myOrder.get("Guideprice");
		String CarColor = myOrder.get("CarColor");
		String PayMode = myOrder.get("PayMode");
		String CarPlan = myOrder.get("CarPlan");
		String CarAddress = myOrder.get("CarAddress");
		String FloorPriceCN = myOrder.get("FloorPriceCN");

		String InsurancePrice = myOrder.get("InsurancePrice");
		String LicensePrice = myOrder.get("LicensePrice");
		String PurchaseTax = myOrder.get("PurchaseTax");
		String Prize = myOrder.get("Prize");

		String CarGift = myOrder.get("CarGift");
		String DingPrice = myOrder.get("DingPrice");
		String Cityname = myOrder.get("Cityname");
		String OrderID = myOrder.get("OrderID");
		String CreateDateCN = myOrder.get("CreateDateCN");
		String OrderState = myOrder.get("OrderState");

		intent.putExtra("Cityname", Cityname);
		intent.putExtra("CreateDateCN", CreateDateCN);
		intent.putExtra("SellerID", SellerID);
		intent.putExtra("Tel", Tel);
		intent.putExtra("CarDetail", CarDetail);
		intent.putExtra("CarImage", CarImage);
		intent.putExtra("Guideprice", Guideprice);
		intent.putExtra("CarColor", CarColor);
		intent.putExtra("PayMode", PayMode);
		intent.putExtra("CarPlan", CarPlan);
		intent.putExtra("CarAddress", CarAddress);
		intent.putExtra("FloorPriceCN", FloorPriceCN);
		intent.putExtra("InsurancePrice", InsurancePrice);
		intent.putExtra("LicensePrice", LicensePrice);
		intent.putExtra("PurchaseTax", PurchaseTax);
		intent.putExtra("Prize", Prize);
		intent.putExtra("CarGift", CarGift);
		intent.putExtra("DingPrice", DingPrice);
		intent.putExtra("QuoteID", QuoteID);
		intent.putExtra("OrderID", OrderID);
		intent.putExtra("State", State);
		intent.putExtra("OrderState", OrderState);
		startActivity(intent);
	}

	/**
	 * 加载更多
	 */
	@Override
	public void onLoad() {
		params = sellerid + "|" + state + "|" + String.valueOf(++currentPage);
		LogN.e(MyOrderListOk.this, "onLoad =====" + params);
		getMyOrderData(params, AutoListView.LOAD);
	}

	/**
	 * 刷新
	 */
	@Override
	public void onRefresh() {
		currentPage = 1;
		params = sellerid + "|" + state + "|" + String.valueOf(currentPage);
		LogN.e(MyOrderListOk.this, "onRefresh =====" + params);
		getMyOrderData(params, AutoListView.REFRESH);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
			long arg3) {
		toOrderDetailActivity(position);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			exit();
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}

	private void exit() {
		if (!isExit) {
			isExit = true;
			Toast.makeText(getApplicationContext(), "再按一次退出程序",
					Toast.LENGTH_SHORT).show();
			// 利用handler延迟发送更改状态信息
			mHandler2.sendEmptyMessageDelayed(0, 2000);
		} else {
			finish();
			System.exit(0);
		}
	}
}
