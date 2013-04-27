package com.android.lonoti.dbhelper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.EventLog.Event;
import android.util.Log;

import com.android.lonoti.R;
import com.android.lonoti.bom.payload.Friend;
import com.android.lonoti.bom.payload.FriendEvents;
import com.android.lonoti.bom.payload.Location;
import com.android.lonoti.bom.payload.Payload;
import com.android.lonoti.bom.payload.LonotiEvent;
import com.android.lonoti.bom.payload.TimeEvent;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

/**
 * Database helper class used to manage the creation and upgrading of database. This class also provides
 * the DAOs used by the other classes.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

	private static final String DATABASE_NAME = "lonoti.db";
	private static final int DATABASE_VERSION = 1;

	// the DAO object we use to access the SimpleData table
	private Dao<LonotiEvent, Integer> eventDao = null;
	private Dao<Friend, Long> friendDao = null;
	private Dao<Location, Integer> locationDao = null;
	private Dao<FriendEvents, Integer> friendEventsDao = null;

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		//use the config file generator later on to reduce the load time.
		//super(context, DATABASE_NAME, null, DATABASE_VERSION, R.raw.ormlite_config);
	}

	/**
	 * This is called when the database is first created. 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
		try {
			Log.i(DatabaseHelper.class.getName(), "onCreate");
			for(Class clazz : getDbClasses()){
				TableUtils.createTable(connectionSource, clazz);
			}
		} catch (SQLException e) {
			Log.e(DatabaseHelper.class.getName(), "Can't create database", e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * This is called when the application is upgraded and it has a higher version number. This allows us to adjust
	 * the various data to match the new version number. It is not required in our architecture currently. 
	 * But once we add new features on new production versions we will have to use this feature.
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
		try {
			Log.i(DatabaseHelper.class.getName(), "onUpgrade");
			for(Class clazz : getDbClasses()){
				TableUtils.dropTable(connectionSource, clazz, true);
			}
			// after we drop the old databases, we create the new ones
			onCreate(db, connectionSource);
		} catch (SQLException e) {
			Log.e(DatabaseHelper.class.getName(), "Can't drop databases", e);
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Close the database connections and clear any cached DAOs.
	 */
	@Override
	public void close() {
		super.close();
	}
	
	//Save the list of Database Classes in dbClasses
	@SuppressWarnings("rawtypes")
	private static List<Class> dbClasses = null;
	@SuppressWarnings({ "rawtypes"})
	public static List<Class> getDbClasses() {
		if (dbClasses == null) {
			dbClasses = new ArrayList<Class>();
			// Populate the Class list representing each of the tables
			Class[] tmpClassList = new Class[] { Location.class, LonotiEvent.class, Friend.class, FriendEvents.class } ; //TimeEvent.class,
					//Payload.class};
			for (Class clazz : tmpClassList) {
				dbClasses.add(clazz);
			}
		}
		return dbClasses;
	}

	/**
	 * Returns the Database Access Object (DAO) for our different classes. It will create it or just give the cached
	 * value.
	 */
		
	public Dao<LonotiEvent, Integer> getLonotiEventDao() throws SQLException {
		if (eventDao == null) {
			eventDao = getDao(LonotiEvent.class);
		}
		return eventDao;
	}
	
	public Dao<Friend, Long> getFriendDao() throws SQLException {
		if (friendDao == null) {
			friendDao = getDao(Friend.class);
		}
		return friendDao;
	}




	public Dao<Location, Integer> getLocationDao() {
		if (locationDao == null) {
			try {
				locationDao = getDao(Location.class);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return locationDao;
	}
	public Dao<FriendEvents, Integer> getFriendEventsDao() {
		if (friendEventsDao == null) {
			try {
				friendEventsDao = getDao(FriendEvents.class);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return friendEventsDao;
	}
}