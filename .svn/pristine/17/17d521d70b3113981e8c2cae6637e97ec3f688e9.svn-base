package com.chehui.maiche.myorder;

import java.util.ArrayList;

import android.app.LocalActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.chehui.maiche.R;
import com.chehui.maiche.comm.CommonData;
import com.chehui.maiche.custom.BaseFragment;
import com.chehui.maiche.utils.LogN;

/**
 * 
 * @author zzp
 *         <P>
 *         我的订单 :问题1，prize为空
 *         <P>
 * 
 */
public class MyOrderFragment extends BaseFragment implements OnClickListener {

	private View mInflater;

	private int currentIndex;

	private Button btn_all;

	private Button btn_waitAccept;

	private Button btn_hadAccept;

	private Button btn_hadTransac;

	private Button currentSelectedView;
	// for receive customer msg from jpush server
	private MessageReceiver mMessageReceiver;

	// viewPager
	ViewPager pager;
	@SuppressWarnings("deprecation")
	LocalActivityManager manager;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mInflater = inflater.inflate(R.layout.fragment_my_order, container,
				false);
		return mInflater;
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		registerMessageReceiver();// used for receive msg
		manager = new LocalActivityManager(getActivity(), true);
		manager.dispatchCreate(savedInstanceState);
		initWidget();
		initPagerViewer();
	}

	/**
	 * 初始化PageViewer
	 */
	private void initPagerViewer() {
		pager = (ViewPager) getActivity().findViewById(R.id.view_pager);
		final ArrayList<View> list = new ArrayList<View>();
		// 全部报价
		Intent intent1 = new Intent(getActivity(), MyOrderListAll.class);
		list.add(getView("0", intent1));
		// 待接收
		Intent intent2 = new Intent(getActivity(), MyOrderListWait.class);
		list.add(getView("1", intent2));
		// 已经接受
		Intent intent3 = new Intent(getActivity(), MyOrderListAccept.class);
		list.add(getView("2", intent3));
		// 已经成交
		Intent intent4 = new Intent(getActivity(), MyOrderListOk.class);
		list.add(getView("3", intent4));

		pager.setAdapter(new MyPagerAdapter(list));
		pager.setCurrentItem(0);
		pager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				System.out.println("onPageSelected=" + position);
				if (position == 0) {
					currentIndex = 0;
					setIndexSelected();
					btn_all.setSelected(true);
					currentSelectedView = btn_all;
				}else if(position == 1){
					currentIndex = 1;
					setIndexSelected();
					btn_waitAccept.setSelected(true);
					currentSelectedView = btn_waitAccept;
				}else if(position == 2){
					currentIndex = 2;
					setIndexSelected();
					btn_hadAccept.setSelected(true);
					currentSelectedView = btn_hadAccept;
				}else if(position == 3){
					currentIndex = 3;
					setIndexSelected();
					btn_hadTransac.setSelected(true);
					currentSelectedView = btn_hadTransac;
				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});

	}

	/**
	 * 通过activity获取视图
	 * 
	 * @param id
	 * @param intent
	 * @return
	 */
	@SuppressWarnings("deprecation")
	private View getView(String id, Intent intent) {
		return manager.startActivity(id, intent).getDecorView();
	}

	@Override
	public void onStart() {
		super.onStart();

		if (currentSelectedView == null) {
			currentSelectedView = btn_all;
			btn_all.setSelected(true);
		}
	}

	private void initWidget() {
		btn_all = (Button) mInflater.findViewById(R.id.myOrder_btn_all);
		btn_waitAccept = (Button) mInflater
				.findViewById(R.id.myOrder_btn_waitAccept);
		btn_hadAccept = (Button) mInflater
				.findViewById(R.id.myOrder_btn_hadAccept);
		btn_hadTransac = (Button) mInflater
				.findViewById(R.id.myOrder_btn_hadTransac);

		btn_all.setOnClickListener(this);
		btn_hadAccept.setOnClickListener(this);
		btn_hadTransac.setOnClickListener(this);
		btn_waitAccept.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		// 全部报价
		case R.id.myOrder_btn_all:
			currentIndex = 0;
			setIndexSelected();
			btn_all.setSelected(true);
			currentSelectedView = btn_all;
			break;
		// 待接受
		case R.id.myOrder_btn_waitAccept:
			currentIndex = 1;
			setIndexSelected();
			btn_waitAccept.setSelected(true);
			currentSelectedView = btn_waitAccept;
			break;
		// 已接受
		case R.id.myOrder_btn_hadAccept:
			currentIndex = 2;
			setIndexSelected();
			btn_hadAccept.setSelected(true);
			currentSelectedView = btn_hadAccept;
			break;
		// 已交易
		case R.id.myOrder_btn_hadTransac:
			currentIndex = 3;
			setIndexSelected();
			btn_hadTransac.setSelected(true);
			currentSelectedView = btn_hadTransac;
			break;
		default:
			break;
		}
		pager.setCurrentItem(currentIndex);
	}

	private void setIndexSelected() {
		if (currentSelectedView != null) {
			currentSelectedView.setSelected(false);
		}
	}

	/**
	 * JPush receiver
	 */
	public void registerMessageReceiver() {
		mMessageReceiver = new MessageReceiver();
		IntentFilter filter = new IntentFilter();
		filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
		filter.addAction(CommonData.MESSAGE_RECEIVED_ACTION);
		getActivity().registerReceiver(mMessageReceiver, filter);
	}

	public class MessageReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			LogN.e(MyOrderFragment.this, "intent===================="+intent.getExtras());
			if (CommonData.MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
				LogN.e(MyOrderFragment.this, "MyOrderFragment is onReceive!!!!!");
				// 收到用户报价通知
			}
		}
	}
}
	