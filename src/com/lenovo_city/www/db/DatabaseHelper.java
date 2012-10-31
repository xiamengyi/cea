package com.lenovo_city.www.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper{
	
	protected Context mContext;
	protected String table_name;
	protected String sql;

	public DatabaseHelper(Context context, String name, CursorFactory factory,
			int version,String table,String str){
		super(context, name, factory, version);
		mContext = context;
		table_name = table;
		sql = str;
	}

	@Override
	public void onCreate(SQLiteDatabase db){
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
		db.execSQL("DROP TABLE"+table_name);
		this.onCreate(db);
	}
}
