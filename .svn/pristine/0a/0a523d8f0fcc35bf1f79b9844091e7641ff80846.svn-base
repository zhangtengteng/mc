package com.chehui.maiche.httpserve;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

public class HttpService {

	/**
	 * 通过POST请求的方式请求服务器、然后返回请求的结果
	 * 
	 * @param URL
	 * @param parameters
	 * @return
	 */
	public static String methodPost(String URL,
			List<BasicNameValuePair> parameters) {

		//
		HttpClient client = new DefaultHttpClient();
		HttpPost request = new HttpPost(URL);
		//
		UrlEncodedFormEntity entity = null;
		try {
			// 把参数作为一个表单提交
			entity = new UrlEncodedFormEntity(parameters, HTTP.UTF_8);

			request.setEntity(entity);

			// 执行请求
			HttpResponse response = client.execute(request);
			// 判断是否完全返回 200
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				// 把实体转换为字符串
				String content = EntityUtils.toString(response.getEntity());
				return content;
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();

		} catch (ClientProtocolException e) {
			e.printStackTrace();

		} catch (IOException e) {
			e.printStackTrace();

		}
		return null;
	}

	/**
	 * 通过GET请求网络返回数据
	 * 
	 * @param URL
	 * @return
	 */
	public static String methodGet(String URL) {

		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet(URL);

		try {
			// 执行请求
			HttpResponse response = client.execute(request);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				//
				String content = EntityUtils.toString(response.getEntity());
				return content;
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();

		} catch (IOException e) {
			e.printStackTrace();

		}
		return null;
	}

}
