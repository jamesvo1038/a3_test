package com.book.service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.book.beans.Product;
import com.book.utils.DBUtils;
import com.book.utils.MyUtils;

@WebServlet(urlPatterns= {"/image"})
public class DisplayImageServlet extends HttpServlet {


	private static final long serialVersionUID = 1L;
	public DisplayImageServlet() {
		super();
	}
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		 Connection conn = MyUtils.getStoredConnection(request);
		 
	        String code = (String) request.getParameter("code");
	 
	        Product product = null;
	 
	        String errorString = null;
	 
	       
	            try {
					product = DBUtils.getImageInTable(conn, code);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            
	            request.setAttribute("errorString", errorString);
	            // When no image found, should return error
	            if(product == null) {
	            	response.sendRedirect(request.getContextPath()+ "/images/noimg.jpg");
	            	return;
	            }
	            	String imageFileName = product.getImageFileName();
	                System.out.println("File Name: "+ imageFileName);
	              
	                // image/jpg
	                // image/png
	                String contentType = this.getServletContext().getMimeType(imageFileName);
	                System.out.println("Content Type: "+ contentType);
	              
	                response.setHeader("Content-Type", contentType);
	              
	                response.setHeader("Content-Length", String.valueOf(product.getImage().length));
	              
	                response.setHeader("Content-Disposition", "inline; filename=\"" + product.getImageFileName() + "\"");
	       
	                // Ghi dữ liệu ảnh vào Response.
	                response.getOutputStream().write(product.getImage());
	       
	            
	        
	}
}