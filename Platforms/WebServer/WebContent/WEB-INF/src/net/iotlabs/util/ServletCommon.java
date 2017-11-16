package net.iotlabs.util;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

public class ServletCommon {
	
	private static final String sessionExpiredHeader = "세션만료";
	private static final String sessionExpiredMsg = "세션정보가 만료 되었습니다<br>다시 로그인 해주시기 바랍니다";
	private static final String sessionExpiredEval = "(function() {location = 'login.html'})";

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public static void setJsonHeader(HttpServletResponse response) {
		// json
		response.setContentType("application/json");

		// Cors
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods",
				"POST, GET, PUT, DELETE, OPTIONS");
		response.addHeader("Access-Control-Allow-Credentials", "true");
		response.addHeader(
				"Access-Control-Allow-Headers",
				"Access-Control-Allow-Headers:origin, X-Requested-With, Authorization, Content-Type");
	}
	
	/**
	 * 세션이 만료된 경우
	 * @param response
	 * @throws IOException
	 * @throws ServletException 
	 */
	public static void sessionExpired(HttpServletResponse response) throws IOException, ServletException {
		handleRuntimeException( response, sessionExpiredHeader, sessionExpiredMsg, sessionExpiredEval, null );
	}

	/**
	 * 알 수 없는 오류 발생 
	 * @param response
	 * @param stackTraceElement
	 * @throws IOException
	 * @throws ServletException 
	 */
	public static void handleRuntimeException(HttpServletResponse response, StackTraceElement stackTraceElement) throws IOException, ServletException {
		String packageClassName = stackTraceElement.getClassName();
		String splitData [] = packageClassName.split("\\.");
		String className = splitData [(splitData.length)-1];
		
		handleRuntimeException( response, "오류", "처리도중 오류가 발생하였습니다<br><span style='font-size:12px'>Code : "+className+"("+stackTraceElement.getLineNumber()+")</span>", null, null );
	}

	public static void handleRuntimeException(HttpServletResponse response, String header, String msg, StackTraceElement stackTraceElement) throws IOException, ServletException {
		String packageClassName = stackTraceElement.getClassName();
		String splitData [] = packageClassName.split("\\.");
		String className = splitData [(splitData.length)-1];
		
		if( stackTraceElement != null ) {
			msg += "<br><span style='font-size:12px'>Code : "+className+"("+stackTraceElement.getLineNumber()+")</span>";
		}
		handleRuntimeException( response, header, msg, null, null );
	}

	public static void handleRuntimeException(HttpServletResponse response, String header, String msg) throws IOException, ServletException {
		handleRuntimeException( response, header, msg, null, null );
	}

	public static void handleRuntimeException(HttpServletResponse response, String header, String msg, String eval, Exception ex) throws IOException, ServletException {
		
		if( ex != null ) {
			ex.printStackTrace();
		}
		
        response.setCharacterEncoding("utf8");
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);	// HTTP status : 500
        
        JSONObject obj = new JSONObject();
        obj.put("errorHeader", header);
        obj.put("errorMsg", msg);
        obj.put("success", false);
        
        if( eval != null )
        	obj.put("errorEval", eval);	
        
        response.getWriter().print(obj);
		response.flushBuffer();
		
		throw new ServletException();
	}

}
