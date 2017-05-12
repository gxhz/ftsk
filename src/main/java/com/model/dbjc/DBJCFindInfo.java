package com.model.dbjc;

public class DBJCFindInfo {
	private int Id;
	private String Time = "";
	private String JCDName = "";
	private String SBName = "";
	private String DMName = "";
	private double SW = 0.0;
	private double SY = 0.0;
	private double SL = 0.0;
	private double WD = 0.0;
	private int index = 0;
	
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
    public String getTime() {
        return Time;
    }
    public void setTime(String time) {
        Time = time;
    }
    public String getJCDName() {
        return JCDName;
    }
    public void setJCDName(String jCDName) {
        JCDName = jCDName;
    }
    public String getSBName() {
        return SBName;
    }
    public void setSBName(String sBName) {
        SBName = sBName;
    }
    public String getDMName() {
        return DMName;
    }
    public double getSW() {
        return SW;
    }
    public void setSW(double sW) {
        SW = sW;
    }
    public void setDMName(String dMName) {
        DMName = dMName;
    }
    public double getSY() {
        return SY;
    }
    public void setSY(double sY) {
        SY = sY;
    }
    public double getSL() {
        return SL;
    }
    public void setSL(double sL) {
        SL = sL;
    }
    public double getWD() {
        return WD;
    }
    public void setWD(double wD) {
        WD = wD;
    }
    public int getIndex() {
        return index;
    }
    public void setIndex(int index) {
        this.index = index;
    }
}
