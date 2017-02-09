import java.awt.Cursor;
import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;


public class DAL {

	
	public static String getconnectionstring()
	{
		BufferedReader br;
		try {
		    br = new BufferedReader(new FileReader("Config.txt"));	
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
	
	public void deletefromStationHistory() {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			
			String connectionUrl = getconnectionstring();
			Connection con = DriverManager.getConnection(connectionUrl);
			Date date = new Date(LocalDateTime.now().getYear(), LocalDateTime.now().getMonthValue(), LocalDateTime.now().getDayOfMonth());
			PreparedStatement p = con.prepareStatement("delete from StationHistory where date='"+date.toString()+"'");
			p.executeUpdate();
		} catch (Exception ex) {

		}
	}
	public void deletefromStationInformation() {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			
			String connectionUrl = getconnectionstring();
			Connection con = DriverManager.getConnection(connectionUrl);

			PreparedStatement p = con.prepareStatement("delete from StationInformation");
			p.executeUpdate();
		} catch (Exception ex) {
			ex.printStackTrace();

		}
	}
	public void deletefromStation() {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			
			
			String connectionUrl = getconnectionstring();
			System.out.println(connectionUrl);
			Connection con = DriverManager.getConnection(connectionUrl);

			PreparedStatement p = con.prepareStatement("delete from Station");
			p.executeUpdate();
		} catch (Exception ex) {

		}
	}

	public void insertStationInformation(ArrayList<StationInformation> input) {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			
			String connectionUrl = getconnectionstring();
			Connection con = DriverManager.getConnection(connectionUrl);

			con.setAutoCommit(false);
			PreparedStatement prepStmt = con.prepareStatement(
					"insert into StationInformation(station_id,num_bikes_available,num_bikes_disabled,num_docks_available,num_docks_disabled) values (?,?,?,?,?)");

			for (StationInformation si : input) {
				prepStmt.setString(1, si.getStation_id());
				prepStmt.setInt(2, si.getNum_bikes_available());
				prepStmt.setInt(3, si.getNum_bikes_disabled());
				prepStmt.setInt(4, si.getNum_docks_available());
				prepStmt.setInt(5, si.getNum_docks_disabled());
				prepStmt.addBatch();
			}
			prepStmt.executeBatch();
			
			con.commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void insertStation(ArrayList<Station> input) {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			
			String connectionUrl = getconnectionstring();
			Connection con = DriverManager.getConnection(connectionUrl);

			con.setAutoCommit(false);
			PreparedStatement prepStmt = con.prepareStatement(
					"insert into Station(station_id,name,region_id,short_name,lon,lat,capacity) values (?,?,?,?,?,?,?)");

			for (Station si : input) {
				prepStmt.setString(1, si.getStation_id());
				prepStmt.setString(2, si.getName());
				prepStmt.setInt(3, si.getRegion_id());
				prepStmt.setString(4, si.getShort_name());
				prepStmt.setFloat(5, si.getLon());
				prepStmt.setFloat(6, si.getLat());
				prepStmt.setInt(7, si.getCapacity());
				prepStmt.addBatch();
			}
			prepStmt.executeBatch();
			
			con.commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}
	public void insertStationHistory(ArrayList<stationhistory> input) {
		try {
			
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			
			String connectionUrl = getconnectionstring();
			Connection con = DriverManager.getConnection(connectionUrl);

			con.setAutoCommit(false);
			Date date = new Date(LocalDateTime.now().getYear(), LocalDateTime.now().getMonthValue(), LocalDateTime.now().getDayOfMonth());
			
			
			PreparedStatement prepStmt = con.prepareStatement(
					"insert into StationHistory(station_id,date,num_of_bike_rides,num_of_disabled_bikes) values (?,?,?,?)");

			for (stationhistory si : input) {
				si.setDate(date);
				prepStmt.setString(1, si.getStation_id());
				prepStmt.setDate(2, si.getDate());
				prepStmt.setInt(3, si.getNum_of_bike_rides());
				prepStmt.setInt(4, si.getNum_of_disabled_bikes());				
				prepStmt.addBatch();
			}
			prepStmt.executeBatch();
			System.out.println("inserting.");
			con.commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}
	public ArrayList<stationhistory> selectStationInformationByDateandID(String station_id) {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			
			String connectionUrl = getconnectionstring();
			Connection con = DriverManager.getConnection(connectionUrl);
			
			Date date = new Date(LocalDateTime.now().getYear(), LocalDateTime.now().getMonthValue(), LocalDateTime.now().getDayOfMonth());
			String SQL = "SELECT * FROM stationhistory where Date= '"+date.toString()+"' and station_id= '"+station_id+"'" ;
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
