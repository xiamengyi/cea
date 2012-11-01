package com.lenovo_city.www.util;

import java.io.File;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Environment;
import android.telephony.TelephonyManager;

public class PhoneUtil {

	public static int getAppVersionCode(Context context) {
		try {
			return context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0).versionCode;
		} catch (NameNotFoundException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static String getAppVersionName(Context context) {
		try {
			return context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0).versionName;
		} catch (NameNotFoundException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static String getIMEI(Context context){		
		return ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
	}	
	
	public static String getFilePath(String dir,String fileName) {
		String path = Environment.getExternalStorageDirectory().toString();
		if(dir.indexOf(path) !=0 ) {
			path += dir;
		}
		File f = new File(path);
		if(!f.exists()) {
			f.mkdir();
		}
		path += fileName;
		return path;
	}
}
