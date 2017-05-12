package com.model;

public class DepartmentInfo {
	private int Id;
	private int Pid;
	private String Name;
	private String Des;
	private String Unuseflag;
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public int getPid() {
		return Pid;
	}
	public void setPid(int pid) {
		Pid = pid;
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

