package com.lenovo_city.www.ui;

import com.lenovo_city.www.R;
import com.lenovo_city.www.model.LoginModel;
import com.lenovo_city.www.util.MyResultReceiver;
import com.lenovo_city.www.util.PhoneInformation;
import com.lenovo_city.www.util.MyResultReceiver.Receiver;

import gueei.binding.app.BindingActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class LoginActivity extends BindingActivity implements Receiver{

	public MyResultReceiver mReceiver;
	
	protected int loginResultCode = -99;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
        mReceiver = new MyResultReceiver(new Handler());
        mReceiver.setReceiver(this);
        
	    LoginModel l_model = new LoginModel(this,getIMEI(), mReceiver); 
	    this.setAndBindRootView(R.layout.login, l_model);
    }  
    
    protected String getIMEI(){
    	PhoneInformation pi = new PhoneInformation(this);
    	return pi.getIMEI();    	
    }

	@Override
	public void onReceiveResult(int resultCode, Bundle resultData) {
		if(resultCode==loginResultCode){
			Intent intent = new Intent();
			intent.setClass(this, MainActivity.class);
			startActivity(intent);
		}
	}
}
