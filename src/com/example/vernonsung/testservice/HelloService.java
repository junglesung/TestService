package com.example.vernonsung.testservice;

import android.app.IntentService;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class HelloService extends IntentService {
	public class LocalBinder extends Binder {
		HelloService getService() {
			return HelloService.this;
		}
	}

	private static final String TAG = "HelloService";
	private static final int MS = 1000;
	private IBinder mBinder = new LocalBinder();
	private int counter = 0;
	private boolean toExit = true;
	
	public HelloService() {
		super("HelloService");
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		super.onStartCommand(intent, flags, startId);
		return START_STICKY;
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		toExit = false;
		while (!toExit) {
			Log.i(TAG, String.valueOf(counter));
			counter++;
			synchronized (this) {
				try {
					wait(MS);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return mBinder;
	}

	/**
	 * @return the counter
	 */
	public int getCounter() {
		return counter;
	}

	public boolean isToExit() {
		return toExit;
	}

	public void setToExit(boolean toExit) {
		this.toExit = toExit;
	}

}
