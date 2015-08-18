package com.chehui.maiche.utils;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.conn.util.InetAddressUtils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.format.Formatter;

public class Utils {
	private static final String EMPTY_IP_ADDRESS = "0.0.0.0";

	private static final int INT_LENGTH = 9;

	/****
	 * 
	 * 移动: 2G号段(GSM网络)有139,138,137,136,135,134(0-8),159,158,152,151,150
	 * 3G号段(TD-SCDMA网络)有157,188,187 147是移动TD上网卡专用号段. 联通:
	 * 2G号段(GSM网络)有130,131,132,155,156 3G号段(WCDMA网络)有186,185 电信:
	 * 2G号段(CDMA网络)有133,153 3G号段(CDMA网络)有189,180
	 * 
	 */

	/***
	 * 手机号码验证（严格）
	 * 
	 * @param mobiles
	 * @return
	 */
	public static boolean isMobileNO(String mobiles) {
		Pattern p = Pattern.compile("^((13[0-9])|(15[0-9])|(18[0-9]))\\d{8}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}

	/**
	 * md5加密 返回32位加密的结果
	 * 
	 * @param src
	 * @return
	 * @see [类�?�类#方法、类#成员]
	 */
	public static String md5Encode(String src) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		try {
			md.update(src.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		byte b[] = md.digest();
		int i;
		StringBuffer buf = new StringBuffer("");
		for (int offset = 0; offset < b.length; offset++) {
			i = b[offset];
			if (i < 0)
				i += 256;
			if (i < 16)
				buf.append("0");
			buf.append(Integer.toHexString(i));
		}
		return buf.toString();
	}

	/**
	 * �?查网络连�?
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isNetworkAvailable(Context context) {
		NetworkInfo info = getNetworkInfo(context);
		return (info != null && info.isConnected());
	}

	/**
	 * 
	 * @param context
	 * @return
	 */
	public static NetworkInfo getNetworkInfo(Context context) {
		if (null == context) {
			return null;
		}

		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = cm.getActiveNetworkInfo();
		return info;
	}

	/**
	 * 检查WiFi
	 * 
	 * @param context
	 * @return
	 */
	public static WifiInfo getWifiInfo(Context context) {
		if (null == context) {
			return null;
		}

		WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		WifiInfo wifiInfo = wifiManager.getConnectionInfo();
		return wifiInfo;
	}

	/**
	 * 获得模拟器的IP地址
	 * 
	 * @return String 本机ip地址
	 */
	public static String getLocalIpAddress(Context context) {
		String address = "";

		WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		WifiInfo wifiInfo = wifiManager.getConnectionInfo();
		int ipAddress = wifiInfo.getIpAddress();

		address = Formatter.formatIpAddress(ipAddress);

		if (!StringUtils.isEmpty(address) && address.length() >= EMPTY_IP_ADDRESS.length()
				&& !EMPTY_IP_ADDRESS.equals(address)) {
			LogN.d(Utils.class, "getLocalIpAddress | wifi address :" + address);
			return address;
		}

		NetworkInterface intf = null;
		InetAddress inetAddress = null;
		try {
			Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
			Enumeration<InetAddress> enumIpAddr = null;

			for (; en.hasMoreElements();) {
				intf = en.nextElement();
				enumIpAddr = intf.getInetAddresses();

				for (; enumIpAddr.hasMoreElements();) {
					inetAddress = enumIpAddr.nextElement();
					if (!inetAddress.isLoopbackAddress()
							&& InetAddressUtils.isIPv4Address(inetAddress.getHostAddress())) {
						address = inetAddress.getHostAddress();
						LogN.d(Utils.class, "getLocalIpAddress | local address :" + address);
						return address;
					}
				}
			}
		} catch (SocketException ex) {
			LogN.e(Utils.class, "getLocalIpAddress | error :" + ex.toString());
		}

		return address;
	}

	/**
	 * 字符串转整型
	 * 
	 * @param str
	 * @return
	 */
	public static int parseINT(String str, int defaultInt) {
		int rInt = defaultInt;

		if (StringUtils.isEmpty(str)) {
			return rInt;
		}

		if (INT_LENGTH < str.length()) {
			str = str.substring(str.length() - INT_LENGTH);
		}

		try {
			rInt = Integer.parseInt(str);
		} catch (NumberFormatException e) {
			rInt = defaultInt;
			LogN.e(Utils.class, "parseINT error, str is: " + str);
		}

		return rInt;
	}

	/**
	 * 获取文件大小
	 * 
	 * @param url
	 * @return
	 */
	public static long getFileSize(String url) {
		if (StringUtils.isEmpty(url)) {
			return 0;
		}

		File file = new File(url);

		if (file.isDirectory()) {
			return 0;
		}

		return file.length();
	}

	/**
	 * 
	 * 获取List的长�?
	 * 
	 * @param list
	 * @return list为空指针或�?�空集合，返�?0；否则返回具体长�?
	 */
	public static int sizeOf(List<?> list) {
		if (null == list) {
			return 0;
		}

		return list.size();
	}

	public static String appendStr(String... strings) {
		StringBuffer sb = new StringBuffer();

		if (null != strings && strings.length > 0) {
			for (String str : strings) {
				sb.append(str);
			}
		}

		return sb.toString();
	}

	/**
	 * 把字符串中的特殊字符用xml指定的字符代�?
	 * 
	 * @param str
	 *            �?要转换的字符�?
	 * @return 转换后的字符�?
	 */
	public static String changeStrToXml(Object str) {
		String tempStr = null == str ? "" : str.toString();
		// 转换�?�?&�?&amp;
		tempStr = tempStr.replaceAll("[&]", "&amp;");
		// 转换�?�?<�?&lt;
		tempStr = tempStr.replaceAll("[<]", "&lt;");
		// 转换�?�?>�?&gt;
		tempStr = tempStr.replaceAll("[>]", "&gt;");
		// 转换�?有�?�为&apos;
		tempStr = tempStr.replaceAll("[']", "&apos;");
		// 转换�?�?"�?&quot;
		tempStr = tempStr.replaceAll("[\"]", "&quot;");
		// 转换�?�?(�?&#40;
		tempStr = tempStr.replaceAll("[(]", "&#40;");
		// 转换�?�?)�?&#41;
		tempStr = tempStr.replaceAll("[)]", "&#41;");
		// 转换�?�?%�?&#37;
		tempStr = tempStr.replaceAll("[%]", "&#37;");
		// 转换�?�?+�?&#43;
		tempStr = tempStr.replaceAll("[+]", "&#43;");
		// 转换�?�?-�?&#45;
		tempStr = tempStr.replaceAll("[-]", "&#45;");

		return tempStr;
	}

	/**
	 * 把xml中的的特殊字符用转换回原始数�?
	 * 
	 * @param xmlStr
	 *            �?要转换的字符�?
	 * @return 转换后的字符�?
	 */
	public static String changeXmlToStr(String xmlStr) {
		if (StringUtils.isEmpty(xmlStr)) {
			return "";
		}

		// 转换�?�?&amp;�?&
		xmlStr = xmlStr.replaceAll("&amp;", "&");
		// 转换�?�?&lt;�?<
		xmlStr = xmlStr.replaceAll("&lt;", "<");
		// 转换�?�?&gt;�?>
		xmlStr = xmlStr.replaceAll("&gt;", ">");
		// 转换�?�?&apos;为�??
		xmlStr = xmlStr.replaceAll("&apos;", "'");
		// 转换�?�?&quot;�?"
		xmlStr = xmlStr.replaceAll("&quot;", "\"");
		// 转换�?�?&#40;�?(
		xmlStr = xmlStr.replaceAll("&#40;", "(");
		// 转换�?�?&#41;�?)
		xmlStr = xmlStr.replaceAll("&#41;", ")");
		// 转换�?�?&#37;�?%
		xmlStr = xmlStr.replaceAll("&#37;", "%");
		// 转换�?�?&#43;�?+
		xmlStr = xmlStr.replaceAll("&#43;", "+");
		// 转换�?�?&#45;�?-
		xmlStr = xmlStr.replaceAll("&#45;", "-");

		return xmlStr;
	}

	/**
	 * 
	 * 统一处理IO的关闭操�?
	 * 
	 * @param c
	 */
	public static void closeIO(Closeable c) {
		if (null == c) {
			return;
		}

		try {
			c.close();
		} catch (IOException e) {
			LogN.e(Utils.class, e.getMessage());
		}
	}

	public static <T> T getCastObject(Class<T> c, Object obj) {
		if (null == obj || null == c) {
			LogN.e(Utils.class, "getBody error param is null");
			return null;
		}

		T body = null;

		if (c.equals(obj.getClass())) {
			try {
				body = c.cast(obj);
			} catch (ClassCastException e) {
				LogN.e(Utils.class, "getBody ClassCastException: " + e.getMessage());
			}
		}

		return body;
	}

	/**
	 * autolistview
	 * 
	 * @param format
	 * @return
	 */
	public static String getCurrentTime(String format) {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
		String currentTime = sdf.format(date);
		return currentTime;
	}

	/**
	 * autolistview
	 * 
	 * @return
	 */
	public static String getCurrentTime() {
		return getCurrentTime("yyyy-MM-dd  HH:mm:ss");
	}
}
