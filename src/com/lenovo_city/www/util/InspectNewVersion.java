package com.lenovo_city.www.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.lenovo_city.www.R;
import com.lenovo_city.www.share.AppConfig;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.widget.Toast;

public class InspectNewVersion {
	
	protected Context context;
	protected ProgressDialog mProgressDialog;
	protected ProgressDialog callerProgressDialog;
	
	public InspectNewVersion(Context context,ProgressDialog callerProgressDialog){
		this.context = context;
		this.callerProgressDialog = callerProgressDialog;
	}
	
	public boolean startinspectNewVersionAsyTask(){
		new inspectNewVersion().execute();
		return false;
	}

	protected class inspectNewVersion extends AsyncTask<String, String, String>{
		@Override
		protected void onPreExecute(){
			callerProgressDialog.setMessage(context.getString(R.string.version_check));
			callerProgressDialog.show();	
		}
		
		@Override
		protected String doInBackground(String... params) {
			PhoneStatus sd = new PhoneStatus(context.getApplicationContext());
			if(sd.isOnline()){
			     try {
			    	 URL url = new URL(AppConfig.VERSION_PATH);
			         HttpURLConnection hrc = (HttpURLConnection) url.openConnection();           
			         hrc.setRequestMethod("GET");
			         hrc.connect();
			         InputStream is = hrc.getInputStream();
			         ByteArrayOutputStream baos = new ByteArrayOutputStream();
			         byte[] buffer = new byte[1024];
			         int currentLength = 0;
			         while ((currentLength = is.read(buffer)) != -1 ) {               
			             baos.write(buffer,0, currentLength);
			         }
			         String version = baos.toString().trim();
			         String serverVersion = "1";
			         if(!version.equals("") && version.matches("^[0-9]*[1-9][0-9]*$")) {
			        	 serverVersion = version;
			         } 
			         baos.close();
			         return serverVersion;
			     }catch (Exception e) {
			         e.printStackTrace();                    
			         return e.toString();
			     } 
			}
			return "http_error";
		}
		
		@Override 
		protected void onPostExecute(String result){
			if(result.matches("^[0-9]*[1-9][0-9]*$")){
			    checkVersion(Integer.parseInt(result));
			}else{
		         Toast.makeText(context.getApplicationContext(), "Error." + result, Toast.LENGTH_LONG).show();
			}
			callerProgressDialog.dismiss();	
		}
		
	 	protected void checkVersion(int serverVersion){
			try{
				int applicationVersion = 0; 
				applicationVersion = context.getPackageManager().getPackageInfo(context.getPackageName(),0).versionCode; 
	    		if(applicationVersion < serverVersion){  
	    			isNeedUpdate();  
	    		}
			}catch(Exception e){
				e.printStackTrace();				
			}
	 	}
	 	
		protected void isNeedUpdate(){
	        new AlertDialog.Builder(context)
	        .setTitle(context.getResources().getString(R.string.version_Update))
	        .setMessage(context.getResources().getString(R.string.find_New_Version))
	        .setIcon(android.R.drawable.ic_menu_info_details)
	        .setPositiveButton(context.getResources().getString(R.string.dialog_Update_Straightway), new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					PhoneStatus sd = new PhoneStatus(context.getApplicationContext());
					if(sd.isOnline()&&sd.isSDCardAvailable()){
						DownloadNewApk dna = new DownloadNewApk(context);
						dna.startDownloadApkToSDcardAsyTask();
					}
				}
	        })
	        .setNegativeButton(context.getResources().getString(R.string.dialog_Not_Update_Now), null)
	        .show();
		}
	}
}
