package com.padiwp.kokurikuler;

import java.util.ArrayList;

import android.app.Activity;
import android.telephony.SmsManager;
import android.util.Log;

public class Sms extends Activity {  
		//metod pengiriman SMS dan Laporan pengiriman
    public void sendSMS(String phoneNumber,String message) {
    	//PendingIntent sentPI=PendingIntent.getBroadcast(this,0,new Intent(SENT),0);
    	Log.i("Info","Berhasil");
    	
    	Log.i("Info","Berhasil Juga");
    	SmsManager sms=SmsManager.getDefault();
    	//sms.sendTextMessage(phoneNumber,null, message, sentPI, sentPI);
    	ArrayList<String> parts = sms.divideMessage(message);
    	sms.sendMultipartTextMessage(phoneNumber, null, parts, null, null);
    }
}
