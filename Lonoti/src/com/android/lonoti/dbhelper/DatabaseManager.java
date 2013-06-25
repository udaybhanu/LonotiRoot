package com.android.lonoti.dbhelper;

import java.sql.SQLException;
import java.util.List;

import com.android.lonoti.bom.UserData;
import com.android.lonoti.bom.payload.Friend;
import com.android.lonoti.bom.payload.FriendEvents;
import com.android.lonoti.bom.payload.LonotiEvent;
import com.android.lonoti.bom.payload.Location;

import android.content.Context;

public class DatabaseManager {

    static private DatabaseManager instance;

    static public void init(Context ctx) {
        if (null==instance) {
            instance = new DatabaseManager(ctx);
        }
    }

    static public DatabaseManager getInstance() {
        return instance;
    }

    private DatabaseHelper helper;
    private DatabaseManager(Context ctx) {
        helper = new DatabaseHelper(ctx);
    }

    private DatabaseHelper getHelper() {
        return helper;
    }
    
    public void createLonotiEvent(LonotiEvent event)
    {
    	try {
			getHelper().getLonotiEventDao().create(event);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
 
    public List<LonotiEvent> getAllLonotiEvents() {
        List<LonotiEvent> lEvents = null;
        try {
        	lEvents = getHelper().getLonotiEventDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lEvents;
    }

	public void createLocation(Location location) {
		try {
			getHelper().getLocationDao().create(location);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public Location getLocation(Integer id)
	{
		try {
			return getHelper().getLocationDao().queryForId(id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public List<Location> getAllLocations() {
		List<Location> locations = null;
        try {
        	locations = getHelper().getLocationDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return locations;
	}

	public void createFriend(Friend friend) {
		try {
			getHelper().getFriendDao().create(friend);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		
	}

	public void createFriendEvents(FriendEvents fe1) {
		try {
			getHelper().getFriendEventsDao().create(fe1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void createUserData(UserData userData) {
		try {
			getHelper().getUserDataDao().create(userData);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		
	}

	public List<Friend> getAllFriends() {
		List<Friend> friends = null;
        try {
        	friends = getHelper().getFriendDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return friends;
	}

	public List<FriendEvents> getAllFriendEvents() {
		List<FriendEvents> fes = null;
        try {
        	fes = getHelper().getFriendEventsDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fes;
	}
	
	public UserData getUserData(){
		
		try {
			List<UserData> listUserData = getHelper().getUserDataDao().queryForAll();
			
			if(listUserData.size() == 0){
				return null;
			}
			if(listUserData.size() == 1){
				return listUserData.get(0);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
}