package com.padiwp.kokurikuler;

import android.util.Log;

public class Xor {
	String sandi(String pesan, char key){
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < pesan.length(); i++)
			sb.append((char)(pesan.charAt(i) ^ key));
		String result = sb.toString();
		Log.i("Hasil",result);
		return(result);
	}
}
