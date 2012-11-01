package com.lenovo_city.www.function;

import org.json.JSONObject;

import com.lenovo_city.www.share.RequestMethod;
import com.lenovo_city.www.util.RestClient;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;

public class Sign extends IntentService{

	public final int SIGN_IN = 1;
	public final int SIGN_OUT = 2;
	public final int ERROR = 3;
	
	public Sign() {
		super("Sign");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
        final ResultReceiver receiver = intent.getParcelableExtra("receiver");  
        boolean type = intent.getBooleanExtra("type", false);
        Bundle result_data = new Bundle();
        RestClient client;
        if(type){
			client = new RestClient("api/signIn");
        }else{
        	client = new RestClient("api/signOut");
        }
 		client.addParam("attendance_longitude","0.0");
 		client.addParam("attendance_latitude","0.0");
        try{
        	client.execute(RequestMethod.POST);
        	if(client.getResponseCode()==200){
				JSONObject jObj = new JSONObject(client.getResponse());
				Boolean success = jObj.getBoolean("success");
				result_data.putBoolean("success", success);
				if(type) receiver.send(SIGN_IN, result_data);
				else receiver.send(SIGN_OUT, result_data);
        	}
        }catch(Exception e){
        	e.printStackTrace();
			result_data.putString("error", e.toString());
			receiver.send(ERROR, result_data);
        } 
        this.stopSelf(); 
	}	
}
