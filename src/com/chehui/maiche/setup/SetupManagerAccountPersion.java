package com.chehui.maiche.setup;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chehui.maiche.ActivityManager;
import com.chehui.maiche.BaseActivity;
import com.chehui.maiche.R;
import com.chehui.maiche.SharedPreManager;
import com.chehui.maiche.comm.CommonData;
import com.chehui.maiche.utils.LogN;
/**
 * @author zzp
 *         <P>
 *         个人信息界面
 *         <P>
 */
public class SetupManagerAccountPersion extends BaseActivity implements OnClickListener {
	private TextView txt_username;
	private TextView txt_phone;
	private TextView txt_city;
	private TextView txt_brand;
	private String mSellerBrandName;
	private String mCityName;
	private String mUserTel;
	private String mUserName;
	private int mUserID;

	/** 默认的ID值 */
	private String mbrandId;
private MessageReceiver mMessageReceiver;private LinearLayout lyt_phone;
	private LinearLayout lyt_address;
	private LinearLayout lyt_brand;
	private Button modify_btn_next;	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup_persion);
		initTitleView(-1, 255, R.string.set_account_persion, 255, -1, 0);
		initWidgets();
initListener();registerMessageReceiver();	}

	@Override
	protected void onStart() {
		super.onStart();
		initdata();
	}

	/**
	 * 初始化数据
	 */
	private void initdata() {
		// 界面显示品牌值与提交值不一致
		mSellerBrandName = SharedPreManager.getInstance().getString(CommonData.SELLBRANDNAME1, "奥迪");

		mbrandId = SharedPreManager.getInstance().getString(CommonData.BlandId1, "1");

		// 界面显示城市值与提交拼音值不一致
		mCityName = SharedPreManager.getInstance().getString(CommonData.USER_CITY_VIEW, "中国");

		mUserTel = SharedPreManager.getInstance().getString(CommonData.USER_PHONE, "");

		mUserName = SharedPreManager.getInstance().getString(CommonData.USER_NAME, "");

		mUserID = SharedPreManager.getInstance().getInt(CommonData.USER_ID, 72);

		// 绑定数据
		txt_username.setText(mUserName);
		txt_phone.setText(mUserTel);
		txt_city.setText(mCityName);
		txt_brand.setText(mSellerBrandName);
	}
	/**
	 * JPush receiver
	 */
	public void registerMessageReceiver() {
		mMessageReceiver = new MessageReceiver();
		IntentFilter filter = new IntentFilter();
		filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
		filter.addAction(CommonData.MESSAGE_RECEIVED_ACTION);
		registerReceiver(mMessageReceiver, filter);
	}
	/**
	 * 初始化监听
	 */
	private void initListener() {
		lyt_phone.setOnClickListener(this);
		modify_btn_next.setOnClickListener(this);
		lyt_address.setOnClickListener(this);
		lyt_brand.setOnClickListener(this);
	}

	/***
	 * 初始化组件
	 */
	private void initWidgets() {

		txt_username = (TextView) findViewById(R.id.setUp_modify_txt_username);

		txt_phone = (TextView) findViewById(R.id.setUp_modify_txt_phone);

		txt_city = (TextView) findViewById(R.id.setUp_modify_txt_city);

		txt_brand = (TextView) findViewById(R.id.setUp_modify_txt_bland);

		lyt_phone = (LinearLayout) findViewById(R.id.setUp_modify_lyt_phone);
		lyt_address = (LinearLayout) findViewById(R.id.setUp_modify_lyt_address);
		lyt_brand = (LinearLayout) findViewById(R.id.setUp_modify_lyt_brand);
		modify_btn_next = (Button) findViewById(R.id.setUp_modify_btn_next);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		// modify phoneNum
		case R.id.setUp_modify_lyt_phone:
			ActivityManager.getInstance().startNextActivity(SetupModifyPhoneVerification.class);
			break;

		// modify brand
		case R.id.setUp_modify_lyt_brand:
			ActivityManager.getInstance().startNextActivity(SetupModifyInfomation.class);
			break;

		// modify cityName
		case R.id.setUp_modify_lyt_address:
			ActivityManager.getInstance().startNextActivity(SetupModifyInfomation.class);
			break;

		// modify Info
		case R.id.setUp_modify_btn_next:

			break;

		default:
			break;

		}

	}
	public class MessageReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (CommonData.MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
				LogN.e(this, "SetupManagerAccountPersion is on receive!!!");
				initdata();
				initWidgets();
			}
		}
	}
}
