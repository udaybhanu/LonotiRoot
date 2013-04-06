package com.android.lonoti.activities;

import com.android.lonoti.HomeActivity;
import com.android.lonoti.R;

import android.opengl.Visibility;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		final Button button = (Button) findViewById(R.id.loginButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), HomeActivity.class);
                finish();
                startActivity(intent);
            }
        });
        final Button register = (Button) findViewById(R.id.registerButton);
        register.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                /*Intent intent = new Intent(getBaseContext(), HomeActivity.class);
                startActivity(intent);*/
            	TextView reEnter = (TextView) findViewById(R.id.textReEnterPassword);
            	if(reEnter.getVisibility() == View.GONE){
            		reEnter.setVisibility(View.VISIBLE);
            	}else{
            		Intent intent = new Intent(getBaseContext(), HomeActivity.class);
            		finish();
                    startActivity(intent);
            	}
            }
        });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
