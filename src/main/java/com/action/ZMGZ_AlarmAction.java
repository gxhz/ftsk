package com.action;

import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.dao.ZMGZ_AlarmDao;
import com.dwr.MessagePush;
import com.model.zmgz_alarm.AlarmDataInfo;
import com.model.zmgz_alarm.WXFBInfo;
import com.model.zmgz_alarm.ZMAlarmDataInfo;
import com.model.zmgz_alarm.ZMRTDataInfo;
import com.model.zmgz_alarm.ZMReportDataInfo;
import com.opensymphony.xwork2.ActionSupport;

public class ZMGZ_AlarmAction extends ActionSupport{
	
	private static final long serialVersionUID = 1L;
	
	private int page;
	private int rows;
	private int Id;
	
	private String zmindex="";  //1为1#闸门，2为2#闸门
	private String zmflag="";  //1为工作闸门，2为检修闸门
	private String sdate="";  //开始时间
	private String edate="";  //结束时间
	private String wxmsg="";  //微信发布的信息
	private String dwrmsg="";  //DWR推送信息
	private String bjlb="";  //报警类别，0为大坝，1为变形
	
	private String mailmsg="";  //邮件信息
	private String mails="";  //邮箱号，多个用“,”号分隔
	
	private String smsmsg="";  //短信信息
	private String tels="";  //手机号，多个用“,”号分隔
	
	public JSONObject result;
	public JSONArray list;
	
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

	public String getZmindex() {
        return zmindex;
    }

    public void setZmindex(String zmindex) {
        this.zmindex = zmindex;
    }

    public String getZmflag() {
        return zmflag;
    }

