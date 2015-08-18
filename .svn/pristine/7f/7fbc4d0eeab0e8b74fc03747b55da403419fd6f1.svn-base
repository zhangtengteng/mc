package com.chehui.maiche.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.chehui.maiche.R;

/**
 * 
 * @author zzp
 *         <P>
 *         自定义toast
 *         <P>
 * 
 */
public class ToastUtils {
	private static Toast mToast;

	private static TextView toastMsg;

	public static void showLongToast(Context mContext, String text) {
		android.widget.Toast.makeText(mContext, text,
				android.widget.Toast.LENGTH_LONG).show();
	}

	public static void showShortToast(Context mContext, String text) {
		android.widget.Toast.makeText(mContext, text,
				android.widget.Toast.LENGTH_SHORT).show();
	}

	public static void showLongToast(Context mContext, int resid) {
		android.widget.Toast.makeText(mContext, resid,
				android.widget.Toast.LENGTH_LONG).show();
	}

	public static void showShortToast(Context mContext, int resid) {
		android.widget.Toast.makeText(mContext, resid,
				android.widget.Toast.LENGTH_SHORT).show();
	}

	/**
	 * 公共提示处理函数
	 * 
	 * @param context
	 * @param resId
	 * @param duration
	 */
	public static void showToast(Context context, int resId, int duration) {
		if (null == context) {
			return;
		}

		createToast(context, duration);
		toastMsg.setText(resId);
		mToast.show();
	}

	/**
	 * 公共提示处理函数
	 * 
	 * @param context
	 * @param msg
	 * @param duration
	 */
	public static void showToast(Context context, String msg, int duration) {
		if (null == context || StringUtils.isEmpty(msg)) {
			return;
		}

		createToast(context, duration);
		toastMsg.setText(msg);
		mToast.show();
	}

	private static synchronized void createToast(Context context, int duration) {
		if (null == mToast) {
			View toastRoot = View.inflate(context, R.layout.toast, null);
			toastMsg = (TextView) toastRoot.findViewById(R.id.message);

			mToast = new Toast(context);
			mToast.setView(toastRoot);
		}

		mToast.setGravity(Gravity.CENTER, 0, 0);
		mToast.setDuration(duration);
	}
}
