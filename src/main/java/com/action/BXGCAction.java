package com.action;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.dao.BXGCDao;
import com.model.bxgc.BXFBTInfo;
import com.model.bxgc.BXNBBTJInfo;
import com.model.bxgc.BXNGCXJInfo;
import com.model.bxgc.BXRTDataDetails;
import com.model.bxgc.BXSDBBTJInfo;
import com.model.bxgc.SXWYDCXDataDetails;
import com.opensymphony.xwork2.ActionSupport;

public class BXGCAction extends ActionSupport{
	
	private static final long serialVersionUID = 1L;
	
	private int page;
	private int rows;
	private int Id;
	
	private String name="";  //查询条件
	private String names="";
	private String sdate="";
	private String edate="";
	private String bname="";  //变形观测类型
	private String dtype="";
	
	private String flag="0";  //报表类型，1为时段报表，2为日报表，3为月报表，4为年报表
	
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
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
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

    public String getBname() {
        return bname;
    }

    public void setBname(String bname) {
        this.bname = bname;
    }

    public String getDtype() {
        return dtype;
    }

    public void setDtype(String dtype) {
        this.dtype = dtype;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public JSONArray getList() {
        return list;
    }
    public void setList(JSONArray list) {
        this.list = list;
    }
    
    //获取变形观测数据
    public String getBXRTData() throws Exception {
        List<BXRTDataDetails> all = null;
        BXGCDao conn=new BXGCDao();
        try {
            all=conn.getBXRTData(this.name);
            JSONArray array = JSONArray.fromObject(all);
            this.setList(array);
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            conn.closeConn();
        }
        return SUCCESS;
    }
    
    //获取时段、日、月报表统计
    public String getBXSDBBTJData() throws Exception {
        List<BXSDBBTJInfo> all = null;
        BXGCDao conn = new BXGCDao();
        try {
            all = conn.getBXSDBBTJData(flag, names, sdate, edate, bname, dtype);
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
    public String exportBXSDBBTJToExcel() throws Exception {
        BXGCDao conn = new BXGCDao();
        try {
            conn.exportBXSDBBTJToExcel(flag, names, sdate, edate, bname, dtype);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.closeConn();
        }
        return null;
    }
    
    //获取年报表统计
    public String getBXNBBTJData() throws Exception {
        List<BXNBBTJInfo> all = null;
        BXGCDao conn = new BXGCDao();
        try {
            all = conn.getBXNBBTJData(names, sdate, edate, bname, dtype);
            JSONArray array = JSONArray.fromObject(all);
            this.setList(array);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.closeConn();
        }
        return SUCCESS;
    }
    
    //导出年报表至Excel
    public String exportBXNBBTJToExcel() throws Exception {
        BXGCDao conn = new BXGCDao();
        try {
            conn.exportBXNBBTJToExcel(names, sdate, edate, bname, dtype);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.closeConn();
        }
        return null;
    }
    
    //获取年过程线
    public String getBXNGCXData() throws Exception {
        List<BXNGCXJInfo> all = null;
        BXGCDao conn = new BXGCDao();
        try {
            all = conn.getBXNGCXData(names, sdate, edate, bname, dtype);
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
    public String getBXFBTData() throws Exception {
        List<BXFBTInfo> all = null;
        BXGCDao conn = new BXGCDao();
        try {
            all = conn.getBXFBTData(names, sdate, edate, bname, dtype);
            JSONArray array = JSONArray.fromObject(all);
            this.setList(array);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.closeConn();
        }
        return SUCCESS;
    }
    
    //获取竖向位移等值线数据
    public String getSXWYDCXData() throws Exception {
        List<BXRTDataDetails> all = null;
        BXGCDao conn = new BXGCDao();
        try {
            all = conn.getSXWYDCXData(sdate, dtype);
            JSONArray array = JSONArray.fromObject(all);
            this.setList(array);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.closeConn();
        }
        return SUCCESS;
    }
    
    //获取竖向位移等值线数据
    public String getSXWYDCXData1() throws Exception {
        List<SXWYDCXDataDetails> all = null;
        BXGCDao conn = new BXGCDao();
        try {
            all = conn.getSXWYDCXData1(sdate, dtype);
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
