package com.lenovo_city.www.function;

import org.json.JSONObject;

import com.lenovo_city.www.share.Constant;
import com.lenovo_city.www.share.RequestMethod;
import com.lenovo_city.www.util.RestClient;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;

public class Login extends IntentService{

	protected int loginResultCode = -99;
	
	public Login() {
		super("Login");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		final ResultReceiver receiver = intent.getParcelableExtra("receiver");
		String IMEI = intent.getStringExtra("user_imei");
		String pasword = intent.getStringExtra("user_password");
        Bundle result_data = new Bundle();
        RestClient client = new RestClient("login/apiAuthenticate");
		client.addParam("user_imei", IMEI);
		client.addParam("user_password", pasword);
		try{
        	client.execute(RequestMethod.POST);
        	if(client.getResponseCode()==200){
				JSONObject jObj = new JSONObject(client.getResponse());
				if(jObj.getBoolean("success")){
					result_data.putString("JSon", client.getResponse());
					receiver.send(loginResultCode, result_data);
				}else{
					result_data.putString("fail", jObj.getString("detail"));
					receiver.send(Constant.SUCCESS_FAIL, result_data);
				}
        	}			
		}catch(Exception e){
			e.printStackTrace();
		}
	}	
}
