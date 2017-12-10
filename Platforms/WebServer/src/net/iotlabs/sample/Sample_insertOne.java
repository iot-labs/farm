package net.iotlabs.sample;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.iotlabs.db.CommonDao;
import net.iotlabs.db.ReturnSt;
import net.iotlabs.util.ServletCommon;
import net.iotlabs.util.ServletUtil;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@WebServlet("/iotlabs/sample/Sample_insertOne")
public class Sample_insertOne extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	// create GSON object
	private Gson gson = new GsonBuilder().setPrettyPrinting().create();			
       
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// set encoding type to UTF-8
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=utf-8");
		// request to map
		HashMap<String, String> p = ServletUtil.paramToHashMap(request);	// parameter to map
		// Return Structure
		ReturnSt returnSt = new ReturnSt();	// reutrn structure
		// check session
		HttpSession session = request.getSession();
		if( session.getAttribute("uid") == null ) {
			ServletCommon.sessionExpired(response);
		}
		// set default data from session
		p.put("uid", (String)session.getAttribute("uid"));
		
		// DB Connection
		CommonDao dao = new CommonDao();
		
		// Insert Task (result 에는 insert 된 건수가 입력된다.)
		int result = dao.commonInsert("SampleMapper." + this.getClass().getSimpleName(), p);
		
		// msg 가 있다면 추가 한다.
		HashMap<String, String> returnMap = new HashMap();
		returnMap.put("insertCount", result + "");
		returnMap.put("msg", "OK_XXXXXXXXXX");
		
		returnSt.putAll(returnMap);
		response.getWriter().write(gson.toJson(returnSt));
	}
}
