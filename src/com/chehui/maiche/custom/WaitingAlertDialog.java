package com.chehui.maiche.custom;

import android.content.Context;
import android.view.Window;
import android.widget.TextView;

import com.chehui.maiche.R;

public class WaitingAlertDialog {
	private android.app.AlertDialog alertDialog;

	private TextView messageTextView;

	public WaitingAlertDialog(Context context) {
		alertDialog = new android.app.AlertDialog.Builder(context).create();
		alertDialog.show();

		alertDialog.setCancelable(true);
		alertDialog.setCanceledOnTouchOutside(false);

		Window window = alertDialog.getWindow();
		window.setContentView(R.layout.alertdialog_waiting);

		messageTextView = (TextView) window.findViewById(R.id.loading_text);
	}

	public WaitingAlertDialog(Context context, int msgText) {
		this(context);
//		setShowText(msgText);
	}

	public void setShowText(int resId) {
		messageTextView.setText(resId);
	}

	public void setShowText(String message) {
		messageTextView.setText(message);
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

}