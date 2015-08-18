package com.chehui.maiche.custom;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;

public class DialogUtil {
	public static final int NO_CANCEL_BUTTON = -1;

	/**
	 * 对话框构建类
	 * 
	 * @param context
	 *            产生dialog的activity
	 * @param title
	 *            提示信息头
	 * @param msg
	 *            提示消息体字符串
	 * @param posRes
	 *            确认按钮资源文件ID
	 * @param negRes
	 *            取消按钮资源文件ID，如果为NO_CANCEL_BUTTON，则不创建取消按钮
	 * @param cancelable
	 *            是否可取消
	 * @param cancelOnOutTouch
	 *            是否可点击对话框外部取消
	 * @param callback
	 *            按钮按下回调函数
	 * @return
	 */
	public static AlertDialog activityDialog(Context context, String msg,
			int posRes, int negRes, boolean cancelable,
			boolean cancelOnOutTouch, final DialogCallback callback) {
		if (null == context) {
			return null;
		}

		final AlertDialog uiDialog = new AlertDialog(context);
		uiDialog.setCancelable(cancelable);
		uiDialog.setMessage(msg);
		uiDialog.setCanceledOnTouchOutside(cancelOnOutTouch);
		uiDialog.setPositiveButton(posRes, new OnClickListener() {
			@Override
			public void onClick(View v) {
				uiDialog.dismiss();
				if (null != callback) {
					callback.onPositiveClicked(uiDialog);
				}
			}
		});

		if (NO_CANCEL_BUTTON != negRes) {
			uiDialog.setNegativeButton(negRes, new OnClickListener() {

				@Override
				public void onClick(View v) {
					uiDialog.dismiss();
					if (null != callback) {
						callback.onNegativeClicked(uiDialog);
					}
				}
			});
		}

		return uiDialog;
	}

	public interface DialogCallback {
		void onPositiveClicked(AlertDialog dialog);

		void onNegativeClicked(AlertDialog dialog);
	}
}
