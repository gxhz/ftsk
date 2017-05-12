package com.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.dao.DBJCDao;
import com.model.dbjc.DBFBTInfo;
import com.model.dbjc.DBJCFindInfo;
import com.model.dbjc.DBNBBTJInfo;
import com.model.dbjc.DBNGCXJInfo;
import com.model.dbjc.DBSDBBTJInfo;
import com.model.dbjc.DJRTDataDetails;
import com.model.dbjc.KSWDataDetails;
import com.model.dbjc.SLRTDataDetails;
import com.model.dbjc.SYSWKSWGXTInfo;
import com.opensymphony.xwork2.ActionSupport;

public class DBJCAction extends ActionSupport{
	
	private static final long serialVersionUID = 1L;
	
	private int page;
	private int rows;
	private int Id;
	
	private String CGQType="1";  //传感器类型，1为渗压计，2为量水堰
	private String Flag="0";  //报表类型，1为时段报表，2为日报表，3为月报表，4为年报表
	private String dbdm="";  //大坝断面，1-断面I，2-断面II，3-断面III，4-断面IV，5-断面V，6-溢洪道
	private String names="";
	private String Sbid="";
	private String Name="";
	private String Skid="";
	private String SDate="";
	private String EDate="";
	private String dtype="";
	
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
	
	public void setResult(JSONObject result) {
		this.result = result;
	}

