package com.model.hsyb;


//调度结果表
public class DDJGBInfo {
    
	public String time;
	public String hour;
	
	public String sc_jyl = "1.0";	//实测降雨量
	public String sc_rkll = "2.0"; //实测入库流量
	public String sc_ksw = "3.0";  //实测库水位
	public String sc_krl = "4.0";  //实测库容量
	public String sc_ckll = "5.0";  //实测出库流量
	
	public String yb_jyl = "2.1";	//预报降雨量
	public String yb_rkll = "2.2";  //预报入库流量
	public String yb_ksw = "3.3";	//预报库水位
	public String yb_krl = "4.4";	//预报库容量
	public String yb_ckll = "5.5";  //预报出库流量
	
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getHour() {
		return hour;
	}
	public void setHour(String hour) {
		this.hour = hour;
	}
    public String getSc_jyl() {
        return sc_jyl;
    }
    public void setSc_jyl(String sc_jyl) {
        this.sc_jyl = sc_jyl;
    }
    public String getSc_ksw() {
        return sc_ksw;
    }
    public void setSc_ksw(String sc_ksw) {
        this.sc_ksw = sc_ksw;
    }
    public String getSc_krl() {
        return sc_krl;
    }
    public void setSc_krl(String sc_krl) {
        this.sc_krl = sc_krl;
    }
    public String getSc_rkll() {
        return sc_rkll;
    }
    public void setSc_rkll(String sc_rkll) {
        this.sc_rkll = sc_rkll;
    }
    public String getSc_ckll() {
        return sc_ckll;
    }
    public void setSc_ckll(String sc_ckll) {
        this.sc_ckll = sc_ckll;
    }
    public String getYb_jyl() {
        return yb_jyl;
    }
    public void setYb_jyl(String yb_jyl) {
        this.yb_jyl = yb_jyl;
    }
    public String getYb_ksw() {
        return yb_ksw;
    }
    public void setYb_ksw(String yb_ksw) {
        this.yb_ksw = yb_ksw;
    }
    public String getYb_krl() {
        return yb_krl;
    }
    public void setYb_krl(String yb_krl) {
        this.yb_krl = yb_krl;
    }
    public String getYb_rkll() {
        return yb_rkll;
    }
    public void setYb_rkll(String yb_rkll) {
        this.yb_rkll = yb_rkll;
    }
    public String getYb_ckll() {
        return yb_ckll;
    }
    public void setYb_ckll(String yb_ckll) {
        this.yb_ckll = yb_ckll;
    }
		
}
