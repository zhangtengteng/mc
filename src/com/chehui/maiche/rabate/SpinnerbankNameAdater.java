package com.chehui.maiche.rabate;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chehui.maiche.R;
/**
 * 开户行适配器
 * @author gqy
 *
 */
public class SpinnerbankNameAdater extends MyBaseAdapter<PersonInfo> {

	public SpinnerbankNameAdater(List<PersonInfo> list, Context context) {
		super(list, context);
	}

	private MyViewHolder holder;

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.spinner_name, parent, false);
			holder = new MyViewHolder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (MyViewHolder) convertView.getTag();
		}
		holder.tv.setText(list.get(position).getBankName());
		return convertView;
	}

	private class MyViewHolder {
		private TextView tv;

		public MyViewHolder(View view) {
			tv = (TextView) view.findViewById(R.id.spinnernametextview);
		}
	}
}
