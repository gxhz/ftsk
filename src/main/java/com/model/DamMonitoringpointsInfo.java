package com.model;

public class DamMonitoringpointsInfo {
	private int Id;
	private String Device_id;
	private String Name;
	private String Sections_id;
	private String Device_name;
	private String Sections_name;
	
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
    public String getDevice_name() {
        return Device_name;
    }
    public void setDevice_name(String device_name) {
        Device_name = device_name;
    }
    public String getDevice_id() {
        return Device_id;
    }
    public void setDevice_id(String device_id) {
        Device_id = device_id;
    }
    public String getSections_id() {
        return Sections_id;
    }
    public void setSections_id(String sections_id) {
        Sections_id = sections_id;
    }
    public String getSections_name() {
        return Sections_name;
    }
    public void setSections_name(String sections_name) {
        Sections_name = sections_name;
    }
}
