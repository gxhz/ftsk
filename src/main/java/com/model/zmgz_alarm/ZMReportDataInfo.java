package com.model.zmgz_alarm;

public class ZMReportDataInfo {
    
    public int id=0;
    public String zmmc="";  //0为1#闸门，1为2#闸门
    public String zmmc1="";  //0为工作闸门，1为检修闸门
    public String zmzt="";  //闸门状态：0-上升、1-下降、2-停止
    public String kssj="";  //开始时间
    public String jssj="";  //结束时间
    public String sz="";  //时长
    public double szh=0.0;  //时长(小时)
    public double sll=0.0;  //水流量
    public double zmkd=0.0;  //闸门开度
    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getZmmc() {
        return zmmc;
    }
    public void setZmmc(String zmmc) {
        this.zmmc = zmmc;
    }
    public String getZmmc1() {
        return zmmc1;
    }
    public void setZmmc1(String zmmc1) {
        this.zmmc1 = zmmc1;
    }
    public String getZmzt() {
        return zmzt;
    }
    public void setZmzt(String zmzt) {
        this.zmzt = zmzt;
    }
    public String getKssj() {
        return kssj;
    }
    public void setKssj(String kssj) {
        this.kssj = kssj;
    }
    public String getJssj() {
        return jssj;
    }
    public void setJssj(String jssj) {
        this.jssj = jssj;
    }
    public String getSz() {
        return sz;
    }
    public void setSz(String sz) {
        this.sz = sz;
    }
    public double getSzh() {
        return szh;
    }
    public void setSzh(double szh) {
        this.szh = szh;
    }
    public double getSll() {
        return sll;
    }
    public void setSll(double sll) {
        this.sll = sll;
    }
    public double getZmkd() {
        return zmkd;
    }
    public void setZmkd(double zmkd) {
        this.zmkd = zmkd;
    }
	
}
