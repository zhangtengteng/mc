package com.chehui.maiche.enquiry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.chehui.maiche.ActivityManager;
import com.chehui.maiche.BaseActivity;
import com.chehui.maiche.R;
import com.chehui.maiche.SharedPreManager;
import com.chehui.maiche.comm.CommonData;
import com.chehui.maiche.custom.PickerView.onSelectListener;
import com.chehui.maiche.enquiry.DownImage.ImageCallBack;
import com.chehui.maiche.httpserve.HttpService;
import com.chehui.maiche.pop.PoPBlandWindowManager;
import com.chehui.maiche.setup.SetupManagerAuthentication;
import com.chehui.maiche.utils.ToastUtils;
import com.chehui.maiche.utils.Utils;

/**
 * @author zzp
 *         <P>
 *         询价详情,该模块http请求不返回json
 *         <P>
 */
public class OrderItemDetail extends BaseActivity implements OnClickListener, TextWatcher {

	private static final String TAG = "OrderItemDetail";

	/** 此界面由于后台接口不返回json数据，只能用soap请求 */
	static final String SPECIALURL = "http://ws.maichetong.chehui.com/SellerOperationService.asmx/AddQuete";

	private String sellerId;

	/** 得到参数 */
	private String params;
	/** 用户名 */
	private TextView txt_name;
	/** 手机号 */
	private TextView txt_tel;
	/** 汽车详细 */
	private TextView txt_carDetail;
	/** 指导价格 */
	private TextView txt_guidePrice;
	/** 参考颜色 */
	private TextView txt_carColor;
	/** 牌照地点 */
	private TextView txt_carAddress;
	/** 提车计划 */
	private TextView txt_carPlan;
	/** 付款方式 */
	private TextView txt_payMode;
	/** 汽车图片 */
	private ImageView img_carImage;
	/** 获取数据 */
	private Intent intent;
	/** 返回json数据 */
	private String json;
	/** 存放数据 */
	private Map<String, String> map;
	/** 提交按钮 */
	private Button btn_post;

	private EditText edt_buyTax;

	private EditText edt_carPrice;

	private TextView edt_drawCarTime;

	private EditText edt_extralGift;

	private TextView edt_indate;

	private EditText edt_insurPrice;

	private EditText edt_licensePrice;

	private EditText edt_ortherCost;
	private String cutCarPlan;
	private String cutCarTime;

	private String purchaseTax, floorPrice, carPlan, carGift, time, insurancePrice, licesePrice, prize, dingPrice;

	/** 提车时间 */
	private List<String> drawCarDate = new ArrayList<String>();
	/** 有效期时间 */
	private List<String> indate = new ArrayList<String>();

	private Builder builder;

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case CommonData.HTTP_HANDLE_FAILE:
				Bundle data = msg.getData();
				String result = data.getString("mess");
				ToastUtils.showShortToast(OrderItemDetail.this, result);
				break;

			case CommonData.HTTP_HANDLE_SUCCESS:

				break;

