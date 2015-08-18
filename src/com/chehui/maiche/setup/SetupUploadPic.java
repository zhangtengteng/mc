package com.chehui.maiche.setup;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.jinouts.xml.bind.DatatypeConverter;
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
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

import com.chehui.maiche.BaseActivity;
import com.chehui.maiche.R;
import com.chehui.maiche.SharedPreManager;
import com.chehui.maiche.comm.CommonData;
import com.chehui.maiche.utils.BitmapUtils;
import com.chehui.maiche.utils.LogN;
import com.chehui.maiche.utils.ToastUtils;

/**
 * 上传身份证照片实名认证
 * 
 * @author gqy
 */
public class SetupUploadPic extends BaseActivity {
	private Button identify_select, business_select;
	private Button btn_uploadpic;

	private static final int PHOTO_REQUEST_CAMERA = 1;// 拍照
	private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
	private static final int PHOTO_REQUEST_CUT = 3;// 结果

	private SetupSelectPicPop mPopView;
	private int i = 0;

	private ImageView identify_pic, business_pic;
	private Bitmap bitmap;

	private int d;
	/* 接口 */
	private static final String NAMESPACE = "http://ws.maichetong.chehui.com/";
	private static final String METHODNAME = "UploadImg";
	private static final String SOAPACTION = "http://BaseInfoService.maichetong.chehui/UploadImg";
	private static final String endPoint = "http://ws.maichetong.chehui.com/BaseInfoService.asmx";
	/* 用户姓名和身份证号码 */
	private String userName;
	private String SFZ_number;
	private String sellerid;

