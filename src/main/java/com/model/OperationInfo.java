package com.model;

public class OperationInfo {
	private int Id;
	private int Mk_id;
	private String Name;
	private String Des;
	private String Unuseflag;
	
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public int getMk_id() {
		return Mk_id;
	}
	public void setMk_id(int mk_id) {
		Mk_id = mk_id;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getDes() {
		return Des;
	}
	public void setDes(String des) {
		Des = des;
	}
	public String getUnuseflag() {
		return Unuseflag;
	}
	public void setUnuseflag(String unuseflag) {
		Unuseflag = unuseflag;
	}
}
