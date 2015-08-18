package com.chehui.maiche.setup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.chehui.maiche.BaseActivity;
import com.chehui.maiche.R;
import com.chehui.maiche.SharedPreManager;
import com.chehui.maiche.comm.CommonData;
import com.chehui.maiche.custom.PickerView.onSelectListener;
import com.chehui.maiche.httpserve.HttpService;
import com.chehui.maiche.pop.PoPBlandWindowManager;
import com.chehui.maiche.pop.PoPModifyPersonBrandManager;
import com.chehui.maiche.utils.ToastUtils;
import com.chehui.maiche.utils.Utils;

/**
 * @author zzp
 *         <P>
 *         个人信息界面
 *         <P>
 */
public class SetupManagerAccountPersion extends BaseActivity implements
		OnClickListener {
	private static final String TAG = "SetupManagerAccountPersion";
	private TextView txt_username;
	private TextView txt_phone;
	private TextView txt_city;
	private TextView txt_brand;
	private TextView modifyLocal;
	private TextView modifiyBrand;

	/** 获取應提交的品牌ID */
	public String postBrandID;

	/** 提交的城市值 */
	private String postCityName;

	/** 品牌信息返回值 */
	private String json;

	/** 品牌集合 */
	public List<Map<String, String>> brandList = new ArrayList<Map<String, String>>();

	public Map<String, String> brandMap;

	/** 品牌名称 */
	public List<String> choiceBrandName = new ArrayList<String>();

	/** 城市信息 */
	public List<String> cityList = new ArrayList<String>();

	/** 品牌ID */
	public Map<String, String> choiceBrandID = new HashMap<String, String>();
	private String mSellerBrandName;
	private String mCityName;
	private String mUserTel;
	private String mUserName;
	private int mUserID;
	private String mEmail;
	private String mHeadImg;
	private String mBankCardNum;

	/** 上传参数值 */
	private String conParams;

	/** 默认的ID值 */
	private String mbrandId;

	private String txt_brand_modifyed = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup_persion);
		initTitleView(-1, 255, R.string.set_account_persion, 255, -1, 0);
		getALLBrandName();
		initWidgets();

	}

	@Override
	protected void onStart() {
		super.onStart();
		initdata();
		txt_username.setText(mUserName);
		txt_city.setText(mCityName);
		txt_phone.setText(mUserTel);
		txt_brand.setText(mSellerBrandName);

	}

	/**
	 * 初始化数据
	 */
	private void initdata() {
		// 界面显示品牌值与提交值不一致
		mSellerBrandName = SharedPreManager.getInstance().getString(
				CommonData.SELLBRANDNAME1, "奥迪");

		mbrandId = SharedPreManager.getInstance().getString(
				CommonData.BlandId1, "1");

		// 界面显示城市值与提交拼音值不一致
		mCityName = SharedPreManager.getInstance().getString(
				CommonData.USER_CITY_VIEW, "中国");

		mUserTel = SharedPreManager.getInstance().getString(
				CommonData.USER_PHONE, "");

		mUserName = SharedPreManager.getInstance().getString(
				CommonData.USER_NAME, "");

		mUserID = SharedPreManager.getInstance().getInt(CommonData.USER_ID, 72);

		mEmail = SharedPreManager.getInstance().getString(
				CommonData.USER_EMAIL, mUserID + "@163.com");

		mHeadImg = null;

		mBankCardNum = null;

		System.out.println(mbrandId
				+ SharedPreManager.getInstance().getString(
						CommonData.USER_CITY, "72"));
	}

	private void initWidgets() {

		txt_username = (TextView) findViewById(R.id.setUp_modify_txt_username);

		txt_phone = (TextView) findViewById(R.id.setUp_modify_txt_phone);

		txt_city = (TextView) findViewById(R.id.setUp_modify_txt_city);

		txt_brand = (TextView) findViewById(R.id.setUp_modify_txt_bland);

		modifiyBrand = (TextView) findViewById(R.id.setUp_modify_event_brand);
		modifiyBrand.setOnClickListener(this);

		modifyLocal = (TextView) findViewById(R.id.setUp_modify_event_location);
		modifyLocal.setOnClickListener(this);

		setPopWidth();

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		// modify brand
		case R.id.setUp_modify_event_brand:
			setPopModifyBrand();
			break;

		// modify cityName
		case R.id.setUp_modify_event_location:
			setCityText(txt_city);
			break;

		default:
			break;

		}

	}

	@Override
	protected void onPause() {
		super.onPause();
		if (CommonData.MODIFYUSERFLAG == true) {
			if (postBrandID == null) {
				postBrandID = mbrandId;
			}
			postCityName = txt_city.getText().toString().trim();

			conParams = String.valueOf(mUserID) + "|" + mUserTel + "|" + mEmail
					+ "|" + converterToSpell(postCityName) + "|" + mHeadImg
					+ "|" + mBankCardNum + "|" + postBrandID + "|"
					+ postBrandID + "|" + postBrandID;

			// 将修改完成的品牌ID储存
			SharedPreManager.getInstance().setString(CommonData.BlandId1,
					postBrandID);

			// 将修改完成的城市名转化为拼音后储存
			SharedPreManager.getInstance().setString(CommonData.USER_CITY,
					converterToSpell(postCityName));

			// 修改界面显示城市值
			SharedPreManager.getInstance().setString(CommonData.USER_CITY_VIEW,
					postCityName);

			// 修改界面显示品牌值
			SharedPreManager.getInstance().setString(CommonData.SELLBRANDNAME1,
					txt_brand.getText().toString().trim());

			// 修改品牌
			sellerModify(conParams);

			// 修改完成初始化标量值
			CommonData.MODIFYUSERFLAG = false;

		}

	}

	/**
	 * 修改的数据上传服务器
	 * 
	 * @param conParams
	 *            接口中的params,城市&品牌不能为空
	 */
	private void sellerModify(final String conParams) {
		if (!Utils.isNetworkAvailable(SetupManagerAccountPersion.this)) {
			ToastUtils.showShortToast(SetupManagerAccountPersion.this,
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
						"SellerModify"));
				parameters.add(new BasicNameValuePair("params", conParams));

				String response = HttpService.methodPost(CommonData.HTTP_URL,
						parameters);
				System.out.println(parameters);
				return response;
			}

			@Override
			protected void onPostExecute(String result) {
				dismissWaitDialog();
				if (result != null) {
					System.out.println(result);
				} else {
					ToastUtils.showShortToast(SetupManagerAccountPersion.this,
							"服务器无响应!");
				}
			}
		}.execute();
	}

	/***
	 * 获取品牌信息
	 */
	private void getALLBrandName() {
		if (!Utils.isNetworkAvailable(SetupManagerAccountPersion.this)) {
			ToastUtils.showShortToast(SetupManagerAccountPersion.this,
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
					Log.d(TAG + "返回品牌信息", json);

					analysisJson();

				} else {
					ToastUtils.showShortToast(SetupManagerAccountPersion.this,
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

		} catch (JSONException e) {
			e.printStackTrace();
		}

		for (int i = 0; i < brandList.size(); i++) {

			choiceBrandName.add(brandList.get(i).get("BrandName"));

			choiceBrandID.put(brandList.get(i).get("BrandName"),
					brandList.get(i).get("ID"));

		}

	}

	/**
	 * 用户选中品牌，更新品牌栏文字
	 * 
	 * @param view
	 *            品牌
	 */
	private void setBrandText(final TextView view) {

		// 获取所有的品牌
		getALLBrandName();
		if (choiceBrandName.size() != 0) {
			PoPBlandWindowManager.getInstance()
					.setPickViewData(choiceBrandName);
			PoPBlandWindowManager.getInstance().changeOnClick(
					new OnClickListener() {

						@Override
						public void onClick(View v) {
							view.setText(choiceBrandName.get(1));
							txt_brand_modifyed = choiceBrandName.get(1);
							PoPBlandWindowManager.getInstance().dismissPop();
							postBrandID = choiceBrandID.get(choiceBrandName
									.get(0));
							// 更改任何一个值将会更新当前的用户信息
							CommonData.MODIFYUSERFLAG = true;
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
											txt_brand_modifyed = text;
											PoPBlandWindowManager.getInstance()
													.dismissPop();
										}
									});

							postBrandID = choiceBrandID.get(text.toString());
							// 更改任何一个值将会更新当前的用户信息
							CommonData.MODIFYUSERFLAG = true;

						}
					});
			setPopWidth();
			PoPBlandWindowManager.getInstance().showPopAllLocation(txt_brand,
					Gravity.CENTER | Gravity.BOTTOM, 0, 0);

		} else {
			ToastUtils.showShortToast(getApplicationContext(), "获取品牌信息失败！");
		}

	}

	/**
	 * 城市值
	 * 
	 * @param view
	 *            城市
	 */
	private void setCityText(final TextView view) {
		String dateStr1 = "南京";
		String dateStr2 = "天津";
		String dateStr3 = "上海";
		cityList.clear();
		cityList.add(dateStr1);
		cityList.add(dateStr2);
		cityList.add(dateStr3);
		PoPBlandWindowManager.getInstance().setPickViewData(cityList);
		PoPBlandWindowManager.getInstance().changeOnClick(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						view.setText(cityList.get(1));
						PoPBlandWindowManager.getInstance().dismissPop();
						// 更改任何一个值将会更新当前的用户信息
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
										PoPBlandWindowManager.getInstance()
												.dismissPop();

									}
								});
						// 更改任何一个值将会更新当前的用户信息
						CommonData.MODIFYUSERFLAG = true;
					}
				});
		setPopWidth();
		PoPBlandWindowManager.getInstance().showPopAllLocation(view,
				Gravity.CENTER | Gravity.BOTTOM, 0, 0);

	}

	/***
	 * 设置底部滑动pop
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
	 * 设置弹出选择品牌的dialog
	 */
	private void setPopModifyBrand() {
		@SuppressWarnings("deprecation")
		int width = getWindowManager().getDefaultDisplay().getWidth();
		@SuppressWarnings({ "deprecation" })
		int height = getWindowManager().getDefaultDisplay().getHeight();
		PoPModifyPersonBrandManager.getInstance().init(getApplicationContext(),
				width, height, R.layout.dialog_modify_brand);
		PoPModifyPersonBrandManager.getInstance().showPopAllLocation(txt_brand,
				Gravity.CENTER, 0, 260);
		PoPModifyPersonBrandManager.getInstance().changeOnClick(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						setBrandText((TextView) v);
					}
				});
		PoPModifyPersonBrandManager.getInstance().sureOnClick(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						txt_brand.setText(txt_brand_modifyed);
						// 修改界面显示品牌值
						SharedPreManager.getInstance().setString(
								CommonData.SELLBRANDNAME1,
								txt_brand.getText().toString().trim());
						PoPModifyPersonBrandManager.getInstance().dismissPop();
					}
				});
	}

	/**
	 * 汉字转换位汉语拼音，英文字符不变
	 * 
	 * @param chines
	 *            汉字
	 * @return 拼音
	 */
	public static String converterToSpell(String chines) {
		String pinyinName = "";
		char[] nameChar = chines.toCharArray();
		HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
		defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		for (int i = 0; i < nameChar.length; i++) {
			if (nameChar[i] > 128) {
				try {
					pinyinName += PinyinHelper.toHanyuPinyinStringArray(
							nameChar[i], defaultFormat)[0];
				} catch (BadHanyuPinyinOutputFormatCombination e) {
					e.printStackTrace();
				}
			} else {
				pinyinName += nameChar[i];
			}
		}
		return pinyinName;
	}

}
