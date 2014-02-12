package com.enkripsisms;



import com.enkripsisms.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class baca_sms extends Activity {
	private EditText kunci,pesan,hasil2,noPengirim;
	private Button dekripsi;
	private byte[] dekrip,bpesan;
	private String Skunci,Spesan,hasildekrip;
	//String arrayisi[]=new String[2];
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.baca_sms);
	    
	    Bundle extras=getIntent().getExtras();
	    String no=extras.getString(Inbox.nosms);
	    String isi=extras.getString(Inbox.isisms);
	    
	    kunci=(EditText) findViewById(R.id.kunci2);
	    pesan=(EditText) findViewById(R.id.pesan2);
	    hasil2=(EditText) findViewById(R.id.hasil2);
	    noPengirim=(EditText)findViewById(R.id.noPengirim);
	    dekripsi=(Button)findViewById(R.id.dekrip);
	    pesan.setFocusable(false);
	    hasil2.setFocusable(false);
	    
	    noPengirim.setText(no);
	    pesan.setText(isi);
	    
	    /*
	    //membuat 2 buah array untuk no HP dan Isi SMS
	    int count=0;
	    arrayisi[0]="";
	    for(int i=0;i<isi.length();i++) {
	      	if(isi.charAt(i)!=':'){
	       		arrayisi[count]+=isi.charAt(i);
	       	}
	        else if( isi.charAt(i)==':') {
	        	arrayisi[count+1]="";
	        	count++;
	        	}
	    }
	    	    
	    //mengambil pesan ke dalam textbox
	    noPengirim.setText(arrayisi[0]);
	    pesan.setText(arrayisi[1]);
	    */
	    
	    dekripsi.setOnClickListener(new Button.OnClickListener() {
	    	@Override
	    	public void onClick(View v) {
	    		dekrip();
	    	}
	    });
	}
	
	private void dekrip() {
		try{
		//memanggil class RC6.java
		RC6 rc6=new RC6();
		Skunci=kunci.getText().toString();
		Spesan=pesan.getText().toString();
		
		   	if(Skunci.length()>0) {
	    		//mengubah pesan enkripsi heksa ke byte
		   		bpesan=hex2Byte(Spesan);
		   		//bpesan= Spesan.getBytes();
	    		//dekripsi pesan
	    		dekrip=rc6.decrypt(bpesan, Skunci.getBytes());
	    		//hasil deksripsi pesan dalam bentuk byte di ubah ke bentuk string
	    		hasildekrip=new String(dekrip);
	    		//hasil dekripsi di cetak
	    		hasil2.setText(hasildekrip);
	        }
	        else {
	        	Toast.makeText(getBaseContext(),"kunci tidak boleh kosong", Toast.LENGTH_SHORT).show();
	        }
		}  	
		   	catch(Exception e){
		   		Toast.makeText(getBaseContext(), "Bukan SMS Terenkripsi",Toast.LENGTH_LONG).show();
		   	}
	}
	
	//konversi hexa ke byte
    private byte[] hex2Byte(String str) {
       byte[] bytes = new byte[str.length() / 2];
       for (int i = 0; i < bytes.length; i++)
       {
          bytes[i] = (byte) Integer
                .parseInt(str.substring(2 * i, 2 * i + 2), 16);
       }
       return bytes;
    }
}
