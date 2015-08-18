package com.chehui.maiche.utils;

import java.util.Locale;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import android.util.Base64;

/**
 * 
 * @author zzp
 * 
 *         加密工具
 * 
 */
public class DASASEEncryptUtil {
	private static final String ALGORITHM = "AES";

	private static final String TRANSFORMATION = "AES/ECB/NoPadding";

	private static final String SECRETKEY = "BBCam@2014";

	private static final String CHARACTERENCODING = "ISO-8859-1";

	private static final String ASCII_STYLE = "base64";// "base64","hex"

	private static final int KEYLEN = 16;

	private static final int NUMBER_TWO = 2;

	private static final int NUMBER_FOUR = 4;

	/**
	 * 
	 * @param decryptedText
	 * @return
	 */
	public static String encrypt(String decryptedText) {
		// 如果密文为空，则不加密
		if (null == decryptedText || "".equals(decryptedText)) {
			return "";
		}

		return encryptAES(decryptedText, SECRETKEY);
	}

	/**
	 * DAS解密
	 * 
	 * @param encryptedText
	 *            encryptedText
	 * @return [参数说明]
	 * 
	 * @see [类�?�类#方法、类#成员]
	 */
	public static String decrypt(String encryptedText) {
		// 如果为空，则不解�?
		if (null == encryptedText || "".equals(encryptedText)) {
			return "";
		}

		return decryptAES(encryptedText, SECRETKEY);
	}

	/**
	 * 敏感数据加密
	 * 
	 * @param decryptedText
	 *            明文密码
	 * @param key
	 *            密钥
	 * @return
	 */
	public static String encrypt(String decryptedText, String key) {
		// 如果密文为空，则不加�?
		if (null == decryptedText || "".equals(decryptedText)) {
			return "";
		}

		return encryptAES(decryptedText, key);
	}

	/**
	 * 敏感数据解密
	 * 
	 * @param encryptedText
	 *            密文密码
	 * @param key
	 *            密钥
	 * @return
	 */
	public static String decrypt(String encryptedText, String key) {
		// 如果为空，则不解�?
		if (null == encryptedText || "".equals(encryptedText)) {
			return "";
		}

		return decryptAES(encryptedText, key);
	}

	/**
	 * AES加密
	 * 
	 * @param decryptedText
	 *            密文
	 * @param secretKey
	 *            密钥
	 * @return
	 */
	private static String encryptAES(String decryptedText, String secretKey) {

		String encryptedText = "";

		try {
			Cipher cipher = Cipher.getInstance(TRANSFORMATION);

			byte[] secretKeyBytes = MD5.compile(secretKey);

			byte[] decryptedBytes = decryptedText.getBytes(CHARACTERENCODING);

			byte[] plainBytes = oneZeroPadding(decryptedBytes);

			SecretKeySpec secretKeySpec = new SecretKeySpec(secretKeyBytes,
					ALGORITHM);

			cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);

			byte encryptedBytes[] = cipher.doFinal(plainBytes);

			if ("base64".equalsIgnoreCase(ASCII_STYLE)) {
				encryptedText = Base64.encodeToString(encryptedBytes,
						Base64.NO_WRAP);
			} else if ("hex".equalsIgnoreCase(ASCII_STYLE)) {
				encryptedText = bytes2HexString(encryptedBytes);
			}

		} catch (Exception e) {
		}

		return encryptedText;
	}

	/**
	 * 
	 * @param srcBytes
	 * @return
	 */
	private static byte[] oneZeroPadding(byte[] srcBytes) {
		int iFinalLen = (srcBytes.length / KEYLEN + 1) * KEYLEN;
		int iPadLen = iFinalLen - srcBytes.length;

		byte[] dstText = new byte[iFinalLen];

		for (int i = 0; i < srcBytes.length; i++) {
			dstText[i] = srcBytes[i];
		}

		for (int i = srcBytes.length; i < iFinalLen; i++) {
			dstText[i] = (byte) iPadLen;
		}

		return dstText;

	}

	/**
	 * 
	 * @param encryptedText
	 * @param secretKey
	 * @return
	 */
	private static String decryptAES(String encryptedText, String secretKey) {
		String decryptedText = "";

		try {
			Cipher cipher = Cipher.getInstance(TRANSFORMATION);

			byte[] secretKeyBytes = MD5.compile(secretKey);

			byte[] encryptedBytes = new byte[0];

			if ("base64".equalsIgnoreCase(ASCII_STYLE)) {
				encryptedBytes = Base64.decode(encryptedText, Base64.NO_WRAP);
			} else if ("hex".equalsIgnoreCase(ASCII_STYLE)) {
				encryptedBytes = hexString2Bytes(encryptedText);
			}

			SecretKeySpec secretKeySpec = new SecretKeySpec(secretKeyBytes,
					ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
			byte decryptedBytes[] = cipher.doFinal(encryptedBytes);

			byte finalBytes[] = oneZeroRTrim(decryptedBytes);

			if (finalBytes != null && 0 < finalBytes.length) {
				decryptedText = new String(finalBytes, CHARACTERENCODING);
			}
		}

		catch (Exception e) {
		}

		return decryptedText;
	}

	/**
	 * 
	 * @param srcBytes
	 * @return
	 * @throws Exception
	 */
	private static byte[] oneZeroRTrim(byte[] srcBytes) throws Exception {
		int count = srcBytes[srcBytes.length - 1] & 0xff;

		byte[] dstBytes = null;

		int iRemainBytes = srcBytes.length - count;

		if (iRemainBytes <= 0) {
			throw new Exception("invalid srcBytes");
		}

		dstBytes = new byte[iRemainBytes];
		for (int j = 0; j < iRemainBytes; j++) {
			dstBytes[j] = srcBytes[j];
		}

		return dstBytes;
	}

	/**
	 * 
	 * @param bytes
	 * @return
	 */
	private static String bytes2HexString(byte[] bytes) {
		StringBuffer hexString = new StringBuffer(0);

		if (bytes != null) {
			for (int bi = 0; bi < bytes.length; bi = bi + 1) {
				/** mod by wangzheng at 20130717 for findbugs begin **/
				// hexString = hexString + (char)((bytes[bi] & 0x0F) + 'A');
				hexString.append((char) ((bytes[bi] & 0x0F) + 'A'));
				// hexString = hexString + (char)(((bytes[bi] >> NUMBER_FOUR) &
				// 0x0F) + 'A');
				hexString
						.append((char) (((bytes[bi] >> NUMBER_FOUR) & 0x0F) + 'A'));
				/** mod by wangzheng at 20130717 for findbugs end **/
			}
		}
		return hexString.toString();
	}

	/**
	 * 
	 * @param hexString
	 * @return
	 */
	private static byte[] hexString2Bytes(String hexString) {
		byte[] dstBytes = null;

		if (hexString != null) {
			String srcHex = hexString;
			if (hexString.length() % NUMBER_TWO != 0) {
				srcHex = "0" + hexString;
			} else {
				srcHex = hexString;
			}

			srcHex = srcHex.toUpperCase(Locale.getDefault());

			int iDstBytes = srcHex.length() / NUMBER_TWO;

			dstBytes = new byte[iDstBytes];

			int j = 0;

			for (int i = 0; i < iDstBytes; i++) {
				char hexChar = srcHex.charAt(j);
				dstBytes[i] = (byte) (hexChar - 'A');
				j++;
				hexChar = srcHex.charAt(j);
				dstBytes[i] += (byte) ((byte) (hexChar - 'A') << NUMBER_FOUR);
				j++;
			}
		}
		return dstBytes;
	}
}