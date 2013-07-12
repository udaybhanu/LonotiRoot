package com.android.lonoti.bom.payload;

import java.util.Date;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "ltimeevent")
public class TimeEvent {

	@DatabaseField(generatedId = true, allowGeneratedIdInsert=true)
	private Integer id;
	@DatabaseField
	private Date notDate;
	@DatabaseField
	private Integer duration;

	@ForeignCollectionField
    public ForeignCollection<LonotiEvent> levents;
	
	public TimeEvent (){
		
	}
	
	public Date getNotDate() {
		return notDate;
	}
	
	public void setNotDate(Date notDate) {
		this.notDate = notDate;
	}
	
	public Integer getDuration() {
		return duration;
	}
	
	public void setDuration(Integer duration) {
		this.duration = duration;
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public ForeignCollection<LonotiEvent> getEvents() {
		return levents;
	}
	
}
