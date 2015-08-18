package com.chehui.maiche.myorder;

import android.app.Activity;
import android.os.Bundle;

import com.chehui.maiche.R;

/****
 * tabhost 已支付订单页面
 */
public class MyOrderWaitAcceptActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tabhost_consume);
		init();

	}

	private void init() {
		// lstv = (AutoListView) findViewById(R.id.lv_consume);
		// adapter = new OrderPayAdapter(list, this);
		// lstv.setAdapter(adapter);
		// lstv.setOnRefreshListener(this);
		// lstv.setOnLoadListener(this);
		// getData(CommonData.TIME_ALL, "1", CommonData.pageSize + "",
		// AutoListView.REFRESH);
	}

	// @Override
	// public void onLoad() {
	// getData(CommonData.TIME_ALL, (CommonData.currentPage + 1) + "",
	// CommonData.pageSize + "", AutoListView.LOAD);
	// }

	// @Override
	// public void onRefresh() {
	// getData(CommonData.TIME_ALL, "1", CommonData.pageSize + "",
	// AutoListView.REFRESH);
	// }

	/***
	 * 
	 * @param time
	 *            0.全部、1.一个月之内2.一个星期之内
	 * @param nextpage
	 * @param pageCount
	 * @param type
	 *            刷新 | 加载
	 */

	// ExtractorThread.getInstance().getMainHandler().post(new Runnable() {
	//
	// @Override
	// public void run() {
	// OrderAccountRequest orderAccountRequest = new OrderAccountRequest(
	// handler);
	//
	// SharedPreferences sharedPreferences = MyOrderWaitAcceptActivity.this
	// .getSharedPreferences("chehui", Activity.MODE_PRIVATE);
	//
	// String name = sharedPreferences.getString("username", "");
	// orderAccountRequest.setParams(name, CommonData.COUSUME, time,
	// nextpage, pageCount, type);
	// orderAccountRequest.sendRequest();
	// // showWaitDialog(R.string.common_querying);
	// }
	// });

}
