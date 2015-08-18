package com.chehui.maiche.custom;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.chehui.maiche.BaseActivity;
import com.chehui.maiche.comm.ErrorCodeManager;
import com.chehui.maiche.utils.LogN;

/**
 * 
 * @author zzp
 * 
 */
public class BaseFragment extends Fragment {

	private WaitingAlertDialog waitDialog;

	@Override
	public void setInitialSavedState(SavedState state) {
		super.setInitialSavedState(state);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {

		LogN.d(this, "onCreate");
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		LogN.d(this, "onCreateView");
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onStart() {
		LogN.d(this, "onStart");
		super.onStart();
	}

	@Override
	public void onResume() {
		LogN.d(this, "onResume");
		super.onResume();
	}

	@Override
	public void onPause() {
		LogN.d(this, "onPause");
		super.onPause();
	}

	@Override
	public void onStop() {
		LogN.d(this, "onStop");
		super.onStop();
	}

	@Override
	public void onDestroyView() {
		LogN.d(this, "onDestroyView");

		dismissWaitDialog();
		waitDialog = null;

		super.onDestroyView();
	}

	@Override
	public void onDestroy() {
		LogN.d(this, "onDestroy");
		super.onDestroy();
	}

	public void showWaitDialog(int textRes) {
		Activity activity = getActivity();

		if (activity instanceof BaseActivity) {
			((BaseActivity) activity).showWaitDialog(textRes);
		} else {
			if (null == waitDialog) {
				waitDialog = new WaitingAlertDialog(activity, textRes);
			} else {
				waitDialog.setShowText(textRes);
				if (!waitDialog.isShown()) {
					waitDialog.show();
				}
			}
		}
	}

	public void dismissWaitDialog() {
		Activity activity = getActivity();

		if (activity instanceof BaseActivity) {
			((BaseActivity) activity).dismissWaitDialog();
		} else {
			if (null != waitDialog) {
				waitDialog.dismiss();
			}
		}
	}

	/**
	 * 
	 * 显示软键盘
	 * 
	 * @param view
	 */
	public void showSoftInput(View view) {
		Activity activity = getActivity();

		if (activity instanceof BaseActivity) {
			((BaseActivity) activity).showSoftInput(view);
		} else if (null != activity) {
			InputMethodManager imm = (InputMethodManager) activity
					.getSystemService(Activity.INPUT_METHOD_SERVICE);
			view.requestFocus();
			imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
		}
	}

	/**
	 * 
	 * 隐藏软键盘
	 * 
	 */
	public void hideSoftInput() {
		Activity activity = getActivity();
		if (activity instanceof BaseActivity) {
			((BaseActivity) activity).hideSoftInput();
		} else if (null != activity && null != activity.getCurrentFocus()) {
			InputMethodManager imm = (InputMethodManager) activity
					.getSystemService(Activity.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(activity.getCurrentFocus()
					.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}

	public void showError(int errorCode) {
		ErrorCodeManager.getInstance().showError(errorCode);
	}

}
