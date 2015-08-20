package com.chehui.maiche.myorder;

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
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.chehui.maiche.BaseActivity;
import com.chehui.maiche.R;
import com.chehui.maiche.SharedPreManager;
import com.chehui.maiche.comm.CommonData;
import com.chehui.maiche.custom.PickerView.onSelectListener;
import com.chehui.maiche.enquiry.DownImage;
import com.chehui.maiche.enquiry.DownImage.ImageCallBack;
import com.chehui.maiche.httpserve.HttpService;
import com.chehui.maiche.pop.PoPBlandWindowManager;
import com.chehui.maiche.utils.LogN;
import com.chehui.maiche.utils.ToastUtils;
import com.chehui.maiche.utils.Utils;

/**
 * 
 * @author zzp
 *         <P>
 *         报价详细界面
 *         <P>
 * 
 */
public class MyOrderDetialActtivity extends BaseActivity {

	private static final String TAG = "MyOrderDetialActtivity";

	/** 再次修改 */
	private static boolean ISMODIFY = false;

	@SuppressWarnings("unused")
	private ImageView img_seller;
	private ImageView img_car;
	private Button btn_modify;
	private TextView txt_carAddress;
	private TextView txt_carColor;
	private TextView txt_carPlan;
	private TextView txt_carPrice;
	private TextView txt_drawCarTime;
	private TextView txt_extralGift;
	private TextView txt_guidePrice;
	private TextView txt_insurPrice;
	private TextView txt_licensePrice;
	private TextView txt_ortherCost;
	private TextView txt_telNum;
	private TextView txt_payMode;
	private TextView txt_seriesName;
	private String cutCarPlan;
	@SuppressWarnings("unused")
	private String sellerID, tel, carDetail, carImage, guideprice, carColor,
			payMode, postDrawCarPlan, carAddress, floorPriceCN, insurancePrice,
			licensePrice, purchaseTax, prize, carGift, dingPrice;
	private TextView txt_buyTax;
	private TextView txt_addprice;
	private TextView txt_Name;
	private EditText post_floorPrice;
	private EditText post_insurance;
	private EditText post_licensePrice;
	private EditText post_tax;
	private EditText post_otherFee;
	private EditText post_extralGift;
	private TextView post_drawCarPlan;
	private String quoteID;
	@SuppressWarnings("unused")
	private String cityname;

	private String postInsurance;
	private String postLicensePrice;
	private String postTax;
	private String postOtherFee;
	private String postExtralGift;
	private String orderID;
	private String state;
	private Builder builder;
	private String OrderState;

	private List<String> drawCarDate = new ArrayList<String>();

	/** 存放数据 */
	private Map<String, String> map;

	private String carPrice;

	private String json;

	private String floorPrice;

	private String carPlan;

	private PoPModifyPriceManager modifyPriceManager;

	private Button post_btn_submit;

	private String hadModifyprice;

