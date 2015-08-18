package com.chehui.maiche.login;

import java.io.Serializable;

/**
 * 
 * @author zzp
 *         <P>
 *         城市信息;备用
 *         <P>
 * 
 */
public class Cityinfo implements Serializable {
	private static final long serialVersionUID = 4002928538558705149L;
	private String id;
	private String city_name;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCity_name() {
		return city_name;
	}

	public void setCity_name(String city_name) {
		this.city_name = city_name;
	}

	@Override
	public String toString() {
		return "Cityinfo [id=" + id + ", city_name=" + city_name + "]";
	}

}
