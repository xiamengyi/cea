package com.lenovo_city.www.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import com.lenovo_city.www.share.AppConfig;
import com.lenovo_city.www.share.RequestMethod;

import android.content.Context;

public class RestClient {

	private boolean authentication;
	private ArrayList<NameValuePair> headers;

	private String jsonBody;
	private String message;

	private ArrayList<NameValuePair> params;
	private String response;
	private int responseCode;

	private String url;

	private String username;
	private String password;
	
	private String sessionName = "ci_session";
	private static String sessionCookie = null; //for sessions keep live 

	protected Context context;

	public RestClient(String url) {
		if(url.indexOf("http://") == -1) {
			this.url = AppConfig.HOST + url;
		} else {
			this.url = url;
		}
		params = new ArrayList<NameValuePair>();
		headers = new ArrayList<NameValuePair>();
	}
	//Be warned that this is sent in clear text, don't use basic auth unless you have to.
	public void addBasicAuthentication(String user, String pass) {
		authentication = true;
		username = user;
		password = pass;
	}

	public void addHeader(String name, String value) {
		headers.add(new BasicNameValuePair(name, value));
	}

	public void addParam(String name, String value) {
		params.add(new BasicNameValuePair(name, value));
	}
	public void addArrayParam(String name,List<?> arr) {
		for(int i = 0 ; i < arr.size() ;i++) {
			params.add(new BasicNameValuePair(String.format("%s[%d]", name,i), arr.get(i).toString()));
		}
	}
	
	public void removeAllParam() {
		params.clear();
	}

	public void execute(RequestMethod method)
	    throws Exception {
		
		if(sessionCookie!=null){
			addHeader("Cookie", sessionCookie);
		}
		
		switch (method) {
			case GET: {
				HttpGet request = new HttpGet(url + addGetParams());
				request = (HttpGet) addHeaderParams(request);
				executeRequest(request, url);
				break;
			}
			case POST: {
				HttpPost request = new HttpPost(url);
				request = (HttpPost) addHeaderParams(request);
				request = (HttpPost) addBodyParams(request);
				executeRequest(request, url);
				break;
			}
			case PUT: {
				HttpPut request = new HttpPut(url);
				request = (HttpPut) addHeaderParams(request);
				request = (HttpPut) addBodyParams(request);
				executeRequest(request, url);
				break;
			}
			case DELETE: {
				HttpDelete request = new HttpDelete(url);
				request = (HttpDelete) addHeaderParams(request);
				executeRequest(request, url);
			}
		}
	}

	private HttpUriRequest addHeaderParams(HttpUriRequest request)
			throws Exception {
		for (NameValuePair h : headers) {
			request.addHeader(h.getName(), h.getValue());
		}

		if (authentication) {

			UsernamePasswordCredentials creds = new UsernamePasswordCredentials(
					username, password);
			request.addHeader(new BasicScheme().authenticate(creds, request));
		}

		return request;
	}

	private HttpUriRequest addBodyParams(HttpUriRequest request)
			throws Exception {
		if (jsonBody != null) {
			request.addHeader("Content-Type", "application/json");
			if (request instanceof HttpPost)
				((HttpPost) request).setEntity(new StringEntity(jsonBody,
						"UTF-8"));
			else if (request instanceof HttpPut)
				((HttpPut) request).setEntity(new StringEntity(jsonBody,
						"UTF-8"));

		} else if (!params.isEmpty()) {
			if (request instanceof HttpPost)
				((HttpPost) request).setEntity(new UrlEncodedFormEntity(params,
						HTTP.UTF_8));
			else if (request instanceof HttpPut)
				((HttpPut) request).setEntity(new UrlEncodedFormEntity(params,
						HTTP.UTF_8));
		}
		return request;
	}

	private String addGetParams()
	    throws Exception {
		StringBuffer combinedParams = new StringBuffer();
		if (!params.isEmpty()) {
			combinedParams.append("?");
			for (NameValuePair p : params) {
				combinedParams.append((combinedParams.length() > 1 ? "&" : "")
						+ p.getName() + "="
						+ URLEncoder.encode(p.getValue(), "UTF-8"));
			}
		}
		return combinedParams.toString();
	}

	public String getErrorMessage() {
		return message;
	}

	public String getResponse() {
		return response;
	}

	public int getResponseCode() {
		return responseCode;
	}

	public void setContext(Context ctx) {
		context = ctx;
	}

	public void setJSONString(String data) {
		jsonBody = data;
	}

	private void executeRequest(HttpUriRequest request, String url) {

		DefaultHttpClient client = new DefaultHttpClient();
		HttpParams params = client.getParams();

		// Setting 30 second timeouts
		HttpConnectionParams.setConnectionTimeout(params, 30 * 1000);
		HttpConnectionParams.setSoTimeout(params, 30 * 1000);

		HttpResponse httpResponse;

		try {
			httpResponse = client.execute(request);
			responseCode = httpResponse.getStatusLine().getStatusCode();
			message = httpResponse.getStatusLine().getReasonPhrase();

			HttpEntity entity = httpResponse.getEntity();
			
			
			//验证登录
			if (responseCode == 200) {
				CookieStore mCookieStore = client.getCookieStore();
	            List<Cookie> cookies = mCookieStore.getCookies();
	            
	            
	            for (int i = 0; i < cookies.size(); i++) {
	                //这里是读取Cookie['PHPSESSID']的值存在静态变量中，保证每次都是同一个值
	            	
	                if (sessionName.equals(cookies.get(i).getName())) {
	                	sessionCookie = sessionName+"="+cookies.get(i).getValue();
	                    break;
	                }

	            }
            }			
            //end of 

			if (entity != null) {

				InputStream instream = entity.getContent();
				response = convertStreamToString(instream);

				// Closing the input stream will trigger connection release
				instream.close();
				try {
					JSONObject addon = (new JSONObject(response)).getJSONObject("addon");
					if(addon.has("code") && addon.getInt("code") == 401) {
						//BaiduMapApp.afterOutOfLoginTime();
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		} catch (ClientProtocolException e) {
			client.getConnectionManager().shutdown();
			e.printStackTrace();
		} catch (IOException e) {
			client.getConnectionManager().shutdown();
			e.printStackTrace();
		}
	}

	private static String convertStreamToString(InputStream is) {

		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}
}