    public void setZmflag(String zmflag) {
        this.zmflag = zmflag;
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

    public String getWxmsg() {
        return wxmsg;
    }

    public void setWxmsg(String wxmsg) {
        this.wxmsg = wxmsg;
    }

    public String getDwrmsg() {
        return dwrmsg;
    }

    public void setDwrmsg(String dwrmsg) {
        this.dwrmsg = dwrmsg;
    }

    public String getBjlb() {
        return bjlb;
    }

    public void setBjlb(String bjlb) {
        this.bjlb = bjlb;
    }

    public String getMailmsg() {
        return mailmsg;
    }

    public void setMailmsg(String mailmsg) {
        this.mailmsg = mailmsg;
    }

    public String getMails() {
        return mails;
    }

    public void setMails(String mails) {
        this.mails = mails;
    }
    
    public String getSmsmsg() {
        return smsmsg;
    }

    public void setSmsmsg(String smsmsg) {
        this.smsmsg = smsmsg;
    }

    public String getTels() {
        return tels;
    }

    public void setTels(String tels) {
        this.tels = tels;
    }

    public void setResult(JSONObject result) {
		this.result = result;
	}

	public int getId() {
        return Id;
    }
    public void setId(int id) {
        Id = id;
    }

    public JSONArray getList() {
        return list;
    }
    public void setList(JSONArray list) {
        this.list = list;
    }

    //获取闸门所有信息
    public String getZMMsg() throws Exception {
        List<ZMRTDataInfo> all = null;
        ZMGZ_AlarmDao conn=new ZMGZ_AlarmDao();
        try {
            all=conn.getZMMsg(this.zmindex);
            JSONArray array = JSONArray.fromObject(all);
            this.setList(array);
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            conn.closeConn();
        }
        return SUCCESS;
    }
    
    //获取闸门报表数据
    public String getZMReportData() throws Exception {
        List<ZMReportDataInfo> all = null;
        Map<String,Object> map=new HashMap<String, Object>();
        ZMGZ_AlarmDao conn=new ZMGZ_AlarmDao();
        try {
            all=conn.getZMReportData(this.page, this.rows, this.zmindex, this.zmflag, this.sdate, this.edate);
            List<ZMReportDataInfo> allrowslist=conn.getZMReportData1(this.zmindex, this.zmflag, this.sdate, this.edate);
            
            int allrows=allrowslist.size();
            
            map.put("total", allrows);
            map.put("rows", all);
            map.put("allrows", allrowslist);
            
            this.setResult(JSONObject.fromObject(map));
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            conn.closeConn();
        }
        return SUCCESS;
    }
    
    //获取闸门报警数据
    public String getZMAlarmData() throws Exception {
        List<ZMAlarmDataInfo> all = null;
        Map<String,Object> map=new HashMap<String, Object>();
        ZMGZ_AlarmDao conn=new ZMGZ_AlarmDao();
        try {
            all=conn.getZMAlarmData(this.page,this.rows,this.zmindex, this.zmflag, this.sdate, this.edate);
            List<Integer> allrowslist=conn.getZMAlarmData(this.zmindex, this.zmflag, this.sdate, this.edate);
            
            int allrows=0,sxsum=0,xxsum=0,gzsum=0;
            if(allrowslist.size() > 0) {
                allrows = allrowslist.get(0);
                sxsum = allrowslist.get(1);
                xxsum = allrowslist.get(2);
                gzsum = allrowslist.get(3);
            }
            
            map.put("total", allrows);
            map.put("rows", all);
            map.put("sxsum", sxsum);
            map.put("xxsum", xxsum);
            map.put("gzsum", gzsum);
            
            this.setResult(JSONObject.fromObject(map));
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            conn.closeConn();
        }
        return SUCCESS;
    }
    
    //导出闸门报表数据
    public String exportZMReoprtToExcel() throws Exception {
        ZMGZ_AlarmDao conn=new ZMGZ_AlarmDao();
        try {
            conn.exportZMReoprtToExcel(this.zmindex, this.zmflag, this.sdate, this.edate);
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            conn.closeConn();
        }
        return null;
    }
    
    //导出闸门报警数据
    public String exportZMAlarmToExcel() throws Exception {
        ZMGZ_AlarmDao conn=new ZMGZ_AlarmDao();
        try {
            conn.exportZMAlarmToExcel(this.zmindex, this.zmflag, this.sdate, this.edate);
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            conn.closeConn();
        }
        return null;
    }
    
    //获取所有报警信息
    public String getAlarmMsg() throws Exception {
        List<AlarmDataInfo> all = null;
        Map<String,Object> map=new HashMap<String, Object>();
        ZMGZ_AlarmDao conn=new ZMGZ_AlarmDao();
        try {
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
            Calendar calendar = Calendar.getInstance();
            if(this.sdate.equals("")) this.sdate = sdf.format(calendar.getTime());
            if(this.edate.equals("")) this.edate = sdf.format(calendar.getTime());
            
            all=conn.getAlarmMsg(this.page,this.rows,this.bjlb, this.sdate, this.edate);
            
            int allrows=conn.getAlarmMsg(-1,-1,this.bjlb, this.sdate, this.edate).size();
            
            map.put("total", allrows);
            map.put("rows", all);
            
            this.setResult(JSONObject.fromObject(map));
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            conn.closeConn();
        }
        return SUCCESS;
    }
    
    //导出报警数据
    public String exportAlarmToExcel() throws Exception {
        ZMGZ_AlarmDao conn=new ZMGZ_AlarmDao();
        try {
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
            Calendar calendar = Calendar.getInstance();
            if(this.sdate.equals("")) this.sdate = sdf.format(calendar.getTime());
            if(this.edate.equals("")) this.edate = sdf.format(calendar.getTime());
            
            conn.exportAlarmToExcel(this.bjlb, this.sdate, this.edate);
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            conn.closeConn();
        }
        return null;
    }
    
    //发布微信信息
    public String sendWXMsg() throws Exception {
        Map<String,Object> map=new HashMap<String, Object>();
        ZMGZ_AlarmDao conn=new ZMGZ_AlarmDao();
        try {
            this.wxmsg = URLDecoder.decode(this.wxmsg, "UTF-8");
            boolean btemp=conn.sendWXMsg(this.wxmsg);
            if(btemp) {
                conn.addWXMsg(this.wxmsg, 1);
                map.put("msg", "发送信息成功");
            } else {
                conn.addWXMsg(this.wxmsg, 2);
                map.put("msg", "发送信息失败");
            }
            this.setResult(JSONObject.fromObject(map));
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            conn.closeConn();
        }
        return SUCCESS;
    }
    
    //获取微信发布信息
    public String getWXFBMsg() throws Exception {
        List<WXFBInfo> all = null;
        ZMGZ_AlarmDao conn=new ZMGZ_AlarmDao();
        try {
            all=conn.getWXFBMsg();
            JSONArray array = JSONArray.fromObject(all);
            this.setList(array);
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            conn.closeConn();
        }
        return SUCCESS;
    }
    
    //DWR推送信息
    public String sendDWRMsg() throws Exception {
        Map<String,Object> map=new HashMap<String, Object>();
        try {
            MessagePush.sendMsg(dwrmsg);
            map.put("msg", "发送信息成功");
            this.setResult(JSONObject.fromObject(map));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SUCCESS;
    }
    
    //发送邮件
    public String sendMail() throws Exception {
        Map<String,Object> map=new HashMap<String, Object>();
        ZMGZ_AlarmDao conn=new ZMGZ_AlarmDao();
        try {
            this.mailmsg = URLDecoder.decode(this.mailmsg, "UTF-8");
            boolean btemp=conn.sendMail(this.mailmsg, this.mails);
            if(btemp) {
                //conn.addWXMsg(this.wxmsg, 1);
                map.put("msg", "发送信息成功");
            } else {
                //conn.addWXMsg(this.wxmsg, 2);
                map.put("msg", "发送信息失败");
            }
            this.setResult(JSONObject.fromObject(map));
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            conn.closeConn();
        }
        return SUCCESS;
    }
    
    //获取APP推送信息
    public String getAPPMsg() throws Exception {
        List<WXFBInfo> all = null;
        ZMGZ_AlarmDao conn=new ZMGZ_AlarmDao();
        try {
            all=conn.getAPPMsg();
            JSONArray array = JSONArray.fromObject(all);
            this.setList(array);
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            conn.closeConn();
        }
        return SUCCESS;
    }
    
    //推送手机APP信息
    public String sendAPPMsg() throws Exception {
        Map<String,Object> map=new HashMap<String, Object>();
        ZMGZ_AlarmDao conn=new ZMGZ_AlarmDao();
        try {
            this.wxmsg = URLDecoder.decode(this.wxmsg, "UTF-8");
            boolean btemp=conn.sendAPPMsg(this.wxmsg);
            if(btemp) {
                conn.addAPPMsg(this.wxmsg, 1);
                map.put("msg", "推送信息成功");
            } else {
                conn.addAPPMsg(this.wxmsg, 2);
                map.put("msg", "推送信息失败");
            }
            this.setResult(JSONObject.fromObject(map));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.closeConn();
        }
        return SUCCESS;
    }
    
    //发送短信
    public String sendSMS() throws Exception {
        Map<String,Object> map=new HashMap<String, Object>();
        ZMGZ_AlarmDao conn=new ZMGZ_AlarmDao();
        try {
            this.smsmsg = URLDecoder.decode(this.smsmsg, "UTF-8");
            boolean btemp=conn.sendSMS(this.smsmsg, this.tels);
            if(btemp) {
                //conn.addWXMsg(this.smsmsg, 1);
                map.put("msg", "发送短信成功");
            } else {
                //conn.addWXMsg(this.smsmsg, 2);
                map.put("msg", "发送短信失败");
            }
            this.setResult(JSONObject.fromObject(map));
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            conn.closeConn();
        }
        return SUCCESS;
    }
	
}
