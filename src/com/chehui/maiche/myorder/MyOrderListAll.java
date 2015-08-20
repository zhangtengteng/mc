package com.chehui.maiche.myorder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

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
 * 全部订单
 * 
 * @author Administrator
 * 
 */
public class MyOrderListAll extends BaseActivity implements OnRefreshListener,
		OnLoadListener {
	private AutoListView autoListView;
	private String state;
	private int currentPage=1;
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
	}

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

				LogN.e(MyOrderListAll.this, "查看参数类型" + parameters.toString());
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
					ToastUtils.showShortToast(MyOrderListAll.this, "加载数据失败!");
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
					tempList.add(map);
					// 根据state【0,1,2】判断订单的状态，待接受，已接受，已支付
				}
				if (AutoListView.REFRESH == type) {
					list.clear();
				}
				list.addAll(tempList);
				LogN.e(MyOrderListAll.this, "jsonArray="+jsonArray.length());
				LogN.e(MyOrderListAll.this, "tempList="+tempList.size());
				LogN.e(MyOrderListAll.this, "list.size="+list.size());
				if (adapter == null) {
					adapter = new MyOrderListAdapter(MyOrderListAll.this, list);
					autoListView.setAdapter(adapter);
				} else {
					adapter.setData(list);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
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

	/**
	 * 加载更多
	 */
	@Override
	public void onLoad() {
		params = sellerid + "|" + state + "|" + String.valueOf(++currentPage);
		LogN.e(MyOrderListAll.this, "onLoad ====="+params);
		getMyOrderData(params, AutoListView.LOAD);
	}

	/**
	 * 刷新
	 */
	@Override
	public void onRefresh() {
		currentPage = 1;
		params = sellerid + "|" + state + "|" + String.valueOf(currentPage);
		LogN.e(MyOrderListAll.this, "onRefresh ====="+params);
		getMyOrderData(params, AutoListView.REFRESH);
	}

}
