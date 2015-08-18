package com.chehui.maiche.update;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;

import com.chehui.maiche.R;
import com.chehui.maiche.comm.CommonData;
import com.chehui.maiche.httpserve.HttpService;
import com.chehui.maiche.utils.StringUtils;
import com.chehui.maiche.utils.ToastUtils;

/**
 * 版本更新管理类
 * 
 * @author gqy
 * 
 */
public class UpdateManager {
	/** 下载 */
	private static final int DOWNLOAD = 1;
	/** 下载完成 */
	private static final int DOWNLOAD_FINISH = 2;
	/** 保存路径 */
	private String mSavePath;
	/** 下载进度 */
	private int progress;
	/** 是否点击取消下载 */
	private boolean cancelUpdate = false;

	private Context mContext;
	/** 下载进度条 */
	private ProgressBar mProgress;
	/** 是否下载提示框 */
	private Dialog mDownloadDialog;
	/** 当前版本 号 */
	private String currentCode;
	/** 服务器版本号 */
	private String serverCode;
	/** 项目包名 */
	private String packName = "com.chehui.maiche";
	
	/** 新版本包下载地址 */
	private String url_downLoadPack = "";
	private String url_downLoadPack_real = "";

	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case DOWNLOAD:
				mProgress.setProgress(progress);
				break;
			case DOWNLOAD_FINISH:
				installApk();
				break;
			default:
				break;
			}
		};
	};

	public UpdateManager(Context context) {
		this.mContext = context;
	}

	/**
	 * 判断是否更新
	 * 
	 * @return
	 * @throws NameNotFoundException
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	public void isupdate() throws NameNotFoundException {

		// 获取当前项目的版本号
		currentCode = mContext.getPackageManager().getPackageInfo(packName, 0).versionName;
		// 执行post请求，获取到服务器的版本号serverCode
		new AsyncTask<Void, Integer, String>() {

			@Override
			protected String doInBackground(Void... params) {
				// 请求服务器
				final List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
				parameters.add(new BasicNameValuePair("classname",
						"BaseInfoService"));
				parameters.add(new BasicNameValuePair("methodname",
						"GetCurrentServerVersion"));
				parameters.add(new BasicNameValuePair("params",
						"android"));
				String response = HttpService.methodPost(CommonData.HTTP_URL,
						parameters);
				return response;
			}

			@Override
			protected void onPostExecute(String result) {
				if (StringUtils.isEmpty(result))
					return;
				else{
					serverCode = result.toString().substring(1,
							result.toString().length() - 1);
					if (!serverCode.equals(currentCode)) {
						showNoticeDialog();
					} else {
						ToastUtils.showLongToast(mContext,
								R.string.soft_update_no);
					}
				}
			}
		}.execute();
	}

	/**
	 * 显示提示更新的dialog
	 */
	private void showNoticeDialog() {
		AlertDialog.Builder builder = new Builder(mContext);
		builder.setTitle(R.string.soft_update_title);
		builder.setMessage(R.string.soft_update_info);
		builder.setPositiveButton(R.string.soft_update_updatebtn,
				new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						new AsyncTask<Void, Integer, String>() {

							@Override
							protected String doInBackground(Void... params) {  
								final List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
								parameters.add(new BasicNameValuePair(
										"classname", "BaseInfoService"));
								parameters.add(new BasicNameValuePair(
										"methodname", "GetLoadCurrentPack"));
								parameters.add(new BasicNameValuePair("params",
										"android"));
								String response = HttpService.methodPost(
										CommonData.HTTP_URL, parameters);
								return response;
							}

							protected void onPostExecute(String result) {

								if (!result.toString().equals("")) {
									url_downLoadPack = result.toString().trim();
									url_downLoadPack_real = url_downLoadPack.substring(1, url_downLoadPack.length()-1);
									System.out.println("url_downLoadPack_real="
											+ url_downLoadPack_real);
									showDownloadDialog();
								}
							};
						}.execute();
					}
				});
		builder.setNegativeButton(R.string.soft_update_later,
				new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
		Dialog noticeDialog = builder.create();
		noticeDialog.show();
	}

	/**
	 * 显示下载进度条
	 */
	private void showDownloadDialog() {
		AlertDialog.Builder builder = new Builder(mContext);
		builder.setTitle(R.string.soft_updating);
		final LayoutInflater inflater = LayoutInflater.from(mContext);
		View v = inflater.inflate(R.layout.dialog_softupdate_progress, null);
		mProgress = (ProgressBar) v.findViewById(R.id.update_progress);
		builder.setView(v);
		builder.setNegativeButton(R.string.soft_update_cancel,
				new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						cancelUpdate = true;
					}
				});
		mDownloadDialog = builder.create();
		mDownloadDialog.show();
		new downloadApkThread().start();
	}

	/**
	 * 从服务器上下载新包
	 * 
	 * @author gqy
	 * 
	 */

	private class downloadApkThread extends Thread {
		@Override
		public void run() {
			try {
				if (Environment.getExternalStorageState().equals(
						Environment.MEDIA_MOUNTED)) {
					String sdpath = Environment.getExternalStorageDirectory()
							+ "/";
					mSavePath = sdpath + "download";
					// url需要访问接口获取
					URL url = new URL(url_downLoadPack_real);
					HttpURLConnection conn = (HttpURLConnection) url
							.openConnection();
					conn.connect();
					int length = conn.getContentLength();
					InputStream is = conn.getInputStream();

					File file = new File(mSavePath);
					if (!file.exists()) {
						file.mkdir();
					}
					File apkFile = new File(mSavePath, "chehuitong.apk");
					FileOutputStream fos = new FileOutputStream(apkFile);
					int count = 0;
					byte buf[] = new byte[1024];
					do {
						int numread = is.read(buf);
						count += numread;
						progress = (int) (((float) count / length) * 100);
						mHandler.sendEmptyMessage(DOWNLOAD);
						if (numread <= 0) {
							mHandler.sendEmptyMessage(DOWNLOAD_FINISH);
							break;
						}
						fos.write(buf, 0, numread);
					} while (!cancelUpdate);
					fos.close();
					is.close();
				}
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			mDownloadDialog.dismiss();
		}
	};

	/**
	 * 安装APK包
	 */
	private void installApk() {
		File apkfile = new File(mSavePath, "chehuitong.apk");
		if (!apkfile.exists()) {
			return;
		}
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setDataAndType(Uri.parse("file://" + apkfile.toString()),
				"application/vnd.android.package-archive");
		mContext.startActivity(i);
	}
}
