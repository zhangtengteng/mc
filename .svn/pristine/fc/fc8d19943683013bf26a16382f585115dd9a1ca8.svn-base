/*
 * 文件名：ErrorCodeManager.java
 * 版权：Copyright 2012-2014 Huawei Tech. Co. Ltd. All Rights Reserved. 
 * 描述： ErrorCodeManager.java
 * 修改人：hu
 * 修改时间：2014年10月10日
 * 修改内容：新增
 */
package com.chehui.maiche.comm;

import android.content.Context;
import android.util.SparseIntArray;

import com.chehui.maiche.BaseManager;
import com.chehui.maiche.R;
import com.chehui.maiche.httpserve.HttpErrorCode;
import com.chehui.maiche.utils.ToastUtils;

/**
 * 
 * @author zhangtengteng
 * 
 */
public class ErrorCodeManager extends BaseManager {
	public volatile static ErrorCodeManager instance;

	public static ErrorCodeManager getInstance() {
		if (null == instance) {
			instance = new ErrorCodeManager();
		}
		return instance;
	}

	private SparseIntArray errCodeResMap;

	@Override
	public void init(Context context) {
		super.init(context);
		initResMap();
	}

	public void showError(int errorCode) {
		if (CommErrorCode.SUCCESS == errorCode) {
			return;
		}

		ToastUtils.showShortToast(appContext, getErrorDesc(errorCode));
	}

	public String getErrorDesc(int errorCode) {
		int resID = errCodeResMap.get(errorCode);

		if (0 == resID) {
			return appContext.getString(R.string.common_error_tips, errorCode);
		}

		return appContext.getString(resID);
	}

	private void initResMap() {
		errCodeResMap = new SparseIntArray();

		errCodeResMap.put(HttpErrorCode.NOT_AUTH, R.string.error_401_not_auth);
		errCodeResMap.put(HttpErrorCode.TIME_OUT, R.string.error_520_time_out);
	}
}
