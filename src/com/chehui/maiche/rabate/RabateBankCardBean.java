package com.chehui.maiche.rabate;

public class RabateBankCardBean {
	private String id;
	private String BankCardNum;
	private String BankPass;
	private String SellerID;
	private String CreateDate;
	private String BankName;
	private String CardAdmin;
	private String Tel;
	private String State;

	@Override
	public String toString() {
		return "MyOrderBankCardBean [id=" + id + ", BankCardNum=" + BankCardNum
				+ ", BankPass=" + BankPass + ", SellerID=" + SellerID
				+ ", CreateDate=" + CreateDate + ", BankName=" + BankName
				+ ", CardAdmin=" + CardAdmin + ", Tel=" + Tel + ", State="
				+ State + "]";
	}
}
