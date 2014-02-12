package com.padiwp.kokurikuler;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Sms extends Activity {  
	public static final String nosms="no";
	public static final String isisms="isi";
	String sms2no[];
	String sms2isi[];
	
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
    
    public void getSMSList(){
    	//menghubungkan ke inbox content message
    	Uri uriSMSURI = Uri.parse("content://sms/inbox");
    	Cursor cur = getContentResolver().query(uriSMSURI, new String[] { "_id", "thread_id", "address", "person", "date", "body" }, null, null,null);
    	String smsno = "";
    	String smsisi="";
    	while (cur.moveToNext()) {
    		//mengambil list sms yang ada pada content message 
    		//mengambil nomor
    		smsno +=cur.getString(2)+"@";
    		//mengambil isi sms
    		smsisi+=cur.getString(5)+"@";
    		}
    	sms2no=smsno.split("\\@");
    	sms2isi=smsisi.split("\\@");
    	
    	//variabel yang akan dicetak di listview
    	String[] listsms=new String[sms2no.length];
    	for(int i=0;i<sms2no.length;i++){
    		if(sms2isi[i].length()>=20){
    			//jika panjang sms lebih dari 20 karakter maka dipotong lalu ditambahkan ....
    			//lalu ditamplkan di listview
    			listsms[i]=sms2no[i]+"\n"+sms2isi[i].substring(0,20)+" ...";
    			}
    		else if(sms2isi[i].length()<20 &&sms2isi.length>=0){
    			//jika dibawah 20 karakter pesan yg nampil di listview ditampilkan
    			listsms[i]=sms2no[i]+"\n"+sms2isi[i];
    			}
    		}
    	
    	//nampilkan list view
    	@SuppressWarnings("unused")
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
    			android.R.layout.simple_list_item_1, listsms);
    	//setListAdapter(adapter);
    }
    
    protected void onListItemClick(ListView l, View v, int position, long id) {
    	Intent i=new Intent(this,baca_sms.class);
		//oper nomor pengirim sms ke form baca_sms
		i.putExtra(nosms, sms2no[position]);
		//oper isi pengirim sms ke form baca_sms
		i.putExtra(isisms, sms2isi[position]);
		startActivity(i);
	}
}
