package com.padiwp.kokurikuler;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Main extends Activity {
	
	Xor xor = new Xor();
	//Button tombol;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//Log.i("Info","Proses Satu");
		//String sandi = xor.sandi("Terang Sekali", 'a');
		//String terang = xor.sandi(sandi, 'a');
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void proses(View v) {
		Log.i("Info","Proses Ditekan");
		EditText pesan = (EditText) findViewById(R.id.editText1);
		String Strpesan = pesan.getText().toString();
		
	}

}
