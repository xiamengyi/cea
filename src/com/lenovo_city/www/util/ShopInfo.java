package com.lenovo_city.www.util;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;  

public class ShopInfo implements Parcelable{
	
	protected final static String TAG = "ShopInfo"; 
	
	protected String  shop_id = "0";
	protected String  shop_name = "";
	protected String  shop_code = "";
	protected String  task_id = "";
	protected String  shop_province = "";
	protected String  shop_city = "";
	protected String  shop_region = "";
	protected String  shop_address = "";
	protected String  shop_status = "";
	protected String  survey_name = "";
	protected String  task_count = "";
	
	protected double shop_latitude = 0.0;
	protected double shop_longitude = 0.0;

	public String getTask_count() {
		return task_count;
	}
	public void setTask_count(String task_count) {
		this.task_count = task_count;
	}
	public String getSurvey_name() {
		return survey_name;
	}
	public void setSurvey_name(String survey_name) {
		this.survey_name = survey_name;
	}
	public String getShopId() {  
		return shop_id;  
	}	
	public void setShopId(String shop_id) {  
		this.shop_id = shop_id;  
	}  
	
	public String getTaskId() {
		return task_id;
	}
	public void setTaskId(String task_id) {
		this.task_id = task_id;
	}
	
	public String getShopName() {  
		return shop_name;
	}
	
	public void setShopName(String shop_name) {  
		this.shop_name = shop_name;  
	}
	
	public String getShopCode() {  
		return shop_code;
	}
	
	public void setShopCode(String shop_code) {  
		this.shop_code = shop_code;
	}
	
	public double getShopLatitude() {  
		return shop_latitude;
	}
	
	public void setShopLatitude(double shop_latitude) {  
		this.shop_latitude = shop_latitude;
	}
	public double getShopLongitude() {  
		return shop_longitude;
	}
	
	public void setShopLongitude(double shop_longitude) {  
		this.shop_longitude = shop_longitude;
	}
	
	public String getShopProvince() {  
		return shop_province;
	}
	
	public void setShopProvince(String shop_province) {  
		this.shop_province = shop_province;
	}
	
	public String getShopCity() {  
		return shop_city;
	}
	
	public void setShopCity(String shop_city) {  
		this.shop_city = shop_city;
	}
	
	public String getShopRegion() {  
		return shop_region;
	}
	
	public void setShopRegion(String shop_region) {  
		this.shop_region = shop_region;
	}
	
	public String getShopAddress() {  
		return shop_address;
	}
	
	public void setShopAddress(String shop_address) {  
		this.shop_address = shop_address;
	}
				
	public String getShop_status() {
		return shop_status;
	}
	public void setShop_status(String shop_status) {
		this.shop_status = shop_status;
	}

	public static final Parcelable.Creator<ShopInfo> CREATOR = new Creator<ShopInfo>() {  
		@Override  
		public ShopInfo createFromParcel(Parcel source) {  
			Log.d(TAG,"createFromParcel");  
			ShopInfo mShopInfo = new ShopInfo();  
			mShopInfo.shop_id = source.readString();
			mShopInfo.shop_name = source.readString(); 
			mShopInfo.shop_code = source.readString();
			
			mShopInfo.shop_latitude = source.readDouble();
			mShopInfo.shop_longitude = source.readDouble();
			
			mShopInfo.shop_province = source.readString();
			mShopInfo.shop_city = source.readString(); 
			mShopInfo.shop_region = source.readString();
			mShopInfo.shop_address = source.readString(); 
			
			return mShopInfo;  
		}  
		@Override  
		public ShopInfo[] newArray(int size) {  
			return new ShopInfo[size];  
		}  
	};  
	@Override  
	public int describeContents() {  
		Log.d(TAG,"describeContents");  
		return 0;  
	}  
	@Override  
	public void writeToParcel(Parcel dest, int flags) {  
		dest.writeString(shop_id);
		dest.writeString(shop_name);
		dest.writeString(shop_code);
		
		dest.writeDouble(shop_latitude);
		dest.writeDouble(shop_longitude);
		
		dest.writeString(shop_province); 
		dest.writeString(shop_city); 
		dest.writeString(shop_region);
		dest.writeString(shop_address); 
	} 
}