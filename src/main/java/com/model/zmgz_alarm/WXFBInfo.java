package com.model.zmgz_alarm;

public class WXFBInfo {
    
    private int id=0;
    private String fbsj="";  //发布时间
    private int fbrid=0;  //发布人ID
    private String fbr="";  //发布人
    private String fbnr="";  //发布内容
    private String fbzt="";  //发布状态
    
    public int getId() {
        return id;
    }

    public String getFbsj() {
        return fbsj;
    }

    public void setFbsj(String fbsj) {
        this.fbsj = fbsj;
    }

    public int getFbrid() {
        return fbrid;
    }

    public void setFbrid(int fbrid) {
        this.fbrid = fbrid;
    }

    public String getFbr() {
        return fbr;
    }

    public void setFbr(String fbr) {
        this.fbr = fbr;
    }

    public String getFbnr() {
        return fbnr;
    }

    public void setFbnr(String fbnr) {
        this.fbnr = fbnr;
    }

    public String getFbzt() {
        return fbzt;
    }

    public void setFbzt(String fbzt) {
        this.fbzt = fbzt;
    }

    public void setId(int id) {
        this.id = id;
    }
    
}
