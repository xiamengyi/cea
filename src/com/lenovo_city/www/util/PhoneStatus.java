package com.lenovo_city.www.util;

import java.io.File;

import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.os.StatFs;
import android.text.format.Formatter;
import android.util.Log;
import android.widget.Toast;

import com.lenovo_city.www.R;

public class PhoneStatus {

	protected final  String TAG = "PhoneInformation";
	protected final  int WARNING_MEMORY = 15*1024*1024;
	protected Context context;
	
	public PhoneStatus(Context context){
		this.context = context;
	}
    
	public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        Boolean InternetStatus = cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnectedOrConnecting();
        if(!InternetStatus){
        	Log.e(TAG, "Networdk connection fails");
    		Toast.makeText(context, R.string.no_Available_Network, Toast.LENGTH_LONG).show();
    		return false;
        }else{
        	Log.i(TAG, "Network is avaliable"); 
	        return true;
        }
    } 
	
	public boolean hasEnoughMemory(){		
		ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		MemoryInfo mi = new MemoryInfo();
		am.getMemoryInfo(mi);
		if(mi.availMem < WARNING_MEMORY){
			Toast.makeText(context, R.string.leak_memory, Toast.LENGTH_LONG).show();
			Log.e(TAG, Formatter.formatFileSize(context, mi.availMem));
			return false;
		}	
		Log.i(TAG, Formatter.formatFileSize(context, mi.availMem));
		return true; 
	}
	
	public Boolean isSDCardAvailable(){
		if (Environment.getExternalStorageState().equals(    
                Environment.MEDIA_MOUNTED)) {
			Log.i(TAG, "SDCard is available");
			return true; 
        }else {    
        	Toast.makeText(context, R.string.sdcard_Not_Available, Toast.LENGTH_LONG).show();
        	Log.e(TAG, "SDCard isnot available"); 
    		return false;
    	}   
	}
	
	public boolean isSDCardSpaceEnough(int apkSize){
		File path = Environment.getExternalStorageDirectory(); 
		StatFs stat = new StatFs(path.getPath()); 
		long blockSize = stat.getBlockSize(); 
		long availableBlocks = stat.getAvailableBlocks();
		if(availableBlocks * blockSize <= apkSize){
			Log.e(TAG, "leak SDCard space,remaining space:"+Formatter.formatFileSize(context, availableBlocks * blockSize)); 
			Toast.makeText(context, R.string.leak_sdcard_space, Toast.LENGTH_LONG).show();
			return false;
		}
		Log.i(TAG, "leak SDCard space,remaining space:"+Formatter.formatFileSize(context, availableBlocks * blockSize)); 
		return true; 	
	}
	
	public boolean isWifiConnected( ){
	    ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo wifiNetworkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
	    if(wifiNetworkInfo.isConnected()){
	        return true ;
	    }	 
	    return false ;
    }
}
