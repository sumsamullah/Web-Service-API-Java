package com.restfulapi;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "StationInformation")

public class StationInformation implements Serializable {
	private static final long serialVersionUID = 1L; 
	String station_id;
	int num_bikes_available;
	int num_bikes_disabled;
	int num_docks_available;
	int num_docks_disabled;
	public String getStation_id() {
		return station_id;
	}
	@XmlElement 
	public void setStation_id(String station_id) {
		this.station_id = station_id;
	}
	public int getNum_bikes_available() {
		return num_bikes_available;
	}
	@XmlElement 
	public void setNum_bikes_available(int num_bikes_available) {
		this.num_bikes_available = num_bikes_available;
	}
	public int getNum_bikes_disabled() {
		return num_bikes_disabled;
	}
	@XmlElement 
	public void setNum_bikes_disabled(int num_bikes_disabled) {
		this.num_bikes_disabled = num_bikes_disabled;
	}
	public int getNum_docks_available() {
		return num_docks_available;
	}
	@XmlElement 
	public void setNum_docks_available(int num_docks_available) {
		this.num_docks_available = num_docks_available;
	}
	public int getNum_docks_disabled() {
		return num_docks_disabled;
	}
	@XmlElement 
	public void setNum_docks_disabled(int num_docks_disabled) {
		this.num_docks_disabled = num_docks_disabled;
	}

}
