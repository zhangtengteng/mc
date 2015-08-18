package com.chehui.maiche;

/**
 * 注册管理
 * 
 * @author zhangtengteng
 * 
 */
public class RegisterManager {

	private static volatile RegisterManager instance;
	private String userName;
	private String NickName;

	public String getNickName() {
		return NickName;
	}

	public void setNickName(String nickName) {
		NickName = nickName;
	}

	private String passWord;
	private String tel;
	private String mail;
	private String cityName;
	private String bland1;
	private String bland2;
	private String bland3;

	public String getBland1() {
		return bland1;
	}

	public void setBland1(String bland1) {
		this.bland1 = bland1;
	}

	public String getBland2() {
		return bland2;
	}

	public void setBland2(String bland2) {
		this.bland2 = bland2;
	}

	public String getBland3() {
		return bland3;
	}

	public void setBland3(String bland3) {
		this.bland3 = bland3;
	}

	private RegisterManager() {
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public static void setInstance(RegisterManager instance) {
		RegisterManager.instance = instance;
	}

	/**
	 * 创建单例类，提供静态方法调用
	 * 
	 * @return ActivityManager
	 */
	public static RegisterManager getInstance() {
		if (instance == null) {
			instance = new RegisterManager();
		}
		return instance;
	}

}
