package com.action;

import java.util.HashMap;
import java.util.Map;

import com.dao.PhoneDao;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.opensymphony.xwork2.ActionSupport;

public class PhoneAction extends ActionSupport{
	private static final long serialVersionUID = 1L;
	
	private int page;
	private int rows;
	public JSONObject result;
	public JSONArray list;
	
	private String loginname="";
	private String loginpass="";
	private String loginimsi="";
	
	private String uname="";  //注册姓名
	private String usex="";  //性别
	private String uemail="";  //邮箱
	
	private String sdate="";
	private String edate="";
	
	private String jylname="";  //雨量站名称
	private String dmnames="";  //断面对应的渗压水位名称列表
	private String ylznames="";  //雨量站名称列表
	
	private String version="";  //手机APP版本号
	
	private String zmindex="";  //闸门编号
	
	private String gcdname="";  //变形观测点名称
	private String zgqname="";  //传感器名称
	
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	public JSONObject getResult() {
		return result;
	}
	public void setResult(JSONObject result) {
		this.result = result;
	}
	public JSONArray getList() {
		return list;
	}
	public void setList(JSONArray list) {
		this.list = list;
	}
    public String getLoginname() {
        return loginname;
    }
    public void setLoginname(String loginname) {
        this.loginname = loginname;
    }
    public String getLoginpass() {
        return loginpass;
    }
    public void setLoginpass(String loginpass) {
        this.loginpass = loginpass;
    }
    public String getLoginimsi() {
        return loginimsi;
    }
    public void setLoginimsi(String loginimsi) {
        this.loginimsi = loginimsi;
    }
    public String getUname() {
        return uname;
    }
    public void setUname(String uname) {
        this.uname = uname;
    }
    public String getUsex() {
        return usex;
    }
    public void setUsex(String usex) {
        this.usex = usex;
    }
    public String getUemail() {
        return uemail;
    }
    public void setUemail(String uemail) {
        this.uemail = uemail;
    }
    public String getSdate() {
        return sdate;
    }
    public void setSdate(String sdate) {
        this.sdate = sdate;
    }
    public String getEdate() {
        return edate;
    }
    public void setEdate(String edate) {
        this.edate = edate;
    }
    public String getJylname() {
        return jylname;
    }
    public void setJylname(String jylname) {
        this.jylname = jylname;
    }
    public String getDmnames() {
        return dmnames;
    }
    public void setDmnames(String dmnames) {
        this.dmnames = dmnames;
    }
    public String getYlznames() {
        return ylznames;
    }
    public void setYlznames(String ylznames) {
        this.ylznames = ylznames;
    }
    public String getVersion() {
        return version;
    }
    public void setVersion(String version) {
        this.version = version;
    }
    public String getZmindex() {
        return zmindex;
    }
    public void setZmindex(String zmindex) {
        this.zmindex = zmindex;
    }
    public String getGcdname() {
        return gcdname;
    }
    public void setGcdname(String gcdname) {
        this.gcdname = gcdname;
    }
    public String getZgqname() {
        return zgqname;
    }
    public void setZgqname(String zgqname) {
        this.zgqname = zgqname;
    }
    
