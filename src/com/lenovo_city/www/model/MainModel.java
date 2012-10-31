package com.lenovo_city.www.model;

import com.lenovo_city.www.function.Sign;
import com.lenovo_city.www.ui.TabWidgetActivity;
import com.lenovo_city.www.util.MyResultReceiver;
import gueei.binding.Command;
import android.app.Activity;
import android.content.Intent;
import android.view.View;

public class MainModel {
	
	public final int SIGN_IN = 1;
	public final int SIGN_OUT = 2;
	
	protected Activity mContext;
	protected MyResultReceiver mReceiver;
	
	public MainModel(Activity context,MyResultReceiver receiver){
		mContext = context;
		mReceiver = receiver;
	}
		
	public Command SignIn = new Command() {		
		@Override
		public void Invoke(View arg0, Object... arg1) {
			Intent intent = new Intent(mContext,Sign.class);
			intent.putExtra("receiver", mReceiver);
			intent.putExtra("type", SIGN_IN);
			mContext.startService(intent);
		}
	};
	
	public Command SignOut = new Command() {		
		@Override
		public void Invoke(View arg0, Object... arg1) {
			Intent intent = new Intent(mContext,Sign.class);
			intent.putExtra("receiver", mReceiver);
			intent.putExtra("type", SIGN_OUT);
			mContext.startService(intent);
		}
	};
	
	public Command ShopList = new Command() {		
		@Override
		public void Invoke(View arg0, Object... arg1) {
			Intent intent = new Intent(mContext,TabWidgetActivity.class);
			mContext.startActivity(intent);
		}
	};
	
	public Command DeleteData = new Command() {		
		@Override
		public void Invoke(View arg0, Object... arg1) {
			// TODO Auto-generated method stub			
		}
	};
}
