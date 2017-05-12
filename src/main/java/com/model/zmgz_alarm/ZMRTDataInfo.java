package com.model.zmgz_alarm;

public class ZMRTDataInfo {
    
    private int id=0;
    private int zm_id=0;  //闸门编号
    private double gzzmss=0.0;  //工作闸门上升
    private double gzzmxj=0.0;  //工作闸门下降
    private double gzzmqt=0.0;  //工作闸门启停
    private double gzzmsx=0.0;  //工作闸门上限
    private double gzzmxx=0.0;  //工作闸门下限
    private double gzzmycjd=0.0;  //工作闸门远程/就地
    private double jxzmss=0.0;  //检修闸门上升
    private double jxzmxj=0.0;  //检修闸门下降
    private double jxzmqt=0.0;  //检修闸门启停
    private double jxzmsx=0.0;  //检修闸门上限
    private double jxzmxx=0.0;  //检修闸门下限
    private double jxzmycjd=0.0;  //检修闸门远程/就地
    private double szd=0.0;  //闸门手/自动
    private double sw=0.0;  //闸门水位
    private double gzzw=0.0;  //工作闸门闸位
    private double jxzw=0.0;  //检修闸门闸位
    private String rq="";  //日期时间
    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getZm_id() {
        return zm_id;
    }
    public void setZm_id(int zm_id) {
        this.zm_id = zm_id;
    }
    public double getGzzmss() {
        return gzzmss;
    }
    public void setGzzmss(double gzzmss) {
        this.gzzmss = gzzmss;
    }
    public double getGzzmxj() {
        return gzzmxj;
    }
    public void setGzzmxj(double gzzmxj) {
        this.gzzmxj = gzzmxj;
    }
    public double getGzzmqt() {
        return gzzmqt;
    }
    public void setGzzmqt(double gzzmqt) {
        this.gzzmqt = gzzmqt;
    }
    public double getGzzmsx() {
        return gzzmsx;
    }
    public void setGzzmsx(double gzzmsx) {
        this.gzzmsx = gzzmsx;
    }
    public double getGzzmxx() {
        return gzzmxx;
    }
    public void setGzzmxx(double gzzmxx) {
        this.gzzmxx = gzzmxx;
    }
    public double getGzzmycjd() {
        return gzzmycjd;
    }
    public void setGzzmycjd(double gzzmycjd) {
        this.gzzmycjd = gzzmycjd;
    }
    public double getJxzmss() {
        return jxzmss;
    }
    public void setJxzmss(double jxzmss) {
        this.jxzmss = jxzmss;
    }
    public double getJxzmxj() {
        return jxzmxj;
    }
    public void setJxzmxj(double jxzmxj) {
        this.jxzmxj = jxzmxj;
    }
    public double getJxzmqt() {
        return jxzmqt;
    }
    public void setJxzmqt(double jxzmqt) {
        this.jxzmqt = jxzmqt;
    }
    public double getJxzmsx() {
        return jxzmsx;
    }
    public void setJxzmsx(double jxzmsx) {
        this.jxzmsx = jxzmsx;
    }
    public double getJxzmxx() {
        return jxzmxx;
    }
    public void setJxzmxx(double jxzmxx) {
        this.jxzmxx = jxzmxx;
    }
    public double getJxzmycjd() {
        return jxzmycjd;
    }
    public void setJxzmycjd(double jxzmycjd) {
        this.jxzmycjd = jxzmycjd;
    }
    public double getSzd() {
        return szd;
    }
    public void setSzd(double szd) {
        this.szd = szd;
    }
    public double getSw() {
        return sw;
    }
    public void setSw(double sw) {
        this.sw = sw;
    }
    public double getGzzw() {
        return gzzw;
    }
    public void setGzzw(double gzzw) {
        this.gzzw = gzzw;
    }
    public double getJxzw() {
        return jxzw;
    }
    public void setJxzw(double jxzw) {
        this.jxzw = jxzw;
    }
    public String getRq() {
        return rq;
    }
    public void setRq(String rq) {
        this.rq = rq;
    }
	
}
