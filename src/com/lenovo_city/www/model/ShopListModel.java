package com.lenovo_city.www.model;

import gueei.binding.Command;
import gueei.binding.observables.StringObservable;
import gueei.binding.validation.validators.RegexMatch;
import android.app.Activity;
import android.view.View;

public class ShopListModel {
	
	protected Activity mContent;
	
	public ShopListModel(Activity content){
		mContent = content;
	}
	
	public StringObservable Keyword = new StringObservable();
	
	
	public Command Search = new Command() {
		@Override
		public void Invoke(View arg0, Object... arg1) {
			// TODO Auto-generated method stub
			
		}
	};	
}
