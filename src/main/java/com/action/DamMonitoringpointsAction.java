package com.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.dao.DamMonitoringpointsDao;
import com.model.DamMonitoringpointsInfo;
import com.opensymphony.xwork2.ActionSupport;

public class DamMonitoringpointsAction extends ActionSupport{
	
	private static final long serialVersionUID = 1L;
	
	private int page;
	private int rows;
	private int Id;
	private String Sbid="";
	private String Name="";
	private String Skid="";
	public JSONObject result;
	
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

    public String getDamMonitoringpointsInfo() throws Exception {
		Map<String,Object> map=new HashMap<String, Object>();
		List<DamMonitoringpointsInfo> all = null;
		DamMonitoringpointsDao conn=new DamMonitoringpointsDao();
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
    
    public String addDamMonitoringpointsInfo() throws Exception {
        DamMonitoringpointsDao conn=new DamMonitoringpointsDao();
        DamMonitoringpointsInfo info=new DamMonitoringpointsInfo();
        info.setDevice_id(this.getSbid());
        info.setName(this.getName());
        info.setSections_id(this.getSkid());
        Map<String,Object> map=new HashMap<String, Object>();
        try {
            if(!this.checkInfo(this.getName()).equals("fail")){
                conn.add(info);
                map.put("success", true);
                map.put("msg", "添加成功");
            }else{
                System.out.println("添加失败");
                map.put("success", false);
                map.put("msg", "该信息已存在");
            }
        } catch (Exception e) {
            map.put("success", false);
            map.put("msg", "添加失败");
            e.printStackTrace();
        }finally{
            conn.closeConn();
        }
        this.setResult(JSONObject.fromObject(map));

        return SUCCESS;
    }
    
    public String checkInfo(String name) throws Exception{
        List<DamMonitoringpointsInfo> all = null;
        DamMonitoringpointsDao conn=new DamMonitoringpointsDao();
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
    
    public String editDamMonitoringpointsInfo() throws Exception {
        DamMonitoringpointsDao conn=new DamMonitoringpointsDao();
        DamMonitoringpointsInfo info=new DamMonitoringpointsInfo();
        info.setId(this.getId());
        info.setDevice_id(this.getSbid());
        info.setName(this.getName());
        info.setSections_id(this.getSkid());

        Map<String,Object> map=new HashMap<String, Object>();
        try {
            conn.update(info);
            map.put("success", true);
            map.put("msg", "修改成功");
        } catch (Exception e) {
            map.put("success", false);
            map.put("msg", "修改失败");
            e.printStackTrace();
        }finally{
            conn.closeConn();
        }
        this.setResult(JSONObject.fromObject(map));

        return SUCCESS;
    }
    
    public String delDamMonitoringpointsInfo() throws Exception {
        DamMonitoringpointsDao conn=new DamMonitoringpointsDao();
        Map<String,Object> map=new HashMap<String, Object>();
        try {
            conn.deleteById(this.getId());
            map.put("success", true);
            map.put("msg", "删除成功");
        } catch (Exception e) {
            map.put("success", false);
            map.put("msg", "删除失败");
            e.printStackTrace();
        }finally{
            conn.closeConn();
        }
        
        this.setResult(JSONObject.fromObject(map));
            
        return SUCCESS;
    }
	
}
