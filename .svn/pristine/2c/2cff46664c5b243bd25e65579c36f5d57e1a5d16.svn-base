package com.chehui.maiche.enquiry;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.chehui.maiche.BaseActivity;
import com.chehui.maiche.R;
import com.chehui.maiche.SharedPreManager;
import com.chehui.maiche.comm.CommonData;

/***
 * 
 * @author zzp
 *         <P>
 *         完成报价
 *         <P>
 * 
 */
public class OrderCompeleted extends BaseActivity {

	private Button btn_compelet;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_detail4);
		TextView txt_chehuifanli = (TextView) findViewById(R.id.orderItemC_txt_chehuifanli);
		TextView txt_huifubaojia = (TextView) findViewById(R.id.orderItemC_txt_huifubaojia);

		txt_huifubaojia.setText("①报价成功您将获取"
				+ SharedPreManager.getInstance().getString(
						CommonData.HUIFUBAOJIA, "") + "元返利!");

		txt_chehuifanli.setText("③若你促使客户成交后，您将获得"
				+ SharedPreManager.getInstance().getString(
						CommonData.CHEHUIFANLI, "") + "元的返利!");

		btn_compelet = (Button) findViewById(R.id.orderItemC_btn_compelet);
		btn_compelet.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 通知抢单报价刷新界面
				CommonData.ISORDERCOMPELETED = true;
				// 通知我的报价刷新
				CommonData.ISMYORDERFRAGMENTREFRESH = true;
				finish();
			}
		});
	}
}