	public int getId() {
        return Id;
    }
    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }
    public void setName(String name) {
        Name = name;
    }

    public String getFlag() {
        return Flag;
    }

    public void setFlag(String flag) {
        Flag = flag;
    }

    public String getDbdm() {
        return dbdm;
    }

    public void setDbdm(String dbdm) {
        this.dbdm = dbdm;
    }

    public String getCGQType() {
        return CGQType;
    }

    public void setCGQType(String cGQType) {
        CGQType = cGQType;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getSbid() {
        return Sbid;
    }
    public void setSbid(String sbid) {
        Sbid = sbid;
    }

    public String getSkid() {
        return Skid;
    }
    public void setSkid(String skid) {
        Skid = skid;
    }

    public String getSDate() {
        return SDate;
    }
    public void setSDate(String sDate) {
        SDate = sDate;
    }

    public String getEDate() {
        return EDate;
    }
    public void setEDate(String eDate) {
        EDate = eDate;
    }
    
    public String getDtype() {
        return dtype;
    }

    public void setDtype(String dtype) {
        this.dtype = dtype;
    }

    public JSONArray getList() {
        return list;
    }
    public void setList(JSONArray list) {
        this.list = list;
    }

    public String findDBJC() throws Exception {
		Map<String,Object> map=new HashMap<String, Object>();
		List<DBJCFindInfo> all = null;
		DBJCDao conn=new DBJCDao();
		try {
			all=conn.getSearchInfo(this.getPage(),this.getRows(),this.getSbid(),this.getName(),
			        this.getSkid(),this.getSDate(),this.getEDate());
			int allrows=conn.getSearchRows(this.getSbid(),this.getName(),this.getSkid(),this.getSDate(),this.getEDate());
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
    
    public String findDBCJSY() throws Exception {
        List<DBJCFindInfo> all = null;
        DBJCDao conn=new DBJCDao();
        try {
            all=conn.getSearchInfo(this.getSkid(),this.getSDate(),this.getEDate());
            JSONArray array = JSONArray.fromObject(all);
            this.setList(array);
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            conn.closeConn();
        }
        return SUCCESS;
    }
    
    //获取大坝所有传感器实时信息
    public String getDBRTData() throws Exception {
        if(this.dbdm.equals("sl")) {  //渗流量
            List<SLRTDataDetails> all = null;
            DBJCDao conn=new DBJCDao();
            try {
                all=conn.getDBRTData();
                JSONArray array = JSONArray.fromObject(all);
                this.setList(array);
            } catch (Exception e) {
                e.printStackTrace();
            }finally{
                conn.closeConn();
            }
        } else if(this.dbdm.equals("ksw")) {  //库水位、库容量
            List<KSWDataDetails> all = null;
            DBJCDao conn=new DBJCDao();
            try {
                all=conn.getKSWRTData();
                JSONArray array = JSONArray.fromObject(all);
                this.setList(array);
            } catch (Exception e) {
                e.printStackTrace();
            }finally{
                conn.closeConn();
            }
        } else {  //渗压
            List<DJRTDataDetails> all = null;
            DBJCDao conn=new DBJCDao();
            try {
                all=conn.getDBRTData(this.dbdm);
                JSONArray array = JSONArray.fromObject(all);
                this.setList(array);
            } catch (Exception e) {
                e.printStackTrace();
            }finally{
                conn.closeConn();
            }
        }
        return SUCCESS;
    }
    
    //获取单个传感器详细信息
    public String getDBRTDataDetails() throws Exception {
		List<DJRTDataDetails> all = null;
		DBJCDao conn = new DBJCDao();
		try {
			all = conn.getDBRTDataDetails(names);
			JSONArray array = JSONArray.fromObject(all);
			this.setList(array);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.closeConn();
		}
		return SUCCESS;
    }
    
    //获取单个传感器详细信息
    public String getSLRTDataDetails() throws Exception {
        List<SLRTDataDetails> all = null;
        DBJCDao conn = new DBJCDao();
        try {
            all = conn.getSLRTDataDetails(names);
            JSONArray array = JSONArray.fromObject(all);
            this.setList(array);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.closeConn();
        }
        return SUCCESS;
    }
    
    //获取时段、日、月报表统计
    public String getSDBBTJData() throws Exception {
        List<DBSDBBTJInfo> all = null;
        DBJCDao conn = new DBJCDao();
        try {
            all = conn.getSDBBTJData(CGQType, Flag, names, SDate, EDate, dtype);
            JSONArray array = JSONArray.fromObject(all);
            this.setList(array);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.closeConn();
        }
        return SUCCESS;
    }
    
    //获取年报表统计
    public String getNBBTJData() throws Exception {
        List<DBNBBTJInfo> all = null;
        DBJCDao conn = new DBJCDao();
        try {
            all = conn.getNBBTJData(CGQType, Flag, names, SDate, EDate, dtype);
            JSONArray array = JSONArray.fromObject(all);
            this.setList(array);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.closeConn();
        }
        return SUCCESS;
    }
    
    //获取年过程线
    public String getNGCXData() throws Exception {
        List<DBNGCXJInfo> all = null;
        DBJCDao conn = new DBJCDao();
        try {
            all = conn.getNGCXData(CGQType, names, SDate, EDate, dtype);
            JSONArray array = JSONArray.fromObject(all);
            this.setList(array);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.closeConn();
        }
        return SUCCESS;
    }
    
    //导出时段、日、月报表至Excel
    public String exportSDBBTJToExcel() throws Exception {
        DBJCDao conn = new DBJCDao();
        try {
            conn.exportSDBBTJToExcel(CGQType, Flag, names, SDate, EDate, dtype);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.closeConn();
        }
        return null;
    }
    
    //导出年报表至Excel
    public String exportNBBTJToExcel() throws Exception {
        DBJCDao conn = new DBJCDao();
        try {
            conn.exportNBBTJToExcel(CGQType, Flag, names, SDate, EDate, dtype);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.closeConn();
        }
        return null;
    }
    
    //获取分布图
    public String getFBTData() throws Exception {
        List<DBFBTInfo> all = null;
        DBJCDao conn = new DBJCDao();
        try {
            all = conn.getFBTData(Name, names, dtype);
            JSONArray array = JSONArray.fromObject(all);
            this.setList(array);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.closeConn();
        }
        return SUCCESS;
    }
    
    //获取分布图
    public String getSYSWKSWGXTData() throws Exception {
        List<SYSWKSWGXTInfo> all = null;
        DBJCDao conn = new DBJCDao();
        try {
            all = conn.getSYSWKSWGXTData(Name, names, dtype);
            JSONArray array = JSONArray.fromObject(all);
            this.setList(array);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.closeConn();
        }
        return SUCCESS;
    }
    
    //获取渗压水位浸润线
    public String getSYSWJRXData() throws Exception {
        List<String> all = null;
        DBJCDao conn = new DBJCDao();
        try {
            all = conn.getSYSWJRXData(SDate, names, dtype);
            JSONArray array = JSONArray.fromObject(all);
            this.setList(array);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.closeConn();
        }
        return SUCCESS;
    }
    
    //获取渗压水位等势线
    public String getSYSWDSXData() throws Exception {
        List<DJRTDataDetails> all = null;
        DBJCDao conn = new DBJCDao();
        try {
            all = conn.getSYSWDSXData(SDate, dtype);
            JSONArray array = JSONArray.fromObject(all);
            this.setList(array);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.closeConn();
        }
        return SUCCESS;
    }
	
}
