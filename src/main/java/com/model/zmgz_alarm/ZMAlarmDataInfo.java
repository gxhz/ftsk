package com.model.zmgz_alarm;

public class ZMAlarmDataInfo {
    
    private int id=0;
    private String zmmc="";  //0为1#闸门，1为2#闸门
    private String zmmc1="";  //0为工作闸门，1为检修闸门
    private String zmzt="";  //闸门状态：闸门状态：0-上限、1-下限、2-故障
    private String kssj="";  //开始时间
    private String jssj="";  //结束时间
    private String sz="";  //时长
    private double szh=0.0;  //时长(小时)
    private String bjzt="";  //1为报警，2为恢得
    
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
    public String getBjzt() {
        return bjzt;
    }
    public void setBjzt(String bjzt) {
        this.bjzt = bjzt;
    }
	
}
