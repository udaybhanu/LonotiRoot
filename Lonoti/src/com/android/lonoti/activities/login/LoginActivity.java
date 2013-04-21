package com.android.lonoti.activities.login;

import java.util.List;

import com.android.lonoti.R;
import com.android.lonoti.bom.payload.Friend;
import com.android.lonoti.bom.payload.FriendEvents;
import com.android.lonoti.bom.payload.LEvent;
import com.android.lonoti.bom.payload.Location;
import com.android.lonoti.dbhelper.DatabaseManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.ForeignCollection;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Activity which displays a login screen to the user, offering registration as
 * well.
 */
public class LoginActivity extends Activity {
	private final String LOG_TAG = getClass().getSimpleName();
	/**
	 * A dummy authentication store containing known user names and passwords.
	 * TODO: remove after connecting to a real authentication system.
	 */
	private static final String[] DUMMY_CREDENTIALS = new String[] {
			"foo@example.com:hello", "bar@example.com:world" };

	/**
	 * The default email to populate the email field with.
	 */
	public static final String EXTRA_EMAIL = "com.example.android.authenticatordemo.extra.EMAIL";

	/**
	 * Keep track of the login task to ensure we can cancel it if requested.
	 */
	private UserLoginTask mAuthTask = null;

	// Values for email and password at the time of the login attempt.
	private String mEmail;
	private String mPassword;

