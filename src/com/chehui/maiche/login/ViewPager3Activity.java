package com.chehui.maiche.login;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.chehui.maiche.BaseActivity;
import com.chehui.maiche.R;

public class ViewPager3Activity extends BaseActivity implements OnClickListener {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loading_viewpager3);
		init();
	}

	private void init() {
		findViewById(R.id.iv_star).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		activityManager.startNextActivity(LoginActivity.class);
	}

}
