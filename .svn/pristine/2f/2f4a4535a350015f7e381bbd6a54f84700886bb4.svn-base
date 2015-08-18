package com.chehui.maiche.setup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.chehui.maiche.MainActivity;
import com.chehui.maiche.R;

/**
 * 提交意见成功
 * @author gqy
 *
 */
public class SetupFeedbackSuccess extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_feedback_success);
		findViewById(R.id.btn_feedback_finish).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(SetupFeedbackSuccess.this,MainActivity.class));
				finish();
			}
		});
	}
}
