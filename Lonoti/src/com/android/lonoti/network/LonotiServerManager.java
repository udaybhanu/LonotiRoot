package com.android.lonoti.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import com.android.lonoti.Config;
import com.android.lonoti.UserPreferences;
import com.android.lonoti.exception.NetworkException;

public class LonotiServerManager {

	public static final String SERVER_URL = "https://lonoti.herokuapp.com/api/users/sign_in";
	
	public static String callServer(String serverURL, String httpMethod, int timeout, boolean isLonotiRequest, String payload, boolean isHTTPS) throws NetworkException{
		
		if(httpMethod == null) throw new NetworkException("HTTP Method Not specified");
		
		if("GET".equals(httpMethod)) return doGet(serverURL, timeout, isLonotiRequest, isHTTPS);
		
		if("POST".equals(httpMethod)) return doPost(serverURL, timeout, isLonotiRequest, payload, isHTTPS);
		
		throw new NetworkException("Invalid HTTP request");
	}
	
	private static String doGet(String serverURL, int timeout, boolean isLonotiRequest, boolean isHTTPS) throws NetworkException{
		
		try {
			URL url = new URL(serverURL);
			HttpURLConnection httpcon = (HttpURLConnection) (url.openConnection());
			httpcon.setConnectTimeout(timeout);
			httpcon.setReadTimeout(timeout);
			if(isLonotiRequest) httpcon.setRequestProperty("Authorization", "Token token=\"fdcfcce343c48d0fef3a032a0a4d251d\"");
			InputStreamReader in = new InputStreamReader(httpcon.getInputStream());
			int read;
	        char[] buff = new char[1024];
	        StringBuilder result = new StringBuilder();
	        while ((read = in.read(buff)) != -1) {
	            result.append(buff, 0, read);
	        }
			
	        if(httpcon.getResponseCode() != HttpURLConnection.HTTP_OK){
	        	throw new NetworkException("Code: "+httpcon.getResponseCode()+ "\nMESSAGE: "+ httpcon.getResponseMessage());
	        }
	        
	        in.close();
	        httpcon.disconnect();
	        return result.toString();
	        
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			throw new NetworkException("MalformedURLException", e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new NetworkException("IOException", e);
		}
		
	}
	
	private static String doPost(String serverURL, int timeout, boolean isLonotiRequest, String payload, boolean isHTTPS) throws NetworkException{
		
		URL url;
		HttpURLConnection httpcon = null;
		try {
			url = new URL(serverURL);
			
			
			if(isHTTPS){
				httpcon = (HttpsURLConnection) (url.openConnection());
			}else{
				httpcon = (HttpURLConnection) (url.openConnection());
			}
			
			httpcon.setConnectTimeout(45000);
			httpcon.setReadTimeout(45000);
			httpcon.setDoInput(true);
			httpcon.setDoOutput(true);
			if(isLonotiRequest) {
				httpcon.setRequestProperty("Authorization", "Token token=\"fdcfcce343c48d0fef3a032a0a4d251d\"");
				String auth_code = UserPreferences.getPreferences().getString("authCode", Config.DEFAULT_AUTH_CODE);
				if(!auth_code.equals(Config.DEFAULT_AUTH_CODE)){
					payload = payload + "&auth_token=" + auth_code;
				}
			}
			httpcon.setRequestMethod("POST");
			/*OutputStreamWriter wr = new OutputStreamWriter(httpcon.getOutputStream());
			wr.write(payload);
			wr.flush();
			wr.close();*/
			OutputStream os = httpcon.getOutputStream();
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(os));
			pw.write(payload);
			pw.flush();
			pw.close();

			System.out.println(httpcon.getResponseCode());
			
			/*if(httpcon.getResponseCode() != HttpURLConnection.HTTP_OK && httpcon.getResponseCode() != HttpURLConnection.HTTP_CREATED && httpcon.getResponseCode() != HttpURLConnection.HTTP_ACCEPTED){
	        	throw new NetworkException("Code: "+httpcon.getResponseCode()+ "\nMESSAGE: "+ httpcon.getResponseMessage());
	        }*/
			
			
			InputStream is = httpcon.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			String line = null;
			StringBuffer sb = new StringBuffer();
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
			is.close();
			httpcon.disconnect();
			return sb.toString();
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			throw new NetworkException("MalformedURLException", e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			
			InputStream error = httpcon.getErrorStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(error));
			String line = null;
			StringBuffer sb = new StringBuffer();
			try {
				while ((line = reader.readLine()) != null) {
					sb.append(line);
				}
				error.close();
				httpcon.disconnect();
				System.out.println(sb.toString());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			throw new NetworkException("IOException", e);
		}
		
		
	}

	public static void main(String args[]) throws NetworkException{
		
		LonotiServerManager.callServer("https://lonoti.herokuapp.com/api/users?email=mrudhukar.batchu@chronus.com&password=test123456", "POST", 3000, true, "", true);
		
	}
	
}
