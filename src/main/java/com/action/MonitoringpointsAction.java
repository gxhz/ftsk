package com.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.dao.MonitoringpointsDao;
import com.model.MonitoringpointsInfo;
import com.opensymphony.xwork2.ActionSupport;

public class MonitoringpointsAction extends ActionSupport{
	
	private static final long serialVersionUID = 1L;
	
	private int page;
	private int rows;
	private int Id;
	private String Sbid="";
	private String Name="";
	private String Skid="";
	public JSONObject result;
	public JSONArray list;
	
	public JSONArray getList() {
		return list;
	}

	public void setList(JSONArray list) {
		this.list = list;
	}

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
    
    public String getAllMonitoringpointsInfo() throws Exception {
        List<MonitoringpointsInfo> all = null;
        MonitoringpointsDao conn=new MonitoringpointsDao();
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

    public String getMonitoringpointsInfo() throws Exception {
		Map<String,Object> map=new HashMap<String, Object>();
		List<MonitoringpointsInfo> all = null;
		MonitoringpointsDao conn=new MonitoringpointsDao();
		try {
			all=conn.getSearchInfo(this.getPage(),this.getRows(),this.getSbid(),this.getName(),this.getSkid());
			int allrows=conn.getSearchRows(this.getSbid(),this.getName(),this.getSkid());
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
    
    public String checkInfo(String name) throws Exception{
        List<MonitoringpointsInfo> all = null;
        MonitoringpointsDao conn=new MonitoringpointsDao();
        try {
            all=conn.getAllInfo();          
            JSONArray array = JSONArray.fromObject(all);
            for(int i=0;i<array.size();i++){
                JSONObject jo = (JSONObject) array.get(i);
                if(name.equals(jo.get("name"))){
                    return "fail";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            conn.closeConn();
        }
        return SUCCESS;
    }
    
    public String getAllMPInfo() throws Exception {
        List<MonitoringpointsInfo> all = null;
		MonitoringpointsDao conn = new MonitoringpointsDao();
        try {
			all = conn.getAllInfo();
            JSONArray array = JSONArray.fromObject(all);
            this.setList(array);
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            conn.closeConn();
        }
        return SUCCESS;
    }
    
}
