package com.lenovo_city.www.app;

import android.app.Application;
import gueei.binding.Binder;
import android.util.Log;
import android.widget.Toast;

import com.baidu.mapapi.*;

public class BindingApp extends Application{

	static BindingApp mApp;
	
	BMapManager mBMapMan = null;
	String mStrKey = "0AC573D37198F575360CAAF05A26BD82284D9A79";
	boolean m_bKeyRight = true;
	
	static class MyGeneralListener implements MKGeneralListener{
		@Override
		public void onGetNetworkState(int iError){
			Log.d("MyGeneralListener", "onGetNetworkState error is "+ iError);
			Toast.makeText(BindingApp.mApp.getApplicationContext(), "您的网络出错啦！",Toast.LENGTH_LONG).show();
		}

		@Override
		public void onGetPermissionState(int iError){
			Log.d("MyGeneralListener", "onGetPermissionState error is "+ iError);
			if(iError ==  MKEvent.ERROR_PERMISSION_DENIED){
				Toast.makeText(BindingApp.mApp.getApplicationContext(), 
					"请在BMapApiDemoApp.java文件输入正确的授权Key！",Toast.LENGTH_LONG).show();
				BindingApp.mApp.m_bKeyRight = false;
			}
		}
	}
	
	@Override
	public void onCreate(){
		Log.v("BindingApp", "onCreate");
		mApp = this;
		mBMapMan = new BMapManager(this);
		mBMapMan.init(this.mStrKey, new MyGeneralListener());
		mBMapMan.getLocationManager().setNotifyInternal(10, 5);
		super.onCreate();
		Binder.init(this);
	}
	
	@Override
	public void onTerminate(){
		if (mBMapMan != null){
			mBMapMan.destroy();
			mBMapMan = null;
		}
		super.onTerminate();
	}
}
