package com.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.dao.ParametersDao;
import com.model.ParametersInfo;
import com.opensymphony.xwork2.ActionSupport;

public class ParametersAction extends ActionSupport{
	
	private static final long serialVersionUID = 1L;
	
	private int page;
	private int rows;
	
	public JSONObject result;
	public JSONArray list;
	
	private String Sbid="";
	
	private String sw="";
	private String xdg="";
	private String zzxs="";
	private String dbxs="";
	private String pbxs1="";
	private String pbxs2="";
	
	private String id="";
	
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

    public JSONArray getList() {
        return list;
    }
    public void setList(JSONArray list) {
        this.list = list;
    }
    
    public String getSbid() {
        return Sbid;
    }
    public void setSbid(String sbid) {
        Sbid = sbid;
    }
    
    public String getSw() {
        return sw;
    }
    public void setSw(String sw) {
        this.sw = sw;
    }
    public String getXdg() {
        return xdg;
    }
    public void setXdg(String xdg) {
        this.xdg = xdg;
    }
    public String getZzxs() {
        return zzxs;
    }
    public void setZzxs(String zzxs) {
        this.zzxs = zzxs;
    }
    public String getDbxs() {
        return dbxs;
    }
    public void setDbxs(String dbxs) {
        this.dbxs = dbxs;
    }
    public String getPbxs1() {
        return pbxs1;
    }
    public void setPbxs1(String pbxs1) {
        this.pbxs1 = pbxs1;
    }
    public String getPbxs2() {
        return pbxs2;
    }
    public void setPbxs2(String pbxs2) {
        this.pbxs2 = pbxs2;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    
    public String getAllParametersInfo() throws Exception {
        List<ParametersInfo> all = null;
        ParametersDao conn=new ParametersDao();
        try {
            all=conn.getAllInfo();
            JSONArray array = JSONArray.fromObject(all);
            this.setList(array);
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            conn.closeConn();
        }
        return SUCCESS;
    }

    public String getParametersInfo() throws Exception {
		Map<String,Object> map=new HashMap<String, Object>();
		List<ParametersInfo> all = null;
		ParametersDao conn=new ParametersDao();
		try {
			all=conn.getSearchInfo(this.getPage(),this.getRows(),this.getSbid());
			int allrows=conn.getSearchRows(this.getSbid());
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
	
}