    //登录
	public String makePhoneLogin() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		PhoneDao conn=new PhoneDao();
		try {	
            String stemp=conn.makePhoneLogin(this.loginname, this.loginpass, this.loginimsi);
            map.put("data", stemp);
			this.setResult(JSONObject.fromObject(map));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
            conn.closeConn();
        }
		return SUCCESS;
	}
	
	//注册申请
    public String sumitRegMsg() throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        PhoneDao conn=new PhoneDao();
        try {   
            String stemp=conn.sumitRegMsg(this.loginname, this.loginpass, this.loginimsi, this.uname, this.usex, this.uemail);
            map.put("data", stemp);
            this.setResult(JSONObject.fromObject(map));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.closeConn();
        }
        return SUCCESS;
    }
	
	//获取实时库水位
    public String getRTKSW() throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        PhoneDao conn=new PhoneDao();
        try {   
            String data=conn.getRTKSW();
            map.put("data", data);
            this.setResult(JSONObject.fromObject(map));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.closeConn();
        }
        return SUCCESS;
    }
	
	//获取当天24小时库水位
    public String getCurDayKSW() throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        PhoneDao conn=new PhoneDao();
        try {   
            String data=conn.getCurDayKSW(sdate, edate);
            map.put("data", data);
            this.setResult(JSONObject.fromObject(map));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.closeConn();
        }
        return SUCCESS;
    }
    
    //获取当天24小时降雨量
    public String getCurDayJYL() throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        PhoneDao conn=new PhoneDao();
        try {   
            String data=conn.getCurDayJYL(sdate, edate, jylname);
            map.put("data", data);
            this.setResult(JSONObject.fromObject(map));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.closeConn();
        }
        return SUCCESS;
    }
    
    //获取实时渗压水位
    public String getRTSYSW() throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        PhoneDao conn=new PhoneDao();
        try {
            String data=conn.getRTSYSW(this.dmnames);
            map.put("data", data);
            this.setResult(JSONObject.fromObject(map));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.closeConn();
        }
        return SUCCESS;
    }
    
    //获取实时渗压水位
    public String getRTJYL() throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        PhoneDao conn=new PhoneDao();
        try {   
            String data=conn.getRTJYL();
            map.put("data", data);
            this.setResult(JSONObject.fromObject(map));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.closeConn();
        }
        return SUCCESS;
    }
    
    //获取历史渗压水位
    public String getHisSYSW() throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        PhoneDao conn=new PhoneDao();
        try {   
            String data=conn.getHisSYSW(this.dmnames, this.sdate, this.edate);
            map.put("data", data);
            this.setResult(JSONObject.fromObject(map));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.closeConn();
        }
        return SUCCESS;
    }
    
    //获取历史降雨量
    public String getHisJYL() throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        PhoneDao conn=new PhoneDao();
        try {   
            String data=conn.getHisJYL(this.ylznames, this.sdate, this.edate);
            map.put("data", data);
            this.setResult(JSONObject.fromObject(map));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.closeConn();
        }
        return SUCCESS;
    }
    
    //获取月库水位
    public String getMonthKSW() throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        PhoneDao conn=new PhoneDao();
        try {   
            String data=conn.getMonthKSW(sdate, edate);
            map.put("data", data);
            this.setResult(JSONObject.fromObject(map));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.closeConn();
        }
        return SUCCESS;
    }
    
    //获取月降雨量
    public String getMonthJYL() throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        PhoneDao conn=new PhoneDao();
        try {
            String data=conn.getMonthJYL(sdate, edate, jylname);
            map.put("data", data);
            this.setResult(JSONObject.fromObject(map));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.closeConn();
        }
        return SUCCESS;
    }
    
    //手机APP升级检测
    public String checkAPPUpdate() throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        PhoneDao conn=new PhoneDao();
        try {
            boolean btemp=conn.checkAPPUpdate(version);
            if(btemp) {
                map.put("data", "true");
            } else {
                map.put("data", "false");
            }
            this.setResult(JSONObject.fromObject(map));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.closeConn();
        }
        return SUCCESS;
    }
    
    //获取实时变形观测数据
    public String getBXGCRTData() throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        PhoneDao conn=new PhoneDao();
        try {
            String data=conn.getBXGCRTData();
            map.put("data", data);
            this.setResult(JSONObject.fromObject(map));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.closeConn();
        }
        return SUCCESS;
    }
    
    //获取闸门监控实时数据
    public String getZMJKRTData() throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        PhoneDao conn=new PhoneDao();
        try {
            String data=conn.getZMJKRTData(this.zmindex);
            map.put("data", data);
            this.setResult(JSONObject.fromObject(map));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.closeConn();
        }
        return SUCCESS;
    }
    
    //获取历史变形观测数据
    public String getHisBXGCData() throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        PhoneDao conn=new PhoneDao();
        try {
            String data=conn.getHisBXGCData(this.sdate, this.edate);
            map.put("data", data);
            this.setResult(JSONObject.fromObject(map));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.closeConn();
        }
        return SUCCESS;
    }
    
    //获取闸门监控历史数据
    public String getZMJLHisData() throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        PhoneDao conn=new PhoneDao();
        try {
            String data=conn.getZMJLHisData(this.sdate, this.edate, this.zmindex);
            map.put("data", data);
            this.setResult(JSONObject.fromObject(map));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.closeConn();
        }
        return SUCCESS;
    }
    
    //获取所有观测点名称
    public String getBXGCName() throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        PhoneDao conn=new PhoneDao();
        try {
            String data=conn.getBXGCName();
            map.put("data", data);
            this.setResult(JSONObject.fromObject(map));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.closeConn();
        }
        return SUCCESS;
    }
    
    //获取变形观测点一个月的位移数据
    public String getMonthBXGC() throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        PhoneDao conn=new PhoneDao();
        try {
            String data=conn.getMonthBXGC(this.sdate, this.edate, this.gcdname);
            map.put("data", data);
            this.setResult(JSONObject.fromObject(map));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.closeConn();
        }
        return SUCCESS;
    }
    
    //获取闸门监控一个月数据数据
    public String getMonthZMJK() throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        PhoneDao conn=new PhoneDao();
        try {
            String data=conn.getMonthZMJK(this.sdate, this.edate);
            map.put("data", data);
            this.setResult(JSONObject.fromObject(map));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.closeConn();
        }
        return SUCCESS;
    }
    
    //获取所有传感器名称
    public String getAllZGQName() throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        PhoneDao conn=new PhoneDao();
        try {
            String data=conn.getAllZGQName();
            map.put("data", data);
            this.setResult(JSONObject.fromObject(map));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.closeConn();
        }
        return SUCCESS;
    }
    
    //获取传感器一个月数据
    public String getZGQQXData() throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        PhoneDao conn=new PhoneDao();
        try {
            String data=conn.getZGQQXData(this.sdate, this.edate, this.zgqname);
            map.put("data", data);
            this.setResult(JSONObject.fromObject(map));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.closeConn();
        }
        return SUCCESS;
    }
    
    //获取当天24小时传感器数据
    public String getZGQHisQXData() throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        PhoneDao conn=new PhoneDao();
        try {   
            String data=conn.getZGQHisQXData(sdate, edate, zgqname);
            map.put("data", data);
            this.setResult(JSONObject.fromObject(map));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.closeConn();
        }
        return SUCCESS;
    }
    
    //获取所有雨量站名称
    public String getAllYLZName() throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        PhoneDao conn=new PhoneDao();
        try {
            String data=conn.getAllYLZName();
            map.put("data", data);
            this.setResult(JSONObject.fromObject(map));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.closeConn();
        }
        return SUCCESS;
    }
	
}