			default:
				break;
			}

		}

	};

	/** 用户认证状态 */
	private int vipLevel;

	private TextView txt_huifubaojia;

	private TextView txt_chehuifanli;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_detail3);
		initTitleView(-1, 255, R.string.order_detail, 255, -1, 0);
		SharedPreManager.getInstance().init(getApplicationContext());
		initData();
		setPopWidth();
		initWidget();
		initPostWidget();
		initListener();
	}

	/**
	 * 初始化需要提交参数的组件信息
	 */
	private void initPostWidget() {

		edt_buyTax = (EditText) findViewById(R.id.orderItemP_edt_buyTax);
		edt_carPrice = (EditText) findViewById(R.id.orderItemP_edt_carPrice);
		edt_drawCarTime = (TextView) findViewById(R.id.orderItemP_edt_drawCarTime);
		edt_extralGift = (EditText) findViewById(R.id.orderItemP_edt_extralGift);
		edt_indate = (TextView) findViewById(R.id.orderItemP_edt_indate);
		edt_insurPrice = (EditText) findViewById(R.id.orderItemP_edt_insurPrice);
		edt_licensePrice = (EditText) findViewById(R.id.orderItemP_edt_licensePrice);
		edt_ortherCost = (EditText) findViewById(R.id.orderItemP_edt_ortherCost);
	}

	/**
	 * 设置pop的宽度
	 */
	private void setPopWidth() {
		@SuppressWarnings("deprecation")
		int width = getWindowManager().getDefaultDisplay().getWidth();
		@SuppressWarnings({ "deprecation" })
		int height = getWindowManager().getDefaultDisplay().getHeight();
		PoPBlandWindowManager.getInstance().init(getApplicationContext(), width, height / 3, R.layout.pop_bland);
	}

	/**
	 * 用户选中，更新文字
	 * 
	 * @param view
	 */
	private void setCarTimeText(final TextView view) {

		String dateStr1 = "现车";
		String dateStr2 = "一周内";
		String dateStr3 = "一月内";
		drawCarDate.clear();
		drawCarDate.add(dateStr1);
		drawCarDate.add(dateStr2);
		drawCarDate.add(dateStr3);
		PoPBlandWindowManager.getInstance().setPickViewData(drawCarDate);
		PoPBlandWindowManager.getInstance().changeOnClick(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 设置文字
				view.setText(drawCarDate.get(1));
				PoPBlandWindowManager.getInstance().dismissPop();
			}
		});
		PoPBlandWindowManager.getInstance().changeOnSelect(new onSelectListener() {
			@Override
			public void onSelect(final String text) {

				PoPBlandWindowManager.getInstance().changeOnClick(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// 设置文字
						view.setText(text);
						PoPBlandWindowManager.getInstance().dismissPop();

					}
				});

			}
		});
		setPopWidth();
		PoPBlandWindowManager.getInstance().showPopAllLocation(view, Gravity.CENTER | Gravity.BOTTOM, 0, 0);

	};

	/**
	 * 用户选中，更新文字
	 * 
	 * @param view
	 */
	private void setIndateText(final TextView view) {
		String dateStr1 = "3天";
		String dateStr2 = "7天";
		String dateStr3 = "15天";
		String dateStr4 = "长期";
		indate.clear();
		indate.add(dateStr1);
		indate.add(dateStr2);
		indate.add(dateStr3);
		indate.add(dateStr4);
		PoPBlandWindowManager.getInstance().setPickViewData(indate);
		PoPBlandWindowManager.getInstance().changeOnClick(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 设置文字
				view.setText(indate.get(1));
				PoPBlandWindowManager.getInstance().dismissPop();
			}
		});
		PoPBlandWindowManager.getInstance().changeOnSelect(new onSelectListener() {

			@Override
			public void onSelect(final String text) {
				PoPBlandWindowManager.getInstance().changeOnClick(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// 设置文字
						view.setText(text);
						PoPBlandWindowManager.getInstance().dismissPop();
					}
				});

				PoPBlandWindowManager.getInstance().changeOnClick(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// 设置文字
						view.setText(text);
						PoPBlandWindowManager.getInstance().dismissPop();
					}
				});
			}
		});
		setPopWidth();
		PoPBlandWindowManager.getInstance().showPopAllLocation(view, Gravity.CENTER | Gravity.BOTTOM, 0, 0);

	};

	/**
	 * 初始化监听
	 */
	private void initListener() {
		edt_drawCarTime.setOnClickListener(this);
		edt_indate.setOnClickListener(this);
		edt_carPrice.addTextChangedListener(this);

		btn_post.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				postNextPramas();

			}
		});
	}

	/**
	 * 执行addquete
	 * 
	 * @param PostfloorPrice
	 * @param Postinsurance
	 * @param postlicensePrice
	 * @param postpurchase
	 * @param postPrize
	 * @param postdingPrice
	 * @param postcutCarTime
	 * @param postCutPlan
	 */
	private void executeAddQuete(final String PostfloorPrice, final String Postinsurance, final String postlicensePrice,
			final String postpurchase, final String postPrize, final String postdingPrice, final String postcutCarTime,
			final String postCutPlan) {
		if (!Utils.isNetworkAvailable(OrderItemDetail.this)) {
			ToastUtils.showShortToast(OrderItemDetail.this, R.string.common_network_unavalible);
			return;
		}
		showWaitDialog(R.string.common_requesting);

		new AsyncTask<Void, Integer, String>() {
			@Override
			protected String doInBackground(Void... params) {

				// 请求服务器
				final List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
				parameters.add(new BasicNameValuePair("SellerID", sellerId));
				parameters.add(new BasicNameValuePair("DDBH", map.get("Ddbh")));
				parameters.add(new BasicNameValuePair("userid", map.get("userId")));
				parameters.add(new BasicNameValuePair("CarID", map.get("CarID")));
				parameters.add(new BasicNameValuePair("Cityname", map.get("Cityname")));
				parameters.add(new BasicNameValuePair("FloorPrice", PostfloorPrice));
				parameters.add(new BasicNameValuePair("InsurancePrice", Postinsurance));
				parameters.add(new BasicNameValuePair("LicensePrice", postlicensePrice));
				parameters.add(new BasicNameValuePair("PurchaseTax", postpurchase));
				parameters.add(new BasicNameValuePair("Prize", postPrize));
				parameters.add(new BasicNameValuePair("DingPrice", postdingPrice));
				parameters.add(new BasicNameValuePair("OrderID", map.get("OrderID")));

				parameters.add(new BasicNameValuePair("Time", postcutCarTime));
				parameters.add(new BasicNameValuePair("CarGift", carGift));
				parameters.add(new BasicNameValuePair("CarPlan", postCutPlan));

				Log.d(TAG + "报价参数值", parameters.toString());

				String response = HttpService.methodPost(SPECIALURL, parameters);
				return response;
			}

			@Override
			protected void onPostExecute(String result) {
				super.onPostExecute(result);
				dismissWaitDialog();
				if (result != null) {
					int startSuccess = result.indexOf("<Success>");
					int endSuccess = result.indexOf("</Success>");
					String isSuccess = result.substring(startSuccess, endSuccess).replaceAll("<Success>", "");
					Log.d(TAG + "确认提交参数", isSuccess);
					if (isSuccess.trim().equals("true")) {
						startNextActivity();
					} else {
						Message msg = handler.obtainMessage(CommonData.HTTP_HANDLE_FAILE);
						Bundle data = msg.getData();
						data.putString("mess", "报价失败");
						handler.sendMessage(msg);
					}
				} else {
					ToastUtils.showShortToast(OrderItemDetail.this, "提交报价失败!");
				}

			}

		}.execute();

	}

	private void startNextActivity() {
		Intent intent1 = new Intent(OrderItemDetail.this, OrderCompeleted.class);
		startActivity(intent1);
		finish();
	}

	/**
	 * 用户输入的下级界面参数
	 */
	private void postNextPramas() {
		purchaseTax = edt_buyTax.getText().toString().trim();
		floorPrice = edt_carPrice.getText().toString().trim();
		carPlan = edt_drawCarTime.getText().toString().trim();
		carGift = edt_extralGift.getText().toString().trim();
		time = edt_indate.getText().toString().trim();
		insurancePrice = edt_insurPrice.getText().toString().trim();
		licesePrice = edt_licensePrice.getText().toString().trim();
		prize = edt_ortherCost.getText().toString().trim();
		dingPrice = "500";

		if (floorPrice == null || floorPrice.equals("")) {
			ToastUtils.showShortToast(OrderItemDetail.this, "请将信息填写完整");
			return;
		} else if (prize == null || prize.equals("")) {
			ToastUtils.showShortToast(OrderItemDetail.this, "请将信息填写完整");
			return;
		} else if (time == null || time.equals("")) {
			ToastUtils.showShortToast(OrderItemDetail.this, "请将信息填写完整");
			return;
		} else if (carPlan == null || carPlan.equals("")) {
			ToastUtils.showShortToast(OrderItemDetail.this, "请将信息填写完整");
			return;
		} else if (insurancePrice == null || insurancePrice.equals("")) {
			ToastUtils.showShortToast(OrderItemDetail.this, "请将信息填写完整");
			return;
		} else if (licesePrice == null || licesePrice.equals("")) {
			ToastUtils.showShortToast(OrderItemDetail.this, "请将信息填写完整");
			return;
		} else if (purchaseTax == null || purchaseTax.equals("")) {
			ToastUtils.showShortToast(OrderItemDetail.this, "请将信息填写完整");
			return;
		} else {
			if (carPlan.equals("现车")) {
				cutCarPlan = "1";
			} else if (carPlan.equals("一周内")) {
				cutCarPlan = "2";
			} else if (carPlan.equals("一月内")) {
				cutCarPlan = "3";
			}
			if (time.equals("3天")) {
				cutCarTime = "3";
			} else if (time.equals("7天")) {
				cutCarTime = "7";
			} else if (time.equals("15天")) {
				cutCarTime = "15";
			} else if (time.equals("长期")) {
				cutCarTime = "365";
			}
			executeAddQuete(floorPrice, insurancePrice, licesePrice, purchaseTax, prize, dingPrice, cutCarTime,
					cutCarPlan);
		}

	}

	/***
	 * 初始化组件
	 */
	private void initWidget() {
		txt_name = (TextView) findViewById(R.id.orderItemD_txt_Name);
		txt_tel = (TextView) findViewById(R.id.orderItemD_txt_telNum);
		txt_carDetail = (TextView) findViewById(R.id.orderItemD_txt_seriesName);
		txt_guidePrice = (TextView) findViewById(R.id.orderItemD_txt_guidePrice);
		txt_carColor = (TextView) findViewById(R.id.orderItemD_txt_carColor);
		txt_carAddress = (TextView) findViewById(R.id.orderItemD_txt_carAddress);
		txt_carPlan = (TextView) findViewById(R.id.orderItemD_txt_carPlan);
		txt_payMode = (TextView) findViewById(R.id.orderItemD_txt_payMode);
		img_carImage = (ImageView) findViewById(R.id.orderItemD_img_car);
		btn_post = (Button) findViewById(R.id.orderItemD_btn_post);
		// 常量值
		txt_huifubaojia = (TextView) findViewById(R.id.orderItemD_txt_huifubaojia);
		txt_chehuifanli = (TextView) findViewById(R.id.orderItemD_txt_chehuifanli);

		txt_huifubaojia.setText(
				"①亲，每回复一条报价您将获得" + SharedPreManager.getInstance().getString(CommonData.HUIFUBAOJIA, "") + "元奖励哦!");

		txt_chehuifanli.setText(
				"③用户接受您的报价，还将再奖励您" + SharedPreManager.getInstance().getString(CommonData.CHEHUIFANLI, "") + "元!");

	}

	/**
	 * 初始化数据
	 */
	private void initData() {

		intent = getIntent();
		sellerId = intent.getStringExtra("sellerid");
		String orderID = intent.getStringExtra("orderid");
		params = orderID + "|" + sellerId;
		getOrderByBrands(params);

		// 暂时将参数值默认为0
		String clientType = "android";
		String clientID = "0";
		String loginIp = "0";

		getVIPlevel(SharedPreManager.getInstance().getString(CommonData.USER_PHONE, "") + "|"
				+ SharedPreManager.getInstance().getString(CommonData.USER_PWD, "") + "|" + clientType + "|" + clientID
				+ "|" + loginIp);

	}

	/***
	 * 获取viplevel
	 * 
	 * @param username
	 * @param password
	 * 
	 */
	private void getVIPlevel(final String postParams) {
		if (!Utils.isNetworkAvailable(this)) {
			ToastUtils.showShortToast(getApplicationContext(), R.string.common_network_unavalible);
			return;
		}

		showWaitDialog(R.string.common_requesting);
		new AsyncTask<Void, Integer, String>() {
			@Override
			protected String doInBackground(Void... params) {
				// 请求服务器
				final List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
				parameters.add(new BasicNameValuePair("classname", "BaseInfoService"));
				parameters.add(new BasicNameValuePair("methodname", "SellerLoginByTel"));
				parameters.add(new BasicNameValuePair("params", postParams));

				String response = HttpService.methodPost(CommonData.HTTP_URL, parameters);

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
							JSONObject contentObject = jsonObject.getJSONObject("Data");

							vipLevel = contentObject.getInt("VipLevel");

						}

						if (vipLevel == 2 || vipLevel == 0) {
							mAlertTipsDialog("您还未提交实名认证，还不能向客户提交报价！");
						}

					} catch (JSONException e) {
						e.printStackTrace();
					}

				} else {
					ToastUtils.showShortToast(OrderItemDetail.this, "网络加载失败!");
				}
			}

		}.execute();

	}

	/**
	 * 弹出提示实名认证
	 * 
	 * @param message
	 */
	@SuppressLint("NewApi")
	private void mAlertTipsDialog(String message) {

		if (builder == null) {
			builder = new android.app.AlertDialog.Builder(OrderItemDetail.this);
		}
		builder.setCancelable(false);
		builder.setMessage(message).setTitle("提示").setPositiveButton("取消", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				finish();
			}
		}).setNegativeButton("去实名认证", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				ActivityManager.getInstance().startNextActivity(SetupManagerAuthentication.class);
				finish();

			}
		}).show();
	}

	/***
	 * 获得询价数据
	 * 
	 * @param conParams
	 *            参数值
	 */
	private void getOrderByBrands(final String conParams) {
		if (!Utils.isNetworkAvailable(OrderItemDetail.this)) {
			ToastUtils.showShortToast(OrderItemDetail.this, R.string.common_network_unavalible);
			return;
		}
		showWaitDialog(R.string.common_requesting);
		new AsyncTask<Void, Integer, String>() {

			@Override
			protected String doInBackground(Void... params) {
				// 请求服务器
				final List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
				parameters.add(new BasicNameValuePair("classname", "SellerOperationService"));
				parameters.add(new BasicNameValuePair("methodname", "GetOrderByID"));
				parameters.add(new BasicNameValuePair("params", conParams));

				Log.d("查看参数类型" + TAG, parameters.toString());
				String response = HttpService.methodPost(CommonData.HTTP_URL, parameters);
				return response;

			}

			@Override
			protected void onPostExecute(String result) {
				super.onPostExecute(result);
				dismissWaitDialog();
				if (result != null) {
					json = result.toString();
					Log.d("返回询价数据", json);
					analysisJson();
				} else {
					ToastUtils.showShortToast(OrderItemDetail.this, "网络加载失败!");
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
				// 解析data数据
				JSONArray jsonArray = jsonObject.getJSONArray("Data");
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject item = jsonArray.getJSONObject(i);
					// 获取对象对应的值
					String carImage = item.optString("CarImage");
					String Name = item.optString("Name", "111");
					String tel = item.optString("Tel");

					// 替换号码*****
					String cutHeadTel = tel.substring(0, 3);
					String cutFootTel = tel.substring(8);
					String viewTel = cutHeadTel + "*****" + cutFootTel;

					String carDetail = item.optString("CarDetail");
					String payMode = item.optString("PayMode");
					String carColor = item.optString("CarColor");
					String carPlan = item.optString("CarPlan");
					String carAddress = item.optString("CarAddress");
					String guideprice = item.optString("Guideprice");

					// 返回下级界面需要参数
					String Ddbh = item.optString("Ddbh");
					int userId = item.optInt("UserID");
					int CarID = item.optInt("CarID");
					String Cityname = item.optString("Cityname");
					int OrderID = item.optInt("OrderID");
					String CarPlan = item.optString("CarPlan");

					// 将参数存进map
					map = new HashMap<String, String>();

					// 页面显示参数
					map.put("carImage", carImage);
					map.put("name", Name);
					map.put("tel", tel);
					map.put("carDetail", carDetail);
					map.put("payMode", payMode);
					map.put("carColor", carColor);
					map.put("carPlan", carPlan);
					map.put("carAddress", carAddress);
					map.put("guideprice", guideprice);
					map.put("viewTel", viewTel);

					// 下级界面需要参数
					map.put("OrderID", String.valueOf(OrderID));
					map.put("Ddbh", Ddbh);
					map.put("Cityname", Cityname);
					map.put("CarPlan", CarPlan);
					map.put("userId", String.valueOf(userId));
					map.put("CarID", String.valueOf(CarID));

					setWidgetVaule();

				}
			} else {
				Message msg = handler.obtainMessage(CommonData.HTTP_HANDLE_FAILE);
				Bundle data = msg.getData();
				data.putString("mess", mess);
				handler.sendMessage(msg);

			}

		} catch (JSONException e) {

			e.printStackTrace();
		}

	}

	/**
	 * 给组件赋值
	 */
	private void setWidgetVaule() {
		txt_name.setText(map.get("name"));
		txt_tel.setText(map.get("viewTel"));
		txt_carDetail.setText(map.get("carDetail"));
		txt_guidePrice.setText("厂商指导价:￥" + map.get("guideprice") + "元");
		txt_carColor.setText(map.get("carColor"));
		txt_carAddress.setText(map.get("carAddress"));
		txt_carPlan.setText(map.get("carPlan"));
		txt_payMode.setText(map.get("payMode"));

		// 绑定数据
		DownImage downImage = new DownImage(CommonData.HTTP_IMG_URL + map.get("carImage"));
		downImage.loadImage(new ImageCallBack() {

			@Override
			public void getDrawable(Drawable drawable) {

				img_carImage.setImageDrawable(drawable);

			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		// 获取提车日期
		case R.id.orderItemP_edt_drawCarTime:
			setCarTimeText(edt_drawCarTime);
			break;

		// 有效期
		case R.id.orderItemP_edt_indate:
			setIndateText(edt_indate);
			break;

		default:
			break;
		}

	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterTextChanged(Editable s) {
		edt_carPrice.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {

				} else {
					String macthPrice = edt_carPrice.getText().toString().trim();
					String guidePrice = map.get("guideprice");

					if (!macthPrice.equals("") && macthPrice != null && !macthPrice.equals("0")) {
						if (Integer.valueOf(macthPrice) < 0.4 * Integer.valueOf(guidePrice)) {
							mAlertTipsDialog_price("您所报的裸车价格与厂商指导价相差比较大，您确定使用这个价格吗？");
						} else if (Integer.valueOf(macthPrice) > 1.4 * Integer.valueOf(guidePrice)) {
							mAlertTipsDialog_price("您所报的裸车价格与厂商指导价相差比较大，您确定使用这个价格吗？");
						}
					}

				}

			}
		});

	}

	/**
	 * 弹出dialog
	 * 
	 * @param message
	 */
	private void mAlertTipsDialog_price(String message) {

		if (builder == null) {
			builder = new android.app.AlertDialog.Builder(OrderItemDetail.this);
		}
		builder.setMessage(message).setTitle("提示").setPositiveButton("取消", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				edt_carPrice.setText("");
			}
		}).setNegativeButton("确定", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

			}
		}).show();
	}

	@Override
	protected void onPause() {
		super.onPause();
		hideSoftInput();
	}
}
