package net.iotlabs.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class ServletUtil {
	
	/**
	 * 사용하지 않음!!
	 */
	public static String getPayload(HttpServletRequest request) throws IOException {

	    String body = null;
	    StringBuilder stringBuilder = new StringBuilder();
	    BufferedReader bufferedReader = null;

	    try {
	        InputStream inputStream = request.getInputStream();
	        if (inputStream != null) {
	            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
	            char[] charBuffer = new char[128];
	            int bytesRead = -1;
	            while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
	                stringBuilder.append(charBuffer, 0, bytesRead);
	            }
	        } else {
	            stringBuilder.append("");
	        }
	    } catch (IOException ex) {
	        throw ex;
	    } finally {
	        if (bufferedReader != null) {
	            try {
	                bufferedReader.close();
	            } catch (IOException ex) {
	                throw ex;
	            }
	        }
	    }

	    body = stringBuilder.toString();
	    return body;
	}
	
	/**
	 * 
	 * HttpServletRequest 객체를 받아 getParameterNames 깂을 HashMap 타입으로 리턴
	 *
	 * @param request
	 * @return
	 */
	public static HashMap<String, String> paramToHashMap(HttpServletRequest request)
	{
	    HashMap<String, String> param = new HashMap<String, String>();
	    
	    Enumeration penum = request.getParameterNames();
	    
	    String key	= null;
	    String value= null;
	    
	    while(penum.hasMoreElements())
	    {
	        key = (String)penum.nextElement();
	        
	        value = (new String(request.getParameter(key)) == null) ? "" : new String(request.getParameter(key).trim());
	        param.put(key, value);
	    }
	    
	    return param;
	}
	
	public static HashMap<String, String> paramToHashMapUtf8(HttpServletRequest request)
	{
	    HashMap<String, String> param = new HashMap<String, String>();
	    
	    Enumeration penum = request.getParameterNames();
	    
	    String key	= null;
	    String value= null;
	    
	    while(penum.hasMoreElements())
	    {
	        key = (String)penum.nextElement();
	        value = (new String(request.getParameter(key)) == null) ? "" : new String(request.getParameter(key).trim());
	        try {
	        	value = URLDecoder.decode(value, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				value = "## 오류:UTF8 Encoding 이 아닙니다.";
			}
	        param.put(key, value);
	    }
	    
	    return param;
	}
	
	/**
	 * 
	 * HttpServletRequest 객체를 받아 getParameterNames 깂을 HashMap 타입으로 리턴
	 *
	 * @param request
	 * @return
	 * @throws ServletException 
	 * @throws JSONException 
	 */
	public static HashMap<String, String> paramJsonToHashMap(HttpServletRequest request, String jsonKey) throws ServletException 
	{
	    HashMap<String, String> param = new HashMap<String, String>();
	    
	    
	    String jsonString = request.getParameter(jsonKey);
	    if( jsonString == null || jsonString.equals("null") ) {
	    	jsonString = "{}";
	    }
	    
	    // this parses the json
	    JSONObject jObj = null;
		try {
			jObj = new JSONObject(jsonString);
		} catch (JSONException e) {
			e.printStackTrace();
			throw new ServletException();
		} 
	    Iterator it = jObj.keys(); //gets all the keys

	    String key = null;
	    String value = null;
	    while(it.hasNext())
	    {
	        key = it.next().toString(); // get key
			try {
				value = jObj.get(key).toString();
			} catch (JSONException e) {
				e.printStackTrace();
				throw new ServletException();
			} // get value
	        param.put(key, value);
	    }
	    return param;
	}
	
	/**
	 * 
	 * HttpServletRequest 객체를 받아 getParameterNames 깂을 HashMap 타입으로 리턴
	 * (Key 가 오직 "dataList"인 것만 처리하면, dataList 의 Value 는  Array 타입이다.)
	 *
	 * @param request
	 * @return
	 * @throws JSONException 
	 */
	public static void paramToHashMapArray(HttpServletRequest request) throws JSONException
	{
		JSONObject json = new JSONObject(request.getParameter("dataList")); // this parses the json
	}
}
