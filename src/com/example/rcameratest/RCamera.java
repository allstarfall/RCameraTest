package com.example.rcameratest;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.util.Log;



public class RCamera {

	private static String login = "http://223.4.145.13:8080/dms/lm_queryservlet?"+
			"jw_extends=%s&operate=%s&userid=%s&password=%s&tablename=%s";

	private static String fetchProgressTable = "http://223.4.145.13:8080/dms/lm_queryservlet?" + 
			"jw_extends=%s&operate=%s&jsessionid=%s&moduleid=%s&type=%d&day=%s";
	
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void run() throws Exception {
		// TODO Auto-generated method stub
		
		String login = genLogin(true, "login", "dms", "dms", "lm_user" );
		
		Log.i("--->", login );
		
		String sessionId = fetch( login ).trim();
		
		Log.i("--->", sessionId );

//      Try to get progress table
		String ptabel = genProgressTabel( true, "query", sessionId, "00090A50260D5", 1, "2012-10-12");
		
		Log.i("--->", ptabel );
		
		String ptableString = fetch( ptabel );
		
		Log.i("--->", ptableString );
		
//      Try to get progress analytics
//		String ptabel = genProgressTabel( true, "query", sessionId, "00090A50260D5", 2, "2012-10-12");
//		
//		Log.i("--->", ptabel );
//		
//		String ptableString = fetch( ptabel );
//		
//		Log.i("--->", ptableString );
	
	}
	
	/**
	 * 通过则：有效字串 jsessionid, 没通过则：301用户名错误；302 密码错误
	 * @param isJW
	 * @param operation
	 * @param uid
	 * @param pw
	 * @param table
	 * @return
	 */
	private static String genLogin(boolean isJW, String operation, String uid, String pw,String table){
		return String.format(login, String.valueOf(isJW) , operation, uid, pw, table);
	}
	
	private static String genProgressTabel(boolean isJW, String operation, String sessionId, String moduleId, int type, String day){
		return String.format(fetchProgressTable, String.valueOf(isJW), operation, sessionId, moduleId, type, day );
	}
	
	private static String fetch(String urlstring) throws Exception{
		URL url = new URL(urlstring);
		
		HttpURLConnection connection = (HttpURLConnection)url.openConnection();
		connection.setRequestMethod("GET");
		connection.connect();

		int code = connection.getResponseCode();
		
		Log.i("--->","status code : " + code + " L : " + connection.getContentLength());
		
		if( code == HttpURLConnection.HTTP_OK ){
			
			byte[] buf = new byte[1024];
			InputStream is = null;
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			int count = 0;

			is = connection.getInputStream();
			while ((count = is.read(buf)) >= 0){
				os.write(buf, 0, count);
			}
			
			if (os.size() == 0){
				return null;
			 }
			
			return os.toString("GBK");
		}
		
		return String.valueOf(code);
	}
}
