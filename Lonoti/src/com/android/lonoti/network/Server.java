package com.android.lonoti.network;

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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.util.Base64;
import android.util.Log;

import com.android.lonoti.bom.Request;
import com.android.lonoti.bom.payload.Friend;
import com.android.lonoti.bom.payload.LonotiEvent;
import com.android.lonoti.bom.payload.Payload;
import com.android.lonoti.bom.payload.TimeEvent;
import com.android.lonoti.bom.payload.Location;

import com.google.gson.Gson;

public class Server {

	public static final String SERVER_URL = "https://lonoti.herokuapp.com/api/users/sign_in";
	
	public static String callServer(String request){
		
		try {
			Log.e("SERVER", request);
			Log.e("SERVER", "connecting to " + SERVER_URL);
			URL url = new URL(SERVER_URL);
			HttpURLConnection httpcon = (HttpURLConnection) (url.openConnection());
			httpcon.setConnectTimeout(45000);
			httpcon.setReadTimeout(45000);
			httpcon.setDoInput(true);
			httpcon.setDoOutput(true);
			httpcon.setRequestProperty("Authorization", "Token token=\"fdcfcce343c48d0fef3a032a0a4d251d\"");
			httpcon.setRequestMethod("POST");
			//httpcon.connect();
			OutputStreamWriter wr = new OutputStreamWriter(httpcon.getOutputStream());
			wr.write(request);
			wr.flush();
			wr.close();
			OutputStream os = httpcon.getOutputStream();
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(os));
			pw.write(request);
			pw.flush();
			pw.close();

			if(httpcon.getResponseCode() == HttpURLConnection.HTTP_OK)
			{
				Log.e("SERVER", "Code:200");
				InputStream is = httpcon.getInputStream();
				BufferedReader reader = new BufferedReader(new InputStreamReader(is));
				String line = null;
				StringBuffer sb = new StringBuffer();
				while ((line = reader.readLine()) != null) {
					sb.append(line);
				}
				is.close();
				String response = sb.toString();
				Log.e("SERVER", response);

				return response;
			}
			else
			{
				Log.e("SERVER","Code: "+httpcon.getResponseCode()+ "\nMESSAGE: "+ httpcon.getResponseMessage());
				return null;
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (Exception e)
		{
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
		LonotiEvent event = new LonotiEvent();
		Friend friend = new Friend();
		friend.setEmailId("bobby.bhanu@gmail.com");
		friend.setMobileNumber("9945527640");
		event.getFriends().add(friend);
		event.getFriends().add(friend);
		Location location = new Location();
		location.setLat("1234");
		location.setLon("3456");
		location.setLocdescrition("descr");
		event.setLocation(location);
		TimeEvent time = new TimeEvent();
		time.setNotDate(Calendar.getInstance().getTime());
		time.setDuration("dur");
		event.setTime(time);
		event.setName("name");
		event.setDescription("Description");
		request.setPayload(event);
		//String requestStr = JSONConverter.convertResponsetoJSON(request);
		
		Gson gson = new Gson();
		System.out.println(gson.toJson(request));
		String requestStr = gson.toJson(request);
		//System.out.println(URLEncoder.encode(gson.toJson(request), "UTF-8"));
		System.out.println(URLEncoder.encode(Base64.encode(gson.toJson(request).getBytes(), Base64.DEFAULT).toString(), "UTF-8"));
		System.out.println(Calendar.getInstance().getTimeInMillis()/1000);
		Payload p = gson.fromJson(requestStr, Payload.class);
		//Server.callServer("data=" + requestStr);
		
	}
	
}
