package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.model.ParametersInfo;
import com.util.ConnectionPool;

public class ParametersDao {
	
	private  ResultSet rs = null;
	private  PreparedStatement ps=null;
	private  Connection conn = null;
	
    public ParametersDao() throws SQLException {
        this.conn=ConnectionPool.getConnection();
    }
    
    public void closeConn() {
        ConnectionPool.release(this.conn, this.ps, this.rs);
    }
 
    public List<ParametersInfo> getSearchInfo(int page,int rows,String sbid) throws SQLException {
    	if(page == 0) page = 1;
    	int start=(page-1)*rows;
    	int end=start+rows;
    	
    	String findwhere="ps.id <> 0";
    	if(!sbid.equals("")) findwhere += " and ps.id_mp='"+sbid+"'";
    	 	 
    	List<ParametersInfo> all=new ArrayList<ParametersInfo>();
    	String sql="select ps.*,ms.name from parameters as ps left join monitoringpoints as ms on ms.id=ps.id_mp where "+
    	        findwhere+" order by id limit "+start+","+end;
    	this.ps=this.conn.prepareStatement(sql);
    	this.rs=ps.executeQuery();
    	while(rs.next()){
    	    ParametersInfo info=new ParametersInfo();
    		info.setId(rs.getInt(1));
    		info.setAlarm_level(rs.getDouble(2));
    		info.setWide_b(rs.getDouble(3));
    		info.setCoefficient_rag(rs.getDouble(4));
    		info.setCoefficient_bs(rs.getDouble(5));
    		info.setCoefficient_ss1(rs.getDouble(6));
    		info.setCoefficient_ss2(rs.getDouble(7));
    		info.setCdid(rs.getString(8));
    		info.setCdname(rs.getString(9));
    		all.add(info);
    	}
    	return all;
    }
    
    public int getSearchRows(String sbid) throws SQLException {
        String findwhere="id <> 0";
        if(!sbid.equals("")) findwhere += " and id_mp='"+sbid+"'";
        
        String sql="select count(*) from parameters where "+findwhere;
        this.ps=this.conn.prepareStatement(sql);
        int rows=0;
        this.rs=ps.executeQuery();
        if(rs.next()){
            rows=rs.getInt(1);
        }
        return rows;
    }
 
    public List<ParametersInfo> getAllInfo() throws SQLException {
    	List<ParametersInfo> all=new ArrayList<ParametersInfo>();
    	String sql="select * from parameters";
    	this.ps=this.conn.prepareStatement(sql);
    	this.rs=ps.executeQuery();
    	while(rs.next()){
    	    ParametersInfo info=new ParametersInfo();
    		info.setId(rs.getInt(1));
    		all.add(info);
    	}
    	return all;
    }
 
    public int update(ParametersInfo info,String id) throws SQLException {
        String sql="update parameters set id_mp='"+info.getCdid()+"',alarm_level="+info.getAlarm_level()+
                ",wide_b="+info.getWide_b()+",coefficient_rag="+info.getCoefficient_rag()+",coefficient_bs="+
                info.getCoefficient_bs()+",coefficient_ss1="+info.getCoefficient_ss1()+",coefficient_ss2="+
                info.getCoefficient_ss2()+" where id="+id;  
        this.ps=this.conn.prepareStatement(sql);
        return ps.executeUpdate();
    }
 
    //添加参数记录
    public int add(ParametersInfo info) throws SQLException {
        String sql="insert into parameters(id_mp,alarm_level,wide_b,coefficient_rag,coefficient_bs,coefficient_ss1,coefficient_ss2) " +
        		"values('"+info.getCdid()+"',"+info.getAlarm_level()+","+info.getWide_b()+","+info.getCoefficient_rag()+","+
        		info.getCoefficient_bs()+","+info.getCoefficient_ss1()+","+info.getCoefficient_ss2()+")";
        this.ps=this.conn.prepareStatement(sql);
        return ps.executeUpdate();
    }
 
    //根据ID删除参数记录
    public int deleteById(String id) throws SQLException {
        String sql="delete from parameters where id="+id;
        this.ps=this.conn.prepareStatement(sql);
        return ps.executeUpdate();
    }
 
}



