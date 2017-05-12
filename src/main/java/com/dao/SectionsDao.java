package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.model.SectionsInfo;
import com.util.ConnectionPool;

public class SectionsDao {
	
	private  ResultSet rs = null;
	private  PreparedStatement ps=null;
	private  Connection conn = null;
	
    public SectionsDao() throws SQLException {
        this.conn=ConnectionPool.getConnection();
    }
    
    public void closeConn() {
        ConnectionPool.release(this.conn, this.ps, this.rs);
    }
 
    public List<SectionsInfo> getSearchInfo(int page,int rows,String name) throws SQLException {
    	if(page == 0) page = 1;
    	int start=(page-1)*rows;
    	int end=start+rows;
    	 	 
    	List<SectionsInfo> all=new ArrayList<SectionsInfo>();
    	String sql="select * from sections where name like '%"+name+"%' order by id limit ?,?";
    	this.ps=this.conn.prepareStatement(sql);
    	this.ps.setInt(1, start);
    	this.ps.setInt(2, end);
    	this.rs=ps.executeQuery();
    	while(rs.next()){
    	    SectionsInfo info=new SectionsInfo();
    		info.setId(rs.getInt(1));
    		info.setName(rs.getString(2));
    		info.setDes(rs.getString(3));
    		all.add(info);
    	}
    	return all;
    }
 
    public List<SectionsInfo> getAllInfo() throws SQLException {
    	List<SectionsInfo> all=new ArrayList<SectionsInfo>();
    	String sql="select * from sections";
    	this.ps=this.conn.prepareStatement(sql);
    	this.rs=ps.executeQuery();
    	while(rs.next()){
    	    SectionsInfo info=new SectionsInfo();
    		info.setId(rs.getInt(1));
    		info.setName(rs.getString(2));
    		info.setDes(rs.getString(3));
    		all.add(info);
    	}
    	return all;
    }
 
    public int getSearchRows(String name) throws SQLException {
        String sql="select count(*) from sections where name like '%"+name+"%'";
        this.ps=this.conn.prepareStatement(sql);
        int rows=0;
        this.rs=ps.executeQuery();
 	    if(rs.next()){
 	        rows=rs.getInt(1);
	 	}
 	    return rows;
    }
 
    public int update(SectionsInfo info) throws SQLException {
        String sql="update sections set name=?,des=? where id=?";  
        this.ps=this.conn.prepareStatement(sql);
        ps.setString(1, info.getName());
        ps.setString(2, info.getDes());
        ps.setInt(3, info.getId());
        return ps.executeUpdate();
    }
 
    public int add(SectionsInfo info) throws SQLException {
        String sql="insert into sections(name,des) values(?,?)";
        this.ps=this.conn.prepareStatement(sql);
        ps.setString(1, info.getName());
        ps.setString(2, info.getDes());
        return ps.executeUpdate();
    }
 
    public int deleteById(int id) throws SQLException {
        String sql="delete from sections where id=?";
        this.ps=this.conn.prepareStatement(sql);
        ps.setInt(1, id);
        return ps.executeUpdate();
    }
 
}



