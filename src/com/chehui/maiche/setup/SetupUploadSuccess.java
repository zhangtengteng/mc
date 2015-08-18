package com.chehui.maiche.setup;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import com.chehui.maiche.BaseActivity;
import com.chehui.maiche.MainActivity;
import com.chehui.maiche.R;

/**
 * 实名认证成功界面
 * 
 * @author gqy
 * 
 */
public class SetupUploadSuccess extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup_upload_success);
		findViewById(R.id.authen_finish).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(SetupUploadSuccess.this, MainActivity.class));
			}
		});
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		return false;
	}
}