	// UI references.
	private EditText mEmailView;
	private EditText mPasswordView;
	private View mLoginFormView;
	private View mLoginStatusView;
	private TextView mLoginStatusMessageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_login);

		// Set up the login form.
		mEmail = getIntent().getStringExtra(EXTRA_EMAIL);
		mEmailView = (EditText) findViewById(R.id.email);
		mEmailView.setText(mEmail);
		
		// create some entries in the onCreate
		Location mgroad = new Location("1","1","MG Road");
		DatabaseManager.getInstance().createLocation(mgroad);
		Location kormangla = new Location("2","2","Kormangla");
		DatabaseManager.getInstance().createLocation(kormangla);
		
		Friend friend1 = new Friend("9916622021", false, "preddy1@gmail.com");
		DatabaseManager.getInstance().createFriend(friend1);
		Friend friend2 = new Friend("9916622022", false, "preddy2@gmail.com");
		DatabaseManager.getInstance().createFriend(friend2);
		Friend friend3 = new Friend("9916622023", false, "preddy3@gmail.com");
		DatabaseManager.getInstance().createFriend(friend3);
		
		LEvent event1 = new LEvent("event1","mg1-desc",mgroad);
		LEvent event2 = new LEvent("event2","mg2-desc",kormangla);
		
		DatabaseManager.getInstance().createLEvent(event1);
		DatabaseManager.getInstance().createLEvent(event2);
		
		//event1-friend1 , friend2 - mgroad
		//event2-friend2 , friend3 - kormangla
		FriendEvents fe1 = new FriendEvents(event1, friend1);
		DatabaseManager.getInstance().createFriendEvents(fe1);
		FriendEvents fe2 = new FriendEvents(event1, friend2);
		DatabaseManager.getInstance().createFriendEvents(fe2);
		FriendEvents fe3 = new FriendEvents(event2, friend2);
		DatabaseManager.getInstance().createFriendEvents(fe3);
		FriendEvents fe4 = new FriendEvents(event2, friend3);
		DatabaseManager.getInstance().createFriendEvents(fe4);
	
		List<LEvent> myLevents = DatabaseManager.getInstance().getAllLEvents();
		List<Location> myLocations = DatabaseManager.getInstance().getAllLocations();
		List<Friend> myFriends = DatabaseManager.getInstance().getAllFriends();
		List<FriendEvents> myFriendEvents = DatabaseManager.getInstance().getAllFriendEvents();
		
		for(FriendEvents fevnt : myFriendEvents)
		{
			Log.e(LOG_TAG, "Event Name is " + fevnt.getEvent().getName());
			Log.e(LOG_TAG, "Friend email is "+ fevnt.getFriend().getEmailId());
		}
		
		for(LEvent evnt : myLevents)
		{
			Log.e(LOG_TAG, "Event Name is " + evnt.getName());
			Location l3 = evnt.getLocation();
			Log.e(LOG_TAG, "Event Location is " + l3.getLocdescrition());
			for(FriendEvents fe : evnt.getFriendEvents())
			{
				Log.e(LOG_TAG, "Event " + evnt.getName() + "Friend " + fe.getFriend().getMobileNumber());
			}
		}
		
		for(Friend frnd : myFriends)
		{
			Log.e(LOG_TAG, "Friend No is " + frnd.getMobileNumber());
			for(FriendEvents fe : frnd.getFriendEvents())
			{
				Log.e(LOG_TAG, "Friend " + frnd.getEmailId() + " is in Event " + fe.getEvent().getName());
			}
		}
		for(Location loc1 : myLocations)
		{
			Log.e(LOG_TAG, "Location Name is " + loc1.getLocdescrition());
			for(LEvent evnt : loc1.getEvents())
			{
				Log.e(LOG_TAG, "Event Name is " + evnt.getName());	
			}
		}

		mPasswordView = (EditText) findViewById(R.id.password);
		mPasswordView
				.setOnEditorActionListener(new TextView.OnEditorActionListener() {
					@Override
					public boolean onEditorAction(TextView textView, int id,
							KeyEvent keyEvent) {
						if (id == R.id.login || id == EditorInfo.IME_NULL) {
							attemptLogin();
							return true;
						}
						return false;
					}
				});

		mLoginFormView = findViewById(R.id.login_form);
		mLoginStatusView = findViewById(R.id.login_status);
		mLoginStatusMessageView = (TextView) findViewById(R.id.login_status_message);

		findViewById(R.id.sign_in_button).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						attemptLogin();
					}
				});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	/**
	 * Attempts to sign in or register the account specified by the login form.
	 * If there are form errors (invalid email, missing fields, etc.), the
	 * errors are presented and no actual login attempt is made.
	 */
	public void attemptLogin() {
		if (mAuthTask != null) {
			return;
		}

		// Reset errors.
		mEmailView.setError(null);
		mPasswordView.setError(null);

		// Store values at the time of the login attempt.
		mEmail = mEmailView.getText().toString();
		mPassword = mPasswordView.getText().toString();

		boolean cancel = false;
		View focusView = null;

		// Check for a valid password.
		if (TextUtils.isEmpty(mPassword)) {
			mPasswordView.setError(getString(R.string.error_field_required));
			focusView = mPasswordView;
			cancel = true;
		} else if (mPassword.length() < 4) {
			mPasswordView.setError(getString(R.string.error_invalid_password));
			focusView = mPasswordView;
			cancel = true;
		}

		// Check for a valid email address.
		if (TextUtils.isEmpty(mEmail)) {
			mEmailView.setError(getString(R.string.error_field_required));
			focusView = mEmailView;
			cancel = true;
		} else if (!mEmail.contains("@")) {
			mEmailView.setError(getString(R.string.error_invalid_email));
			focusView = mEmailView;
			cancel = true;
		}

		if (cancel) {
			// There was an error; don't attempt login and focus the first
			// form field with an error.
			focusView.requestFocus();
		} else {
			// Show a progress spinner, and kick off a background task to
			// perform the user login attempt.
			mLoginStatusMessageView.setText(R.string.login_progress_signing_in);
			showProgress(true);
			mAuthTask = new UserLoginTask();
			mAuthTask.execute((Void) null);
		}
	}

	/**
	 * Shows the progress UI and hides the login form.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	private void showProgress(final boolean show) {
		// On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
		// for very easy animations. If available, use these APIs to fade-in
		// the progress spinner.
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			int shortAnimTime = getResources().getInteger(
					android.R.integer.config_shortAnimTime);

			mLoginStatusView.setVisibility(View.VISIBLE);
			mLoginStatusView.animate().setDuration(shortAnimTime)
					.alpha(show ? 1 : 0)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mLoginStatusView.setVisibility(show ? View.VISIBLE
									: View.GONE);
						}
					});

			mLoginFormView.setVisibility(View.VISIBLE);
			mLoginFormView.animate().setDuration(shortAnimTime)
					.alpha(show ? 0 : 1)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mLoginFormView.setVisibility(show ? View.GONE
									: View.VISIBLE);
						}
					});
		} else {
			// The ViewPropertyAnimator APIs are not available, so simply show
			// and hide the relevant UI components.
			mLoginStatusView.setVisibility(show ? View.VISIBLE : View.GONE);
			mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
		}
	}

	/**
	 * Represents an asynchronous login/registration task used to authenticate
	 * the user.
	 */
	public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {
		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO: attempt authentication against a network service.

			try {
				// Simulate network access.
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				return false;
			}

			for (String credential : DUMMY_CREDENTIALS) {
				String[] pieces = credential.split(":");
				if (pieces[0].equals(mEmail)) {
					// Account exists, return true if the password matches.
					return pieces[1].equals(mPassword);
				}
			}

			// TODO: register the new account here.
			return true;
		}

		@Override
		protected void onPostExecute(final Boolean success) {
			mAuthTask = null;
			showProgress(false);

			if (success) {
				finish();
			} else {
				mPasswordView
						.setError(getString(R.string.error_incorrect_password));
				mPasswordView.requestFocus();
			}
		}

		@Override
		protected void onCancelled() {
			mAuthTask = null;
			showProgress(false);
		}
	}
}
