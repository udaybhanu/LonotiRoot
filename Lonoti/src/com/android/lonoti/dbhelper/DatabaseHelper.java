package com.android.lonoti.dbhelper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.android.lonoti.R;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

/**
 * Database helper class used to manage the creation and upgrading of your database. This class also usually provides
 * the DAOs used by the other classes.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

	private static final String DATABASE_NAME = "lonoti.db";
	private static final int DATABASE_VERSION = 1;

	// the DAO object we use to access the SimpleData table
	private Dao<SimpleData, Integer> simpleDao = null;

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		//super(context, DATABASE_NAME, null, DATABASE_VERSION, R.raw.ormlite_config);
	}

	/**
	 * This is called when the database is first created. Usually you should call createTable statements here to create
	 * the tables that will store your data.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
		try {
			Log.i(DatabaseHelper.class.getName(), "onCreate");
			for(Class clazz : getDbClasses()){
				TableUtils.createTable(connectionSource, clazz);
			}
			// here we try inserting data in the on-create as a test
			Dao<SimpleData, Integer> dao = getSimpleDataDao();
			long millis = System.currentTimeMillis();
			// create some entries in the onCreate
			SimpleData simple = new SimpleData(millis);
			dao.create(simple);
			simple = new SimpleData(millis + 1);
			dao.create(simple);
			Log.i(DatabaseHelper.class.getName(), "created new entries in onCreate: " + millis);
		} catch (SQLException e) {
			Log.e(DatabaseHelper.class.getName(), "Can't create database", e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * This is called when the your application is upgraded and it has a higher version number. This allows you to adjust
	 * the various data to match the new version number.
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
	 * Returns the Database Access Object (DAO) for our SimpleData class. It will create it or just give the cached
	 * value.
	 */
	
	public Dao<SimpleData, Integer> getSimpleDataDao() throws SQLException {
		if (simpleDao == null) {
			simpleDao = getDao(SimpleData.class);
		}
		return simpleDao;
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
			Class[] tmpClassList = new Class[] { SimpleData.class};
			for (Class clazz : tmpClassList) {
				dbClasses.add(clazz);
			}
		}
		return dbClasses;
	}
}