package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.model.MonitoringpointsInfo;
import com.util.ConnectionPool;

public class MonitoringpointsDao {
	
	private  ResultSet rs = null;
	private  PreparedStatement ps=null;
	private  Connection conn = null;
	
    public MonitoringpointsDao() throws SQLException {
        this.conn=ConnectionPool.getConnection();
    }
    
    public void closeConn() {
        ConnectionPool.release(this.conn, this.ps, this.rs);
    }
    
    public List<MonitoringpointsInfo> getSearchInfo(int page,int rows,String sbid,String name,String skid) throws SQLException {
    	if(page == 0) page = 1;
    	int start=(page-1)*rows;
    	int end=start+rows;
    	
    	String findwhere = "mps.id<>0";
    	if(!sbid.equals("")) findwhere += " and mps.id_device='"+sbid+"'";
    	if(!skid.equals("")) findwhere += " and mps.id_reservoir='"+skid+"'";
    	 	 
    	List<MonitoringpointsInfo> all=new ArrayList<MonitoringpointsInfo>();
    	String sql="select mps.id,mps.id_device,mps.id_reservoir," +
    			"(select name from devices where code=mps.id_device) as dname" +
    			",mps.name," +
    			"(select name from reservoirs where id=mps.id_reservoir) as rname" +
    			" from monitoringpoints as mps" +
    			" where mps.name like '%"+name+"%' and " + findwhere +
    			" order by mps.id limit ?,?";
    	this.ps=this.conn.prepareStatement(sql);
    	this.ps.setInt(1, start);
    	this.ps.setInt(2, end);
    	this.rs=ps.executeQuery();
    	while(rs.next()){
    	    MonitoringpointsInfo info=new MonitoringpointsInfo();
    		info.setId(rs.getInt(1));
    		info.setDevice_id(rs.getString(2));
    		info.setReservoir_id(rs.getString(3));
    		info.setDevice_name(rs.getString(4));
    		info.setName(rs.getString(5));
    		info.setReservoir_name(rs.getString(6));
    		all.add(info);
    	}
    	return all;
    }
 
    public List<MonitoringpointsInfo> getAllInfo() throws SQLException {
    	List<MonitoringpointsInfo> all=new ArrayList<MonitoringpointsInfo>();
    	String sql="select * from monitoringpoints";
    	this.ps=this.conn.prepareStatement(sql);
    	this.rs=ps.executeQuery();
    	while(rs.next()){
    	    MonitoringpointsInfo info=new MonitoringpointsInfo();
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
        if(!skids.equals("")) skidfindwhere = " and id_reservoir='"+skids+"'";
        
        String sql="select count(*) from monitoringpoints where name like '%"+name+"%'"+sbidfindwhere+skidfindwhere;
        this.ps=this.conn.prepareStatement(sql);
        int rows=0;
        this.rs=ps.executeQuery();
 	    if(rs.next()){
 	        rows=rs.getInt(1);
	 	}
 	    return rows;
    }
 
    public int update(MonitoringpointsInfo info) throws SQLException {
        String sql="update monitoringpoints set id_device=?,name=?,id_reservoir=? where id=?";  
        this.ps=this.conn.prepareStatement(sql);
        ps.setString(1, info.getDevice_id());
        ps.setString(2, info.getName());
        ps.setString(3, info.getReservoir_id());
        ps.setInt(4, info.getId());
        return ps.executeUpdate();
    }
 
    public int add(MonitoringpointsInfo info) throws SQLException {
        String sql="insert into monitoringpoints(id_device,name,id_reservoir) values(?,?,?)";
        this.ps=this.conn.prepareStatement(sql);
        ps.setString(1, info.getDevice_id());
        ps.setString(2, info.getName());
        ps.setString(3, info.getReservoir_id());
        return ps.executeUpdate();
    }
 
    public int deleteById(int id) throws SQLException {
        String sql="delete from monitoringpoints where id=?";
        this.ps=this.conn.prepareStatement(sql);
        ps.setInt(1, id);
        return ps.executeUpdate();
    }
 
}



