package com.lenovo_city.www.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.lenovo_city.www.R;
import com.lenovo_city.www.share.AppConfig;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.widget.Toast;

public class DownloadNewApk {
	
	protected Context context;
	protected ProgressDialog mProgressDialog;
	
	public DownloadNewApk(Context context){
		this.context = context;
	}
	
	public Boolean startDownloadApkToSDcardAsyTask(){
		setProgressDialog();
		new DownloadApkToSDcard().execute();
		return false;
	}	

	protected void setProgressDialog(){
		mProgressDialog = new ProgressDialog(context);
		mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		mProgressDialog.setTitle(context.getResources().getString(R.string.progress_Title));
		mProgressDialog.setProgress(0);
		mProgressDialog.setCancelable(false);
		mProgressDialog.setMessage(context.getResources().getString(R.string.progress_Message));
		mProgressDialog.show();
	}
	
    protected class DownloadApkToSDcard extends AsyncTask<String, Integer, String> {
        @Override  
        protected String doInBackground(String... params) {  
        	 HttpURLConnection c = null;
        	try {
	        	URL url = new URL(AppConfig.APP_PATH.toString());
	            c = (HttpURLConnection) url.openConnection();
	            c.setRequestMethod("GET");
	            c.setDoOutput(false);
	            c.setRequestProperty("Accept-Encoding", "identity");
	            c.connect();
	            String PATH = Environment.getExternalStorageDirectory() + "/download/";
	            File file = new File(PATH); 
	            if (!file.exists()) {
	                file.mkdirs();
	            }	        	
	        	int totalLen=0;
	            if(c.getContentLength() != -1) {
	            	totalLen = c.getContentLength();
	            }
	            File outputFile = new File(file, AppConfig.APK_NAME.toString());           
	            FileOutputStream fos = new FileOutputStream(outputFile);
	            InputStream is = c.getInputStream();
	            byte[] buffer = new byte[1024];
	            int len1 = 0;
	            int curLen = 0;
	            while ((len1 = is.read(buffer)) != -1) {
	            	curLen += len1;
	            	publishProgress(curLen,totalLen);
	                fos.write(buffer, 0, len1); 
	            }
	            fos.close();
       	     	is.close();
	        } 
	        catch (IOException e){
	            Toast.makeText(context.getApplicationContext(), "Error! "+e.toString(), Toast.LENGTH_LONG).show();
	        }           
        	finally {
        	    c.disconnect();
        	}
            return null;  
        }  
        
        @Override  
        protected void onProgressUpdate(Integer... progress) {    
        	mProgressDialog.setProgress(progress[0]);
        	mProgressDialog.setMax(progress[1]);
        } 
        
        @Override  
        protected void onPostExecute(String result) {  
            super.onPostExecute(result);  
            mProgressDialog.dismiss();
            newDialog();
        }
        
        protected void InstallApplication(){   
	        Uri packageURI = Uri.parse(AppConfig.PACKAGE_NAME);
	        Intent intent = new Intent(android.content.Intent.ACTION_VIEW, packageURI);
	        intent.setDataAndType
	        (Uri.fromFile(new File(Environment.getExternalStorageDirectory() + "/download/"  + AppConfig.APK_NAME.toString())), 
	        "application/vnd.android.package-archive");
	        context.startActivity(intent);
	        ((Activity) context).finish(); 
	    }
        
        protected void newDialog(){
            new AlertDialog.Builder(context)
            .setTitle(context.getResources().getString(R.string.version_Update))
            .setMessage(context.getResources().getString(R.string.new_Version_Message))
            .setIcon(android.R.drawable.ic_menu_info_details)
            .setPositiveButton(context.getResources().getString(R.string.dialog_Ok), new DialogInterface.OnClickListener() {
    			@Override
    			public void onClick(DialogInterface dialog, int which) {
                     InstallApplication();
    			}
            })
            .setNegativeButton(context.getResources().getString(R.string.dialog_Cancel), null)
            .show();
        }  
    }

}
