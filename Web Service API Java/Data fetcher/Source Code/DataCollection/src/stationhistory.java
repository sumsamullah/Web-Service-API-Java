import java.sql.Date;

public class stationhistory {
	String station_id;
	Date date;
	int num_of_bike_rides;
	int num_of_disabled_bikes;
	public String getStation_id() {
		return station_id;
	}
	public void setStation_id(String station_id) {
		this.station_id = station_id;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date Date) {
		date = Date;
	}
	public int getNum_of_bike_rides() {
		return num_of_bike_rides;
	}
	public void setNum_of_bike_rides(int num_of_bike_rides) {
		this.num_of_bike_rides = num_of_bike_rides;
	}
	public int getNum_of_disabled_bikes() {
		return num_of_disabled_bikes;
	}
	public void setNum_of_disabled_bikes(int num_of_disabled_bikes) {
		this.num_of_disabled_bikes = num_of_disabled_bikes;
	}
	

}
