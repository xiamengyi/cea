package com.lenovo_city.www.ui;

import android.os.Bundle;
import android.os.Handler;

import com.lenovo_city.www.R;
import com.lenovo_city.www.model.ShopListModel;
import com.lenovo_city.www.util.MyResultReceiver;

import gueei.binding.app.BindingActivity;

public class ShopListActivity extends BindingActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
        ShopListModel s_model = new ShopListModel(this);
        this.setAndBindRootView(R.layout.main, s_model);
	}
}
