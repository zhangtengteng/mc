package com.chehui.maiche.rabate;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.chehui.maiche.BaseActivity;
import com.chehui.maiche.R;
import com.chehui.maiche.SharedPreManager;
import com.chehui.maiche.comm.CommonData;
import com.chehui.maiche.custom.PickerView.onSelectListener;
import com.chehui.maiche.httpserve.HttpService;
import com.chehui.maiche.pop.PoPBlandWindowManager;
import com.chehui.maiche.utils.ToastUtils;
import com.chehui.maiche.utils.Utils;

/***
 * 
 * @author zzp
 *         <P>
 *         添加银行卡页面
 *         <P>
 * 
 */
public class RabateAddBankcardActivity extends BaseActivity implements
		OnClickListener {

	/** 下拉框选项数据源 */
	private ArrayList<String> datas = new ArrayList<String>();;

	/** 添加完成 */
	private Button btnAddComplete;
	/** 银行卡持有者 */
	private EditText edtHolder;
	private EditText edtBankcardNum;
	private TextView edt_bankname;
	private EditText edt_telnumber;

	private String bankName;
	private String bankcardNum;
	private String telNum;
	private String holder;
	private int sellerid;
	/** 应该提交的参数值 */
	private String postPramas;
	private String json;

	/** 钱包金额 */
	private String myMoney;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rabate_bankcark_add);
		initTitleView(-1, 255, R.string.set_account_add_bank_card, 255, -1, 0);
		initData();
		initWidgets();

	}

	private void initData() {
		// 判断是否英爱从上个界面获取money
		if (CommonData.ISPOSSESSBANKCARD) {
			myMoney = getIntent().getStringExtra("MONEY");
		}

		sellerid = SharedPreManager.getInstance()
				.getInt(CommonData.USER_ID, 72);

	}

	/***
	 * 初始化组件
	 */
	private void initWidgets() {
//		/** 设置自定义字体 */
//		Typeface typeFace = Typeface.createFromAsset(getAssets(),
//				"fonts/ChanticleerRomanNF.ttf");
		btnAddComplete = (Button) findViewById(R.id.rabate_add_btn_complete);
		btnAddComplete.setOnClickListener(this);
		edtHolder = (EditText) findViewById(R.id.rabate_add_edt_holder);
		edtBankcardNum = (EditText) findViewById(R.id.rabate_add_edt_cardnum);
		edt_telnumber = (EditText) findViewById(R.id.rabate_add_edt_telnumber);
		edt_bankname = (TextView) findViewById(R.id.rabate_add_edt_bankname);
		bankCardNumAddSpace(edtBankcardNum);
//		edtHolder.setTypeface(typeFace);
//		edtBankcardNum.setTypeface(typeFace);
//		edt_telnumber.setTypeface(typeFace);
//		edt_bankname.setTypeface(typeFace);
		edt_bankname.setOnClickListener(this);
		setPopWidth();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rabate_add_btn_complete:
			bankName = edt_bankname.getText().toString().trim();
			bankcardNum = edtBankcardNum.getText().toString().replace(" ", "").trim();
			telNum = edt_telnumber.getText().toString().trim();
			holder = edtHolder.getText().toString().trim();

			if (bankName == null || bankName.equals("")) {
				ToastUtils.showShortToast(RabateAddBankcardActivity.this,
						"请选择开户行");
				return;
			} else if (bankcardNum == null || bankcardNum.equals("")) {
				ToastUtils.showShortToast(RabateAddBankcardActivity.this,
						"请输入卡号");
				return;
			} else if (bankcardNum.length() != 16 && bankcardNum.length() != 19) {
				ToastUtils.showShortToast(RabateAddBankcardActivity.this,
						"请输入正确的卡号");
			} else if (holder == null || holder.equals("")) {
				ToastUtils.showShortToast(RabateAddBankcardActivity.this,
						"请输入姓名");
				return;
			} else if (!Utils.isMobileNO(telNum)) {
				ToastUtils.showShortToast(RabateAddBankcardActivity.this,
						"请输入正确的手机号");
				return;
			} else {
				postPramas = bankcardNum + "|" + String.valueOf(sellerid) + "|"
						+ bankName + "|" + holder + "|" + telNum;
				addBankCard(postPramas);

			}

			break;

		case R.id.rabate_add_edt_bankname:
			// 显示PopupWindow窗口
			setBankName(edt_bankname);
			break;
		default:
			break;
		}
	}

	/**
	 * 添加银行卡
	 * 
	 * @param params
	 */
	private void addBankCard(final String postParams) {

		if (!Utils.isNetworkAvailable(RabateAddBankcardActivity.this)) {
			ToastUtils.showShortToast(RabateAddBankcardActivity.this,
					R.string.common_network_unavalible);
			return;
		}

		showWaitDialog(R.string.common_requesting);
		new AsyncTask<Void, Integer, String>() {

			@Override
			protected String doInBackground(Void... params) {
				// 请求服务器
				final List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
				parameters.add(new BasicNameValuePair("classname",
						"BankService"));
				parameters.add(new BasicNameValuePair("methodname",
						"AddBankCard"));
				parameters.add(new BasicNameValuePair("params", postParams));

				Log.d("查看参数类型", parameters.toString());
				String response = HttpService.methodPost(CommonData.HTTP_URL,
						parameters);
				return response;
			}

			@Override
			protected void onPostExecute(String result) {
				super.onPostExecute(result);
				if (result != null) {
					json = result.toString();
					Log.d("返回银行卡列表", json);

					analysisJson();
				} else {
					ToastUtils.showShortToast(RabateAddBankcardActivity.this,
							"网络加载失败!");
				}

			}

		}.execute();
	}

	/**
	 * 解析json数据
	 */
	private void analysisJson() {
		try {
			JSONObject jsonObject = new JSONObject(json);
			Boolean success = jsonObject.getBoolean("Success");
			String mess = jsonObject.getString("Mess");
			if (success == true) {
				dismissLastAlertDialog();
				startNextActivity();
			} else {
				ToastUtils.showShortToast(RabateAddBankcardActivity.this, mess);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 跳转下一个界面
	 */
	private void startNextActivity() {
		Intent intent = new Intent(RabateAddBankcardActivity.this,
				RabateAddCompleteActivity.class);
		// 如果是提现跳转过来的，携带总金额
		if (CommonData.ISPOSSESSBANKCARD == true) {
			intent.putExtra("MONEY", myMoney);
		}
		startActivity(intent);
	}

	/**
	 * 选择银行卡
	 * 
	 * @param view
	 *            填写银行卡的TextView
	 */
	private void setBankName(final TextView view) {
		datas.clear();
		datas.add("中国银行");
		datas.add("工商银行");
		datas.add("建设银行");
		datas.add("农业银行");
		datas.add("招商银行");
		datas.add("华夏银行");

		PoPBlandWindowManager.getInstance().setPickViewData(datas);
		PoPBlandWindowManager.getInstance().changeOnClick(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						view.setText(datas.get(1));
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
					}
				});
		setPopWidth();
		PoPBlandWindowManager.getInstance().showPopAllLocation(view,
				Gravity.CENTER | Gravity.BOTTOM, 0, 0);

	}

	/**
	 * 设置底部滑动pop
	 */
	private void setPopWidth() {
		@SuppressWarnings("deprecation")
		int width = getWindowManager().getDefaultDisplay().getWidth();
		@SuppressWarnings({ "deprecation" })
		int height = getWindowManager().getDefaultDisplay().getHeight();
		PoPBlandWindowManager.getInstance().init(getApplicationContext(),
				width, height/3, R.layout.pop_bland);
	}

	/**
	 * 银行卡四位加空格
	 * 
	 * @param mEditText
	 */
	protected void bankCardNumAddSpace(final EditText mEditText) {
		mEditText.addTextChangedListener(new TextWatcher() {
			int beforeTextLength = 0;
			int onTextLength = 0;
			boolean isChanged = false;

			int location = 0;// 记录光标的位置
			private char[] tempChar;
			private StringBuffer buffer = new StringBuffer();
			int konggeNumberB = 0;

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				beforeTextLength = s.length();
				if (buffer.length() > 0) {
					buffer.delete(0, buffer.length());
				}
				konggeNumberB = 0;
				for (int i = 0; i < s.length(); i++) {
					if (s.charAt(i) == ' ') {
						konggeNumberB++;
					}
				}
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				onTextLength = s.length();
				buffer.append(s.toString());
				if (onTextLength == beforeTextLength || onTextLength <= 3
						|| isChanged) {
					isChanged = false;
					return;
				}
				isChanged = true;
			}

			@Override
			public void afterTextChanged(Editable s) {
				if (isChanged) {
					location = mEditText.getSelectionEnd();
					int index = 0;
					while (index < buffer.length()) {
						if (buffer.charAt(index) == ' ') {
							buffer.deleteCharAt(index);
						} else {
							index++;
						}
					}

					index = 0;
					int konggeNumberC = 0;
					while (index < buffer.length()) {
						if ((index == 4 || index == 9 || index == 14 || index == 19)) {
							buffer.insert(index, ' ');
							konggeNumberC++;
						}
						index++;
					}

					if (konggeNumberC > konggeNumberB) {
						location += (konggeNumberC - konggeNumberB);
					}

					tempChar = new char[buffer.length()];
					buffer.getChars(0, buffer.length(), tempChar, 0);
					String str = buffer.toString();
					if (location > str.length()) {
						location = str.length();
					} else if (location < 0) {
						location = 0;
					}

					mEditText.setText(str);
					Editable etable = mEditText.getText();
					Selection.setSelection(etable, location);
					isChanged = false;
				}
			}
		});
	}
}
