package com.chehui.maiche.setup;

import java.util.HashSet;
import java.util.Set;

import org.jinouts.ws.JinosService;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

import com.chehui.maiche.MainActivity;
import com.chehui.maiche.R;
import com.chehui.maiche.SharedPreManager;
import com.chehui.maiche.comm.CommonData;
import com.chehui.maiche.custom.BaseFragment;
import com.chehui.maiche.utils.LogN;
import com.chehui.maiche.utils.ToastUtils;

/**
 * 设置模块
 * 
 * @author gqy
 * 
 */
public class SetFragment extends BaseFragment implements OnClickListener {

	private Intent intent;
	Builder builder;
	private RelativeLayout mPersonMsg;
	private RelativeLayout mAuthen;
	private RelativeLayout mPushMsg;
	private RelativeLayout mFeedback;
	private RelativeLayout mClear;
	private RelativeLayout mAbout;
	private RelativeLayout mMsgWay;
	private Button mLoginOut;
	private View layout;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		layout = inflater.inflate(R.layout.setup_fragment_layout, container, false);
		initWidgets();
		initListener();
		return layout;
	}

	private void initListener() {
		mPersonMsg.setOnClickListener(this);
		mAuthen.setOnClickListener(this);
		mPushMsg.setOnClickListener(this);
		mFeedback.setOnClickListener(this);
		mClear.setOnClickListener(this);
		mAbout.setOnClickListener(this);
		mMsgWay.setOnClickListener(this);
		mLoginOut.setOnClickListener(this);

	}

	private void initWidgets() {
		mPersonMsg = (RelativeLayout) layout.findViewById(R.id.setup_pm_layout);
		mAuthen = (RelativeLayout) layout.findViewById(R.id.setup_authen_layout);
		mPushMsg = (RelativeLayout) layout.findViewById(R.id.setup_pushmsg_layout);
		mFeedback = (RelativeLayout) layout.findViewById(R.id.setup_fb_layout);
		mClear = (RelativeLayout) layout.findViewById(R.id.setup_clear_layout);
		mAbout = (RelativeLayout) layout.findViewById(R.id.setup_about_layout);
		mMsgWay = (RelativeLayout) layout.findViewById(R.id.setup_mj_layout);
		mLoginOut = (Button) layout.findViewById(R.id.setup_btn_out);

	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		// 个人信息
		case R.id.setup_pm_layout:
			intent = new Intent(getActivity(), SetupManagerAccountPersion.class);
			startActivity(intent);
			break;
		// 实名认证
		case R.id.setup_authen_layout:
			intent = new Intent(getActivity(), SetupManagerAuthentication.class);
			startActivity(intent);
			break;

		// 关于页面
		case R.id.setup_about_layout:
			intent = new Intent(getActivity(), SetupAboutActivity.class);
			startActivity(intent);
			break;

		// 消息推送
		case R.id.setup_pushmsg_layout:

			break;
		// 意见反馈
		case R.id.setup_fb_layout:
			intent = new Intent(getActivity(), SetupFeedBackActivity.class);
			startActivity(intent);
			break;

		// 清理缓存
		case R.id.setup_clear_layout:
			// intent = new Intent(getActivity(),MyOderUploadInvoice.class);
			// startActivity(intent);
			mAlertClearDialog();
			break;

		// 卖车秘籍
		case R.id.setup_mj_layout:
			intent = new Intent(getActivity(), SetupTipsActivity.class);
			startActivity(intent);
			break;
		// 注销
		case R.id.setup_btn_out:
			mAlertOutDialog();
			break;
		default:
			break;
		}
	}

	/**
	 * 清理缓存
	 */
	private void mAlertClearDialog() {

		if (builder == null) {
			builder = new AlertDialog.Builder(getActivity());
		}
		builder.setMessage("是否清空缓存?").setIcon(android.R.drawable.ic_dialog_info)
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						ToastUtils.showShortToast(getActivity(), "清理完成");
					}
				}).setNegativeButton("取消", null).show();
	}

	private void mAlertOutDialog() {

		if (builder == null) {
			builder = new AlertDialog.Builder(getActivity());
		}
		builder.setMessage("确定要退出吗？").setIcon(android.R.drawable.ic_dialog_info)
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						JPushInterface.setAliasAndTags(getActivity(), "", new HashSet<String>(),
								new TagAliasCallback() {

							@Override
							public void gotResult(int result, String arg1, Set<String> arg2) {
								LogN.d(getActivity(), "JPushInterface.setTags---------------------result=" + result);
							}
						});

						SharedPreManager.getInstance().setString(CommonData.USER_PWD, "");

						CommonData.LOGINFLAG = true;
						getActivity().finishAffinity();

					}
				}).setNegativeButton("取消", null).show();
	}
}
