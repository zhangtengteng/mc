package com.chehui.maiche.myorder;

import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.chehui.maiche.R;
import com.chehui.maiche.enquiry.DownImage;
import com.chehui.maiche.enquiry.DownImage.ImageCallBack;

/**
 * 订单列表适配器
 */
public class MyOrderListAdapter extends BaseAdapter {

	/** 异步加载图片的URL */
	private static final String url = "http://upload.chehui.com/seriesface/";
	/** 在绑定的数据 */
	private List<Map<String, String>> listTOrder;
	private LayoutInflater inflater;

	@SuppressWarnings("unused")
	private Context context;
	public MyOrderListAdapter(Context context,
			List<Map<String, String>> listTOder) {
		super();
		this.context = context;
		this.listTOrder = listTOder;
		this.inflater = LayoutInflater.from(context);

	}

	public void setData(List<Map<String, String>> listTOder) {
		this.listTOrder = listTOder;
		this.notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return listTOrder.size();
	}

	@Override
	public Object getItem(int position) {
		return listTOrder.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressLint("ResourceAsColor") @Override
	public View getView(int position, View convertView, ViewGroup parent) {

		final ImageView img_CarImage;
		TextView txt_CarDetail = null;
		TextView txt_CreateDateCN = null;
		TextView txt_FloorPriceCN = null;
		View line_top = null;
		View line_bottom = null;
		View line_last = null;
//		if (convertView == null) {
			// 生成条目对象
		if(listTOrder.get(position).get("OrderState").equals("1")){
			convertView = inflater.inflate(R.layout.fragment_my_order_item_gray,
					null);
		}else{
			convertView = inflater.inflate(R.layout.fragment_my_order_item,
					null);
		}
			img_CarImage = (ImageView) convertView
					.findViewById(R.id.myOrderListItem_img_carImage);

			txt_CarDetail = (TextView) convertView
					.findViewById(R.id.myOrderListItem_txt_CarDetail);

			txt_CreateDateCN = (TextView) convertView
					.findViewById(R.id.myOrderListItem_txt_CreateDateCN);

			txt_FloorPriceCN = (TextView) convertView
					.findViewById(R.id.myOrderListItem_txt_FloorPriceCN);
			line_top = convertView.findViewById(R.id.line_top);
			line_bottom = convertView.findViewById(R.id.line_below);
			line_last = convertView.findViewById(R.id.line_last);

//			ViewCahe viewCahe = new ViewCahe();
//			viewCahe.CarImage = img_CarImage;
//			viewCahe.CarDetail = txt_CarDetail;
//			viewCahe.CreateDateCN = txt_CreateDateCN;
//			viewCahe.FloorPriceCN = txt_FloorPriceCN;
//			viewCahe.line_top = line_top;
//			viewCahe.line_bottom = line_bottom;
//			viewCahe.line_last = line_last;
			
//			convertView.setTag(viewCahe);
//		} else {
//			ViewCahe viewCahe = (ViewCahe) convertView.getTag();
//			img_CarImage = viewCahe.CarImage;
//			txt_CarDetail = viewCahe.CarDetail;
//			txt_CreateDateCN = viewCahe.CreateDateCN;
//			txt_FloorPriceCN = viewCahe.FloorPriceCN;
//			line_top = viewCahe.line_top;
//			line_bottom = viewCahe.line_bottom;
//			line_last = viewCahe.line_last;
//
//		}

		// 如果是第一个条目
		if (position == 0) {
			line_top.setVisibility(View.VISIBLE);
		}
		// 如果是最后一个条目
		if (position == listTOrder.size() - 1) {
			line_bottom.setVisibility(View.GONE);
			line_last.setVisibility(View.VISIBLE);
		}
		
		
		// 绑定数据
		DownImage downImage = new DownImage(url
				+ listTOrder.get(position).get("CarImage"));
		downImage.loadImage(new ImageCallBack() {

			@Override
			public void getDrawable(Drawable drawable) {
				img_CarImage.setImageDrawable(drawable);
			}
		});

		txt_CarDetail.setText(listTOrder.get(position).get("CarDetail"));
		txt_CreateDateCN.setText(listTOrder.get(position).get("CreateDateCN"));
		txt_FloorPriceCN.setText(listTOrder.get(position).get("FloorPriceCN"));
		
		return convertView;
	}

	private final class ViewCahe { 
		public ImageView CarImage;
		public TextView CarDetail;
		public TextView CreateDateCN;
		public TextView FloorPriceCN;
		private View line_top, line_bottom, line_last;

	}
}
