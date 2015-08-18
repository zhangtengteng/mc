package com.chehui.maiche.pop;


import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.chehui.maiche.R;
import com.chehui.maiche.SharedPreManager;
import com.chehui.maiche.comm.CommonData;
import com.chehui.maiche.utils.LogN;

public class PoPModifyPersonBrandManager {
	private PopupWindow pop;
	private View popView;
	private static volatile PoPModifyPersonBrandManager instance;
	public Context context;
	private TextView tvModify;
    private Button btnModify;

	private PoPModifyPersonBrandManager() {
	}

	/**
	 * 创建单例类，提供静态方法调用
	 * 
	 * @return ActivityManager
	 */
	public static PoPModifyPersonBrandManager getInstance() {
		if (instance == null) {
			instance = new PoPModifyPersonBrandManager();
		}
		return instance;
	}

	/***
	 * popWindow初始化方法
	 * 
	 * @param context
	 * @param w
	 */
	@SuppressWarnings("deprecation")
	public void init(Context context, final int width, int height, int id) {
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

	public PopupWindow getPopView() {
		return pop;
	}

	public View getView(){
		return popView;
	}
	public View setPopView(Context context, int id) {
		if (popView == null) {
			LayoutInflater inflater = LayoutInflater.from(this.context);
			// 引入窗口配置文件
			popView = inflater.inflate(id, null);
			popView.setAlpha(110);
			popView.findViewById(R.id.img_modify_brand_close).setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					dismissPop();
				}
			});
		}
		return popView;
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

	public void dismissPop() {
		pop.dismiss();
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
		
		tvModify = (TextView) popView.findViewById(R.id.tv_modify_brand);
		tvModify.setText(SharedPreManager.getInstance().getString(CommonData.SELLBRANDNAME1, "一汽大众"));
		btnModify=(Button)popView.findViewById(R.id.btn_modify_brand_sure);
	}

	public void changeOnClick(OnClickListener onClickListener) {
		tvModify.setOnClickListener(onClickListener);
	}
	
	public void sureOnClick(OnClickListener onClickListener) {
		btnModify.setOnClickListener(onClickListener);
	}
}
