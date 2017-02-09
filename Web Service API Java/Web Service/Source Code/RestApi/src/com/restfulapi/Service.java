package com.restfulapi;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.reflect.*;

@Path("/Service")
public class Service {
	@GET
	@Path("/overall-stats")
	@Produces(MediaType.APPLICATION_XML)
	public String getOverallStats() throws IOException, JSONException {
				
		ArrayList<StationInformation> SI = new DAL().getstationInformation();
		int TotalBikes = 0;
		int TotalDocks = 0;
		
		for (StationInformation a : SI) {
			TotalBikes += a.getNum_bikes_available();
			TotalDocks += a.getNum_docks_available();
		}

		return "Total Bikes=" + Integer.toString(TotalBikes) + "\nTotal Docks=" + Integer.toString(TotalDocks);
		
	}

	@GET
	@Path("/station-stats/{station_id}")
	@Produces(MediaType.APPLICATION_XML)
	public String getstationStats(@PathParam("station_id") String station_id) throws IOException, JSONException  {
		
		ArrayList<StationInformation> SI = new DAL().getstationInformation();
		StationInformation station=null;
		for (StationInformation a : SI) {
			if(a.getStation_id().toString().equals(station_id))
			{
				station = a;
				
				break;
			}
		
		}
		if(station==null)
		{
			return "No station found for the provided ID";
		}
		return "StationID: "+ station_id + "\nTotal Bikes=" + Integer.toString(station.getNum_bikes_available()) + "\nTotal Docks=" + Integer.toString(station.getNum_docks_available());
	}
	
	@GET
	@Path("/stations-with-capacity/{num_bikes}")
	@Produces(MediaType.APPLICATION_XML)
	public String getstationwithcapcity(@PathParam("num_bikes") int num_bikes) throws IOException, JSONException  {
		
		
		ArrayList<StationInformation> SI = new DAL().getstationInformation();
		Gson gson = new Gson();
		
		JSONArray arr = new JSONArray(gson.toJson(SI));
		JSONArray resultarr = new JSONArray();
		for(int i=0;i<arr.length();i++)
		{
			JSONObject obj = arr.getJSONObject(i);
			if(obj.getInt("num_bikes_available")>=num_bikes)
			{
				resultarr.put(obj);
			}
			
			
		}
		if(resultarr.length()==0)
		{
			return "All stations have less Bikes available";
		}
		return resultarr.toString();
	}
	@GET
	@Path("/closest-station/{lat}/{lon}")
	@Produces(MediaType.APPLICATION_XML)
	public String getcloseststation(@PathParam("lat") float lat,@PathParam("lon") float lon) throws IOException, JSONException  {
		
		ArrayList<Station> S = new DAL().getstation();
		Gson gson = new Gson();
		
		JSONArray arr = new JSONArray(gson.toJson(S));
		int index=0;
		float distance = Float.MAX_VALUE;
		for(int i=0;i<arr.length();i++)
		{
			JSONObject obj = arr.getJSONObject(i);
			float lat2 = (float)obj.getDouble("lat");
			float lon2 = (float)obj.getDouble("lon");
			float tempdist = (float)Math.sqrt((lat-lat2)*(lat-lat2) + (lon-lon2)*(lon-lon2));
			if(tempdist<distance)
			{
				distance=tempdist;
				index=i;
			}
			
			
			
		}
		
		return arr.getJSONObject(index).toString();
	}
	
	
	@GET
	@Path("/closest-station-street/{streetname}")
	@Produces(MediaType.APPLICATION_XML)
	public String getcloseststationtostreet(@PathParam("streetname") String streetname) throws IOException, JSONException  {
		ServiceBO sbo = new ServiceBO();
		JSONObject jobj = sbo.readJsonFromUrl("https://maps.googleapis.com/maps/api/geocode/json?address="+streetname+"&key=AIzaSyD8_gJoJRyZeXGK0jMBwOS-ORNfYXvK9Nw");
		
		JSONObject LatLon = jobj.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getJSONObject("location");
		float lat = (float)LatLon.getDouble("lat");
		float lon = (float)LatLon.getDouble("lng");
		
		ArrayList<Station> S = new DAL().getstation();
		Gson gson = new Gson();
		
		JSONArray arr = new JSONArray(gson.toJson(S));
		int index=0;
		float distance = Float.MAX_VALUE;
		for(int i=0;i<arr.length();i++)
		{
			JSONObject obj = arr.getJSONObject(i);
			float lat2 = (float)obj.getDouble("lat");
			float lon2 = (float)obj.getDouble("lon");
			float tempdist = (float)Math.sqrt((lat-lat2)*(lat-lat2) + (lon-lon2)*(lon-lon2));
			if(tempdist<distance)
			{
				distance=tempdist;
				index=i;
			}
			
			
			
		}
		
		return arr.getJSONObject(index).toString();
	}
	
	@GET
	@Path("/monthly-stats/{month}")
	@Produces(MediaType.APPLICATION_XML)
	public String getmonthlystats(@PathParam("month") int month) throws IOException, JSONException  {
		
		try
		{
		ArrayList<stationhistory> arr = new DAL().selectStationInformationBymonth(month) ;
		int TotalBikes = 0;
		int TotalDis = 0;
		
		for (stationhistory a : arr) {
			TotalBikes += a.getNum_of_bike_rides();
			TotalDis += a.getNum_of_disabled_bikes();
		}
		return "Total Bike Rides=" + Integer.toString(TotalBikes) + "\nTotal Disabled=" + Integer.toString(TotalDis);
		}
		catch(Exception ex)
		{
			
		}
		return "Error";
		
		
	}
	@GET
	@Path("/popular-station/{month}")
	@Produces(MediaType.APPLICATION_XML)
	public String getpopularstation(@PathParam("month") int month) throws IOException, JSONException  {
		
		try
		{
		ArrayList<stationhistory> arr = new DAL().selectStationInformationBymonth(month) ;
		int maxbikerides=0;
		String StationID=null;
		for (int i=0;i<arr.size();i++) {
			stationhistory a = arr.get(i);
			if(a.getNum_of_bike_rides()>maxbikerides)
			{
				maxbikerides = a.getNum_of_bike_rides();
				StationID = a.getStation_id();
			}
		}
		
		return "Popular station ID: " +StationID;
		}
		catch(Exception ex)
		{
			
		}
		return "Error";
		
		
	}


}
