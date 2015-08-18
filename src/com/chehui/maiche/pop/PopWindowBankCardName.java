package com.chehui.maiche.pop;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.FrameLayout;
import android.widget.PopupWindow;

import com.chehui.maiche.utils.LogN;

public class PopWindowBankCardName {

	private PopupWindow pop;
	private View popView;
	private static volatile PopWindowBankCardName instance;

	List<String> data = new ArrayList<String>();

	private PopWindowBankCardName() {
	}

	/**
	 * 创建单例类，提供静态方法调用
	 * 
	 * @return ActivityManager
	 */
	public static PopWindowBankCardName getInstance() {
		if (instance == null) {
			instance = new PopWindowBankCardName();
		}
		return instance;
	}

	/***
	 * popWindow初始化方法
	 * 
	 * @param context
	 * @param w
	 */
	public void init(Context context, final int width, int height, int id) {
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

	public void setPopWidth(Context context, final int width, final int height) {
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

	public View setPopView(Context context, int id) {
		if (popView == null) {
			LayoutInflater inflater = LayoutInflater.from(context);
			// 引入窗口配置文件
			popView = inflater.inflate(id, null);

			popView.getBackground().setAlpha(110);
		}
		return popView;
	}

	public PopupWindow getPopView() {
		return pop;
	}

}
