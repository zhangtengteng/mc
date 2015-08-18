package com.chehui.maiche.comm;

public interface CommErrorCode {
	/**
	 * 通用成功
	 */
	int SUCCESS = 0;

	/**
	 * 通用失败
	 */
	int FAILED = -1;

	/**
	 * 帐号在其他地方登录，导致本次回话为空
	 */
	int BPC_SESSION_NULL = 601;

	/**
	 * 文件下载错误码
	 */
	int DOWNLOAD_ERR_NO_SDCARD = 7200;

	int DOWNLOAD_ERR_FILE_NOT_FOUND = 7201;

	int DOWNLOAD_ERR_LINK_URL_FAIL = 7203;

	int DOWNLOAD_ERR_DOWNLOAD_FILE_FAIL = 7204;

	int DOWNLOAD_ERR_DOWNLOAD_FILE_CACELED = 7205;

}
