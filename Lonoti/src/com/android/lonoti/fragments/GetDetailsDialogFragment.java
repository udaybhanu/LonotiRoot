package com.android.lonoti.fragments;

import com.android.lonoti.R;
import com.android.lonoti.activities.MainActivity;
import com.android.lonoti.bom.UserData;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.EditText;

public class GetDetailsDialogFragment extends DialogFragment implements View.OnClickListener{

	private EditText emailEditText;
	private EditText phoneNumberEditText;
	private Button continueButton;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.get_details_fragment, container);
		emailEditText = (EditText) view.findViewById(R.id.getDetailsEmailEditText);
		phoneNumberEditText = (EditText) view.findViewById(R.id.getDetailsPhoneNumberEditText);
		continueButton = (Button) view.findViewById(R.id.getDetailsAgreeAndContinueButton);
		getDialog().setTitle("Details Required");
		
		// Show soft keyboard automatically
		emailEditText.requestFocus();
        getDialog().getWindow().setSoftInputMode(
                LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        
        continueButton.setOnClickListener(this);
        
		return view;
	}
	
	
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		String email = emailEditText.getText().toString();
		String phoneNumber = phoneNumberEditText.getText().toString();
		
		if(!isValidEmail(email)){
			emailEditText.setError("Invalid Email");
			return;
		}
		
		if(!isValidPhone(phoneNumber)){
			phoneNumberEditText.setError("Invalid Phone Number");
			return;
		}
		
		MainActivity activity = (MainActivity) getActivity();
		
		UserData userData = new UserData();
		userData.setEmail(email);
		userData.setPhonenumber(phoneNumber);
		userData.setTermsAccepted(true);
		
		activity.doListenerTask(userData);
		this.dismiss();
	}

	public final static boolean isValidEmail(CharSequence target) {
	    if (target == null) {
	        return false;
	    } else {
	        return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
	    }
	}
	
	public final static boolean isValidPhone(CharSequence target) {
	    if (target == null) {
	        return false;
	    } else {
	        return android.util.Patterns.PHONE.matcher(target).matches();
	    }
	}
	
}
