package com.book.service;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.book.beans.Product;
import com.book.beans.UserAccount;
import com.book.utils.DBUtils;
import com.book.utils.MyUtils;
import com.google.gson.Gson;

@WebServlet(urlPatterns = { "/GetMessages1" })
public class JSONServlet2 extends HttpServlet {

	private static final long serialVersionUID = 1L;
	public JSONServlet2() {
		super();
	}
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		handleRequest(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		handleRequest(request, response);
	}

	public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {

		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();

		// Check User has logged on
		UserAccount loginedUser = MyUtils.getLoginedUser(session);

		// Not logged in
		if (loginedUser == null) {
			// Redirect to login page.
			response.sendRedirect(request.getContextPath() + "/login");
			return;
		}
		// Store info to the request attribute before forwarding.
		request.setAttribute("user", loginedUser);

		Connection conn = MyUtils.getStoredConnection(request);

		Product product = null;

		String code = (String) request.getParameter("code");
		String paramValue = request.getParameter(code);
		String imageFileName = (String) request.getParameter("imageFileName");
		String contentType = this.getServletContext().getMimeType(imageFileName);


		try {
			String check1 = null;
			product = DBUtils.findProduct(conn, code);
			if(imageFileName != null) {
				 check1 = "has image:" + "yes";
			}
			Gson gson = new Gson();
			String info = gson.toJson(product);
			
			out.println("{\"JSON Display\":"+info+"yes"+"}" +"\n");




		} catch (SQLException e) {
			e.printStackTrace();
		} 

		out.close();

	}

}