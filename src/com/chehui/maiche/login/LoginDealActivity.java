package com.chehui.maiche.login;

import com.chehui.maiche.BaseActivity;
import com.chehui.maiche.R;

import android.os.Bundle;

public class LoginDealActivity extends BaseActivity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_deal);
		initTitleView(-1, 255, R.string.deal, 255, -1, 0);
	}
}
