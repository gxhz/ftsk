package com.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dao.SYQJCDao;
import com.model.JYLInfo;
import com.model.KSWInfo;
import com.model.SyqInfo;
import com.model.syq.CurDayJYLInfo;
import com.model.syq.JYLNBBTJInfo;
import com.model.syq.JYLRTDataInfo;
import com.model.syq.JYLSDBBTJInfo;
import com.model.syq.JYLSDGCXJInfo;
import com.model.syq.KSWNBBTJInfo;
import com.model.syq.KSWNGCXJInfo;
import com.model.syq.KSWSDBBTJInfo;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.opensymphony.xwork2.ActionSupport;

public class SYQJCAction extends ActionSupport{
	private static final long serialVersionUID = 1L;
	
	private int page;
	private int rows;
	public JSONObject result;
	public JSONArray list;
	
	private String Flag="0";  //报表类型，1为时段报表，2为日报表，3为月报表，4为年报表
	private String code="";
	private String name="";
	private String sdate="";
	private String edate="";
	
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
	public String getFlag() {
        return Flag;
    }
    public void setFlag(String flag) {
        Flag = flag;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
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
    
    //获取库水位实时数据
	public String getSWAction() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List<SyqInfo> all = new ArrayList<SyqInfo>();
		SYQJCDao conn=new SYQJCDao();
		try {	
            all = conn.getSWAction();
            map.put("total", all.size());
            map.put("rows", all);   
			this.setResult(JSONObject.fromObject(map));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
            conn.closeConn();
        }
		return SUCCESS;
	}
	
	//获取降雨量实时数据
	public String getYLAction() throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        List<JYLInfo> all = new ArrayList<JYLInfo>();
        SYQJCDao conn=new SYQJCDao();
        try {   
            all = conn.getYLAction();
            map.put("total", all.size());
            map.put("rows", all);
            this.setResult(JSONObject.fromObject(map));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.closeConn();
        }
        return SUCCESS;
    }
	
	//获取当天库水位、库容量相关信息
	public String getKSWAction() throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        List<KSWInfo> all = new ArrayList<KSWInfo>();
        SYQJCDao conn=new SYQJCDao();
        try {   
            all = conn.getKSWAction();
            map.put("total", all.size());
            map.put("rows", all);
            this.setResult(JSONObject.fromObject(map));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.closeConn();
        }
        return SUCCESS;
    }
	
	//获取当天24小时降雨量信息
    public String getJYLAction() throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        List<CurDayJYLInfo> all = new ArrayList<CurDayJYLInfo>();
        SYQJCDao conn=new SYQJCDao();
        try {   
            all = conn.getJYLAction();
            map.put("total", all.size());
            map.put("rows", all);
            this.setResult(JSONObject.fromObject(map));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.closeConn();
        }
        return SUCCESS;
    }
	
	//获取库水位时段、日、月报表统计
    public String getKSWSDBBTJData() throws Exception {
        List<KSWSDBBTJInfo> all = null;
        SYQJCDao conn = new SYQJCDao();
        try {
            all = conn.getKSWSDBBTJData(sdate, edate);
            JSONArray array = JSONArray.fromObject(all);
            this.setList(array);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.closeConn();
        }
        return SUCCESS;
    }
    
    //导出库水位时段、日、月报表统计至Excel
    public String exportKSWSDBBTJToExcel() throws Exception {
        SYQJCDao conn = new SYQJCDao();
        try {
            conn.exportKSWSDBBTJToExcel(Flag, sdate, edate);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.closeConn();
        }
        return null;
    }
    
    //获取库水位年报表统计
    public String getKSWNBBTJData() throws Exception {
        List<KSWNBBTJInfo> all = null;
        SYQJCDao conn = new SYQJCDao();
        try {
            all = conn.getKSWNBBTJData(Flag, code, sdate, edate);
            JSONArray array = JSONArray.fromObject(all);
            this.setList(array);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.closeConn();
        }
        return SUCCESS;
    }
    
    //导出库水位年报表至Excel
    public String exportKSWNBBTJToExcel() throws Exception {
        SYQJCDao conn = new SYQJCDao();
        try {
            name = new String(name.getBytes("iso-8859-1"), "UTF-8");
            conn.exportKSWNBBTJToExcel(Flag, code, name, sdate, edate);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.closeConn();
        }
        return null;
    }
    
    //获取降雨量时段、日、月报表统计
    public String getJYLSDBBTJData() throws Exception {
        List<JYLSDBBTJInfo> all = null;
        SYQJCDao conn = new SYQJCDao();
        try {
            all = conn.getJYLSDBBTJData(sdate, edate);
            JSONArray array = JSONArray.fromObject(all);
            this.setList(array);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.closeConn();
        }
        return SUCCESS;
    }
    
    //导出降雨量时段、日、月报表统计至Excel
    public String exportJYLSDBBTJToExcel() throws Exception {
        SYQJCDao conn = new SYQJCDao();
        try {
            conn.exportJYLSDBBTJToExcel(Flag, sdate, edate);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.closeConn();
        }
        return null;
    }
    
    //获取降雨量年报表统计
    public String getJYLNBBTJData() throws Exception {
        List<JYLNBBTJInfo> all = null;
        SYQJCDao conn = new SYQJCDao();
        try {
            all = conn.getJYLNBBTJData(Flag, code, sdate, edate);
            JSONArray array = JSONArray.fromObject(all);
            this.setList(array);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.closeConn();
        }
        return SUCCESS;
    }
    
    //导出降雨量年报表至Excel
    public String exportJYLNBBTJToExcel() throws Exception {
        SYQJCDao conn = new SYQJCDao();
        try {
            name = new String(name.getBytes("iso-8859-1"), "UTF-8");
            conn.exportJYLNBBTJToExcel(Flag, code, name, sdate, edate);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.closeConn();
        }
        return null;
    }
    
    //获取库水位年过程线
    public String getKSWNGCXData() throws Exception {
        List<KSWNGCXJInfo> all = null;
        SYQJCDao conn = new SYQJCDao();
        try {
            all = conn.getKSWNGCXData(code, sdate, edate);
            JSONArray array = JSONArray.fromObject(all);
            this.setList(array);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.closeConn();
        }
        return SUCCESS;
    }
    
    //获取降雨量时段、月过程线
    public String getJYLSDGCXData() throws Exception {
        List<JYLSDGCXJInfo> all = null;
        SYQJCDao conn = new SYQJCDao();
        try {
            all = conn.getJYLSDGCXData(code, sdate, edate);
            JSONArray array = JSONArray.fromObject(all);
            this.setList(array);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.closeConn();
        }
        return SUCCESS;
    }
    
    //获取雨量站今日降雨量实时数据
    public String getJYLRTData() throws Exception {
        List<JYLRTDataInfo> all = null;
        SYQJCDao conn = new SYQJCDao();
        try {
            all = conn.getJYLRTData();
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
