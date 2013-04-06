package com.lonoti.utility;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.lonoti.bom.Request;
import com.lonoti.bom.Response;
import com.lonoti.bom.payload.Event;
import com.lonoti.bom.payload.FacebookLogin;
import com.lonoti.bom.payload.Friend;
import com.lonoti.bom.payload.Location;
import com.lonoti.bom.payload.Login;
import com.lonoti.bom.payload.Payload;
import com.lonoti.bom.payload.Register;
import com.lonoti.bom.payload.TimeEvent;

public class JSONConverter {

	public static Request convertJsonStringtoRequest(String str){
		
		JSONParser jsonParser = new JSONParser();
		Request request = null;
		try {
			Object obj = jsonParser.parse(str);
			
			request = getRequest(obj);
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return request;
		
	}
	
	public static Response convertJsonStringtoResponse(String str){
		
		JSONParser jsonParser = new JSONParser();
		Response response = null;
		try {
			Object obj = jsonParser.parse(str);
			
			response = getResponse(obj);
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return response;
		
	}
	
	private static Response getResponse(Object obj) {
		// TODO Auto-generated method stub
		return null;
	}

	private static Request getRequest(Object obj) {
		// TODO Auto-generated method stub
		
		Request request = new Request();
		Payload payload = null;
		
		if(obj instanceof JSONObject){
			
			JSONObject reqObj = (JSONObject) ((JSONObject) obj).get("request");
			String type = reqObj.get("type").toString();
			request.setType(type);
			JSONObject payloadObj = (JSONObject)(reqObj.get("payload"));
			
			if(Request.REGISTER.equals(type)){
				payload = new Register();
				((Register)payload).setUsername(payloadObj.get("username").toString());
				((Register)payload).setPassword(payloadObj.get("password").toString());
				((Register)payload).setDeviceId(payloadObj.get("deviceid").toString());
			}else if(Request.LOGIN.equals(type)){
				payload = new Login();
				((Login)payload).setUsername(payloadObj.get("username").toString());
				((Login)payload).setPassword(payloadObj.get("password").toString());
			}else if(Request.FLOGIN.equals(type)){
				payload = new FacebookLogin();
				((FacebookLogin)payload).setFusername(payloadObj.get("username").toString());
				((FacebookLogin)payload).setAuthToken(payloadObj.get("fauthtoken").toString());
			}else if(Request.EVENT.equals(type)){
				payload = new Event();
				((Event)payload).setName(payloadObj.get("name").toString());
				((Event)payload).setDescription(payloadObj.get("description").toString());
				JSONObject locationObj = (JSONObject) payloadObj.get("location");
				if(locationObj != null){
					Location location = new Location();
					location.setLat((locationObj.get("lat").toString()));
					location.setLon((locationObj.get("lon").toString()));
					location.setLocdescrition(locationObj.get("locdescrition").toString());
					((Event)payload).setLocation(location);
				}
				JSONObject timeEventObj = (JSONObject) payloadObj.get("time");
				if(timeEventObj != null){
					TimeEvent event = new TimeEvent();
					event.setNotDate(Calendar.getInstance().getTime());
					event.setDuration(timeEventObj.get("offset").toString());
					((Event)payload).setTime(event);
				}
				JSONArray friendsObj = (JSONArray) payloadObj.get("friends");
				if(friendsObj != null){
					Iterator it = friendsObj.iterator();
					while(it.hasNext()){
						Friend friend = new Friend();
						JSONObject friendObj = (JSONObject) it.next();
						friend.setMobileNumber(friendObj.get("mobilenumber").toString());
						friend.setEmailId(friendObj.get("email").toString());
						((Event)payload).getFriends().add(friend);
					}
				}
				System.out.println(friendsObj);
				
			}else if(Request.NOTIFICATION.equals(type)){
				payload = new Register();
				((Register)payload).setUsername(payloadObj.get("username").toString());
				((Register)payload).setPassword(payloadObj.get("fauthtoken").toString());
			}
			
			request.setPayload(payload);
			
			return request;
			
		} else{
			
			return null;
			
		}
		
	}

	public static void main(String args[]){
		
		/*//String str = "{\"request\":{\"type\": \"register\",\"payload\":{\"username\": \"emailid\",\"password\": \"XXX\",\"deviceid\": \"XXX\"}}}";
		String str = "{\"request\": {\"type\": \"event\",\"payload\": {\"name\": \"XXX\",\"description\": \"XXX\",\"location\": {\"lat\": \"XXX\",\"lon\": \"XXX\",\"locdescrition\": \"XXX\",},\"time\": {\"date\": \"XXX\",\"offset\": \"XXX\"},\"friends\":[{\"mobilenumber\":\"xxxxxxxxxxxx\",\"isAppUser\": true,\"email\":\"XXX\",}],}}}";
		Request request = JSONConverter.convertJsonStringtoRequest(str);
		//System.out.println(obj);
		
		System.out.println(request.getType());*/
		
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
		System.out.println(convertResponsetoJSON(request));
	}
	
	@SuppressWarnings("unchecked")
	public static String convertResponsetoJSON(Request request){
		if(request == null) return null;
		
		JSONObject obj = new JSONObject();
		JSONObject requestObj = new JSONObject();
		JSONObject payloadObject = new JSONObject();
		
		String type = request.getType();
		
		if(Request.LOGIN.equals(type)){
			Login login = (Login) request.getPayload();
			payloadObject.put("username", login.getUsername());
			payloadObject.put("password", login.getPassword());
			requestObj.put("payload", payloadObject);
		}else if(Request.REGISTER.equals(type)){
			Register register = (Register) request.getPayload();
			payloadObject.put("username", register.getUsername());
			payloadObject.put("password", register.getPassword());
			payloadObject.put("deviceid", register.getDeviceId());
			requestObj.put("payload", payloadObject);
		}else if(Request.FLOGIN.equals(type)){
			FacebookLogin flogin = (FacebookLogin) request.getPayload();
			payloadObject.put("username", flogin.getFusername());
			payloadObject.put("fauthtoken", flogin.getAuthToken());
		}else if(Request.FLOGIN.equals(type)){
			FacebookLogin flogin = (FacebookLogin) request.getPayload();
			payloadObject.put("username", flogin.getFusername());
			payloadObject.put("fauthtoken", flogin.getAuthToken());
		}else if(Request.EVENT.equals(type)){
			Event event = (Event) request.getPayload();
			Location location = event.getLocation();
			TimeEvent time = event.getTime();
			
			JSONObject locationObject = new JSONObject();
			locationObject.put("lat", location.getLat());
			locationObject.put("lon", location.getLat());
			locationObject.put("locdescription", location.getLocdescrition());
			payloadObject.put("location", locationObject);
			
			JSONObject timeObject = new JSONObject();
			timeObject.put("date", time.getNotDate());
			timeObject.put("offset", time.getDuration());
			payloadObject.put("time", timeObject);
			
			List<Friend> friends = event.getFriends();
			JSONArray friendArray = new JSONArray();
			for(Friend friend:friends){
				
				JSONObject friendObj = new JSONObject();
				friendObj.put("mobilenumber", friend.getMobileNumber());
				friendObj.put("email", friend.getEmailId());
				
				friendArray.add(friendObj);
			}
			payloadObject.put("friends", friendArray);
		}
		
		requestObj.put("payload", payloadObject);
		obj.put("request", requestObj);
		
		String jsonRequest = JSONValue.toJSONString(obj);
		
		return jsonRequest;
	}
	
	private static JSONObject newJSONKeyValue(String key, String value){
		JSONObject object = new JSONObject();
		object.put(key, value);
		return object;
	}
	
	
}
