package com.chehui.maiche;

import java.util.Timer;
import java.util.TimerTask;

import com.chehui.maiche.comm.CommonData;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;


/**
 * 
 * @author zzp
 * 
 */
public class RequestTimeOutManager extends BaseManager {
	private Timer timer;

	private static String CONFIG_FILE_NAME = "chehui_maiche.config";

	private volatile static RequestTimeOutManager instance = null;

	public static RequestTimeOutManager getInstance() {
		if (instance == null) {
			instance = new RequestTimeOutManager();
		}
		return instance;
	}

	private SharedPreferences sp = null;

	private RequestTimeOutManager() {

	}

	@Override
	public void init(Context context) {
		super.init(context);
		sp = context.getSharedPreferences(CONFIG_FILE_NAME,
				Context.MODE_PRIVATE);
		timer = new Timer();
	}

	/**
	 * 
	 * @param flag
	 *            是否正在请求
	 * @param handler
	 * @param type
	 *            AutoListView.REFRESH AutoListView.LOAD
	 */
	public void startTimerTask(final boolean flag, final Handler handler,
			final int type) {
		TimerTask timerTask = new TimerTask() {
			@Override
			public void run() {
				if (flag) {
					Message message = handler.obtainMessage();
					message.what = CommonData.HTTP_TIME_OUT;
					message.obj = type;
					handler.sendMessage(message);
				}
			}
		};
		if (timer == null) {
			timer = new Timer();
		}
		timer.schedule(timerTask, CommonData.TIME_OUT);
	}

	// public void refushAutoListView(Context context, AutoListView
	// autoListView,
	// BaseAdapter adapter, int type) {
	// if (type == AutoListView.REFRESH) {
	// autoListView.onRefreshComplete();
	// adapter.notifyDataSetChanged();
	// // ToastUtils.showShortToast(context,
	// // R.string.common_requesting_time_out);
	// }
	// }
}
