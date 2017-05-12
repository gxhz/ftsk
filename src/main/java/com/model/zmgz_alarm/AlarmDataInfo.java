package com.model.zmgz_alarm;

public class AlarmDataInfo {
    
    private int id=0;
    private String bjsj="";  //报警时间
    private String bjxx="";  //报警信息
    private String bjzt="";  //报警状态
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    public String getBjsj() {
        return bjsj;
    }
    public void setBjsj(String bjsj) {
        this.bjsj = bjsj;
    }
    public String getBjxx() {
        return bjxx;
    }
    public void setBjxx(String bjxx) {
        this.bjxx = bjxx;
    }
    public String getBjzt() {
        return bjzt;
    }
    public void setBjzt(String bjzt) {
        this.bjzt = bjzt;
    }
    
	
}
