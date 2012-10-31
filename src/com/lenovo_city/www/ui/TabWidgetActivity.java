package com.lenovo_city.www.ui;

import com.lenovo_city.www.R;

import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

public class TabWidgetActivity extends TabActivity {
	
	 TabHost mTabHost;
	 android.widget.TabWidget  mTabWidget;
	 
		private void setupTabHost() {
			mTabHost = (TabHost) findViewById(android.R.id.tabhost);
			mTabHost.setup();
		}
	 
     @Override
     public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab);
		setupTabHost();
		setupTab(new TextView(this), "店面列表","ShopListActivity.class");
		setupTab(new TextView(this), "当前位置","ShopMapActivity.class");
    }
     
	private void setupTab(final View view, final String tag,final String className) {
		
		View tabview = createTabView(mTabHost.getContext(), tag);  
		Intent intent;
		intent = new Intent().setClass(this, ShopListActivity.class);
		if (className.equals("ShopListActivity.class")) {
		   intent = new Intent().setClass(this, ShopListActivity.class);
		   }	
		if (className.equals("ShopMapActivity.class")) {
		   intent = new Intent().setClass(this, ShopMapActivity.class);
		   }
		TabSpec setContent = mTabHost.newTabSpec(tag).setIndicator(tabview).setContent(intent);
		mTabHost.addTab(setContent);
	}
	private static View createTabView(final Context context, final String text) {
		View view = LayoutInflater.from(context).inflate(R.layout.tabs_bg, null);
		TextView tv = (TextView) view.findViewById(R.id.tabsText);
		tv.setText(text);
		return view;
	} 
}


