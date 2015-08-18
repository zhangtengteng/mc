package com.chehui.maiche.rabate;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.chehui.maiche.BaseActivity;
import com.chehui.maiche.MainActivity;
import com.chehui.maiche.R;
import com.chehui.maiche.comm.CommonData;

/**
 * 提现申请完成界面
 * 
 * @author gqy
 * 
 */
public class RabateCommitFinishActivity extends BaseActivity {
	private String bankName, cardNum, card_num, date, terminal_date;
	private String cash_num;
	private TextView tv_bank_name, tv_card_num, tv_cash, tv_terminal_date;
	private Button btn_commit_finish;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_order_commit_finish);
		initView();
	}

	private void initView() {
		bankName = getIntent().getStringExtra("UBANKNAME");
		cardNum = getIntent().getStringExtra("UCARDNUM");
		card_num = cardNum.substring(cardNum.length() - 4);
		cash_num = getIntent().getStringExtra("CASH");
		date = getIntent().getStringExtra("DATE");
		String s = date.replace("/", "-");
		terminal_date = s.split(":")[0];

		tv_bank_name = (TextView) findViewById(R.id.finish_bank_name);
		tv_card_num = (TextView) findViewById(R.id.finish_card_num);
		tv_cash = (TextView) findViewById(R.id.finish_cash_num);
		tv_terminal_date = (TextView) findViewById(R.id.finish_terminal_time);
		tv_bank_name.setText(bankName);
		tv_card_num.setText("尾号  " + card_num);
		tv_cash.setText("￥" + cash_num);
		tv_terminal_date.setText(terminal_date + "点前");

		btn_commit_finish = (Button) findViewById(R.id.btn_commit_bank_finish);
		btn_commit_finish.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(RabateCommitFinishActivity.this,
						MainActivity.class);
				CommonData.ISRABATEFRAGMENTREFRESH = true;
				startActivity(i);
				finish();
			}
		});
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		return false;
	}
}
