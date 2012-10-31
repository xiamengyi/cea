package com.lenovo_city.www.model;

import org.json.JSONObject;

import com.lenovo_city.www.R;
import com.lenovo_city.www.function.Login;
import com.lenovo_city.www.share.AppConfig;
import com.lenovo_city.www.share.RequestMethod;
import com.lenovo_city.www.ui.MainActivity;
import com.lenovo_city.www.util.MyResultReceiver;
import com.lenovo_city.www.util.RestClient;

import gueei.binding.Command;
import gueei.binding.observables.BooleanObservable;
import gueei.binding.observables.StringObservable;
import gueei.binding.validation.ModelValidator;
import gueei.binding.validation.ValidationResult;
import gueei.binding.validation.validators.Required;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Toast;

public class LoginModel {

	protected Activity mContext;
	protected String IMEI;
	protected SharedPreferences mPreferences;
	protected ProgressDialog mProgressDialog;
	protected MyResultReceiver mReceiver;
	
	public LoginModel(Activity context,String imei,MyResultReceiver receiver){
		mContext = context;
		IMEI = imei;
		mReceiver = receiver;
		mPreferences = mContext.getSharedPreferences(AppConfig.TAG, Context.MODE_PRIVATE);
		mProgressDialog = new ProgressDialog(mContext); 
	}
	
	@Required(ErrorMessage="密码不能为空!")
	public StringObservable password = new StringObservable();
	
	public BooleanObservable remember_password = new BooleanObservable();
			
	public Command Login = new Command(){
		@Override
		public void Invoke(View view, Object... args) {
			ValidationResult result = ModelValidator.ValidateModel(LoginModel.this); 
			if (result.isValid()){
				//new Login().execute("login/apiAuthenticate",IMEI,password.get());
				Intent intent = new Intent(mContext,Login.class);
				intent.putExtra("receiver", mReceiver);
				intent.putExtra("user_imei", IMEI);
				intent.putExtra("user_password", password.get());
				mContext.startService(intent);
			}
		}
	};
	
	protected class Login2 extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {
			RestClient client = new RestClient(params[0]);			
			client.addParam("user_imei", params[1]);
			client.addParam("user_password", params[2]);
			try {			
				client.execute(RequestMethod.POST);				 
				if(client.getResponseCode() == 200) {
					mProgressDialog.dismiss();
					JSONObject jObj = new JSONObject(client.getResponse());
					Boolean success = jObj.getBoolean("success");
					if(success){						
						JSONObject addon = jObj.getJSONObject("addon");
						//Shares.taskNumbers = addon.getInt("task_count");
						Boolean signInStatus = addon.getBoolean("signInStatus");
						Boolean signOutStatus = addon.getBoolean("signOutStatus");
						updateUserInfo(params[2]);						
						Intent intent = new Intent();
						intent.setClass(mContext, MainActivity.class);
			        	intent.putExtra("notification",client.getResponse());
			        	intent.putExtra("signInStatus",signInStatus);
			        	intent.putExtra("signOutStatus",signOutStatus);
			        	mContext.startActivity(intent);
			        	return null;
					}else{
						return jObj.getString("detail");
					}
				} else {
					mProgressDialog.dismiss();
					return mContext.getString(R.string.http_error);
				}
			} catch (Exception e) {
				mProgressDialog.dismiss();
				return e.toString();
			}
        }        
        @Override
        protected void onPostExecute(String errorMessage) {
            if (errorMessage != null) {
            	Toast.makeText(mContext, errorMessage, Toast.LENGTH_LONG).show();
            }
        }
        
    	protected void updateUserInfo(String password){
    		Editor editor = mPreferences.edit();
    		if(remember_password.get()){
    			editor.putString("password", password);
    		}else{
    			editor.putString("password", "");
    		}
    		editor.commit();
    	}
	}	
}
