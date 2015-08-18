package com.chehui.maiche.setup;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.chehui.maiche.BaseActivity;
import com.chehui.maiche.R;

/****
 * 我的钱包页面 finish
 */

public class SetupManagerAccountWallet7 extends BaseActivity implements
		OnClickListener {
	private Button btnNext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_manager_account_wallet_finish);
		initTitleView(-1, 255, R.string.set_account_persion, 255, -1, 0);
		init();
	}

	private void init() {
		btnNext = (Button) findViewById(R.id.btn_next);
		btnNext.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_next:
			break;

		default:
			break;
		}
	}

}
