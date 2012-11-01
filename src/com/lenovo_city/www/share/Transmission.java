package com.lenovo_city.www.share;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import com.lenovo_city.www.util.RestClient;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.widget.Toast;

public class Transmission {
	private Context context; 
	private String url = "";
	private RestClient client;
	private boolean isInThread = false; //判断是否在线程中调用
	private String handlerMessage = "数据处理中...";
	private String resultMessage = "";
	private JSONObject jObj;
	private Handler handler; //针对线程调用
	
	private ProgressDialog handlerDialog;
	
	private enum ResultCode {
		SUCCESS,FAILURE,ERROR,EXCEPTION
	}
	
	public JSONObject getResponse() {
		return jObj;
	}
	
	public Transmission(Context context) {
		this.context = context;
	}
	
	public Transmission(Context context,String url) {
		this.context = context;
		this.url = url;
		client = new RestClient(this.url);
	}
	public Transmission(Context context,String url,String handlerMessage) {
		this.context = context;
		this.url = url;
		client = new RestClient(this.url);
		this.handlerMessage = handlerMessage;
	}
	
	public void start() {
		isInThread = context.getMainLooper().getThread() != Thread.currentThread();
		if(isInThread) {
			handlerResult(sendRequest());
		} else {
			if(handlerDialog!=null&&handlerDialog.isShowing()) {
				handlerDialog.dismiss();
			}
			if(handlerDialog == null) {
				handlerDialog = new ProgressDialog(context);
				handlerDialog.setMessage(handlerMessage);
			}
			handlerDialog.show();
			new AsyncRequest().execute("");
		}
	}
	
	public void addParam(String name,String value) {
		client.addParam(name, value);
	}
	public void addArrayParams(String name,ArrayList<?> values) {
		client.addArrayParam(name, values);
	}
	/*
	 * 显示toast
	 */
	public void showToast(final String msg,final int time) {
		if(isInThread) {
			handler.post(new Runnable() {            
		        @Override
		        public void run() {
		            Toast.makeText(context, msg, time).show();                
		        }
		    });
		} else {
			Toast.makeText(context, msg, time).show();    
		}
	}
	public void showToast(final String msg) {
		showToast(msg, Toast.LENGTH_LONG);
	}
	
	private ResultCode sendRequest() {
		try {
			client.execute(RequestMethod.POST);	
			if(client.getResponseCode() == 200) {
				jObj = new JSONObject(client.getResponse());
				Boolean success = jObj.getBoolean("success");
				if(success){
					return ResultCode.SUCCESS;
				}else{
					return ResultCode.FAILURE;
				}
			} else {
				return ResultCode.ERROR;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResultCode.EXCEPTION;
		}
	}
	
	protected void onSuccess(JSONObject jObj) throws JSONException {
		showToast(jObj.getString("detail"));
	}
	
	protected void onFailure(JSONObject jObj) throws JSONException  {
		showToast(jObj.getString("detail"));
		try {
			JSONObject addon = jObj.optJSONObject("addon");
			if(addon != null && addon.has("code") && addon.getInt("code") == 401) {
				//Intent dialog = new Intent(context, TimeoutDialog.class);
				//dialog.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				//context.startActivity(dialog);
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	protected void onError() throws Exception  {
		showToast("连接网络出现错误!");
	}
	protected void onException() {
		showToast("连接网络出现错误!");
	}
	protected void onCancel() {
		onCancel();
	}
	
	private void handlerResult(ResultCode result) {
		try {
			switch (result) {
			case SUCCESS:
				onSuccess(jObj);
				break;
			case FAILURE:
				onFailure(jObj);
				break;
			case ERROR:
				onError();
				break;
			case EXCEPTION:
				onException();
				break;
			default:
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	class AsyncRequest extends AsyncTask<String, String, ResultCode> {

		@Override
		protected ResultCode doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			return sendRequest();
		}

		@Override
		protected void onCancelled() {
			// TODO Auto-generated method stub
			super.onCancelled();
			onCancel();
		}

		@Override
		protected void onPostExecute(ResultCode result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			handlerDialog.dismiss();
			handlerResult(result);
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}

		@Override
		protected void onProgressUpdate(String... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
		}
		
	}
	
	public void destory() {
		this.client = null;
		this.handlerDialog = null;
		this.handler = null;
	}
	/*
	 * 一系列的setter和getter
	 * */
	public Context getContext() {
		return context;
	}
	public void setContext(Context context) {
		this.context = context;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
		client = new RestClient(this.url);
	}
	public boolean getIsInThread() {
		return isInThread;
	}
	public void setHandlerMessage(String handlerMessage) {
		this.handlerMessage = handlerMessage;
	}
	public String getResultMessage() {
		return resultMessage;
	}
	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}
	public Handler getHandler() {
		return handler;
	}
	public void setHandler(Handler handler) {
		this.handler = handler;
	}
	public ProgressDialog getHandlerDialog() {
		return handlerDialog;
	}
	public void setHandlerDialog(ProgressDialog handlerDialog) {
		this.handlerDialog = handlerDialog;
	}
}
