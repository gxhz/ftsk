package com.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.dao.DevicesDao;
import com.model.DevicesInfo;
import com.opensymphony.xwork2.ActionSupport;

public class DevicesAction extends ActionSupport{
	
	private static final long serialVersionUID = 1L;
	
	private int page;
	private int rows;
	private int Id;
	private String Code="";
	private String Name="";
	private String Des="";
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

    public String getCode() {
        return Code;
    }
    public void setCode(String code) {
        Code = code;
    }

    public String getName() {
        return Name;
    }
    public void setName(String name) {
        Name = name;
    }

    public String getDes() {
        return Des;
    }
    public void setDes(String des) {
        Des = des;
    }
    
    public JSONArray getList() {
        return list;
    }
    public void setList(JSONArray list) {
        this.list = list;
    }

    public String getAllDevicesInfo() throws Exception {
        List<DevicesInfo> all = null;
        DevicesDao conn=new DevicesDao();
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

    public String getDevicesInfo() throws Exception {
		Map<String,Object> map=new HashMap<String, Object>();
		List<DevicesInfo> all = null;
		DevicesDao conn=new DevicesDao();
		try {
			all=conn.getSearchInfo(this.getPage(),this.getRows(),this.getCode(),this.getName());
			int allrows=conn.getSearchRows(this.getCode(),this.getName());
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
        List<DevicesInfo> all = null;
        DevicesDao conn=new DevicesDao();
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
    
    public String editDevicesInfo() throws Exception {
        DevicesDao conn=new DevicesDao();
        DevicesInfo info=new DevicesInfo();
        info.setId(this.getId());
        info.setCode(this.getCode());
        info.setName(this.getName());
        info.setDes(this.getDes());

        Map<String,Object> map=new HashMap<String, Object>();
        try {
            conn.update(info);
            map.put("success", true);
            map.put("msg", "�޸ĳɹ�");
        } catch (Exception e) {
            map.put("success", false);
            map.put("msg", "�޸�ʧ��");
            e.printStackTrace();
        }finally{
            conn.closeConn();
        }
        this.setResult(JSONObject.fromObject(map));

        return SUCCESS;
    }
    
    public String delDevicesInfo() throws Exception {
        DevicesDao conn=new DevicesDao();
        Map<String,Object> map=new HashMap<String, Object>();
        try {
            conn.deleteById(this.getId());
            map.put("success", true);
            map.put("msg", "ɾ��ɹ�");
        } catch (Exception e) {
            map.put("success", false);
            map.put("msg", "ɾ��ʧ��");
            e.printStackTrace();
        }finally{
            conn.closeConn();
        }
        
        this.setResult(JSONObject.fromObject(map));
            
        return SUCCESS;
    }
	
}
