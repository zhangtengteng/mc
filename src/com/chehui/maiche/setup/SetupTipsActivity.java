package com.chehui.maiche.setup;

import android.os.Bundle;

import com.chehui.maiche.BaseActivity;
import com.chehui.maiche.R;

public class SetupTipsActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup_tips_actiivty);
		initTitleView(-1, 255, R.string.set_tips, 255, -1, 0);
	}
}
