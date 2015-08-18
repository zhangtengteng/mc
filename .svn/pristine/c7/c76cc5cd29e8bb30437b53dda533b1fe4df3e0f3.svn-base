package com.chehui.maiche.rabate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.Spinner;

import com.chehui.maiche.BaseActivity;
import com.chehui.maiche.R;
import com.chehui.maiche.SharedPreManager;
import com.chehui.maiche.comm.CommonData;
import com.chehui.maiche.httpserve.HttpService;
import com.chehui.maiche.utils.ToastUtils;

/**
 * 提现选择银行卡
 * 
 * @author gqy
 * 
 */
public class RabateSelectCardActivity extends BaseActivity {
	/** 存放银行卡的总的集合 */
	private ArrayList<PersonInfo> personinfoDataList = new ArrayList<PersonInfo>();
	private List<String> infoList = new ArrayList<String>();
	/** 人名对应银行 */
	private HashMap<String, List<PersonInfo>> nameForBankMap = new HashMap<String, List<PersonInfo>>();
	/** 银行对应卡 */
	private HashMap<String, List<String>> bankForCardMap = new HashMap<String, List<String>>();

	private Button btn_next;

	private int sellerId;
	private String json;
	private Spinner sp_name, sp_bankName, sp_bankNumber;
	private SpinnerNameAdater spinnernameAdapter;
	private SpinnerbankNameAdater banknameAdapter;
	private SpinnerBankNumAdater banknumAdapter;

	private String name, bankName, cardNum;
	private double cash_num;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_card);
		initView();
		initTitleView(-1, 255, R.string.set_account_select_bank_card, 255, -1,
				0);
		getAllData();
	}

	private void setListener() {
		sp_name.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				name = infoList.get(position);
				banknameAdapter = new SpinnerbankNameAdater(nameForBankMap
						.get(name), RabateSelectCardActivity.this);
				sp_bankName.setAdapter(banknameAdapter);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});
		sp_bankName.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				bankName = nameForBankMap.get(name).get(position).getBankName();
				banknumAdapter = new SpinnerBankNumAdater(bankForCardMap
						.get(bankName + name), RabateSelectCardActivity.this);
				sp_bankNumber.setAdapter(banknumAdapter);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});
		sp_bankNumber.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				cardNum = bankForCardMap.get(bankName + name).get(position);
				Log.e("TAG", "tel是：" + checkTel());
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});
	}

	private void initView() {
		sp_name = (Spinner) this.findViewById(R.id.sp_name);
		sp_bankName = (Spinner) this.findViewById(R.id.sp_bank);
		sp_bankNumber = (Spinner) this.findViewById(R.id.sp_card_number);
		cash_num = getIntent().getDoubleExtra("CASH", 0);
		btn_next = (Button) findViewById(R.id.btn_next);
		btn_next.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(RabateSelectCardActivity.this,
						RabateVertifyActivity.class);
				i.putExtra("TEL", checkTel());
				i.putExtra("NAME", name);
				i.putExtra("BANKNAME", bankName);
				i.putExtra("BANKNUM", cardNum);
				i.putExtra("CASH", cash_num);
				startActivity(i);
			}
		});
	}

	/**
	 * 从服务器上拿下所有数据
	 */
	private void getAllData() {
		sellerId = SharedPreManager.getInstance().getInt(CommonData.USER_ID,
				103);

		new AsyncTask<Void, Integer, String>() {
			@Override
			protected String doInBackground(Void... params) {
				// 请求服务器
				final List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
				parameters.add(new BasicNameValuePair("classname",
						"BankService"));
				parameters.add(new BasicNameValuePair("methodname",
						"GetBankCardList"));
				parameters.add(new BasicNameValuePair("params", sellerId + ""));
				String response = HttpService.methodPost(CommonData.HTTP_URL,
						parameters);
				return response;
			}

			@Override
			protected void onPostExecute(String result) {
				super.onPostExecute(result);
				json = result.toString();
				if (TextUtils.isEmpty(json)) {
					ToastUtils.showShortToast(RabateSelectCardActivity.this,
							"网络无响应!");
					return;
				}
				analysisJson();
				getNameForBankMap();
				setListener();
			}

		}.execute();
	}

	private void analysisJson() {
		try {
			JSONObject jsonObject = new JSONObject(json);
			Boolean success = jsonObject.getBoolean("Success");
			if (success != true) {
				return;
			}
			// 解析data数据
			JSONArray jsonArray = jsonObject.getJSONArray("Data");
			if (jsonArray.equals("")) {
				ToastUtils.showShortToast(RabateSelectCardActivity.this,
						"请添加银行卡");
			}

			personinfoDataList.clear();
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject contentItem = jsonArray.getJSONObject(i);
				String ID = contentItem.getString("Id");
				String BankCardNum = contentItem.getString("BankCardNum");
				String BankPass = contentItem.getString("BankPass");
				String State = contentItem.getString("State");
				String SellerID = contentItem.getString("SellerID");
				String CreateDate = contentItem.getString("CreateDate");
				String BankName = contentItem.getString("BankName");
				String CardAdmin = contentItem.getString("CardAdmin");
				String Tel = contentItem.getString("Tel");

				PersonInfo pinfo = new PersonInfo();
				pinfo.setId(ID);
				pinfo.setBankCardNum(BankCardNum);
				pinfo.setBankPass(BankPass);
				pinfo.setState(State);
				pinfo.setSellerID(SellerID);
				pinfo.setCreateDate(CreateDate);
				pinfo.setBankName(BankName);
				pinfo.setCardAdmin(CardAdmin);
				pinfo.setTel(Tel);
				if (!infoList.contains(CardAdmin)) {
					infoList.add(CardAdmin);
				}
				personinfoDataList.add(pinfo);
			}
			spinnernameAdapter = new SpinnerNameAdater(infoList,
					RabateSelectCardActivity.this);
			sp_name.setAdapter(spinnernameAdapter);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private void getNameForBankMap() {
		for (int i = 0; i < personinfoDataList.size(); i++) {
			List<PersonInfo> bankList = new ArrayList<PersonInfo>();
			List<String> cardList = new ArrayList<String>();
			PersonInfo info = personinfoDataList.get(i);
			bankList.add(info);
			cardList.add(info.getBankCardNum());
			for (int j = i + 1; j < personinfoDataList.size(); j++) {
				PersonInfo info2 = personinfoDataList.get(j);
				boolean b = info.getCardAdmin().equals(info2.getCardAdmin());
				boolean c = info.getBankName().equals(info2.getBankName());
				boolean d = info.getBankCardNum()
						.equals(info2.getBankCardNum());
				if (b && !bankList.contains(info2))// 重写了eauqls方法
				{
					bankList.add(info2);
				}
				if (b && c && !d)// 同一个人的同一个银行不同的卡
				{
					cardList.add(info2.getBankCardNum());
				}
			}
			boolean e = nameForBankMap.containsKey(info.getCardAdmin());
			boolean f = bankForCardMap.containsKey(info.getBankName()
					+ info.getCardAdmin());
			if (!f) {
				bankForCardMap.put(info.getBankName() + info.getCardAdmin(),
						cardList);
			}
			if (!e) {
				nameForBankMap.put(info.getCardAdmin(), bankList);
			}
		}
	}

	private String checkTel() {
		for (int i = 0, length = personinfoDataList.size(); i < length; i++) {
			PersonInfo info = personinfoDataList.get(i);
			if (info.getBankName().equals(bankName)
					&& info.getCardAdmin().equals(name)
					&& info.getBankCardNum().equals(cardNum)) {
				return info.getTel();
			}
		}
		return null;
	}
}
