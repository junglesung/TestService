package com.example.vernonsung.testservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BootCompletedReceiver extends BroadcastReceiver {
    private static final String LOG_TAG = "Debug";

	public BootCompletedReceiver() {
	}

	@Override
	public void onReceive(Context context, Intent intent) {
        Log.d(LOG_TAG, context.getString(R.string.starting_service));
		if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
			context.startService(new Intent(context, HelloService.class));
		}
	}

}
