package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.model.DamMonitoringpointsInfo;
import com.util.ConnectionPool;

public class DamMonitoringpointsDao {
	
	private  ResultSet rs = null;
	private  PreparedStatement ps=null;
	private  Connection conn = null;
	
    public DamMonitoringpointsDao() throws SQLException {
        this.conn=ConnectionPool.getConnection();
    }
    
    public void closeConn() {
        ConnectionPool.release(this.conn, this.ps, this.rs);
    }
 
    public List<DamMonitoringpointsInfo> getSearchInfo(int page,int rows,String sbid,String name,String skid) throws SQLException {
    	if(page == 0) page = 1;
    	int start=(page-1)*rows;
    	int end=start+rows;
    	
    	String findwhere = "dms.id<>0";
    	if(!sbid.equals("")) findwhere += " and dms.id_device='"+sbid+"'";
    	if(!skid.equals("")) findwhere += " and dms.id_section='"+skid+"'";
    	 	 
    	List<DamMonitoringpointsInfo> all=new ArrayList<DamMonitoringpointsInfo>();
    	String sql="select dms.id,dms.id_device,dms.id_section," +
    			"(select name from devices where code=dms.id_device) as dname" +
    			",dms.name," +
    			"(select name from sections where id=dms.id_section) as rname" +
    			" from dammonitoringpoints as dms" +
    			" where dms.name like '%"+name+"%' and " + findwhere +
    			" order by dms.id limit ?,?";
    	this.ps=this.conn.prepareStatement(sql);
    	this.ps.setInt(1, start);
    	this.ps.setInt(2, end);
    	this.rs=ps.executeQuery();
    	while(rs.next()){
    	    DamMonitoringpointsInfo info=new DamMonitoringpointsInfo();
    		info.setId(rs.getInt(1));
    		info.setDevice_id(rs.getString(2));
    		info.setSections_id(rs.getString(3));
    		info.setDevice_name(rs.getString(4));
    		info.setName(rs.getString(5));
    		info.setSections_name(rs.getString(6));
    		all.add(info);
    	}
    	return all;
    }
 
    public List<DamMonitoringpointsInfo> getAllInfo() throws SQLException {
    	List<DamMonitoringpointsInfo> all=new ArrayList<DamMonitoringpointsInfo>();
    	String sql="select * from dammonitoringpoints";
    	this.ps=this.conn.prepareStatement(sql);
    	this.rs=ps.executeQuery();
    	while(rs.next()){
    	    DamMonitoringpointsInfo info=new DamMonitoringpointsInfo();
    		info.setId(rs.getInt(1));
    		info.setName(rs.getString(3));
    		all.add(info);
    	}
    	return all;
    }
 
    public int getSearchRows(String sbids,String name,String skids) throws SQLException {
        String sbidfindwhere="";
        if(!sbids.equals("")) sbidfindwhere = " and id_device='"+sbids+"'";
        String skidfindwhere="";
        if(!skids.equals("")) skidfindwhere = " and id_section='"+skids+"'";
        
        String sql="select count(*) from dammonitoringpoints where name like '%"+name+"%'"+sbidfindwhere+skidfindwhere;
        this.ps=this.conn.prepareStatement(sql);
        int rows=0;
        this.rs=ps.executeQuery();
 	    if(rs.next()){
 	        rows=rs.getInt(1);
	 	}
 	    return rows;
    }
 
    public int update(DamMonitoringpointsInfo info) throws SQLException {
        String sql="update dammonitoringpoints set id_device=?,name=?,id_section=? where id=?";  
        this.ps=this.conn.prepareStatement(sql);
        ps.setString(1, info.getDevice_id());
        ps.setString(2, info.getName());
        ps.setString(3, info.getSections_id());
        ps.setInt(4, info.getId());
        return ps.executeUpdate();
    }
 
    public int add(DamMonitoringpointsInfo info) throws SQLException {
        String sql="insert into dammonitoringpoints(id_device,name,id_section) values(?,?,?)";
        this.ps=this.conn.prepareStatement(sql);
        ps.setString(1, info.getDevice_id());
        ps.setString(2, info.getName());
        ps.setString(3, info.getSections_id());
        return ps.executeUpdate();
    }
 
    public int deleteById(int id) throws SQLException {
        String sql="delete from dammonitoringpoints where id=?";
        this.ps=this.conn.prepareStatement(sql);
        ps.setInt(1, id);
        return ps.executeUpdate();
    }
 
}



