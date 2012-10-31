package com.lenovo_city.www.db;
  
import android.content.Context;   
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase; 

public class MyDataBaseAdapter {
	
	protected Context mContext;
	protected SQLiteDatabase mSqLiteDatabase;
	protected DatabaseHelper mDatabaseHelper;
	protected String create_table;
	protected String table_name;	
	
	protected final String DB_NAME = "cea";
	protected final int DB_VERSION = 1;
	
	public MyDataBaseAdapter(Context context,String tablename,String sql){
		mContext = context;
		table_name = tablename;
		create_table = sql;
	}
	
	public void open(){	
		mDatabaseHelper = new DatabaseHelper(mContext,DB_NAME,null,DB_VERSION,create_table,table_name);  
		mSqLiteDatabase = mDatabaseHelper.getWritableDatabase(); 
	}
	
	public void close(){
		
	}
	
	public void insert(){
		
	}
	
	public void update(){
		
	}
	
	public void delete(){
		
	}
	
	public Cursor select(){
		Cursor cursor = null;
		return cursor;
	}
}
