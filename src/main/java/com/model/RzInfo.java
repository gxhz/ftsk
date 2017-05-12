package com.model;

public class RzInfo {
	private int Id;
	private String Memo;
	private String Time;
	private String YGID;
	private String YGName;
	
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
    public String getMemo() {
        return Memo;
    }
    public void setMemo(String memo) {
        Memo = memo;
    }
    public String getTime() {
        return Time;
    }
    public void setTime(String time) {
        Time = time;
    }
    public String getYGID() {
        return YGID;
    }
    public void setYGID(String yGID) {
        YGID = yGID;
    }
    public String getYGName() {
        return YGName;
    }
    public void setYGName(String yGName) {
        YGName = yGName;
    }
}
