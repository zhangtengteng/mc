package com.chehui.maiche.myorder;

import android.os.Bundle;
import com.chehui.maiche.BaseActivity;
import com.chehui.maiche.R;

public class MyOrderVertifyActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vertify_phone);
		initTitleView(-1, 255, R.string.set_account_vertify_phone, 255, -1, 0);
	}
}
