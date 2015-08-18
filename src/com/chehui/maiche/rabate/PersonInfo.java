package com.chehui.maiche.rabate;

public class PersonInfo
{
	private String Id;
	private String BankCardNum;
	private String BankPass;
	private String SellerID;
	private String CreateDate;
	private String BankName;
	private String CardAdmin;
	private String Tel;
	private String State;
	
	public String getCardAdmin()
	{
		return CardAdmin;
	}
	public void setCardAdmin(String cardAdmin)
	{
		CardAdmin = cardAdmin;
	}
	public String getBankCardNum()
	{
		return BankCardNum;
	}
	public void setBankCardNum(String bankCardNum)
	{
		BankCardNum = bankCardNum;
	}
	public String getBankPass()
	{
		return BankPass;
	}
	public void setBankPass(String bankPass)
	{
		BankPass = bankPass;
	}
	public String getSellerID()
	{
		return SellerID;
	}
	public void setSellerID(String sellerID)
	{
		SellerID = sellerID;
	}
	public String getCreateDate()
	{
		return CreateDate;
	}
	public void setCreateDate(String createDate)
	{
		CreateDate = createDate;
	}
	public String getBankName()
	{
		return BankName;
	}
	public void setBankName(String bankName)
	{
		BankName = bankName;
	}
	public String getTel()
	{
		return Tel;
	}
	public void setTel(String tel)
	{
		Tel = tel;
	}
	public String getId()
	{
		return Id;
	}
	public void setId(String id)
	{
		Id = id;
	}
	public String getState()
	{
		return State;
	}
	public void setState(String state)
	{
		State = state;
	}
	@Override
	public String toString()
	{
		return BankName+",,"+CardAdmin;
	}
	@Override
	public boolean equals(Object o)
	{
		if(o instanceof PersonInfo){
			PersonInfo p = (PersonInfo) o;
			return 	this.BankName.equals(p.BankName)
					&& this.CardAdmin.equals(p.CardAdmin);
		}
		return super.equals(o);
	}
}
