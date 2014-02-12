package com.enkripsisms;



import com.enkripsisms.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

//method menampilkan menu utama dan actifity ketika tombol di klik
public class Sms extends Activity {
	Button tulispesan,bacasms,about;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    
	    setContentView(R.layout.awal);
	    tulispesan=(Button)findViewById(R.id.TulisPesan);
	    bacasms=(Button)findViewById(R.id.bacasms);
	    about=(Button)findViewById(R.id.about);
	        
	    tulispesan.setOnClickListener(new Button.OnClickListener() {
	       	@Override
			public void onClick(View v) {
				tulispesan();
	       	}
	    });
	    
		bacasms.setOnClickListener(new Button.OnClickListener() {
	        @Override
			public void onClick(View v) {
				inbox();
	        }
	    });
        
		about.setOnClickListener(new Button.OnClickListener() {
	        @Override
			public void onClick(View v) {
	        	about();
	        }
	    });
	}
	
	//memanggil form pesan
	public void tulispesan() {
		Intent i=new Intent(this,tulis_pesan.class);
		startActivity(i);
	}
	
	//memanggil form inbox
	public void inbox() {
		Intent i=new Intent(this,Inbox.class);
		startActivity(i);
	}
	
	//memanggil form about
	public void about() {
		Intent i=new Intent(this,About.class);
		startActivity(i);	
	}
}