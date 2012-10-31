package com.lenovo_city.www.app;

import android.app.Application;
import gueei.binding.Binder;

public class BindingApp extends Application{

	@Override
	public void onCreate() {
		super.onCreate();
		Binder.init(this);
	}
}