	/* 图片名称 */
	private static final String PHOTO_FILE_NAME = "temp_photo";
	private File tempFile;
	private String fileBytes1;
	private String fileBytes2;
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				ToastUtils.showShortToast(getApplicationContext(), "第一张上传成功");
				uploadImg(fileBytes2, 2);
				break;
			case 2:
				ToastUtils.showShortToast(getApplicationContext(), "第二张上传成功");
				startActivity(new Intent(SetupUploadPic.this,
						SetupUploadSuccess.class));
				SetupUploadPic.this.finish();
				break;
			case CommonData.HTTP_HANDLE_FAILE:
				ToastUtils.showShortToast(SetupUploadPic.this, R.string.error);
				break;
			default:
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup_upload_pic);
		initTitleView(-1, 255, R.string.set_account_uploadpic, 255, -1, 0);
		init();
	}

	/**
	 * 界面初始化
	 */
	private void init() {
		identify_select = (Button) findViewById(R.id.identify_select);
		business_select = (Button) findViewById(R.id.business_select);
		identify_select.setVisibility(View.VISIBLE);
		business_select.setVisibility(View.VISIBLE);
		btn_uploadpic = (Button) findViewById(R.id.upload);
		identify_pic = (ImageView) findViewById(R.id.identify_pic);
		business_pic = (ImageView) findViewById(R.id.business_pic);
		userName = getIntent().getStringExtra("USERNAME");
		SFZ_number = getIntent().getStringExtra("IDETIFY");
		sellerid = SharedPreManager.getInstance().getInt(CommonData.USER_ID, 1)
				+ "";
		identify_select.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 实例化SetupSelectPicPop
				mPopView = new SetupSelectPicPop(SetupUploadPic.this,
						itemsOnClick);
				i = 1;
				// 设置layout在PopupWindow中显示的位置 显示窗口
				mPopView.showAtLocation(
						SetupUploadPic.this.findViewById(R.id.layout),
						Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
			}
		});
		business_select.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 实例化SetupSelectPicPop
				mPopView = new SetupSelectPicPop(SetupUploadPic.this,
						itemsOnClick);
				i = 2;
				// 设置layout在PopupWindow中显示的位置 显示窗口
				mPopView.showAtLocation(
						SetupUploadPic.this.findViewById(R.id.layout),
						Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);

			}
		});
		btn_uploadpic.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				if(fileBytes1==null||fileBytes2==null){
					ToastUtils.showShortToast(getApplicationContext(), "请选择要上传的图片！");
					return;
				}
				if(fileBytes1.equals("")||fileBytes2.equals("")){
					ToastUtils.showShortToast(getApplicationContext(), "请选择要上传的图片！");
					return;
				}
				uploadImg(fileBytes1, 1);
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
									PHOTO_FILE_NAME + i + ".jpg")));
				}
				startActivityForResult(intent, PHOTO_REQUEST_CAMERA);
				mPopView.dismiss();
				break;
			// 点击从相册中获取
			case R.id.btn_pick_photo:
				// 激活系统图库，选择一张图片
				System.out.println("i1==" + i);
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
						PHOTO_FILE_NAME + i + ".jpg");
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
				String path = PHOTO_FILE_NAME + i + ".jpg";
				if (i == 1) {
					this.identify_pic.setImageBitmap(bitmap);
					identify_select.setVisibility(View.GONE);
					fileBytes1 = BitmapUtils.bitmapToBase64(bitmap);
				} else if (i == 2) {
					this.business_pic.setImageBitmap(bitmap);
					business_select.setVisibility(View.GONE);
					fileBytes2 = BitmapUtils.bitmapToBase64(bitmap);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	/**
	 * 剪切图片
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


	/**
	 * 转换成file
	 * 
	 * @param contentUri
	 * @return
	 */
	public String getRealPathFromURI(Uri contentUri) {
		String[] proj = { MediaStore.Images.Media.DATA };

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
	 * file转换成byte64
	 * 
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public static String encodeBase64File(File file) throws Exception {
		FileInputStream inputFile = new FileInputStream(file);
		byte[] buffer = new byte[(int) file.length()];
		inputFile.read(buffer);
		inputFile.close();
		// return Base64.encodeToString(buffer, Base64.DEFAULT);
		return DatatypeConverter.printBase64Binary(Base64.encodeToString(
				buffer, Base64.DEFAULT).getBytes());
	}

	public void uploadImg(final String filePath, final int i) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				// 命名空间
				String nameSpace = "http://BaseInfoService.maichetong.chehui/";
				// 调用方法的名称
				String methodName = "UploadImg";
				// EndPoint
				String endPoint = "http://ws.maichetong.chehui.com/BaseInfoService.asmx";
				// SOAP Action
				String soapAction = "http://BaseInfoService.maichetong.chehui/UploadImg";
				// 指定WebService的命名空间和调用方法
				SoapObject soapObject = new SoapObject(nameSpace, methodName);
				// 设置需要调用WebService接口的两个参数mobileCode UserId
				int id = SharedPreManager.getInstance().getInt(
						CommonData.USER_ID, -1);
				// String fileBytes =
				// BitmapUtils.bitmapToBase64(BitmapFactory.decodeFile(filePath));
				if (i == 1) {
					soapObject.addProperty("fileBytes", fileBytes1);
				} else{
					soapObject.addProperty("fileBytes", fileBytes2);
				}
				if(fileBytes1.equals(fileBytes2)){
					LogN.e(SetupUploadPic.class, "fileBytes1 == fileBytes2");
				}else{
					LogN.e(SetupUploadPic.class, "fileBytes1 != fileBytes2");
				}
				String valueOf = String.valueOf(i);
				LogN.e(SetupUploadPic.class, "valueOf is "+valueOf);
				soapObject.addProperty("sellerid", id);
				soapObject.addProperty("surfix", "JPEG");
				soapObject.addProperty("img_index",valueOf );
				soapObject.addProperty("SFZ", SFZ_number);
				soapObject.addProperty("RealName", userName);
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
				System.out.println("envelope.bodyIn-------------"
						+ envelope.bodyIn);
				// 获取返回的数据
				SoapObject object = (SoapObject) envelope.bodyIn;
				// 获取返回的结果
				String result = object.getProperty(0).toString();
				System.out.println("result ----------" + result);
				Message message = handler.obtainMessage();
				message.obj = result;
				if (result.indexOf("true") > 0) {
					message.what = i;
				} else {
					message.what = CommonData.HTTP_HANDLE_FAILE;
				}
				handler.sendMessage(message);
			}
		}).start();
	}

}
