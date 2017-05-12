package com.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.dao.SectionsDao;
import com.model.SectionsInfo;
import com.opensymphony.xwork2.ActionSupport;

public class SectionsAction extends ActionSupport{
	
	private static final long serialVersionUID = 1L;
	
	private int page;
	private int rows;
	private int Id;
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
    
    public String getAllSectionsInfo() throws Exception {
        List<SectionsInfo> all = null;
        SectionsDao conn=new SectionsDao();
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

    public String getSectionsInfo() throws Exception {
		Map<String,Object> map=new HashMap<String, Object>();
		List<SectionsInfo> all = null;
		SectionsDao conn=new SectionsDao();
		try {
			all=conn.getSearchInfo(this.getPage(),this.getRows(),this.getName());
			int allrows=conn.getSearchRows(this.getName());
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
    
    public String addSectionsInfo() throws Exception {
        SectionsDao conn=new SectionsDao();
        SectionsInfo info=new SectionsInfo();
        info.setName(this.getName());
        info.setDes(this.getDes());
        Map<String,Object> map=new HashMap<String, Object>();
        try {
            if(!this.checkInfo(this.getName()).equals("fail")){
                conn.add(info);
                map.put("success", true);
                map.put("msg", "成功！");
            }else{
                map.put("success", false);
                map.put("msg", "失败！");
            }
        } catch (Exception e) {
            map.put("success", false);
            map.put("msg", "失败！");
            e.printStackTrace();
        }finally{
            conn.closeConn();
        }
        this.setResult(JSONObject.fromObject(map));

        return SUCCESS;
    }
    
    public String checkInfo(String name) throws Exception{
        List<SectionsInfo> all = null;
        SectionsDao conn=new SectionsDao();
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
    
    public String editSectionsInfo() throws Exception {
        SectionsDao conn=new SectionsDao();
        SectionsInfo info=new SectionsInfo();
        info.setId(this.getId());
        info.setName(this.getName());
        info.setDes(this.getDes());

        Map<String,Object> map=new HashMap<String, Object>();
        try {
            conn.update(info);
            map.put("success", true);
            map.put("msg", "成功！");
        } catch (Exception e) {
            map.put("success", false);
            map.put("msg", "失败！");
            e.printStackTrace();
        }finally{
            conn.closeConn();
        }
        this.setResult(JSONObject.fromObject(map));

        return SUCCESS;
    }
    
    public String delSectionsInfo() throws Exception {
        SectionsDao conn=new SectionsDao();
        Map<String,Object> map=new HashMap<String, Object>();
        try {
            conn.deleteById(this.getId());
            map.put("success", true);
            map.put("msg", "成功！");
        } catch (Exception e) {
            map.put("success", false);
            map.put("msg", "失败！");
            e.printStackTrace();
        }finally{
            conn.closeConn();
        }
        
        this.setResult(JSONObject.fromObject(map));
            
        return SUCCESS;
    }
	
}
