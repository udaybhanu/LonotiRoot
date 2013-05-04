package com.android.lonoti.bom.payload;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.j256.ormlite.field.ForeignCollectionField;

@DatabaseTable(tableName = "locations")
public class Location {

	@DatabaseField(generatedId = true)
	private Integer id;
	@DatabaseField
	private String lat;
	@DatabaseField
	private String lon;
	@DatabaseField
	private String locdescrition;
	
	@DatabaseField
	private String reference;
	
	@ForeignCollectionField
    public ForeignCollection<LonotiEvent> levents;
	
	public Location() {
		// TODO Auto-generated constructor stub
	}
	
	public Location(String lat, String lon,String locdescrition){
		this.lat = lat;
		this.lon = lon;
		this.locdescrition = locdescrition;
	}
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	public String getLon() {
		return lon;
	}
	public void setLon(String lon) {
		this.lon = lon;
	}
	public String getLocdescrition() {
		return locdescrition;
	}
	public void setLocdescrition(String locdescrition) {
		this.locdescrition = locdescrition;
	}

	public Integer getId() {
		// TODO Auto-generated method stub
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public ForeignCollection<LonotiEvent> getEvents() {
		return levents;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}
		

}