package com.chehui.maiche.myorder;

import android.os.Bundle;

import com.chehui.maiche.BaseActivity;
import com.chehui.maiche.R;

public class MyOrderSelectCardActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_card);
		initTitleView(-1, 255, R.string.set_account_select_bank_card, 255, -1, 0);
	}
}
