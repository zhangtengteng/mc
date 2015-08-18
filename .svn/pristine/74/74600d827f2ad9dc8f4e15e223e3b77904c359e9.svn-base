package com.chehui.maiche.custom;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import com.chehui.maiche.R;
import com.chehui.maiche.utils.DisplayUtils;

public class AlertDialogBase {
	protected Context context;

	protected android.app.AlertDialog alertDialog;

	protected LinearLayout buttonLayout;

	protected int layoutWidth = DisplayUtils.getScreenWidth() * 7 / 8;

	public AlertDialogBase(Context context) {
		this.context = context;
		alertDialog = new android.app.AlertDialog.Builder(context).create();
		alertDialog.show();
	}

	protected void initView(int layoutRes) {
		alertDialog.setContentView(layoutRes);

		buttonLayout = (LinearLayout) findViewById(R.id.buttonLayout);

		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				layoutWidth, LayoutParams.MATCH_PARENT);
		alertDialog.findViewById(R.id.dialog_content).setLayoutParams(params);
	}

	public View findViewById(int id) {
		return alertDialog.findViewById(id);
	}

	public boolean isShown() {
		return alertDialog.isShowing();
	}

	/**
	 * 显示对话框
	 */
	public void show() {
		alertDialog.show();
	}

	/**
	 * 关闭对话框
	 */
	public void dismiss() {
		alertDialog.dismiss();
	}

	public void setCancelable(boolean flag) {
		alertDialog.setCancelable(flag);
	}

	public void setCanceledOnTouchOutside(boolean flag) {
		alertDialog.setCanceledOnTouchOutside(flag);
	}

	/**
	 * 设置按钮
	 * 
	 * @param textRes
	 * @param listener
	 */
	public void setPositiveButton(int textRes,
			final View.OnClickListener listener) {
		addDialogButton(textRes, R.drawable.selector_dialog_btn_ok, listener);
	}

	/**
	 * 设置按钮
	 * 
	 * @param textRes
	 * @param listener
	 */
	public void setNegativeButton(int textRes,
			final View.OnClickListener listener) {
		addDialogButton(textRes, R.drawable.selector_dialog_btn_cancel,
				listener);
	}

	public void addDialogButton(int textRes, int btnBg,
			final View.OnClickListener listener) {
		Button button = new Button(context);
		button.setBackgroundResource(btnBg);
		button.setText(textRes);
		button.setTextSize(16);
		button.setTextAppearance(context, R.style.TextWhiteInButton);
		button.setOnClickListener(listener);

		LinearLayout.LayoutParams params = new LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

		int bCount = buttonLayout.getChildCount();
		if (bCount > 0) {
			params.setMargins(layoutWidth / 20, 0, 0, 0);
			button.setLayoutParams(params);
			buttonLayout.addView(button, bCount);
		} else {
			button.setLayoutParams(params);
			buttonLayout.addView(button);
		}
	}

}