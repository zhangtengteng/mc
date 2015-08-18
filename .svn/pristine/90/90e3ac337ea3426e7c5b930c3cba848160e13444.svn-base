package com.chehui.maiche.setup;

import android.os.Bundle;

import com.chehui.maiche.BaseActivity;
import com.chehui.maiche.R;
import com.chehui.maiche.custom.WiperSwitch;
import com.chehui.maiche.custom.WiperSwitch.OnChangedListener;

public class SetupMessageSetActivity extends BaseActivity implements
		OnChangedListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_message);
		init();
		initTitleView(-1, 255, R.string.set_account_pushmsg, 255, -1, 0);
	}

	private void init() {
		WiperSwitch wiperSwitch = (WiperSwitch) findViewById(R.id.wiperSwitchMessage);
		wiperSwitch.setChecked(true);
		wiperSwitch.setOnChangedListener(this);
	}

	@Override
	public void OnChanged(WiperSwitch wiperSwitch, boolean checkState) {

	}

}
