package com.lenovo_city.www.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class QuestionNaire extends MyDataBaseAdapter{
	
	protected SQLiteDatabase mSqLiteDatabase = null;
	
	protected final String TABLE_NAME = "questionnaire";
	protected final String QUESTION_ID = "question_id";
	protected final String SHOP_NAME = "shop_name";
	protected final String QUESTION_STATUS = "question_status";	
	protected final String QUESTION_FINISH_TIME = "question_finish_time";
	protected final String LONGITUDE = "longitude";
	protected final String LATITUDE = "latitude";
	protected final String SHOP_ID = "shop_id";
	protected final String QUESTION_TYPE = "question_type";
	protected final String QUESTION_NAME = "question_name";
	
	protected final String CREATE_TABLE = 
            "CREATE TABLE if not exists "+TABLE_NAME+" " +"("+
            		 QUESTION_ID+" INT(11) PRIMARY KEY NOT NULL," +
            		 QUESTION_STATUS+" SMALLINT  NOT NULL DEFAULT 0," +
            		 SHOP_NAME+"  VARCHAR(100) NOT NULL DEFAULT ''," +
            		 QUESTION_FINISH_TIME+"   TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP," +
            		 LONGITUDE+" DECIMAL(25,20) NOT NULL DEFAULT 0," +
            		 LATITUDE+" DECIMAL(25,20) NOT NULL DEFAULT 0," +
            		 QUESTION_NAME+" VARCHAR(100) NOT NULL DEFAULT ''," +
            		 QUESTION_TYPE+" VARCHAR(10) NOT NULL DEFAULT ''," +
            		 SHOP_ID+" VARCHAR(20) NOT NULL DEFAULT '')";

	public QuestionNaire(Context context, String tablename, String sql) {
		super(context, tablename, sql);
	}

	@Override
	public void open() {
		super.open();
	}

	@Override
	public void close() {
		super.close();
	}

	@Override
	public void insert() {
		super.insert();
	}

	@Override
	public void update() {
		
	}

	@Override
	public void delete() {
		super.delete();
	}

	@Override
	public Cursor select() {
		return super.select();
	}
	
}
