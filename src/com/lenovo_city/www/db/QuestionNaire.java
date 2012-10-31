package com.lenovo_city.www.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class QuestionNaire extends MyDataBaseAdapter{
	
	protected SQLiteDatabase mSqLiteDatabase = null;
	protected String table_name = "question_naire";
	protected String create_table = "";

	public QuestionNaire(Context context, String tablename, String sql) {
		super(context, tablename, sql);
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		super.close();
	}

	@Override
	public void insert() {
		// TODO Auto-generated method stub
		super.insert();
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		super.update();
	}

	@Override
	public void delete() {
		// TODO Auto-generated method stub
		super.delete();
	}

	@Override
	public Cursor select() {
		// TODO Auto-generated method stub
		return super.select();
	}
	
}
