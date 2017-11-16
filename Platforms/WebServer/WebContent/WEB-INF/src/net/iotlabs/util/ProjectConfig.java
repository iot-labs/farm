package net.iotlabs.util;

public class ProjectConfig {
	public static String SERVER_MODE = "LOCAL"; // LOCAL , TEST , PRODUCTION
	
	public static String WEBROOT_LOCAL = "http://localhost:8080/";
	public static String WEBROOT_PRODUCTION = "https://www.iotlabs.net/";
	public static String WEBROOT = SERVER_MODE.equals("LOCAL") ? WEBROOT_LOCAL : WEBROOT_PRODUCTION;

}
