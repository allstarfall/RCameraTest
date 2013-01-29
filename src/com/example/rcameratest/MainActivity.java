package com.example.rcameratest;

import mobile.Mobile;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		testMobileJar();
		
		try{
			RCamera.run();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	//--->
	private void testMobileJar(){
		   Mobile m = new Mobile("223.4.145.13","8080","dms");
		   String jsessionid = m.login("dms","dms","lm_user");
		   String p = "jw_extends=true&operate=login&tablename=lm_user&userid=dms&password=dms"; // 登录
		   Log.i("--->", "登录得到 jsessionid="+jsessionid);
		   String data = "";
		   String s = "jw_extends=true&operate=query&moduleid=00090A50260D5&type=02&day=2012-12-12";
		   s = m.sendToWeb(s,data);
		   Log.i("--->", "s="+s);
	}
	
	private void testAPI(){
		
	}
}
