package com.restfulapi;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.glassfish.hk2.osgiresourcelocator.ServiceLoader;

public class DAL  {
	
	public static String getconnectionstring()
	{
		BufferedReader br;
		try {
		    br = new BufferedReader(new FileReader("C:\\Config\\Config.txt"));	
		    StringBuilder sb = new StringBuilder();
		    String line = br.readLine();

		    while (line != null) {
		        sb.append(line);
		        sb.append(System.lineSeparator());
		        line = br.readLine();
		    }
		    String config = sb.toString().trim();
		    br.close();
		    return config;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return null;
	}
	
	public ArrayList<StationInformation> getstationInformation()
	{
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			String connectionUrl = getconnectionstring();
			Connection con = DriverManager.getConnection(connectionUrl);
			System.out.println("Connected.");
			String SQL = "SELECT * FROM stationinformation";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(SQL);

			ArrayList<StationInformation> res = new ArrayList<StationInformation>(); 
			while (rs.next()) {
				StationInformation obj = new StationInformation();
				obj.setStation_id(rs.getString(1));
				obj.setNum_bikes_available(rs.getInt(2));
				obj.setNum_bikes_disabled(rs.getInt(3));
				obj.setNum_docks_available(rs.getInt(4));
				obj.setNum_docks_disabled(rs.getInt(5));
				res.add(obj);
				
			}
			return res;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public ArrayList<Station> getstation()
	{
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			String connectionUrl = getconnectionstring();
			Connection con = DriverManager.getConnection(connectionUrl);
			System.out.println("Connected.");
			String SQL = "SELECT * FROM station";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(SQL);

			ArrayList<Station> res = new ArrayList<Station>(); 
			while (rs.next()) {
				Station obj = new Station();
				obj.setStation_id(rs.getString(1));
				obj.setName(rs.getString(2));
				obj.setRegion_id(rs.getInt(3));
				obj.setShort_name(rs.getString(4));
				obj.setLon(rs.getFloat(5));
				obj.setLat(rs.getFloat(6));
				obj.setCapacity(rs.getInt(7));
				res.add(obj);
				
			}
			return res;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public ArrayList<stationhistory> selectStationInformationBymonth(int month) {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			String connectionUrl = getconnectionstring();
			Connection con = DriverManager.getConnection(connectionUrl);
			
			Date date = new Date(LocalDateTime.now().getYear(), LocalDateTime.now().getMonthValue(), LocalDateTime.now().getDayOfMonth());
			String SQL = "SELECT * FROM stationhistory where Month(Date)= '"+month+"'"; 
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(SQL);

			ArrayList<stationhistory> list = new ArrayList<stationhistory>();
			while (rs.next()) {
				stationhistory sh = new stationhistory();
				sh.setStation_id(rs.getString(1));
				sh.setDate(rs.getDate(2));
				sh.setNum_of_bike_rides(rs.getInt(3));
				sh.setNum_of_disabled_bikes(rs.getInt(4));
				
				list.add(sh);
				
			}
			return list;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	

}
