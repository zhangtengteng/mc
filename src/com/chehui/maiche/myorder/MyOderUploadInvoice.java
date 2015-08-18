package com.chehui.maiche.myorder;

import java.io.File;
import java.io.IOException;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

import com.chehui.maiche.BaseActivity;
import com.chehui.maiche.MainActivity;
import com.chehui.maiche.R;
import com.chehui.maiche.setup.SetupSelectPicPop;
import com.chehui.maiche.utils.BitmapUtils;
import com.chehui.maiche.utils.ToastUtils;
/**
 * 上传发票
 * @author gqy
 *
 */
public class MyOderUploadInvoice extends BaseActivity {

	private static final int PHOTO_REQUEST_CAMERA = 1;// 拍照
	private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
	private static final int PHOTO_REQUEST_CUT = 3;// 结果
	
	/* 图片名称 */
	private static final String PHOTO_FILE_NAME = "invoice_photo.jpg";
	
	private ImageView upload_invoice_pic;
	private Button upload_invoice_select;
	private SetupSelectPicPop mPopView;
	private int d;
	
	private File tempFile;
	private String fileBytes;
	private Bitmap bitmap;
	private Button upload_commit_invoice;
	private String DDBH = "MCT20158124234547";
	@Override 
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_upload_invoice);
		
		initTitleView(-1, 255, R.string.upload_invoice_txt, 255, -1, 0);
		upload_invoice_pic = (ImageView) findViewById(R.id.upload_invoice_pic);
		upload_invoice_select = (Button) findViewById(R.id.upload_invoice_select);
		upload_commit_invoice = (Button) findViewById(R.id.upload_commit_invoice);
		upload_invoice_select.setVisibility(View.VISIBLE);
		upload_commit_invoice.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if(fileBytes==null||fileBytes.equals("")){
					ToastUtils.showShortToast(getApplicationContext(), "请选择要上传的图片！");
					return;
				}
				uploadImg();
			}
		});
		upload_invoice_select.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// 实例化SetupSelectPicPop
				mPopView = new SetupSelectPicPop(MyOderUploadInvoice.this,
						itemsOnClick);
				// 设置layout在PopupWindow中显示的位置 显示窗口
				mPopView.showAtLocation(
						MyOderUploadInvoice.this.findViewById(R.id.layout),
						Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
			}
		});
	}
	
	/**
	 * 为弹出窗口实现监听类
	 */
	private OnClickListener itemsOnClick = new OnClickListener() {

		public void onClick(View v) {

			switch (v.getId()) {
			// 点击拍照
			case R.id.btn_take_photo:
				Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
				// 判断存储卡是否可以用，可用进行存储
				if (hasSdcard()) {
					intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri
							.fromFile(new File(Environment
									.getExternalStorageDirectory(),
									PHOTO_FILE_NAME)));
				}
				startActivityForResult(intent, PHOTO_REQUEST_CAMERA);
				mPopView.dismiss();
				break;
			// 点击从相册中获取
			case R.id.btn_pick_photo:
				// 激活系统图库，选择一张图片
				Intent intent_pick = new Intent(Intent.ACTION_PICK);
				intent_pick.setType("image/*");
				startActivityForResult(intent_pick, PHOTO_REQUEST_GALLERY);
				mPopView.dismiss();
				break;
			default:
				break;
			}
		}
	};
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == PHOTO_REQUEST_GALLERY) {
			if (data != null) {
				// 得到图片的全路径
				Uri uri = data.getData();
				tempFile = new File(getRealPathFromURI(uri));
				d = 1;
				crop(uri);
			}

		} else if (requestCode == PHOTO_REQUEST_CAMERA) {
			if (hasSdcard()) {
				tempFile = new File(Environment.getExternalStorageDirectory(),
						PHOTO_FILE_NAME);
				Uri uri = Uri.fromFile(tempFile);
				d = 2;
				crop(uri);
			} else {
				ToastUtils.showShortToast(getApplicationContext(),
						"未找到存储卡,无法存储图片！");
			}

		} else if (requestCode == PHOTO_REQUEST_CUT) {
			try {
				if (d == 1)
					bitmap = data.getParcelableExtra("data");
				else {
					Bundle bundle = data.getExtras();
					if (bundle != null) {
						bitmap = (Bitmap) bundle.get("data");
					}
				}
					this.upload_invoice_pic.setImageBitmap(bitmap);
					upload_invoice_select.setVisibility(View.GONE);
					fileBytes = BitmapUtils.bitmapToBase64(bitmap);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	/**
	 * 
	 * @param uri
	 */
	private void crop(Uri uri) {
		// 裁剪图片意图
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");
		// 裁剪框的比例，1：1
		intent.putExtra("aspectX", 3);
		intent.putExtra("aspectY", 2);
		// 裁剪后输出图片的尺寸大小
		intent.putExtra("outputX", 240);
		intent.putExtra("outputY", 180);
		// 图片格式
		intent.putExtra("outputFormat", "JPEG");
		intent.putExtra("noFaceDetection", true);
		intent.putExtra("return-data", true);// true:不返回uri，false：返回uri
		startActivityForResult(intent, PHOTO_REQUEST_CUT);
	}


	/**
	 * 转换成file
	 * 
	 * @param contentUri
	 * @return
	 */
	public String getRealPathFromURI(Uri contentUri) {
		String[] proj = { MediaStore.Images.Media.DATA };

		@SuppressWarnings("deprecation")
		Cursor actualimagecursor = managedQuery(contentUri, proj, null, null,
				null);

		int actual_image_column_index = actualimagecursor
				.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

		actualimagecursor.moveToFirst();

		String img_path = actualimagecursor
				.getString(actual_image_column_index);
		return img_path;
	}
	/**
	 * 判断SD卡是否存在
	 * 
	 * @return
	 */
	private boolean hasSdcard() {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			return false;
		}
	}
	

	public void uploadImg() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				// 命名空间
				String nameSpace = "http://SellerOperationService.maichetong.chehui/";
				// 调用方法的名称
				String methodName = "UploadImg";
				// EndPoint
				String endPoint = "http://ws.maichetong.chehui.com/SellerOperationService.asmx";
				// SOAP Action
				String soapAction = "http://SellerOperationService.maichetong.chehui/UploadImg";
				// 指定WebService的命名空间和调用方法
				SoapObject soapObject = new SoapObject(nameSpace, methodName);
			    soapObject.addProperty("fileBytes", fileBytes);
				soapObject.addProperty("DDBH",DDBH);
				// 生成调用WebService方法调用的soap信息，并且指定Soap版本
				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
						SoapEnvelope.VER10);
				envelope.bodyOut = soapObject;
				// 是否调用DotNet开发的WebService
				envelope.dotNet = true;
				envelope.setOutputSoapObject(soapObject);
				HttpTransportSE transport = new HttpTransportSE(endPoint);
				try {
					transport.call(soapAction, envelope);
				} catch (IOException e) {
					e.printStackTrace();
				} catch (XmlPullParserException e) {
					e.printStackTrace();
				}
				// 获取返回的数据
				SoapObject object = (SoapObject) envelope.bodyIn;
				// 获取返回的结果
				String result = object.getProperty(0).toString();
				System.out.println("==============================="+result);
				if (result.indexOf("true") !=-1) {
					startActivity(new Intent(MyOderUploadInvoice.this,MyOrderInvoiceSuccess.class));
				 } else {
					  
				}
			}
		}).start();
	}
}

