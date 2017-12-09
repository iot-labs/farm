package net.iotlabs.sample;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.iotlabs.db.CommonDao;
import net.iotlabs.db.ReturnSt;
import net.iotlabs.util.ServletUtil;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * 여러건 조회 하는 경우
 */
@WebServlet("/servlet/sample/Sample_selectOne")
public class Sample_selectOne extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	// create GSON object
	private Gson gson = new GsonBuilder().setPrettyPrinting().create();	
       
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// set encoding type to UTF-8
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=utf-8");
		// Parameter
		HashMap<String, String> p = ServletUtil.paramToHashMapUtf8(request);
		// DB
		CommonDao dao = new CommonDao();
		HashMap<String, String> result = dao.commonSelectOne("SampleMapper." + this.getClass().getSimpleName(), p);
		System.out.println( result );

		// Create Session
		HttpSession session = null;
		if( result != null ) {
			session = request.getSession();
			session.setMaxInactiveInterval(12 * 60 * 60); // unit is seconds
			session.setAttribute("email", result.get("email") );
			
			request.setAttribute("result", result);
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("/Sample_Welcome.jsp");
		    dispatcher.forward(request, response);
		} else {
			RequestDispatcher dispatcher = request.getRequestDispatcher("/Sample_Login.jsp");
		    dispatcher.forward(request, response);	
		}
	}
}
