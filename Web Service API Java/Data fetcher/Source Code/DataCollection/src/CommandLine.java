import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class CommandLine extends TimerTask {

	public static void main(String[] arguments) throws JSONException, IOException {

		
		 storestaticdata("https://gbfs.citibikenyc.com/gbfs/en/station_information.json");
		 Timer timer = new Timer();
		 timer.schedule(new CommandLine(), 0, 5000);// fetching new data after
		// every 5 seconds
	}

	public void run() {

		storeDynamicdatastatus("https://gbfs.citibikenyc.com/gbfs/en/station_status.json");		
	}

	public static void storestaticdata(String URL) {
		JSONArray arr;
		try {
			arr = readJsonFromUrl(URL).getJSONObject("data").getJSONArray("stations");
			Gson gson = new Gson();
			ArrayList<Station> SI = gson.fromJson(arr.toString(), new TypeToken<List<Station>>() {
			}.getType());
			new DAL().deletefromStation();
			new DAL().insertStation(SI);

		} catch (Exception ex) {
			ex.printStackTrace();

		}		
	}

	public static void storeDynamicdatastatus(String URL) {
		JSONArray arr;
		try {
			arr = readJsonFromUrl(URL).getJSONObject("data").getJSONArray("stations");
			Gson gson = new Gson();
			ArrayList<StationInformation> SI = gson.fromJson(arr.toString(), new TypeToken<List<StationInformation>>() {
			}.getType());
			
			
			new DAL().deletefromStationInformation(); // to keep only latest data
			new DAL().insertStationInformation(SI);
			
			
			// maintaining history
			ArrayList<stationhistory> liststationhistory = new ArrayList<stationhistory>();
			for(StationInformation s:SI)
			{
				ArrayList<stationhistory> sh= new DAL().selectStationInformationByDateandID( s.getStation_id());
				if(sh.size()>0)
				{
					stationhistory temp = sh.get(0);
					stationhistory stationhist = new stationhistory();
					stationhist.setStation_id(s.getStation_id());
					stationhist.setNum_of_bike_rides((s.getNum_docks_available()+temp.getNum_of_bike_rides())/2);
					stationhist.setNum_of_disabled_bikes((s.getNum_bikes_disabled()+temp.getNum_of_disabled_bikes())/2);
					liststationhistory.add(temp);
					
				}
				else
				{
					stationhistory stationhist = new stationhistory();
					stationhist.setStation_id(s.getStation_id());
					stationhist.setNum_of_bike_rides(s.getNum_docks_available());
					stationhist.setNum_of_disabled_bikes(s.getNum_bikes_disabled());
					liststationhistory.add(stationhist);
				}
			}
			new DAL().deletefromStationHistory();
			
			new DAL().insertStationHistory(liststationhistory);

		} catch (Exception ex) {
			ex.printStackTrace();

		}

	}

	private static String readAll(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1) {
			sb.append((char) cp);
		}
		return sb.toString();
	}

	public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
		InputStream is = new URL(url).openStream();
		try {
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			String jsonText = readAll(rd);
			JSONObject json = new JSONObject(jsonText);
			return json;
		} finally {
			is.close();
		}
	}
}
