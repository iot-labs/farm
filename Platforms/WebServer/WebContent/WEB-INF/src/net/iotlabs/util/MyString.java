package net.iotlabs.util;

public class MyString {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	public static String nvl(String first, String second) {
		if(first == null) {
			return second;
		}
		return first;
	}

}
