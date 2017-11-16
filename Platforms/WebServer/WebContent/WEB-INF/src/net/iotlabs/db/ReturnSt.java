package net.iotlabs.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ReturnSt extends HashMap {
	
	public void putAll(Map map) {
		if( map == null ) {
			this.put("IS_EMPTY", true);
		} else {
			super.putAll(map);
		}
	}
	
	public boolean TEMP_VAR;
	
	public boolean session = true;
	public Object userInfo = null;
	public Object result = null;
	public ArrayList resultArray = null;
	public String msg = null;
}

