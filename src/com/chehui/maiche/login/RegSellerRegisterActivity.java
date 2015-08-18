package com.chehui.maiche.login;

import java.util.ArrayList;
import java.util.List;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chehui.maiche.BaseActivity;
import com.chehui.maiche.R;
import com.chehui.maiche.RegisterManager;
import com.chehui.maiche.custom.PickerView.onSelectListener;
import com.chehui.maiche.pop.PoPBlandWindowManager;
import com.chehui.maiche.utils.StringUtils;
import com.chehui.maiche.utils.ToastUtils;

/**
 * @author zzp
 *         <P>
 *         填写详细注册信息post
 *         <P>
 */
public class RegSellerRegisterActivity extends BaseActivity implements
		OnClickListener {

	private Button btn_register;
	private EditText edt_name;
	private EditText edt_checkpassword;
	private EditText edt_password;
	private TextView edt_cityName;
	private String postParams;

	/** 从上一个界面获取的手机号 */
	public String phoneNum;

	private CityPicker cityPicker;
	/** 用户名 */
	private String username;
	/** 注册城市 */
	private String cityname;

	/** 密码 */
	private String password;

	private String checkpassword;

	private TextView tv_edt_cityName;
	/** 城市信息 */
	public List<String> cityList = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_regsellerregister);
		initTitleView(-1, 255, R.string.reg, 255, -1, 0);
		
	}

	@Override
	protected void onResume() {
		super.onResume();
		initData();
		initWidgets();
		initListener();
	}
	/**
	 * 初始化数据
	 */
	private void initData() {
		phoneNum = RegisterManager.getInstance().getTel();

	}

	/** 初始化组件 */
	@SuppressLint("CutPasteId")
	private void initWidgets() {
		edt_name = (EditText) findViewById(R.id.reg_edt_name);
		edt_password = (EditText) findViewById(R.id.reg_edt_password);
		edt_checkpassword = (EditText) findViewById(R.id.reg_edt_checkpassword);

		edt_cityName = (TextView) findViewById(R.id.reg_edt_cityName);
		btn_register = (Button) findViewById(R.id.reg_btn_register);
		tv_edt_cityName = (TextView) findViewById(R.id.reg_edt_cityName);
		setPopWidth();
	}

	private void initListener() {

		btn_register.setOnClickListener(this);

		tv_edt_cityName.setOnClickListener(this);
	}

	/***
	 * 设置pop的宽度
	 */
	private void setPopWidth() {
		@SuppressWarnings("deprecation")
		int width = getWindowManager().getDefaultDisplay().getWidth();
		@SuppressWarnings({ "deprecation" })
		int height = getWindowManager().getDefaultDisplay().getHeight();
		PoPBlandWindowManager.getInstance().init(getApplicationContext(),
				width, height / 3, R.layout.pop_bland);
	}

	private void setCityText(final TextView view) {
		String dateStr1 = "南京";
		String dateStr2 = "天津";
		String dateStr3 = "上海";
		cityList.clear();
		cityList.add(dateStr1);
		cityList.add(dateStr2);
		cityList.add(dateStr3);

		PoPBlandWindowManager.getInstance().setPickViewData(cityList);
		// pickView不滑动时的选中状态
		PoPBlandWindowManager.getInstance().changeOnClick(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						view.setText(cityList.get(1));
						PoPBlandWindowManager.getInstance().dismissPop();
					}
				});

		PoPBlandWindowManager.getInstance().changeOnSelect(
				new onSelectListener() {

					@Override
					public void onSelect(final String text) {
						PoPBlandWindowManager.getInstance().changeOnClick(
								new OnClickListener() {

									@Override
									public void onClick(View v) {
										view.setText(text);
										PoPBlandWindowManager.getInstance()
												.dismissPop();
									}
								});
						// 设置文字
					}
				});
		setPopWidth();
		PoPBlandWindowManager.getInstance().showPopAllLocation(view,
				Gravity.CENTER | Gravity.BOTTOM, 0, 0);

	}

	/**
	 * 注册业务流程
	 */
	private void register() {
		username = edt_name.getText().toString().trim();
		cityname = edt_cityName.getText().toString().trim();
		password = edt_password.getText().toString().trim();
		checkpassword = edt_checkpassword.getText().toString().trim();
		String mail = "";
		if (StringUtils.isEmpty(username) || StringUtils.isEmpty(checkpassword)
				|| StringUtils.isEmpty(cityname)
				|| StringUtils.isEmpty(phoneNum)
				|| StringUtils.isEmpty(password)) {

			ToastUtils
					.showShortToast(RegSellerRegisterActivity.this, "请输入完整信息");

			return;
		} else if (!password.equals(checkpassword)) {
			ToastUtils
					.showShortToast(RegSellerRegisterActivity.this, "两次密码不一样");

			return;
		}

		// 将密码存入实体类，下一个界面用
		RegisterManager.getInstance().setPassWord(checkpassword);

		postParams = username + "|" + password + "|" + phoneNum + "|" + mail
				+ "|" + converterToSpell(cityname);
		Intent intent = new Intent(RegSellerRegisterActivity.this,
				RegSelectBrandActivity.class);
		intent.putExtra("PARAMS", postParams);
		startActivity(intent);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		// 城市选择器
		case R.id.reg_edt_cityName:
			// mCityPopupWindow();作为下阶段备用方法
			setCityText(edt_cityName);
			break;
		// 注册
		case R.id.reg_btn_register:

			register();

			break;

		default:
			break;
		}
	}

	/**
	 * 城市选择器窗口
	 * 
	 * 暂时用不到作为备用
	 */
	public void mCityPopupWindow() {
		// 装载/res/layout/login.xml界面布局
		RelativeLayout loginForm = (RelativeLayout) getLayoutInflater()
				.inflate(R.layout.city_choice_layout, null);

		cityPicker = (CityPicker) loginForm.findViewById(R.id.citypicker);

		new AlertDialog.Builder(this)
		// 设置对话框显示的View对象
				.setView(loginForm)
				// 为对话框设置一个“确定”按钮
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						String sub = "";
						String choiceName = cityPicker.getCity_string();
						sub = choiceName.replaceAll("市辖区|县", "");
						edt_cityName.setText(sub);
					}
				})

				.setNegativeButton("取消", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

					}
				})
				// 创建、并显示对话框
				.create().show();

	}

	/**
	 * 汉字转换位汉语拼音，英文字符不变
	 * 
	 * @param chines
	 *            汉字
	 * @return 拼音
	 */
	public static String converterToSpell(String chines) {
		String pinyinName = "";
		char[] nameChar = chines.toCharArray();
		HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
		defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		for (int i = 0; i < nameChar.length; i++) {
			if (nameChar[i] > 128) {
				try {
					pinyinName += PinyinHelper.toHanyuPinyinStringArray(
							nameChar[i], defaultFormat)[0];
				} catch (BadHanyuPinyinOutputFormatCombination e) {
					e.printStackTrace();
				}
			} else {
				pinyinName += nameChar[i];
			}
		}
		return pinyinName;
	}
}