	/***
	 * 接收消息更新UI
	 */
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case CommonData.HTTP_HANDLE_FAILE:
				Bundle data = msg.getData();
				String result = data.getString("mess");
				ToastUtils.showShortToast(MyOrderDetialActtivity.this, result);
				break;
			case CommonData.HTTP_HANDLE_SUCCESS:
				sendBroadCastRecevier();
				txt_carPrice.setText(hadModifyprice);
				txt_buyTax.setText(postTax);
				txt_extralGift.setText(postExtralGift);
				txt_insurPrice.setText(postInsurance);
				txt_licensePrice.setText(postLicensePrice);
				txt_ortherCost.setText(postOtherFee);
				txt_drawCarTime.setText(postDrawCarPlan);
				ToastUtils.showShortToast(MyOrderDetialActtivity.this,
						"修改报价成功！");
				break;
			default:
				break;
			}

		}
	};

	/** 创建日期 */
	private String createDateCN;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_myorderdetial);
		initTitleView(-1, 255, R.string.myorder_detail, 255, -1, 0);
		btn_modify = (Button) findViewById(R.id.myOrderDetail_btn_post);
		initData();
		initWidget();
		initListener();

	}

	/***
	 * 初始化修改报价组件
	 */
	private void initModifyWidgets() {
		View updataForm = modifyPriceManager.getView();

		post_floorPrice = (EditText) updataForm
				.findViewById(R.id.myorderDetail_edt_quote);
		post_insurance = (EditText) updataForm
				.findViewById(R.id.myorderDetail_edt_insurance);
		post_licensePrice = (EditText) updataForm
				.findViewById(R.id.myorderDetail_edt_licensePrice);
		post_tax = (EditText) updataForm
				.findViewById(R.id.myorderDetail_edt_tax);
		post_otherFee = (EditText) updataForm
				.findViewById(R.id.myorderDetail_edt_otherFee);
		post_extralGift = (EditText) updataForm
				.findViewById(R.id.myorderDetail_edt_extralGift);
		post_drawCarPlan = (TextView) updataForm
				.findViewById(R.id.myorderDetail_edt_drawCarPlan);

		post_btn_submit = (Button) updataForm
				.findViewById(R.id.myorderDetail_btn_submit);

		post_btn_submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				modifyPrice();

				modifyPriceManager.dismissPop();

			}
		});

	}

	/**
	 * 修改报价内容初始化
	 */
	private void modifyPrice() {

		hadModifyprice = post_floorPrice.getText().toString().trim().equals("") ? post_floorPrice
				.getHint().toString().trim().replaceAll("当前价格", "").trim()
				: post_floorPrice.getText().toString().trim();

		postInsurance = post_insurance.getText().toString().trim().equals("") ? post_insurance
				.getHint().toString().trim().replaceAll("当前价格", "").trim()
				: post_insurance.getText().toString().trim();

		postLicensePrice = post_licensePrice.getText().toString().trim()
				.equals("") ? post_licensePrice.getHint().toString().trim()
				.replaceAll("当前价格", "").trim() : post_licensePrice.getText()
				.toString().trim();

		postTax = post_tax.getText().toString().trim().equals("") ? post_tax
				.getHint().toString().trim().replaceAll("当前价格", "").trim()
				: post_tax.getText().toString().trim();

		postOtherFee = post_otherFee.getText().toString().trim().equals("") ? post_otherFee
				.getHint().toString().trim().replaceAll("当前价格", "").trim()
				: post_otherFee.getText().toString().trim();

		postExtralGift = post_extralGift.getText().toString().trim().equals("") ? carGift
				: post_extralGift.getText().toString().trim();

		postDrawCarPlan = post_drawCarPlan.getText().toString().trim()
				.equals("") ? carPlan : post_drawCarPlan.getText().toString()
				.trim();

		// 更具提车计划替换成数字提交后台
		if (postDrawCarPlan.equals("现车")) {
			cutCarPlan = "1";
		} else if (postDrawCarPlan.equals("一周内")) {
			cutCarPlan = "2";
		} else if (postDrawCarPlan.equals("一个月内")) {
			cutCarPlan = "3";
		} else {

		}

		String updataParams = quoteID + "|" + cutCarPlan + "|" + hadModifyprice
				+ "|" + postInsurance + "|" + postLicensePrice + "|" + postTax
				+ "|" + postOtherFee + "|" + dingPrice + "|" + postExtralGift;

		Log.d(TAG + "查看修改报价的信息", updataParams);

		updataOrder(updataParams);

	}

	@SuppressLint("ResourceAsColor")
	private void initListener() {
		if (OrderState.equals("1")) {
			btn_modify.setBackgroundColor(Color.parseColor("#E8E5E5"));
			btn_modify.setClickable(false);
		} else {
			btn_modify.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					if (state.equals("1")) {
						// 上传发票
						activityManager
								.startNextActivity(MyOderUploadInvoice.class);
					} else if (state.equals("2")) {
						// 隐藏
					} else {
						setPopModifyWidth();
					}
				}
			});
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		ISMODIFY = false;
	}

	/**
	 * 修改报价对话框
	 */
	private void updataQuote() {
		post_floorPrice.setHint("当前价格" + carPrice);
		post_insurance.setHint("当前价格" + insurancePrice);
		post_licensePrice.setHint("当前价格" + licensePrice);
		post_drawCarPlan.setText(carPlan);
		post_tax.setHint("当前价格" + purchaseTax);
		post_otherFee.setHint("当前价格" + prize);
		post_extralGift.setHint(carGift);

		if (ISMODIFY == true) {
			post_floorPrice.setHint("当前价格" + hadModifyprice);
			post_insurance.setHint("当前价格" + postInsurance);
			post_licensePrice.setHint("当前价格" + postLicensePrice);
			post_drawCarPlan.setText(postDrawCarPlan);
			post_tax.setHint("当前价格" + postTax);
			post_otherFee.setHint("当前价格" + postOtherFee);
			post_extralGift.setHint(postExtralGift);
			ISMODIFY = false;
		}

		post_floorPrice.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View arg0, boolean hasFocus) {
				if (hasFocus) {
				} else {
					String price = post_floorPrice.getText().toString();
					if (price.equals("")) {
						price = floorPrice.substring(0, floorPrice.indexOf("."));
					}
					int valueOf = Integer.valueOf(price);
					Integer oldPrice = (int) (Integer.valueOf(guideprice) * 0.6);
					if (valueOf < oldPrice
							|| valueOf > Integer.valueOf(guideprice) * 1.4) {
						ToastUtils.showShortToast(MyOrderDetialActtivity.this,
								"您所报的裸车价格与厂商指导价相差比较大，您确定使用这个价格吗？");
					}
				}
			}
		});

		post_drawCarPlan.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				setCarTimeText(post_drawCarPlan);
			}
		});

	}

	/**
	 * 更新报价
	 * 
	 * @param updataParams
	 */
	private void updataOrder(final String updataParams) {
		if (!Utils.isNetworkAvailable(MyOrderDetialActtivity.this)) {
			ToastUtils.showShortToast(this, R.string.common_network_unavalible);
			return;
		}
		showWaitDialog(R.string.common_requesting);
		new AsyncTask<Void, Integer, String>() {

			@Override
			protected String doInBackground(Void... params) {
				// 请求服务器
				final List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
				parameters.add(new BasicNameValuePair("classname",
						"SellerOperationService"));
				parameters.add(new BasicNameValuePair("methodname",
						"UpdateQuete"));
				parameters.add(new BasicNameValuePair("params", updataParams));

				String response = HttpService.methodPost(CommonData.HTTP_URL,
						parameters);
				return response;
			}

			@Override
			protected void onPostExecute(String result) {
				super.onPostExecute(result);
				dismissWaitDialog();
				if (result != null) {
					String json = result.toString();
					try {
						JSONObject jsonObject = new JSONObject(json);
						Boolean success = jsonObject.getBoolean("Success");
						String mess = jsonObject.getString("Mess");
						if (success) {
							Message msg = handler
									.obtainMessage(CommonData.HTTP_HANDLE_SUCCESS);
							Bundle data = msg.getData();
							data.putString("success", String.valueOf(success));
							handler.sendMessage(msg);

							CommonData.ISMYORDERFRAGMENTREFRESH = true;
							ISMODIFY = true;
						} else {
							Message msg = handler
									.obtainMessage(CommonData.HTTP_HANDLE_FAILE);
							Bundle data = msg.getData();
							data.putString("mess", mess);
							handler.sendMessage(msg);
							return;
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}

				} else {
					ToastUtils.showShortToast(MyOrderDetialActtivity.this,
							"网络加载失败!");
				}

			}

		}.execute();

	}

	/**
	 * 初始化组件
	 */
	private void initWidget() {
		img_seller = (ImageView) findViewById(R.id.myOrderDetail_img_seller);
		img_car = (ImageView) findViewById(R.id.myOrderDetail_img_car);
		DownImage downImage = new DownImage(CommonData.HTTP_IMG_URL + carImage);

		downImage.loadImage(new ImageCallBack() {

			@Override
			public void getDrawable(Drawable drawable) {
				img_car.setImageDrawable(drawable);
			}
		});

		// 根据state【0,1,2】判断订单的状态，待接受，已接受，已支付
		if (state.equals("1")) {
			btn_modify.setText("上传发票");
			btn_modify.setVisibility(View.VISIBLE);
		} else if (state.equals("2")) {
			btn_modify.setVisibility(View.INVISIBLE);
		} else {
			btn_modify.setText("修改报价");
			btn_modify.setVisibility(View.VISIBLE);
		}
		txt_carAddress = (TextView) findViewById(R.id.myOrderDetail_txt_carAddress);
		txt_carColor = (TextView) findViewById(R.id.myOrderDetail_txt_carColor);
		txt_carPlan = (TextView) findViewById(R.id.myOrderDetail_txt_carPlan);
		txt_carPrice = (TextView) findViewById(R.id.myOrderDetail_txt_carPrice);
		TextView txt_createDateCN = (TextView) findViewById(R.id.myOrderDetail_txt_createDateCN);
		txt_createDateCN
				.setText("详细报价\t\t" + createDateCN.replaceAll("/", "-"));

		// 讲万元改为元
		float floorPriceCN_txt = Float
				.valueOf(floorPriceCN.replaceAll("￥", "")) * 10000;
		floorPrice = String.valueOf(floorPriceCN_txt);
		carPrice = floorPrice.substring(0, floorPrice.indexOf("."));
		txt_carPrice.setText(carPrice);

		txt_drawCarTime = (TextView) findViewById(R.id.myOrderDetail_txt_drawCarTime);
		txt_drawCarTime.setText(carPlan);

		txt_extralGift = (TextView) findViewById(R.id.myOrderDetail_txt_extralGift);
		txt_extralGift.setText(carGift);

		txt_guidePrice = (TextView) findViewById(R.id.myOrderDetail_txt_guidePrice);

		txt_insurPrice = (TextView) findViewById(R.id.myOrderDetail_txt_insurPrice);
		txt_insurPrice.setText(insurancePrice);

		txt_licensePrice = (TextView) findViewById(R.id.myOrderDetail_txt_licensePrice);
		txt_licensePrice.setText(licensePrice);

		txt_ortherCost = (TextView) findViewById(R.id.myOrderDetail_txt_ortherCost);
		txt_ortherCost.setText(prize);

		txt_telNum = (TextView) findViewById(R.id.myOrderDetail_txt_telNum);

		txt_payMode = (TextView) findViewById(R.id.myOrderDetail_txt_payMode);

		txt_seriesName = (TextView) findViewById(R.id.myOrderDetail_txt_seriesName);
		txt_seriesName.setText(carDetail);

		txt_buyTax = (TextView) findViewById(R.id.myOrderDetail_txt_buyTax);
		txt_buyTax.setText(purchaseTax);

		txt_addprice = (TextView) findViewById(R.id.myOrderDetail_txt_price);
		txt_addprice.setText(dingPrice + " 元");

		txt_Name = (TextView) findViewById(R.id.myOrderDetail_txt_Name);

		TextView txt_fanli = (TextView) findViewById(R.id.myOrderDetail_txt_fanli);
		txt_fanli.setText(SharedPreManager.getInstance().getString(
				CommonData.CHEHUIFANLI, "300"));

		setPopWidthDrawCar();

	}

	/**
	 * 初始化数据
	 */
	@SuppressLint("ResourceAsColor")
	private void initData() {
		String dateStr1 = "现车";
		String dateStr2 = "一周内";
		String dateStr3 = "一个月内";
		drawCarDate.add(dateStr1);
		drawCarDate.add(dateStr2);
		drawCarDate.add(dateStr3);

		Intent intent = this.getIntent();
		createDateCN = intent.getStringExtra("CreateDateCN");

		sellerID = intent.getStringExtra("SellerID");
		tel = intent.getStringExtra("Tel");
		carDetail = intent.getStringExtra("CarDetail");
		carImage = intent.getStringExtra("CarImage");
		guideprice = intent.getStringExtra("Guideprice");
		carColor = intent.getStringExtra("CarColor");
		carColor = carColor.equals("null") ? "待定" : carColor;
		payMode = intent.getStringExtra("PayMode");
		payMode = payMode.equals("null") ? "待定" : payMode;
		carPlan = intent.getStringExtra("CarPlan");
		carAddress = intent.getStringExtra("CarAddress");
		carAddress = carAddress.equals("null") ? "待定" : carAddress;
		// 裸车
		floorPriceCN = intent.getStringExtra("FloorPriceCN");
		// 车险
		insurancePrice = intent.getStringExtra("InsurancePrice");
		// 购车费用
		licensePrice = intent.getStringExtra("LicensePrice");
		// 税
		purchaseTax = intent.getStringExtra("PurchaseTax");
		purchaseTax = purchaseTax.equals("null") ? "待定" : purchaseTax;
		// 其他费用
		prize = intent.getStringExtra("Prize");
		prize = prize.equals("null") ? "0" : prize;
		// 额外赠送
		carGift = intent.getStringExtra("CarGift");
		carGift = carGift.equals("null") ? "待定" : carGift;
		dingPrice = SharedPreManager.getInstance().getString(
				CommonData.CHEHUIJIAJIA, "500");
		cityname = intent.getStringExtra("Cityname");
		quoteID = intent.getStringExtra("QuoteID");
		orderID = intent.getStringExtra("OrderID");
		state = intent.getStringExtra("State");
		getOrderByID(orderID + "|" + sellerID);

		OrderState = intent.getStringExtra("OrderState");
		if (OrderState.equals("1")) {
			btn_modify.setBackgroundColor(Color.parseColor("#E8E5E5"));
			btn_modify.setClickable(false);
		}
	}

	/**
	 * 获取当前汽车比价信息
	 * 
	 * @param conParams
	 *            orderID与sellerId
	 */
	private void getOrderByID(final String conParams) {
		if (!Utils.isNetworkAvailable(MyOrderDetialActtivity.this)) {
			ToastUtils.showShortToast(MyOrderDetialActtivity.this,
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
						"SellerOperationService"));
				parameters.add(new BasicNameValuePair("methodname",
						"GetOrderByID"));
				parameters.add(new BasicNameValuePair("params", conParams));

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
					Log.d(TAG + "getOrderByID数据", json);
					analysisJson();
				} else {
					ToastUtils.showShortToast(MyOrderDetialActtivity.this,
							"网络加载失败!");
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
				if (jsonArray.length() != 0) {
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject item = jsonArray.getJSONObject(i);
						// 获取对象对应的值
						String carImage = item.optString("CarImage", "0");
						String Name = item.optString("Name", "111");
						String tel = item.optString("Tel", "0");

						// 替换号码*****
						String cutHeadTel = tel.substring(0, 3);
						String cutFootTel = tel.substring(8);
						String viewTel = cutHeadTel + "*****" + cutFootTel;

						String carDetail = item.optString("CarDetail", "0");
						String payMode = item.optString("PayMode", "0");
						String carColor = item.optString("CarColor", "0");
						String carPlan = item.optString("CarPlan", "0");
						String carAddress = item.optString("CarAddress", "0");
						String guideprice = item.optString("Guideprice", "0");

						// 返回下级界面需要参数
						String Ddbh = item.optString("Ddbh", "0");
						int userId = item.optInt("UserID", 0);
						int CarID = item.optInt("CarID", 0);
						String Cityname = item.optString("Cityname", "0");
						int OrderID = item.optInt("OrderID", 0);
						String CarPlan = item.optString("CarPlan", "0");

						// 将参数存进map
						map = new HashMap<String, String>();

						// 页面显示参数
						map.put("carImage", carImage);
						map.put("name", Name);
						map.put("tel", viewTel);
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

					}

					// 比价初始值getorderbyid
					setWidgetVaule();

				}

			} else {
				ToastUtils.showShortToast(MyOrderDetialActtivity.this, mess);
			}

		} catch (JSONException e) {

			e.printStackTrace();
		}

	}

	/**
	 * 设置初始值
	 */
	private void setWidgetVaule() {
		String guidePrice = map.get("guideprice");

		txt_Name.setText(map.get("name"));
		txt_telNum.setText(map.get("tel"));
		txt_guidePrice.setText("厂商指导价:￥" + guidePrice + "元");
		txt_carColor.setText(map.get("carColor"));
		txt_payMode.setText(map.get("payMode"));
		txt_carAddress.setText(map.get("carAddress"));
		txt_carPlan.setText(map.get("carPlan"));
	}

	/***
	 * 设置pop的宽度
	 */
	private void setPopWidthDrawCar() {
		@SuppressWarnings("deprecation")
		int width = getWindowManager().getDefaultDisplay().getWidth();
		@SuppressWarnings({ "deprecation" })
		int height = getWindowManager().getDefaultDisplay().getHeight();
		PoPBlandWindowManager.getInstance().init(getApplicationContext(),
				width, height / 3, R.layout.pop_bland);
	}

	/**
	 * 用户选中，更新文字
	 * 
	 * @param view
	 */
	private void setCarTimeText(final TextView view) {
		PoPBlandWindowManager.getInstance().setPickViewData(drawCarDate);
		PoPBlandWindowManager.getInstance().changeOnClick(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						view.setText(drawCarDate.get(1));
						PoPBlandWindowManager.getInstance().dismissPop();
					}
				});
		PoPBlandWindowManager.getInstance().changeOnSelect(
				new onSelectListener() {

					@Override
					public void onSelect(final String text) {
						PoPBlandWindowManager.getInstance().changeOnClick(
								new OnClickListener() {

									@Override
									public void onClick(View v) {
										// 设置文字
										view.setText(text);
										PoPBlandWindowManager.getInstance()
												.dismissPop();
									}
								});
					}
				});
		setPopWidthDrawCar();
		PoPBlandWindowManager.getInstance().showPopAllLocation(txt_drawCarTime,
				Gravity.CENTER | Gravity.BOTTOM, 0, 0);

	};

	/***
	 * 设置修改报价pop
	 */
	private void setPopModifyWidth() {
		modifyPriceManager = new PoPModifyPriceManager();
		@SuppressWarnings("deprecation")
		int width = getWindowManager().getDefaultDisplay().getWidth();
		@SuppressWarnings("deprecation")
		int height = getWindowManager().getDefaultDisplay().getHeight();

		modifyPriceManager.init(MyOrderDetialActtivity.this, width, height,
				R.layout.acitivty_myorderdetial_updata_layout);

		initModifyWidgets();
		modifyPriceManager.showPopAllLocation(img_car, Gravity.CENTER, 0, 0);
		updataQuote();
		modifyPriceManager.changeOnClick(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				modifyPrice();
				modifyPriceManager.dismissPop();

			}
		});
	}

	/**
	 * 
	 * @author zzp
	 * 
	 *         <P>
	 *         点击修改实例化一次
	 *         <P>
	 * 
	 */
	private class PoPModifyPriceManager {
		private PopupWindow pop;
		private View popView;
		@SuppressWarnings("unused")
		List<String> data = new ArrayList<String>();
		private Context context;

		private PoPModifyPriceManager() {
		}

		/***
		 * popWindow初始化方法
		 * 
		 * @param context
		 * @param w
		 */
		@SuppressWarnings("deprecation")
		private void init(Context context, final int width, int height, int id) {
			this.context = context;
			// 创建PopupWindow对象
			pop = new PopupWindow(setPopView(context, id),
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, false);
			setPopWidth(context, width, height);
			// 需要设置一下此参数，点击外边可消失
			pop.setBackgroundDrawable(new BitmapDrawable());
			// 设置点击窗口外边窗口消失
			pop.setOutsideTouchable(true);
			// 设置此参数获得焦点，否则无法点击
			pop.setFocusable(true);

		}

		@SuppressWarnings("unused")
		private PopupWindow getPopView() {
			return pop;
		}

		private View getView() {
			return popView;
		}

		private View setPopView(Context context, int id) {
			if (popView == null) {
				LayoutInflater inflater = LayoutInflater.from(this.context);
				// 引入窗口配置文件
				popView = inflater.inflate(id, null);
				// popView.setAlpha(110);
				popView.findViewById(R.id.myorderDetail_close)
						.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View arg0) {
								dismissPop();
							}
						});
			}
			return popView;
		}

		@SuppressWarnings("unused")
		private void showPopAsDropDown(View view, int x, int y) {
			if (pop != null) {
				pop.showAsDropDown(view, x, y);
			} else {
				LogN.e(this, "popWindow is null!!!");
			}
		}

		private void showPopAllLocation(View parent, int gravity, int x, int y) {
			if (pop != null) {
				pop.showAtLocation(parent, gravity, x, y);
			} else {
				LogN.e(this, "popWindow is null!!!");
			}
		}

		private void dismissPop() {
			pop.dismiss();
		}

		private void setPopWidth(Context context, final int width,
				final int height) {
			if (popView == null) {
				LogN.e(this, "popView is null !!!");
				return;
			}
			popView.getViewTreeObserver().addOnGlobalLayoutListener(
					new OnGlobalLayoutListener() {

						@Override
						public void onGlobalLayout() {
							// 动态设置pop的宽度
							FrameLayout.LayoutParams linearParams = (FrameLayout.LayoutParams) popView
									.getLayoutParams();
							linearParams.width = width;
							linearParams.height = height;
						}
					});
		}

		private void changeOnClick(OnClickListener onClickListener) {

			popView.findViewById(R.id.myorderDetail_btn_submit)
					.setOnClickListener(onClickListener);
		}

	}

	/**
	 * 弹出dialog
	 * 
	 * @param message
	 */
	private void mAlertTipsDialog_price(String message) {

		if (builder == null) {
			builder = new android.app.AlertDialog.Builder(
					MyOrderDetialActtivity.this);
		}
		builder.setMessage(message).setTitle("提示")
				.setPositiveButton("取消", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						post_floorPrice.setText("");
					}
				})
				.setNegativeButton("确定", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

					}
				}).show();
	}
	
	private void sendBroadCastRecevier(){
		 Intent intent = new Intent();
       intent.setAction(CommonData.MESSAGE_RECEIVED_ACTION);
       this.sendBroadcast(intent);
	}
}
