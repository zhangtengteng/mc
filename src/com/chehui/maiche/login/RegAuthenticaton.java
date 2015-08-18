package com.chehui.maiche.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.chehui.maiche.R;
import com.chehui.maiche.custom.EditTextWithDel;
import com.chehui.maiche.setup.SetupUploadPic;

/**
 * 
 * @author zzp 注册时认证
 * 
 */
public class RegAuthenticaton extends Activity {

	/** 编辑用户名 */
	private EditTextWithDel edt_username;

	/** 编辑身份证号 */
	private EditTextWithDel edt_id;

	private Button btn_next;

	private String username;

	private String idcard;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_regauthentication);
		initWidgets();
		initData();
	}

	private void initWidgets() {

		edt_username = (EditTextWithDel) findViewById(R.id.reg_authentication_edt_username);
		edt_id = (EditTextWithDel) findViewById(R.id.reg_authentication_edt_id);
		btn_next = (Button) findViewById(R.id.reg_authen_btn_next);

	}

	private void initData() {
		username = edt_id.getText().toString().trim();
		idcard = edt_username.getText().toString().trim();
		btn_next.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (username != null && idcard != null) {
					Intent intent = new Intent(RegAuthenticaton.this,
							SetupUploadPic.class);
					intent.putExtra("username", username);
					intent.putExtra("idcard", idcard);
					startActivity(intent);
				}

			}
		});

	}

}
