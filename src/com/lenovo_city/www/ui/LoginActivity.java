package com.lenovo_city.www.ui;

import com.lenovo_city.www.R;
import com.lenovo_city.www.model.LoginModel;
import com.lenovo_city.www.share.AppConfig;
import com.lenovo_city.www.util.MyResultReceiver;
import com.lenovo_city.www.util.MyResultReceiver.Receiver;
import com.lenovo_city.www.util.PhoneUtil;

import gueei.binding.app.BindingActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;

public class LoginActivity extends BindingActivity implements Receiver{

	public MyResultReceiver mReceiver;
	
	protected int loginResultCode = -99;
	protected SharedPreferences mPreferences;
	protected LoginModel l_model;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
        mReceiver = new MyResultReceiver(new Handler());
        mReceiver.setReceiver(this);
        
		mPreferences = getSharedPreferences(AppConfig.TAG, Context.MODE_PRIVATE);
	    l_model = new LoginModel(this,PhoneUtil.getIMEI(this), mReceiver);  
	    this.setAndBindRootView(R.layout.login, l_model);
    }  


	protected void rememberPassword(){
		Editor editor = mPreferences.edit();
		if(l_model.remember_password.get()){
			editor.putString("password",l_model.password.get());
		}else{
			editor.putString("password", "");
		}
		editor.commit();
	}
	
	@Override
	public void onReceiveResult(int resultCode, Bundle resultData) {
		if(resultCode==loginResultCode){ 
			//rememberPassword();			
			Intent intent = new Intent();
			intent.putExtra("notification", resultData.getBundle("JSon"));
			intent.setClass(this, MainActivity.class);
			startActivity(intent);
		}
	}
}
