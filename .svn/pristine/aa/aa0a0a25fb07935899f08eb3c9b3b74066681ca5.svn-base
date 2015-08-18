package com.chehui.maiche.login;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.chehui.maiche.ActivityManager;
import com.chehui.maiche.BaseActivity;
import com.chehui.maiche.R;

/**
 * 
 * @author zzp 软件介绍导界面
 * 
 */
public class GuideActivity extends BaseActivity implements OnPageChangeListener {

	private View view01;
	private View view02;
	private View view03;
	/**
	 * 实例化viewpager
	 */
	private ViewPager mViewPager;

	private ArrayList<View> mVLists;

	/**
	 * 实例化ViewPager适配器
	 */
	private ViewPagerAdapter mPagerAdapter;

	/**
	 * 点击开始
	 */
	private ImageView mImg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_loading);

		initView();
		initData();
	}

	private void initView() {
		/**
		 * 实例化布局对象
		 */
		@SuppressWarnings("static-access")
		LayoutInflater mInflater = getLayoutInflater().from(this);
		view01 = mInflater.inflate(R.layout.loading_viewpager1, null);
		view02 = mInflater.inflate(R.layout.loading_viewpager2, null);
		view03 = mInflater.inflate(R.layout.loading_viewpager3, null);

		mViewPager = (ViewPager) findViewById(R.id.viewpage);

		mVLists = new ArrayList<View>();

		mPagerAdapter = new ViewPagerAdapter(mVLists);

		mImg = (ImageView) view03.findViewById(R.id.iv_star);

	}

	private void initData() {
		mViewPager.setOnPageChangeListener(this);

		mVLists.add(view01);
		mVLists.add(view02);
		mVLists.add(view03);

		mViewPager.setAdapter(mPagerAdapter);

		mImg.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startNextActivity();
			}
		});

	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageSelected(int arg0) {
		// TODO Auto-generated method stub

	}

	private void startNextActivity() {
		ActivityManager.getInstance().startNextActivity(LoginActivity.class);
		GuideActivity.this.finish();
	}

	/**
	 * 
	 * @author zzp
	 *         <P>
	 *         viewpager适配器
	 *         <P>
	 * 
	 */
	private class ViewPagerAdapter extends PagerAdapter {

		/**
		 * viewpager集合
		 */
		private ArrayList<View> viewList;

		public ViewPagerAdapter(ArrayList<View> viewList) {
			this.viewList = viewList;

		}

		/**
		 * 初始化位置
		 */
		@Override
		public Object instantiateItem(View view, int position) {
			((ViewPager) view).addView(viewList.get(position), 0);
			return viewList.get(position);
		}

		/**
		 * 获得页面数目
		 */
		@Override
		public int getCount() {
			if (viewList != null) {
				return viewList.size();
			}
			return 0;
		}

		/**
		 * 判断是否为对象生成
		 */
		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {

			return (arg0 == arg1);
		}

		/**
		 * 销毁
		 */
		@Override
		public void destroyItem(View container, int position, Object object) {
			((ViewPager) container).removeView(viewList.get(position));
		}

	}

}
