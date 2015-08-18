package com.chehui.maiche.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

/**
 * 
 * @author zzp
 *         <P>
 *         创建弹出对话框
 *         <P>
 */
public class AlertUtil {
	private static AlertDialog.Builder builder;

	public static void createDialog(Activity activity, String title,
			String msg, final AlertDialogCallback callback) {

		if (builder == null) {
			builder = new AlertDialog.Builder(activity);
		}

		builder.setTitle(title).setMessage(msg)
				.setNegativeButton("取消", new OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						if (callback != null) {
							callback.onCancelCallback();
						}
					}
				}).setPositiveButton("确定", new OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						if (callback != null) {
							callback.onConfirmCallback();
						}

					}
				});
		builder.create().show();
	}

	public interface AlertDialogCallback {

		public void onCancelCallback();

		public void onConfirmCallback();
	}
}
