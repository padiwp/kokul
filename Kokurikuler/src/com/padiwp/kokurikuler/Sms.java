package com.padiwp.kokurikuler;

import java.util.ArrayList;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.telephony.SmsManager;
import android.widget.Toast;

public class Sms extends Activity {  
		//metod pengiriman SMS dan Laporan pengiriman
	    public void sendSMS(String phoneNumber,String message) {
	    	String SENT="SMS_SENT";
	    	//PendingIntent sentPI=PendingIntent.getBroadcast(this,0,new Intent(SENT),0);
	    	registerReceiver(new BroadcastReceiver() {
	    		
				@Override
				public void onReceive(Context arg0,Intent arg1) {
	    			switch(getResultCode()) {
						case Activity.RESULT_OK:
							Toast.makeText(getBaseContext(),"SMS Sent", Toast.LENGTH_SHORT).show();
							break;
						case Activity.RESULT_CANCELED:
							Toast.makeText(getBaseContext(),"SMS not delivered", Toast.LENGTH_SHORT).show();
		    				break;
						case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
							Toast.makeText(getBaseContext(),"Generic failure", Toast.LENGTH_SHORT).show();
							break;
						case SmsManager.RESULT_ERROR_NO_SERVICE:
							Toast.makeText(getBaseContext(),"No Service", Toast.LENGTH_SHORT).show();
							break;
						case SmsManager.RESULT_ERROR_NULL_PDU:
							Toast.makeText(getBaseContext(),"Null PDU", Toast.LENGTH_SHORT).show();
							break;
						case SmsManager.RESULT_ERROR_RADIO_OFF:
							Toast.makeText(getBaseContext(),"Radio Off", Toast.LENGTH_SHORT).show();
							break;
	    			}
	    		}
	    	}, new IntentFilter(SENT));
	    	
	    	SmsManager sms=SmsManager.getDefault();
	    	//sms.sendTextMessage(phoneNumber,null, message, sentPI, sentPI);
	    	ArrayList<String> parts = sms.divideMessage(message);
	    	sms.sendMultipartTextMessage(phoneNumber, null, parts, null, null);
	    }
}
