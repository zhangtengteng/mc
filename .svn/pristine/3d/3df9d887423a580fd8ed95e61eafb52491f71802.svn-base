package com.chehui.maiche.rabate;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.chehui.maiche.BaseActivity;
import com.chehui.maiche.R;
import com.chehui.maiche.utils.StringUtils;
import com.chehui.maiche.utils.ToastUtils;

/***
 * 
 * @author zzp
 *         <P>
 *         提取现金，更具是否添加银行卡，有不同的跳转方式
 *         <P>
 */
public class RabateWithdrawDepositActivity extends BaseActivity implements
		OnClickListener {
	private Button btn;
	private EditText etMoney;
	private String txt_money;
	private double temp;
	private String real_money;
	private int real_money_int;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rabate_withdraw_deposit);
		initTitleView(-1, 255, R.string.rabate_withdraw_money, 255, -1, 0);
		initData();
		initView();
	}

	private void initData() {
		real_money = getIntent().getStringExtra("MONEY");

		real_money_int = Integer.parseInt(real_money.substring(0,
				real_money.indexOf(".")));
	}

	@SuppressLint("ResourceAsColor")
	private void initView() {
		btn = (Button) findViewById(R.id.rabate_withdrawDeposit_btn_balance);
		btn.setOnClickListener(this);
		etMoney = (EditText) findViewById(R.id.rabate_withdrawDeposit_edt_money);
		etMoney.setHint("当前可提现金额为" + real_money + "元");
		etMoney.setHintTextColor(R.color.gray_back);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rabate_withdrawDeposit_btn_balance:
			txt_money = etMoney.getText().toString();
			if (StringUtils.isEmpty(txt_money)) {
				ToastUtils.showShortToast(getApplicationContext(), "请填写金额！");
			}
			try {
				temp = Double.parseDouble(txt_money);// 不抛出异常则全为数字
			} catch (Exception e) {
				ToastUtils.showShortToast(getApplicationContext(), "请填写数字！");
			}
			if (temp > real_money_int) {
				ToastUtils.showShortToast(getApplicationContext(), "可提现金额不足!");
				return;
			}
			if (temp < 2000) {
				ToastUtils.showShortToast(getApplicationContext(),
						"每次提现不能低于2000元！");
				return;
			}

			Intent intent = new Intent(RabateWithdrawDepositActivity.this,
					RabateSelectCardActivity.class);
			intent.putExtra("CASH", temp);
			startActivity(intent);
			break;
		default:
			break;
		}
	}
}
