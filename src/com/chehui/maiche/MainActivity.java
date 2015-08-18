package com.chehui.maiche;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

import com.chehui.maiche.comm.CommonData;
import com.chehui.maiche.httpserve.HttpService;
import com.chehui.maiche.login.LoginActivity;
import com.chehui.maiche.rabate.RabateListBankcardActivity;
import com.chehui.maiche.setup.SetupManagerAuthentication;
import com.chehui.maiche.update.UpdateManager;
import com.chehui.maiche.utils.LogN;
import com.chehui.maiche.utils.ToastUtils;

/**
 * 主页面
 * 
 * @author zzp
 * 
 */
public class MainActivity extends FragmentActivity implements OnClickListener {

	private int title;
	protected TextView topTitle;
	protected TextView left;
	protected TextView right;

	/** 品牌id */
	// private String sellbrand1;
	// private String sellbrand2;
	// private String sellbrand3;
	// /** 城市 */
	// private String cityname;
	// /** 登录ID */
	// private String sellerid;

	/** 定义一个变量，来标识是否退出 */
	private static boolean isExit = false;

	/** public static Fragment[] fragments 获取FragmentManager实例 */
	private static FragmentManager mFragmentManager;

	/** 当前显示页面 */
	public static String TAG = "order_check";

	// 用于查找回退栈中的fragment，findFragmentByTag
	public static final String ORDER_CHECK = "order_check";
	public static final String ORDER_COUNT = "order_count";
	public static final String MESSAGE = "message";
	public static final String SETUP = "set";

	Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			isExit = false;
		}
	};
	private TextView tvNumber;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		CommonData.activity = this;

		// 版本判断
		try {
			new UpdateManager(MainActivity.this).isupdate();
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		initData();// 初始化数据
		// 获取FragmentManager实例
		mFragmentManager = getSupportFragmentManager();
		FragmentsManager.getInstance().setFragmentManager(mFragmentManager);
		initFragment();
		findViewById(R.id.rbOrderCheck).setSelected(true);
		initTitleView(-1, 0, R.string.main_order, 255, -1, 0);
		RequestTimeOutManager.getInstance().init(this);
		initJPushTag(getApplicationContext());
		toAuthon();
		GetConst();
	}

	/***
	 * 获取常量值
	 */
	private void GetConst() {

		new AsyncTask<Void, Integer, String>() {

			@Override
			protected String doInBackground(Void... params) {

				// 请求服务器
				final List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
				parameters.add(new BasicNameValuePair("classname",
						"SellerOperationService"));
				parameters
						.add(new BasicNameValuePair("methodname", "GetConst"));

				String response = HttpService.methodPost(CommonData.HTTP_URL,
						parameters);
				return response;
			}

			@Override
			protected void onPostExecute(String result) {
				super.onPostExecute(result);
				if (result != null) {
					String json = result.toString();
					try {
						JSONObject jsonObject = new JSONObject(json);
						SharedPreManager.getInstance().setString(
								CommonData.CHEHUIJIAJIA,
								jsonObject.optString("chehuijiajia"));
						SharedPreManager.getInstance().setString(
								CommonData.CHEHUIFANLI,
								jsonObject.optString("chehuifanli"));
						SharedPreManager.getInstance().setString(
								CommonData.HUIFUBAOJIA,
								jsonObject.optString("huifubaojia"));

						Log.d("===========",
								jsonObject.optString("chehuijiajia")
										+ jsonObject.optString("chehuifanli")
										+ jsonObject.optString("huifubaojia"));
					} catch (JSONException e) {
						e.printStackTrace();
					}

				}

			}

		}.execute();

	}

	/***
	 * 注册成功直接认证
	 */
	private void toAuthon() {
		if (CommonData.toAuthon) {
			CommonData.toAuthon = false;
			startActivity(new Intent(MainActivity.this,
					SetupManagerAuthentication.class));
		}
	}

	private void initJPushTag(Context context) {
		String username = SharedPreManager.getInstance().getString(
				CommonData.USER_NAME, "");
		Set<String> set = new HashSet<String>();
		set.add(SharedPreManager.getInstance().getString(CommonData.USER_PHONE,
				""));
		JPushInterface.setAliasAndTags(context, username, set,
				new TagAliasCallback() {

					@Override
					public void gotResult(int result, String arg1,
							Set<String> arg2) {
						LogN.d(MainActivity.this,
								"JPushInterface.setTags---------------------result="
										+ result);
					}
				});

	}

	/**
	 * 初始化数据
	 */
	private void initData() {
		tvNumber = (TextView) findViewById(R.id.tv_number);
		CommonData.tvNumber = tvNumber;
	}

	/***
	 * 初始化头部标题栏
	 * 
	 * @param leftId
	 * @param leftAlpha
	 * @param topId
	 * @param topAlpha
	 * @param rightId
	 * @param rightAlpha
	 */
	protected void initTitleView(int leftId, int leftAlpha, int topId,
			int topAlpha, int rightId, int rightAlpha) {
		if (left == null) {
			left = (TextView) findViewById(R.id.tvLeft);
		}
		if (right == null) {

			right = (TextView) findViewById(R.id.tvRight);
		}
		if (topTitle == null) {

			topTitle = (TextView) findViewById(R.id.tvTop);
		}

		if (leftId != -1) {
			left.setText(leftId);
		} else {
			left.setText("");
		}
		left.getBackground().setAlpha(leftAlpha);

		if (topId != -1) {
			topTitle.setText(topId);
		} else {
			topTitle.setText("");
		}

		topTitle.getBackground().setAlpha(topAlpha);
		if (rightId != -1) {
			right.setText(rightId);
		} else {
			right.setText("");
		}
		right.getBackground().setAlpha(rightAlpha);
		right.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(MainActivity.this,
						RabateListBankcardActivity.class));
				CommonData.ISPOSSESSBANKCARD = false;
			}
		});
	}

	/**
	 * 初始化首个Fragment
	 */
	private void initFragment() {

		// 订单询价
		findViewById(R.id.rbOrderCheck).setOnClickListener(this);

		// 我的报价
		findViewById(R.id.rbOrderCount).setOnClickListener(this);

		// 我的返利
		findViewById(R.id.rbMessage).setOnClickListener(this);

		// 设置
		findViewById(R.id.rbSet).setOnClickListener(this);

		FragmentsManager.getInstance()
				.addFragment(
						getSupportFragmentManager().findFragmentById(
								R.id.fgOrderCheck), ORDER_CHECK);

		FragmentsManager.getInstance()
				.addFragment(
						getSupportFragmentManager().findFragmentById(
								R.id.fgOrderCount), ORDER_COUNT);

		FragmentsManager.getInstance().addFragment(
				getSupportFragmentManager().findFragmentById(R.id.fgMessage),
				MESSAGE);

		FragmentsManager.getInstance()
				.addFragment(
						getSupportFragmentManager()
								.findFragmentById(R.id.fgSet), SETUP);

		FragmentsManager.getInstance().changeFragment(ORDER_CHECK);
	}

	@Override
	protected void onResumeFragments() {
		super.onResumeFragments();
		left.setVisibility(View.INVISIBLE);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.rbOrderCheck:
			TAG = ORDER_CHECK;
			title = R.string.main_order;
			initTitleView(-1, 0, title, 255, -1, 0);
			break;
		case R.id.rbOrderCount:
			TAG = ORDER_COUNT;
			title = R.string.main_my_order;
			initTitleView(-1, 0, title, 255, -1, 0);
			break;
		case R.id.rbMessage:
			TAG = MESSAGE;
			title = R.string.main_rebate;
			initTitleView(-1, 0, title, 255,
					R.string.set_account_add_bank_top2, 0);

			break;
		case R.id.rbSet:
			TAG = SETUP;
			title = R.string.main_set;
			initTitleView(-1, 0, title, 255, -1, 0);
			FragmentsManager.getInstance().changeFragment(TAG);
			break;
		default:
			break;
		}

		FragmentsManager.getInstance().changeFragment(TAG);
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
			mHandler.sendEmptyMessageDelayed(0, 2000);
		} else {
			finish();
			System.exit(0);
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		JPushInterface.setAliasAndTags(getApplicationContext(), "",
				new HashSet<String>(), new TagAliasCallback() {

					@Override
					public void gotResult(int arg0, String arg1,
							Set<String> arg2) {
					}
				});

		if (CommonData.LOGINFLAG = true) {
			startActivity(new Intent(MainActivity.this, LoginActivity.class));
			System.exit(0);
		}

	}
}

