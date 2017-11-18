package net.iotlabs.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class EncodeDecode {
	public static void main(String args []){
		
		String [] types = {"UTF-8","EUC-KR","ISO-8859-1"};
		String testValue = "한글";
		System.out.println("기본 글자 : " + testValue);
		String encode_result = null;
		
		System.out.println("------ TEST ------");
		try {
			for(String type : types){
			encode_result = URLEncoder.encode(testValue, type);
			System.out.println("encode with " + type +" : "+ URLEncoder.encode(testValue, type));
				for(String type2 : types){
					System.out.println("decode with " + type2 +" : "+ URLDecoder.decode(encode_result, type2));		
				}
				System.out.println("--------------------");		
			}	
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		System.out.println("------------------ Simple Method ------------------");
		String utf8Text = "%ED%95%9C%EA%B8%80";
		System.out.println("Simple Method : " + decodeUtf8(utf8Text));
	}
	
	public static String decodeUtf8( String text ) {
		String returnValue = null;
		try {
			returnValue = URLDecoder.decode(text, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return returnValue;
	}
}
