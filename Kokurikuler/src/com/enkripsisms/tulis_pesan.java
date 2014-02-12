package com.enkripsisms;

import java.util.ArrayList;

import com.enkripsisms.R;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
//import android.provider.Contacts;
//import android.provider.Contacts.People;
//import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class tulis_pesan extends Activity {

	private EditText kunci,pesan,hasil,NoTujuan;
	private Button enkripsi,kirim,contact;
	private String Skunci,Spesan,hexenkrip,no,pesanEnkrip;
	private byte[] enkrip;
	final static int RQS_PICK_CONTACT = 1;
		@Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        
	        setContentView(R.layout.main);
	        kunci=(EditText) findViewById(R.id.kunci);
	        pesan=(EditText) findViewById(R.id.pesan);
	        hasil=(EditText) findViewById(R.id.hasil);
	        NoTujuan=(EditText)findViewById(R.id.NoTujuan);
	        enkripsi=(Button)findViewById(R.id.enkrip);
	        kirim=(Button)findViewById(R.id.kirim);
	        contact=(Button) findViewById(R.id.contact);	        
	        hasil.setFocusable(false);
	        
	        //ambil kontak telp
	        contact.setOnClickListener(new View.OnClickListener(){
	        	@Override
	        	public void onClick(View v) {
	        		kontak();
	        	}
	        });
	        
	        enkripsi.setOnClickListener(new Button.OnClickListener() {
	        	@Override
				public void onClick(View v) {
					enkrip();
	        	}
			    
	        });
	        
	        kirim.setOnClickListener(new Button.OnClickListener() {
	        	@Override
				public void onClick(View v) {
	        		send();
	        	}
	        });
	        
		}
			    	    
	    //konvert dari byte array ke hexa string
	    private String byteArrayToHexString(byte[] b) {
			StringBuffer sb = new StringBuffer(b.length * 2);
			for (int i = 0; i < b.length; i++) {
				int v = b[i] & 0xff;
				if (v < 16) {
					sb.append('0');
				}
				sb.append(Integer.toHexString(v));
			}
			return sb.toString().toUpperCase();
		}
	    
	    	    
	    //operasi load kontak
	    private void kontak() {
			Intent intent = new Intent(Intent.ACTION_PICK,
	        ContactsContract.Contacts.CONTENT_URI);
	        startActivityForResult(intent, RQS_PICK_CONTACT);
		}
		
	    //operasi enkrip
		private void enkrip() {
			//memanggil class RC6.java
	        RC6 rc6=new RC6();
	        Skunci=kunci.getText().toString();
	        Spesan=pesan.getText().toString();
	        
	        //pengecekan panjang kunci
	        if (Skunci.length()==0){
	        	Toast.makeText(getBaseContext(),"Kunci belum terisi", Toast.LENGTH_SHORT).show();
	        }
	        
	        else if (Skunci.length()>0 && Spesan.length()>0) {
	        	//mengenkripsi pesan 
	        	enkrip=rc6.encrypt(Spesan.getBytes(), Skunci.getBytes());
	        	//mengubah hasil enkripsi ke bentuk heksadesimal
	        	hexenkrip=byteArrayToHexString(enkrip);
	        	//hexenkrip = new String(enkrip);
	        	//cetak hasil enkrip dalam bentuk heksadesimal
	        	hasil.setText(hexenkrip);
	        }
	        else {
	        		Toast.makeText(getBaseContext(),"Pesan Tidak Boleh Kosong!!", Toast.LENGTH_SHORT).show();
	        }
		}
		
		//operasi kirim pesan
		private void send() {
			no=NoTujuan.getText().toString();
    		pesanEnkrip=hasil.getText().toString();
    		if(no.length()>0 && pesanEnkrip.length()>0) {
    			sendSMS(no,pesanEnkrip);
				pindah();
    		}
    		else if (no.length()>0 && hasil.length()==0) {
    			Toast.makeText(getBaseContext(),"Pesan Kosong / Belum Terenkripsi", Toast.LENGTH_SHORT).show();
    		}
    		else {
    			Toast.makeText(getBaseContext(),"Nomor Tujuan Belum Terisi", Toast.LENGTH_SHORT).show();
    		}
		}
		
		//kembali ke menu utama
		public void pindah() {
			Intent i=new Intent(this,Sms.class);
			startActivity(i);
		}
		
		//ambil nomor dari kontak hp
	    @Override
	    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    	super.onActivityResult(requestCode, resultCode, data);
	    	if (requestCode == RQS_PICK_CONTACT && resultCode==Activity.RESULT_OK) {
	    		Uri contactData = data.getData();
	    		ContentResolver cr = getContentResolver();
	    		Cursor cur = managedQuery(contactData, null, null, null, null);
	    		if (cur.getCount() > 0) {
	    			while (cur.moveToNext()) {
	    				//ambil id contact
	    				String id = cur.getString(cur
	    				.getColumnIndex(ContactsContract.Contacts._ID));
	    				//cek jumlah nomor pada contact yg dipilih
	    				if (Integer
	    					.parseInt(cur.getString(cur
	    					.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
	    						Cursor pCur = cr
	    						//query ke SQLite Contact
	    						.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
	    						null,
	    						ContactsContract.CommonDataKinds.Phone.CONTACT_ID
	    						+ " = ?", new String[] { id },
	    						null);
	    			
	    						while (pCur.moveToNext()) {
	    							//ambil nomor berdasarkan id yang dipilih
	    							String nomorHp = pCur
	    							.getString(pCur
	    							.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DATA));
	    							NoTujuan.setText(nomorHp);
	    						}
	    						pCur.close();
	    				}
	    			}
	    		}
	    	}
	    }
	    
		//metod pengiriman SMS dan Laporan pengiriman
	    private void sendSMS(String phoneNumber,String message) {
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
