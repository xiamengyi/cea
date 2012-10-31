package com.lenovo_city.www.db;
  
import android.content.Context;   
import android.database.sqlite.SQLiteDatabase; 

public class MyDataBaseAdapter {
	
	protected Context mContext;
	protected SQLiteDatabase mSqLiteDatabase;
	protected DatabaseHelper mDatabaseHelper;
	
	protected final String DB_NAME = "cea";
	
	public MyDataBaseAdapter(Context context){
		mContext = context;
	}
}
