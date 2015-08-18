package com.chehui.maiche.comm;

import android.support.v4.app.FragmentActivity;
import android.widget.TextView;

/**
 * 常量
 * 
 * @author zzp
 * 
 */
public class CommonData {
	/**
	 * JPush
	 */
	public static final String MESSAGE_RECEIVED_ACTION = "com.chehui.maiche.MESSAGE_RECEIVED_ACTION";
	public static final String KEY_TITLE = "title";
	public static final String KEY_MESSAGE = "message";
	public static final String KEY_EXTRAS = "extras";
	public static boolean isForeground = false;
	/** 判断我的报价界面刷新 */
	public static boolean ISMYORDERFRAGMENTREFRESH;

	/** 修改个人信息 默认值 */
	public static boolean MODIFYUSERFLAG;

	/** 判断退出状态 默认值为false */
	public static boolean LOGINFLAG = false;

	/** 判断是否拥有银行卡默认值false */
	public static boolean ISPOSSESSBANKCARD = false;

	/** 修改报价 */
	public static boolean ISMODIFYPRICE;

	/** 完成报价通知刷新界面 */
	public static boolean ISORDERCOMPELETED;

	/** 判断刷新我的奖金界面 */
	public static boolean ISRABATEFRAGMENTREFRESH;

	/** 返回JSON数据的调用接口 */
	public static final String HTTP_URL = "http://ws.maichetong.chehui.com/JsonHandler.ashx";

	/** 异步图片获取接口 */
	public static final String HTTP_IMG_URL = "http://upload.chehui.com/seriesface/";

	public static final int MESS = 0X01;
	public static String server_ip = "";

	public static String SERVER_ADDRESS = "http://192.168.4.252:8081/";
	public static String IMAGE_ADDRESS = ".scar.com.cn/upload/fuwu/";

	public static final int HTTP_HANDLE_SUCCESS = 0X02;
	public static final int HTTP_HANDLE_FAILE = 0;
	public static final int HTTP_TIME_OUT = 1010;
	public static final int HTTP_VALICODE = 202;

	// 毫秒
	public static final int TIME_OUT = 6000;
	public static boolean TAG;
	//
	public static TextView tvNumber;
	public static FragmentActivity activity;
	public static boolean toAuthon;
	/** 用户名 */
	public static final String USER_NAME = "userName";
	/** 状态值 */
	public static final String USER_STATE = "State";
	/** 用户ID */
	public static final String USER_ID = "userId";
	/** 用户昵称 */
	public static final String NICK_NAME = "nickName";
	/** 用户性别 */
	public static final String USER_SEX = "userSex";
	/** 用户密码 */
	public static final String USER_PWD = "userPwd";
	/** 用户手机号 */
	public static final String USER_PHONE = "userPhone";
	/** 修改密码手机号 */
	public static final String CHANGE_PWD_PHONE = "userPhone";
	/** 用户邮箱 */
	public static final String USER_EMAIL = "userEmail";
	/** 城市 */
	public static final String USER_CITY = "userCity";

	public static final String USER_CITY_VIEW = "南京";
	/** 卖家品牌 */
	public static final String SELLBRANDNAME1 = "bland1";
	/***/
	public static final String Bland2 = "bland2";
	/***/
	public static final String Bland3 = "bland3";
	/** 品牌id */
	public static final String BlandId1 = "blandId1";
	/** 品牌id */
	public static final String BlandId2 = "blandId2";
	/** 品牌id */
	public static final String BlandId3 = "blandId3";
	/** 用户认证 【0,1】0表示未认证，1表示已认证 */
	public static final String VIPLEVEL = "VipLevel";
	/** 身份证号码 */
	public static final String SFZ = "SFZ";

	/**
	 * 一页数量
	 */
	public static final int pageSize = 50;
	/** 已支付 */
	public static final String PAY = "0";
	/** 已消费 */
	public static final String COUSUME = "1";
	public static final String TIME = "2";
	/** 全部 */
	public static final String TIME_ALL = "0";
	/** 一个月 */
	public static final String TIME_MONTH = "1";
	/** 一个星期 */
	public static final String TIME_WEEk = "2";
	public static int currentPage;

	/**
	 * 排序方式
	 */
	public static final int sort_type_1 = 0;// 0：默认；1：最新上架；2：价格最低；3：价格最高
	public static final int sort_type_2 = 1;
	public static final int sort_type_3 = 2;
	public static final int sort_type_4 = 3;

	// 常量
	/** 车惠加价 */
	public static final String CHEHUIJIAJIA = "CHEHUIJIAJIA";
	/** 车惠返利 */
	public static final String CHEHUIFANLI = "CHEHUIFANLI";
	/** 回复报价 */
	public static final String HUIFUBAOJIA = "HUIFUBAOJIA";

}
