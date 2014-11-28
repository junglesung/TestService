package com.example.vernonsung.testservice;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends Activity {
	
	// UI
	private Button buttonStartService;
	private Button buttonGetCount;
	private Button buttonKillService;
	
	// Variable
	private HelloService mService;
	private ServiceConnection mConnection = new ServiceConnection() {
		
		@Override
		public void onServiceDisconnected(ComponentName name) {
			mService = null;
		}
		
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			HelloService.LocalBinder binder = (HelloService.LocalBinder)service;
			mService = binder.getService();
		}
	};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // Get UI components
        buttonStartService = (Button)findViewById(R.id.buttonStartService);
        buttonGetCount = (Button)findViewById(R.id.buttonGetCount);
        buttonKillService = (Button)findViewById(R.id.buttonKillService);
        
        // Set listener
        buttonStartService.setOnClickListener(new Button.OnClickListener() {
        	
        	@Override
        	public void onClick(View v) {
        		startHelloService();
        	}
        });
        buttonGetCount.setOnClickListener(new Button.OnClickListener() {
        	
        	@Override
        	public void onClick(View v) {
        		getServiceCount();
        	}
        });
        buttonKillService.setOnClickListener(new Button.OnClickListener() {
        	
        	@Override
        	public void onClick(View v) {
        		killHelloService();
        	}
        });
        
        // Action
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

	@Override
	protected void onStart() {
		super.onStart();
		bindHelloService();
		startHelloService();
	}

	@Override
	protected void onStop() {
		super.onStop();
		unBindHelloService();
	}


	private void startHelloService() {
    	Intent intent;
    	ComponentName c;

    	// Hello service is already running
    	if (mService != null && !mService.isToExit()) {
    		Toast.makeText(this, "Hello service is already running", Toast.LENGTH_SHORT).show();
    		return;
    	}
    	
    	intent = new Intent(this, HelloService.class);
    	try {
    		c = startService(intent);
    		if (c == null) {
    			Toast.makeText(this, "Return null", Toast.LENGTH_SHORT).show();
    		} else {
    			Toast.makeText(this, c.toString(), Toast.LENGTH_SHORT).show();
    		}
    	} catch (Exception e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
    }
    
    private void bindHelloService() {
    	Intent intent = new Intent(this, HelloService.class);
    	bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }
    
    private void unBindHelloService() {
    	if (mService != null) {
    		unbindService(mConnection);
    	}
    }
    
    private void getServiceCount() {
    	int counter;
    	if (mService != null) {
	    	counter = mService.getCounter();
	    	Toast.makeText(this, String.valueOf(counter), Toast.LENGTH_SHORT).show();
    	} else {
    		Toast.makeText(this, "Service is not bound", Toast.LENGTH_SHORT).show();
    	}
    }
    
    private void killHelloService() {
    	if (mService != null)
    		mService.setToExit(true);
    }
}
