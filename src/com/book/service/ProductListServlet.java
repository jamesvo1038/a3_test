package com.book.service;

import java.io.IOException;
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

@WebServlet(urlPatterns = { "/productList" })
public class ProductListServlet extends HttpServlet {

	  private static final long serialVersionUID = 1L;
	  
	    public ProductListServlet() {
	        super();
	    }
	 
	    @Override
	    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {
	    	
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
	 
	        String errorString = null;
	        List<Product> list = null;
	        try {
	            list = DBUtils.queryProduct(conn);
	        } catch (SQLException e) {
	            e.printStackTrace();
	            errorString = e.getMessage();
	        }
	        // Store info in request attribute, before forward to views
	        request.setAttribute("errorString", errorString);
	        request.setAttribute("productList", list);
	        
	        
	         
	        // Forward to /WEB-INF/views/productListView.jsp
	        RequestDispatcher dispatcher = request.getServletContext()
	                .getRequestDispatcher("/WEB-INF/views/productListView.jsp");
	        dispatcher.forward(request, response);
	    }
	 
	    @Override
	    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {
	        doGet(request, response);
	    }
}
