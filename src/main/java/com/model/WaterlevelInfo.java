package com.model;

import java.sql.Date;

public class WaterlevelInfo {
	
	private int Id;
	private String Name;
	private String Point;
	private double Waterline;
	private float Hourlyrainfall;
	private float Daylyrainfall;
	private float Evaporation;
	private String Time;
	private String Unuseflag;
	private String Fx;
	private float Fs;
	private float Wd;
	
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
	public String getPoint() {
		return Point;
	}
	public void setPoint(String point) {
		Point = point;
	}
	public double getWaterline() {
		return Waterline;
	}
	public void setWaterline(double waterline) {
		Waterline = waterline;
	}
	public float getHourlyrainfall() {
		return Hourlyrainfall;
	}
	public void setHourlyrainfall(float hourlyrainfall) {
		Hourlyrainfall = hourlyrainfall;
	}
	public float getDaylyrainfall() {
		return Daylyrainfall;
	}
	public void setDaylyrainfall(float daylyrainfall) {
		Daylyrainfall = daylyrainfall;
	}
	public float getEvaporation() {
		return Evaporation;
	}
	public void setEvaporation(float evaporation) {
		Evaporation = evaporation;
	}
	public String getTime() {
		return Time;
	}
	public void setTime(String time) {
		Time = time;
	}
	public String getUnuseflag() {
		return Unuseflag;
	}
	public void setUnuseflag(String unuseflag) {
		Unuseflag = unuseflag;
	}
    public String getFx() {
        return Fx;
    }
    public void setFx(String fx) {
        Fx = fx;
    }
    public float getFs() {
        return Fs;
    }
    public void setFs(float fs) {
        Fs = fs;
    }
    public float getWd() {
        return Wd;
    }
    public void setWd(float wd) {
        Wd = wd;
    }

}
