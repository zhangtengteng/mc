package com.chehui.maiche.login;

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
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chehui.maiche.BaseActivity;
import com.chehui.maiche.R;
import com.chehui.maiche.comm.CommonData;
import com.chehui.maiche.custom.PickerView.onSelectListener;
import com.chehui.maiche.httpserve.HttpService;
import com.chehui.maiche.pop.PoPBlandWindowManager;
import com.chehui.maiche.utils.ToastUtils;
import com.chehui.maiche.utils.Utils;

/**
 * 选择品牌
 * 
 * @author gqy
 * 
 */
public class RegSelectBrandActivity extends BaseActivity implements
		OnClickListener {
	private static final String TAG = "RegSelectBrandActivity";

	private LinearLayout layout_select_brand;
	private Button btn_reg_next;
	private TextView txt_select_brand;
	/** 获取應提交的品牌ID */
	public String postID;
	/** 品牌名称 */
	public List<String> choiceBrandName = new ArrayList<String>();
	/** 品牌ID */
	public Map<String, String> choiceBrandID = new HashMap<String, String>();
	private String json;
	public Map<String, String> brandMap;
	public List<Map<String, String>> brandList = new ArrayList<Map<String, String>>();
	private String getparams = "";
	private String postParams = "";
	private String regJson = "";
	String lastloginIP = "192.168.1.1";

	private View view;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.activity_login_select_brand, null);
		setContentView(view);
		initTitleView(-1, 255, R.string.reg_select_brand, 255, -1, 0);
		initData();
		initView();

	}

	private void initData() {
		getparams = getIntent().getStringExtra("PARAMS");
		Log.d(TAG + "注册信息显示", getparams);
		getALLBrandName();
	}

	private void initView() {
		layout_select_brand = (LinearLayout) findViewById(R.id.reg_select_brand);
		btn_reg_next = (Button) findViewById(R.id.btn_reg_select_brand_next);
		txt_select_brand = (TextView) findViewById(R.id.txt_select_brand);

		layout_select_brand.setOnClickListener(this);
		btn_reg_next.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.reg_select_brand:
//			getALLBrandName();
			if (brandList.size() > 0) {
				for (int i = 0; i < brandList.size(); i++) {

					choiceBrandName.add(brandList.get(i).get("BrandName"));
					choiceBrandID.put(brandList.get(i).get("BrandName"), brandList
							.get(i).get("ID"));
				}
				setBrandText(txt_select_brand);
			}
			break;
		case R.id.btn_reg_select_brand_next:
			sellerRegister();
			break;
		default:
			break;
		}
	}

	/***
	 * 获取品牌信息
	 */
	private void getALLBrandName() {
		if (!Utils.isNetworkAvailable(RegSelectBrandActivity.this)) {
			ToastUtils.showShortToast(RegSelectBrandActivity.this,
					R.string.common_network_unavalible);
			return;
		}

		new AsyncTask<Void, Integer, String>() {
			@Override
			protected String doInBackground(Void... params) {
				// 请求服务器
				final List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
				parameters.add(new BasicNameValuePair("classname",
						"UserOperationService"));
				parameters.add(new BasicNameValuePair("methodname",
						"GetALLBrandName"));
				String response = HttpService.methodPost(CommonData.HTTP_URL,
						parameters);
				return response;
			}

			@Override
			protected void onPostExecute(String result) {
				super.onPostExecute(result);
				if (result != null) {
					json = result.toString();
					analysisJson();
				} else {
					ToastUtils.showShortToast(RegSelectBrandActivity.this,
							"网络无响应!");
				}
			}

		}.execute();
	}

	/**
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
				brandList.clear();
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject contentItem = jsonArray.getJSONObject(i);
					int ID = contentItem.getInt("ID");
					String BrandID = contentItem.getString("BrandID");
					String BrandName = contentItem.getString("BrandName");
					String State = contentItem.getString("State");
					String BrandABC = contentItem.getString("BrandABC");

					brandMap = new HashMap<String, String>();
					brandMap.put("ID", String.valueOf(ID));
					brandMap.put("BrandID", BrandID);
					brandMap.put("BrandName", BrandName);
					brandMap.put("State", State);
					brandMap.put("BrandABC", BrandABC);
					brandList.add(brandMap);
				}
			} else {
				ToastUtils.showShortToast(RegSelectBrandActivity.this, mess);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		System.out.println(brandList.toString());
		
	}

	/***
	 * 泡泡窗口选择brand
	 * <P>
	 * choiceBrandName & choiceBrandID
	 * <P>
	 * 
	 * 
	 */
	private void popupChoiceBrand() {
		if (brandList.size() > 0) {
			for (int i = 0; i < brandList.size(); i++) {
				choiceBrandName.add(brandList.get(i).get("BrandName"));
				choiceBrandID.put(brandList.get(i).get("BrandName"), brandList
						.get(i).get("ID"));
			}
		}
	}

	/**
	 * 用户选中品牌，更新品牌栏文字
	 * 
	 * @param view
	 *            品牌
	 */
	public void setBrandText(final TextView view) {
		if (choiceBrandName == null) {
			ToastUtils.showShortToast(getApplicationContext(), "获取品牌信息失败！");
			popupChoiceBrand();
		}
		PoPBlandWindowManager.getInstance().setPickViewData(choiceBrandName);
		// 不滑动时的选中状态
		PoPBlandWindowManager.getInstance().changeOnClick(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						view.setText(choiceBrandName.get(1));
						postID = choiceBrandID.get(choiceBrandName.get(1));
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
										view.setText(text);
										postID = choiceBrandID.get(text
												.toString());
										PoPBlandWindowManager.getInstance()
												.dismissPop();
									}
								});
					}
				});
		setPopWidth();
		PoPBlandWindowManager.getInstance().showPopAllLocation(view,
				Gravity.CENTER | Gravity.BOTTOM, 0, 0);
	}

	/***
	 * 设置pop的宽度
	 */
	private void setPopWidth() {
		@SuppressWarnings("deprecation")
		int width = getWindowManager().getDefaultDisplay().getWidth();
		@SuppressWarnings({ "deprecation" })
		int height = getWindowManager().getDefaultDisplay().getHeight();
		PoPBlandWindowManager.getInstance().init(getApplicationContext(),
				width, height / 3, R.layout.pop_bland);
	}

	/**
	 * 界面跳转
	 */
	@SuppressWarnings("static-access")
	private void startNextActivity() {
		activityManager.getInstance().startNextActivity(
				RegCompleteActivity.class);
	}

	/**
	 * 注册
	 */
	private void sellerRegister() {

		postParams = getparams + "|" + postID + "|" + postID + "|" + postID
				+ "|" + lastloginIP;

		new AsyncTask<Void, Integer, String>() {

			@Override
			protected String doInBackground(Void... params) {
				// 请求服务器
				final List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
				parameters.add(new BasicNameValuePair("classname",
						"BaseInfoService"));
				parameters.add(new BasicNameValuePair("methodname",
						"SellerRegister"));
				parameters.add(new BasicNameValuePair("params", postParams));

				String response = HttpService.methodPost(CommonData.HTTP_URL,
						parameters);
				return response;

			}

			@Override
			protected void onPostExecute(String result) {
				super.onPostExecute(result);
				if (result != null) {
					regJson = result.toString();
					try {
						JSONObject jsonObject = new JSONObject(regJson);
						Boolean success = jsonObject.getBoolean("Success");
						String mess = jsonObject.getString("Mess");
						if (success == true) {
							startNextActivity();
						} else {
							ToastUtils.showShortToast(
									RegSelectBrandActivity.this, mess);
						}

					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
		}.execute();
	}
}
