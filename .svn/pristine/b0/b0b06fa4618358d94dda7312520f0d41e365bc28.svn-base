package com.chehui.maiche.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.chehui.maiche.BaseActivity;
import com.chehui.maiche.R;

/**
 * 登录遇到问题界面
 * 
 * @author gqy
 * 
 */
public class LoginQuestionActicity extends BaseActivity {
	private TextView login_question_session;
	private Button btn_sms;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login_question_acticity);
		initTitleView(-1, 255, R.string.login_question, 255, -1, 0);
		initView();
	}

	private void initView() {
		login_question_session = (TextView) findViewById(R.id.login_question_session);
		Spanned text = Html.fromHtml("您可通过"
				+ "<font color=#1497c0><b>手机号+短信验证码</b></font>" + "重设密码");
		login_question_session.setText(text);
		btn_sms = (Button) findViewById(R.id.loginques_sms);
		btn_sms.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(LoginQuestionActicity.this,
						LoginVerifyActiivty.class));
			}
		});
	}
}
