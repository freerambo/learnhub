
package org.rambo.spring.boot.weixin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
public class DemoServlet extends HttpServlet{
 
	/**
	 * 
	 */
	private static final long serialVersionUID = 2600143224322327782L;
 
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
			System.out.println("==>In DemoServlet ");
		resp.getWriter().write("DemoServlet access success");
		
	}
 
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.doGet(req, resp);
	}
	
}