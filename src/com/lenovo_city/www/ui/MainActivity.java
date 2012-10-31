package com.lenovo_city.www.ui;

import com.lenovo_city.www.R;
import com.lenovo_city.www.model.MainModel;
import com.lenovo_city.www.util.MyResultReceiver;
import com.lenovo_city.www.util.MyResultReceiver.Receiver;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;
import gueei.binding.app.BindingActivity;

public class MainActivity extends BindingActivity implements Receiver {

	public MyResultReceiver mReceiver;
	
	public final int SIGN_IN = 1;
	public final int SIGN_OUT = 2;
	public final int ERROR = 3;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        mReceiver = new MyResultReceiver(new Handler());
        mReceiver.setReceiver(this);
		
        MainModel m_model = new MainModel(this,mReceiver);
        this.setAndBindRootView(R.layout.main, m_model);
        this.setAndBindOptionsMenu(R.layout.main_menu, m_model);
	}
	
	@Override
    public void onPause() {
        mReceiver.setReceiver(null);
        super.onPause();
    }
    
	@Override
	public void onReceiveResult(int resultCode, Bundle resultData) {
		switch(resultCode){
		case SIGN_IN:
			Toast.makeText(this, new Boolean(resultData.getBoolean("success")).toString(), Toast.LENGTH_LONG).show();
			break;
		case SIGN_OUT:
			Toast.makeText(this, new Boolean(resultData.getBoolean("success")).toString(), Toast.LENGTH_LONG).show();
			break;
		}
	}
	
}


