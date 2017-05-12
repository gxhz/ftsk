package com.model;

public class ParametersInfo {
	private int Id;
	private String cdname="";
	private String cdid="";
	private double alarm_level=0.0;
	private double wide_b=0.0;
	private double coefficient_rag=0.0;
	private double coefficient_bs=0.0;
	private double coefficient_ss1=0.0;
	private double coefficient_ss2=0.0;
	
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
    public String getCdname() {
        return cdname;
    }
    public void setCdname(String cdname) {
        this.cdname = cdname;
    }
    public String getCdid() {
        return cdid;
    }
    public void setCdid(String cdid) {
        this.cdid = cdid;
    }
    public double getAlarm_level() {
        return alarm_level;
    }
    public void setAlarm_level(double alarm_level) {
        this.alarm_level = alarm_level;
    }
    public double getWide_b() {
        return wide_b;
    }
    public void setWide_b(double wide_b) {
        this.wide_b = wide_b;
    }
    public double getCoefficient_rag() {
        return coefficient_rag;
    }
    public void setCoefficient_rag(double coefficient_rag) {
        this.coefficient_rag = coefficient_rag;
    }
    public double getCoefficient_bs() {
        return coefficient_bs;
    }
    public void setCoefficient_bs(double coefficient_bs) {
        this.coefficient_bs = coefficient_bs;
    }
    public double getCoefficient_ss1() {
        return coefficient_ss1;
    }
    public void setCoefficient_ss1(double coefficient_ss1) {
        this.coefficient_ss1 = coefficient_ss1;
    }
    public double getCoefficient_ss2() {
        return coefficient_ss2;
    }
    public void setCoefficient_ss2(double coefficient_ss2) {
        this.coefficient_ss2 = coefficient_ss2;
    }
}
