package com.model;

public class MonitoringpointsInfo {
	private int Id;
	private String Device_id;
	private String Name;
	private String Reservoir_id;
	private String Device_name;
	private String Reservoir_name;
	
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
    public String getReservoir_name() {
        return Reservoir_name;
    }
    public void setReservoir_name(String reservoir_name) {
        Reservoir_name = reservoir_name;
    }
    public String getDevice_id() {
        return Device_id;
    }
    public void setDevice_id(String device_id) {
        Device_id = device_id;
    }
    public String getReservoir_id() {
        return Reservoir_id;
    }
    public void setReservoir_id(String reservoir_id) {
        Reservoir_id = reservoir_id;
    }
}
