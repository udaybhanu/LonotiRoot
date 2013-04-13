package com.lonoti.utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Calendar;

import com.google.gson.Gson;
import com.lonoti.bom.Request;
import com.lonoti.bom.payload.Event;
import com.lonoti.bom.payload.Friend;
import com.lonoti.bom.payload.Location;
import com.lonoti.bom.payload.TimeEvent;
import com.sun.org.apache.xml.internal.security.utils.Base64;

public class Server {

	public static final String SERVER_URL = "https://lonoti.herokuapp.com/api/users/sign_in";
	
	public static String callServer(String request){
		
		try {
			URL url = new URL(SERVER_URL);
			HttpURLConnection httpcon = (HttpURLConnection) (url.openConnection());
			httpcon.setDoInput(true);
			httpcon.setDoOutput(true);
			httpcon.setRequestProperty("Authorization", "Token token=\"fdcfcce343c48d0fef3a032a0a4d251d\"");
			httpcon.setRequestMethod("POST");
			httpcon.connect();
			
			OutputStream os = httpcon.getOutputStream();
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(os));
			pw.write(request);
			pw.close();

			InputStream is = httpcon.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			String line = null;
			StringBuffer sb = new StringBuffer();
			while ((line = reader.readLine()) != null) {
			    sb.append(line);
			}
			is.close();
			String response = sb.toString();
			System.out.println(response);
			return response;
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	
	public static void main(String arg[]) throws UnsupportedEncodingException{
		
Request request = new Request();
		
		request.setType(Request.EVENT);
		/*Login payload = new Login();
		payload.setUsername("XXXXX");
		payload.setPassword("XXX");
		request.setPayload(payload);*/
		Event event = new Event();
		Friend friend = new Friend();
		friend.setEmailId("bobby.bhanu@gmail.com");
		friend.setMobileNumber("9945527640");
		event.getFriends().add(friend);
		event.getFriends().add(friend);
		Location location = new Location();
		location.setLat("lan");
		location.setLon("lon");
		location.setLocdescrition("descr");
		event.setLocation(location);
		TimeEvent time = new TimeEvent();
		time.setNotDate(Calendar.getInstance().getTime());
		time.setDuration("dur");
		event.setTime(time);
		event.setName("name");
		event.setDescription("Description");
		request.setPayload(event);
		String requestStr = JSONConverter.convertResponsetoJSON(request);
		
		Gson gson = new Gson();
		System.out.println(gson.toJson(request));
		//System.out.println(URLEncoder.encode(gson.toJson(request), "UTF-8"));
		System.out.println(Base64.encode(gson.toJson(request).getBytes()));
		System.out.println(Calendar.getInstance().getTimeInMillis()/1000);
		Server.callServer("data=" + requestStr);
		
	}
	
}
