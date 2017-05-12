package com.model.syq;

//降雨量实时数据
public class JYLRTDataInfo {
    
	public String code = "";
	public String name="";
	public double jyl_day=0.0;
	public double jyl_year=0.0;
	public double jd=0.0;
	public double wd=0.0;
	
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public double getJyl_day() {
        return jyl_day;
    }
    public void setJyl_day(double jyl_day) {
        this.jyl_day = jyl_day;
    }
    public double getJyl_year() {
        return jyl_year;
    }
    public void setJyl_year(double jyl_year) {
        this.jyl_year = jyl_year;
    }
    public double getJd() {
        return jd;
    }
    public void setJd(double jd) {
        this.jd = jd;
    }
    public double getWd() {
        return wd;
    }
    public void setWd(double wd) {
        this.wd = wd;
    }
	
}
