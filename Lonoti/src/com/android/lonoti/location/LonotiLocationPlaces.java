package com.android.lonoti.location;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.lonoti.exception.NetworkException;
import com.android.lonoti.network.LonotiServerManager;

public class LonotiLocationPlaces {

	public final static String SERVER_URL_AUTOCOMPLETE = "https://maps.googleapis.com/maps/api/place/autocomplete/json";
	public final static String SERVER_URL_LOCATION_DETAILS = "https://maps.googleapis.com/maps/api/place/details/json";
	
	public static List<String> getContent(String key) throws NetworkException{
		
		List<String> resultList = null;
		
		try 
		{	
			String jsonResults = LonotiServerManager.callServer(SERVER_URL_AUTOCOMPLETE + "?input="+ URLEncoder.encode(key, "utf8") +"&sensor=false&key=AIzaSyBy_w78WsrAv96w81nDRjl2LLWX2ob09wQ", "GET", 45000, false, null);
			JSONObject jsonObj = new JSONObject(jsonResults.toString());
	        JSONArray predsJsonArray = jsonObj.getJSONArray("predictions");
	        
	        // Extract the Place descriptions from the results
	        resultList = new ArrayList<String>(predsJsonArray.length());
	        for (int i = 0; i < predsJsonArray.length(); i++) {
	            resultList.add(predsJsonArray.getJSONObject(i).getString("description"));
	        }
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			throw new NetworkException("UnsupportedEncodingException (LonotiLocationPlaces)", e);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			throw new NetworkException("JSONException : Invalid Response (LonotiLocationPlaces) ", e);
		}
		
        return resultList;
	}
	
	public static Map<String, String> getContentMap(String key) throws IOException, JSONException{
		
		
		
		URL url = new URL(SERVER_URL_AUTOCOMPLETE + "?input="+ URLEncoder.encode(key, "utf8") +"&sensor=false&key=AIzaSyBy_w78WsrAv96w81nDRjl2LLWX2ob09wQ");
		HttpURLConnection httpcon = (HttpURLConnection) (url.openConnection());
		InputStreamReader in = new InputStreamReader(httpcon.getInputStream());
        
        // Load the results into a StringBuilder
        int read;
        char[] buff = new char[1024];
        StringBuilder jsonResults = new StringBuilder();
        while ((read = in.read(buff)) != -1) {
            jsonResults.append(buff, 0, read);
        }
		JSONObject jsonObj = new JSONObject(jsonResults.toString());
        JSONArray predsJsonArray = jsonObj.getJSONArray("predictions");
        
        // Extract the Place descriptions from the results

        Map<String, String> results = new HashMap<String, String>();
        
        for (int i = 0; i < predsJsonArray.length(); i++) {
            //resultList.add(predsJsonArray.getJSONObject(i).getString("description"));
            String description = predsJsonArray.getJSONObject(i).getString("description");
            String reference = predsJsonArray.getJSONObject(i).getString("reference");
            results.put(description, reference);
        }
		
        return results;
	}
	
	
	public com.android.lonoti.bom.payload.Location getLocation(String reference) throws IOException, JSONException{
		
		com.android.lonoti.bom.payload.Location location = new com.android.lonoti.bom.payload.Location();
		
		URL url = new URL(SERVER_URL_LOCATION_DETAILS + "?reference="+ URLEncoder.encode(reference, "utf8") +"&sensor=false&key=AIzaSyBy_w78WsrAv96w81nDRjl2LLWX2ob09wQ");
		HttpURLConnection httpcon = (HttpURLConnection) (url.openConnection());
		InputStreamReader in = new InputStreamReader(httpcon.getInputStream());
        
        // Load the results into a StringBuilder
        int read;
        char[] buff = new char[1024];
        StringBuilder jsonResults = new StringBuilder();
        while ((read = in.read(buff)) != -1) {
            jsonResults.append(buff, 0, read);
        }
		
        JSONObject jsonObj = new JSONObject(jsonResults.toString());
        String status = jsonObj.getJSONObject("status").toString();
        
        if("INVALID_REQUEST".equals(status)){
        	return null;
        }
        
        String lat = jsonObj.getJSONObject("lat").toString();
        String log = jsonObj.getJSONObject("lon").toString();
        String desc = jsonObj.getJSONObject("name").toString();
        
        location.setLat(lat);
        location.setLon(log);
        location.setReference(reference);
        location.setLocdescrition(desc);
        
        return location;
        
	}
	
}
