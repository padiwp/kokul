package com.padiwp.kokurikuler;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class Main extends Activity {
	
	//Xor xor = new Xor();
	RC6 rc = new RC6();
	Sms kirimsms = new Sms();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void proses(View v) {
		
		String Strkey = "Kunci";
		
		EditText pesan = (EditText) findViewById(R.id.editText1);
		String Strpesan = pesan.getText().toString();
		
		EditText tujuan = (EditText) findViewById(R.id.editText2);
		String Strtujuan = tujuan.getText().toString();
		
		byte[] Bytsandi = rc.encrypt(Strpesan.getBytes(), Strkey.getBytes());
		
		kirimsms.sendSMS(Strtujuan, Bytsandi.toString());
		
	}

}
