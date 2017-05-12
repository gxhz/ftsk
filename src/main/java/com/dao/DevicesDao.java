package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.model.DevicesInfo;
import com.util.ConnectionPool;

public class DevicesDao {
	
	private  ResultSet rs = null;
	private  PreparedStatement ps=null;
	private  Connection conn = null;
	
    public DevicesDao() throws SQLException {
        this.conn=ConnectionPool.getConnection();
    }
    
    public void closeConn() {
        ConnectionPool.release(this.conn, this.ps, this.rs);
    }
 
    public List<DevicesInfo> getSearchInfo(int page,int rows,String code,String name) throws SQLException {
    	if(page == 0) page = 1;
    	int start=(page-1)*rows;
    	int end=start+rows;
    	 	 
    	List<DevicesInfo> all=new ArrayList<DevicesInfo>();
    	String sql="select * from devices where code like '%"+code+"%' and name like '%"+name+"%' order by id limit ?,?";
    	this.ps=this.conn.prepareStatement(sql);
    	this.ps.setInt(1, start);
    	this.ps.setInt(2, end);
    	this.rs=ps.executeQuery();
    	while(rs.next()){
    	    DevicesInfo info=new DevicesInfo();
    		info.setId(rs.getInt(1));
    		info.setCode(rs.getString(2));
    		info.setName(rs.getString(3));
    		info.setDes(rs.getString(4));
    		all.add(info);
    	}
    	return all;
    }
 
    public List<DevicesInfo> getAllInfo() throws SQLException {
    	List<DevicesInfo> all=new ArrayList<DevicesInfo>();
    	String sql="select * from devices";
    	this.ps=this.conn.prepareStatement(sql);
    	this.rs=ps.executeQuery();
    	while(rs.next()){
    	    DevicesInfo info=new DevicesInfo();
    		info.setId(rs.getInt(1));
    		info.setCode(rs.getString(2));
    		info.setName(rs.getString(3));
    		info.setDes(rs.getString(4));
    		all.add(info);
    	}
    	return all;
    }
 
    public int getSearchRows(String code,String name) throws SQLException {
        String sql="select count(*) from devices where code like '%"+code+"%' and name like '%"+name+"%'";
        this.ps=this.conn.prepareStatement(sql);
        int rows=0;
        this.rs=ps.executeQuery();
 	    if(rs.next()){
 	        rows=rs.getInt(1);
	 	}
 	    return rows;
    }
 
    public int update(DevicesInfo info) throws SQLException {
        String sql="update devices set code=?,name=?,des=? where id=?";  
        this.ps=this.conn.prepareStatement(sql);
        ps.setString(1, info.getCode());
        ps.setString(2, info.getName());
        ps.setString(3, info.getDes());
        ps.setInt(4, info.getId());
        return ps.executeUpdate();
    }
 
    public int add(DevicesInfo info) throws SQLException {
        String sql="insert into devices(code,name,des) values(?,?,?)";
        this.ps=this.conn.prepareStatement(sql);
        ps.setString(1, info.getCode());
        ps.setString(2, info.getName());
        ps.setString(3, info.getDes());
        return ps.executeUpdate();
    }
 
    public int deleteById(int id) throws SQLException {
        String sql="delete from devices where id=?";
        this.ps=this.conn.prepareStatement(sql);
        ps.setInt(1, id);
        return ps.executeUpdate();
    }
 
}



