package com.chehui.maiche.rabate;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.chehui.maiche.BaseActivity;
import com.chehui.maiche.R;
import com.chehui.maiche.comm.CommonData;
import com.chehui.maiche.utils.ToastUtils;

/***
 * 
 * @author zzp
 *         <P>
 *         完成添加银行卡&添加完成返回list界面还是跳转withdraw
 *         <P>
 * 
 */
public class RabateAddCompleteActivity extends BaseActivity implements
		OnClickListener {
	private Button btnNext;
	private String money;
	private Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rabate_finish_addcard);
		initData();
		initWidgets();
	}

	private void initData() {
		if (CommonData.ISPOSSESSBANKCARD == true) {
			money = getIntent().getStringExtra("MONEY");
		}
	}

	private void initWidgets() {
		btnNext = (Button) findViewById(R.id.btn_add_bank_finish);
		
		if (CommonData.ISPOSSESSBANKCARD == false) {
			btnNext.setText("完成");
		}
		btnNext.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_add_bank_finish:
			if (CommonData.ISPOSSESSBANKCARD == true) {
				intent = new Intent(RabateAddCompleteActivity.this,
						RabateWithdrawDepositActivity.class);
				intent.putExtra("MONEY", money);

			} else {
				intent = new Intent(RabateAddCompleteActivity.this,
						RabateListBankcardActivity.class);
			}
			startActivity(intent);
			finish();
			break;
		default:
			break;
		}
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		return false;
	}
}
