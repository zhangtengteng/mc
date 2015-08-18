package com.chehui.maiche;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chehui.maiche.R;
import com.chehui.maiche.comm.ErrorCodeManager;
import com.chehui.maiche.custom.AlertDialog;
import com.chehui.maiche.custom.DialogUtil;
import com.chehui.maiche.custom.WaitingAlertDialog;
import com.chehui.maiche.custom.DialogUtil.DialogCallback;
import com.chehui.maiche.utils.LogN;

public class BaseActivity extends Activity {
	protected TextView topTitle;
	protected TextView left;
	protected TextView right;

	private static final int ADD_BTN_NATURE = -1;

	protected ActivityManager activityManager = ActivityManager.getInstance();

	protected TextView titleTextView;

	protected ImageButton backBtn;

	private LinearLayout operBtnLayout;

	private WaitingAlertDialog waitDialog;

	protected AlertDialog alertDialog;

	protected boolean isActive;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		SharedPreManager.getInstance().init(getApplicationContext());
		isActive = true;
		activityManager.pushActivity(this);

	}

	@Override
	protected void onStart() {
		super.onStart();
		LogN.d(this, "onStart");
	}

	@Override
	protected void onResume() {
		super.onResume();
		LogN.d(this, "onResume");
	}

	@Override
	protected void onPause() {
		super.onPause();
		LogN.d(this, "onPause");
	}

	@Override
	protected void onStop() {
		super.onStop();
		LogN.d(this, "onStop");
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		LogN.d(this, "onRestart");
	}

	@Override
	protected void onDestroy() {
		isActive = false;
		dismissWaitDialog();
		dismissLastAlertDialog();
		waitDialog = null;

		activityManager.popActivity(this);

		super.onDestroy();
		LogN.d(this, "onDestroy");
	}

	protected void initTitleView(int leftId, int leftAlpha, int topId, int topAlpha, int rightId, int rightAlpha) {
		if (left == null) {
			left = (TextView) findViewById(R.id.tvLeft);
		}
		if (right == null) {

			right = (TextView) findViewById(R.id.tvRight);
		}
		if (topTitle == null) {

			topTitle = (TextView) findViewById(R.id.tvTop);
		}

		if (leftId != -1) {
			left.setText(leftId);
		} else {
			left.setText("");
		}
		left.getBackground().setAlpha(leftAlpha);

		if (topId != -1) {
			topTitle.setText(topId);
		} else {
			topTitle.setText("");
		}

		topTitle.getBackground().setAlpha(topAlpha);
		if (rightId != -1) {
			right.setText(rightId);
		} else {
			right.setText("");
		}
		right.getBackground().setAlpha(rightAlpha);

		left.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	@SuppressLint("NewApi")
	public void changeLeftBackground(Drawable drawable) {

		if (left == null) {
			left = (TextView) findViewById(R.id.tvLeft);
		}
		left.setBackground(drawable);
	}

	@SuppressLint("NewApi")
	public void changeRightBackground(Drawable drawable) {

		if (right == null) {

			right = (TextView) findViewById(R.id.tvRight);
		}
		right.setBackground(drawable);
	}

	public void changeLeftOnClickListener(OnClickListener clickListener) {
		if (left == null) {
			left = (TextView) findViewById(R.id.tvLeft);
		}
		left.setOnClickListener(clickListener);
	}

	/***
	 * 点击右侧按钮激发事件
	 * 
	 * @param clickListener
	 */
	public void changeRightOnClickListener(OnClickListener clickListener) {
		if (right == null) {

			right = (TextView) findViewById(R.id.tvRight);
		}
		right.setOnClickListener(clickListener);
	}

	public void showError(int errorCode) {
		ErrorCodeManager.getInstance().showError(errorCode);
	}

	@Override
	public void finish() {
		super.finish();
		// overridePendingTransition(R.anim.push_left_in,
		// R.anim.push_right_out);
	}

	public void finishWithoutAnim() {
		super.finish();
	}

	public void dismissLastAlertDialog() {
		if (null != alertDialog) {
			if (alertDialog.isShown()) {
				alertDialog.dismiss();
			}

			alertDialog = null;
		}
	}

	public void showWaitDialog(int textRes) {
		if (null == waitDialog) {
			waitDialog = new WaitingAlertDialog(this, textRes);
		} else {
			waitDialog.setShowText(textRes);
			if (!waitDialog.isShown()) {
				waitDialog.show();
			}
		}
	}

	public void changeWaitDialogText(String text) {
		if (null != waitDialog) {
			waitDialog.setShowText(text);
		}
	}

	public void dismissWaitDialog() {
		if (null != waitDialog) {
			waitDialog.dismiss();
		}
	}

	public void hideBackBtn(boolean isHide) {
		if (null != backBtn) {
			backBtn.setVisibility(isHide ? View.INVISIBLE : View.VISIBLE);
		}
	}

	public void changeTitle(int titleTextRes) {
		if (null != this.titleTextView) {
			this.titleTextView.setText(titleTextRes);
		}
	}

	public void changeTitle(String titleText) {
		if (null != this.titleTextView) {
			this.titleTextView.setText(titleText);
		}
	}

	public void clearOperBtn() {
		if (null != operBtnLayout) {
			operBtnLayout.removeAllViews();
		}
	}

	public void addOperBtn(int textRes, OnClickListener clickListener) {
		addOperBtn(ADD_BTN_NATURE, textRes, clickListener);
	}

	/**
	 * public void addImageOperBtn(int imgRes, OnClickListener clickListener) {
	 * addImageOperBtn(ADD_BTN_NATURE, imgRes, clickListener); }
	 */
	public void addOperBtn(int pos, int textRes, OnClickListener clickListener) {
		if (null == operBtnLayout) {
			LogN.e(this, "addOperBtn error: operBtnLayout is null");
			return;
		}

		Button button = new Button(getApplicationContext());
		button.setOnClickListener(clickListener);
		button.setText(textRes);
		button.setBackgroundResource(R.color.transparent);

		if (pos < 0) {
			operBtnLayout.addView(button);
		} else {
			operBtnLayout.addView(button, pos);
		}
	}

	/**
	 * public void addImageOperBtn(int pos, int imgRes, OnClickListener
	 * clickListener) { if (null == operBtnLayout) { LogN.e(this,
	 * "addOperBtn error: operBtnLayout is null"); return; }
	 * 
	 * ImageButton operBtn = (ImageButton) View.inflate(this,
	 * R.layout.activity_title_operbtn, null); operBtn.setImageResource(imgRes);
	 * operBtn.setOnClickListener(clickListener);
	 * 
	 * if (pos < 0) { operBtnLayout.addView(operBtn); } else {
	 * operBtnLayout.addView(operBtn, pos); } }
	 */

	/**
	 * 从View中获取BadgeView
	 * 
	 * @param view
	 * @return
	 * 
	 * 		public BadgeView getBadgeViewFromTag(View view) { BadgeView badge
	 *         = null;
	 * 
	 *         if(null != view) { if(null == view.getTag()) { badge = new
	 *         BadgeView(getApplicationContext(), view);
	 *         badge.setBackgroundResource(R.drawable.common_new_tip);
	 *         badge.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);
	 *         view.setTag(badge); } else if(view.getTag() instanceof BadgeView)
	 *         { badge = (BadgeView) view.getTag(); } }
	 * 
	 *         return badge; }
	 * 
	 * 
	 *         public void showOrHideBadgeView(View view, boolean needShow) {
	 *         BadgeView badgeView = getBadgeViewFromTag(view); if(null !=
	 *         badgeView) { if(needShow) { badgeView.show(); } else {
	 *         badgeView.hide(); } } }
	 */
	/**
	 * 
	 * 显示软键盘
	 * 
	 * @param view
	 */
	public void showSoftInput(View view) {
		if (null == view) {
			LogN.e(this, "showSoftInput | view is null");
			return;
		}
		InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
		view.requestFocus();
		imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
	}

	/**
	 * 
	 * 隐藏软键盘
	 * 
	 */
	public void hideSoftInput() {
		if (null == getCurrentFocus()) {
			LogN.w(this, "hideSoftInput currFocus is null");
			return;
		}
		InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
	}

	public void showExitAppMessage(DialogCallback callback) {
		activityDialog(R.string.dialog_message_exitapp, R.string.common_confirm, R.string.common_cancel, true, true,
				callback);
	}

	public void showNotifyDialog(int msg) {
		activityDialog(msg, R.string.common_confirm, DialogUtil.NO_CANCEL_BUTTON, true, false, null);
	}

	public void showNotifyDialogExit(int msg) {
		activityDialog(msg, R.string.common_confirm, DialogUtil.NO_CANCEL_BUTTON, false, false, new DialogCallback() {

			@Override
			public void onPositiveClicked(AlertDialog dialog) {
				finish();
			}

			@Override
			public void onNegativeClicked(AlertDialog dialog) {
			}
		});
	}

	public void showConfirmDialog(int msg, DialogCallback callback) {
		activityDialog(msg, R.string.common_confirm, R.string.common_cancel, true, false, callback);
	}

	/**
	 * 对话框构建类
	 * 
	 * @param context
	 *            产生dialog的activity
	 * @param title
	 *            提示信息头
	 * @param msg
	 *            提示消息体资源ID
	 * @param posRes
	 *            确认按钮资源文件ID
	 * @param negRes
	 *            取消按钮资源文件ID，如果为-1，则不创建取消按钮
	 * @param cancelable
	 *            是否可取消
	 * @param cancelOnOutTouch
	 *            是否可点击对话框外部取消
	 * @param callback
	 *            按钮按下回调函数
	 * @return
	 */
	public void activityDialog(int msg, int posRes, int negRes, boolean cancelable, boolean cancelOnOutTouch,
			DialogCallback callback) {
		alertDialog = DialogUtil.activityDialog(this, getString(msg), posRes, negRes, cancelable, cancelOnOutTouch,
				callback);
	}

}
