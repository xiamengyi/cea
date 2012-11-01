package com.lenovo_city.www.model;

import com.lenovo_city.www.function.Login;
import com.lenovo_city.www.util.MyResultReceiver;
import gueei.binding.Command;
import gueei.binding.observables.BooleanObservable;
import gueei.binding.observables.StringObservable;
import gueei.binding.validation.ModelValidator;
import gueei.binding.validation.ValidationResult;
import gueei.binding.validation.validators.Required;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.view.View;

public class LoginModel {

	protected Activity mContext;

	protected ProgressDialog mProgressDialog;
	protected MyResultReceiver mReceiver;
	
	public StringObservable IMEI = new StringObservable();
	
	public LoginModel(Activity context,String imei,MyResultReceiver receiver){
		mContext = context;
		IMEI.set(imei);
		mReceiver = receiver;
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
				Intent intent = new Intent(mContext,Login.class);
				intent.putExtra("receiver", mReceiver);
				intent.putExtra("user_imei", IMEI.get());
				intent.putExtra("user_password", password.get());
				mContext.startService(intent);
			}
		}
	};
}
