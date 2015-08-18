package com.chehui.maiche.myorder;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.chehui.maiche.BaseActivity;
import com.chehui.maiche.MainActivity;
import com.chehui.maiche.R;

/**
 * 发票上传成功界面
 * @author gqy
 *
 */
public class MyOrderInvoiceSuccess extends BaseActivity {
	private Button btn_finish;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_invoice_success);
		btn_finish  =(Button) findViewById(R.id.btn_invoice_finish);
		btn_finish.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(MyOrderInvoiceSuccess.this,MainActivity.class));
				finish();
			}
		});
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		return false;
	}
}
