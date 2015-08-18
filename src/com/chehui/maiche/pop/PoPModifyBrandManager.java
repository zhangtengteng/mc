package com.chehui.maiche.pop;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.chehui.maiche.R;
import com.chehui.maiche.utils.LogN;

/**
 * 
 * @author zzp
 * 
 */
public class PoPModifyBrandManager {
	private PopupWindow pop;
	private View popView;
	private static volatile PoPModifyBrandManager instance;
	private TextView tvOne, tvTwo;

	private PoPModifyBrandManager() {
		
	}

	/**
	 * 创建单例类，提供静态方法调用
	 * 
	 * @return ActivityManager
	 */
	public static PoPModifyBrandManager getInstance() {
		if (instance == null) {
			instance = new PoPModifyBrandManager();
		}
		return instance;
	}

	/***
	 * popWindow初始化方法
	 * 
	 * @param context
	 * @param w
	 */
	public void init(Context context, final int width) {
		setPopWidth(context, width);
		// 创建PopupWindow对象
		pop = new PopupWindow(getPopView(context), LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT, false);
		// 需要设置一下此参数，点击外边可消失
		pop.setBackgroundDrawable(new BitmapDrawable());
		// 设置点击窗口外边窗口消失
		pop.setOutsideTouchable(true);
		// 设置此参数获得焦点，否则无法点击
		pop.setFocusable(true);
	}

	public View getPopView(Context context) {
		if (popView == null) {
			LayoutInflater inflater = LayoutInflater.from(context);
			// 引入窗口配置文件
			popView = inflater.inflate(R.layout.alertdialog_modify_brand, null);
		}
		return popView;
	}

	public void showPop(View view, int x, int y) {
		if (pop != null) {
			pop.showAsDropDown(view, x, y);
		}
	}

	public void showPopAsDropDown(View view, int x, int y) {
		if (pop != null) {
			pop.showAsDropDown(view, x, y);
		} else {
			LogN.e(this, "popWindow is null!!!");
		}
	}

	public void showPopAllLocation(View parent, int gravity, int x, int y) {
		if (pop != null) {
			pop.showAtLocation(parent, gravity, x, y);
		} else {
			LogN.e(this, "popWindow is null!!!");
		}
	}

	public void disMissPop() {
		if (pop != null) {
			pop.dismiss();
		}
	}

	public void dismissPop() {
		pop.dismiss();
	}

	public void setPopWidth(Context context, final int width) {
		if (popView == null) {
			popView = getPopView(context);
		}
		popView.getViewTreeObserver().addOnGlobalLayoutListener(
				new OnGlobalLayoutListener() {

					@Override
					public void onGlobalLayout() {
						// 动态设置pop的宽度
						FrameLayout.LayoutParams linearParams = (FrameLayout.LayoutParams) popView
								.getLayoutParams();
						linearParams.width = width;
					}
				});
	}

	public void changeOnClick(OnClickListener onClickListener) {
		tvOne = (TextView) popView.findViewById(R.id.tv_one);
		tvOne.setTag(1);
		tvTwo = (TextView) popView.findViewById(R.id.tv_two);
		tvTwo.setTag(2);
		tvOne.setOnClickListener(onClickListener);
		tvTwo.setOnClickListener(onClickListener);
	}

	public TextView getPopTextOne() {
		if (tvOne == null) {
			tvOne = (TextView) popView.findViewById(R.id.tv_one);
			tvOne.setTag(1);
		}
		return tvOne;
	}

	public TextView getPopTextTwo() {
		if (tvTwo == null) {
			tvTwo = (TextView) popView.findViewById(R.id.tv_two);
			tvTwo.setText(2);
		}
		return tvTwo;
	}

}
