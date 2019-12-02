package com.book.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
 
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.book.beans.Product;
import com.book.utils.DBUtils;
import com.book.utils.MyUtils;

@WebServlet(urlPatterns = { "/createProduct" })
@MultipartConfig(maxFileSize = 16177215) //16Mb
public class CreateProductServlet extends HttpServlet {
	 private static final long serialVersionUID = 1L;
	 
	    public CreateProductServlet() {
	        super();
	    }
	 
	    // Hiển thị trang tạo sản phẩm.
	    @Override
	    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {
	 
	        RequestDispatcher dispatcher = request.getServletContext()
	                .getRequestDispatcher("/WEB-INF/views/createProductView.jsp");
	        dispatcher.forward(request, response);
	    }
	 
	    // Khi người dùng nhập các thông tin sản phẩm, và nhấn Submit.
	    // Phương thức này sẽ được gọi.
	    @Override
	    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {
	    	        
	        Connection conn = MyUtils.getStoredConnection(request);
	 
	        String code = (String) request.getParameter("code");
	        String name = (String) request.getParameter("name");
	        String priceStr = (String) request.getParameter("price");
	       
	        InputStream inputStream = null;

	        
	     // obtains the upload file part in this multipart request
	        Part filePart = request.getPart("image");
	        String fileName = extractFileName(filePart);

	        if (filePart != null && fileName != null && fileName.length() > 0) {
	            // prints out some information for debugging
	            System.out.println(filePart.getName());
	            System.out.println(filePart.getSize());
	            System.out.println(filePart.getContentType());
	             
	            // obtains input stream of the upload file
	            inputStream = filePart.getInputStream();
	        }
	        
	        int price = 0;
	   
			try {
	        	price = Integer.parseInt(priceStr);
	        } catch (Exception e) {
	        }
	        Product product = new Product(code, name, price);
	 
	        String errorString = null;
	 
	        // Mã sản phẩm phải là chuỗi chữ [a-zA-Z_0-9]
	        // Có ít nhất một ký tự.
	        String regex = "\\w+";
	 
	        if (code == null || !code.matches(regex)) {
	            errorString = "Product Code invalid!";
	        }
	 
	        if (errorString == null) {
	            try {
	                DBUtils.insertProduct(conn, product, inputStream, fileName);
	                        
	            } catch (SQLException e) {
	                e.printStackTrace();
	                errorString = e.getMessage();
	            }
	        }
	 
	        // Lưu thông tin vào request attribute trước khi forward sang views.
	        request.setAttribute("errorString", errorString);
	        request.setAttribute("product", product);
	 
	        // Nếu có lỗi forward (chuyển tiếp) sang trang 'edit'.
	        if (errorString != null) {
	            RequestDispatcher dispatcher = request.getServletContext()
	                    .getRequestDispatcher("/WEB-INF/views/createProductView.jsp");
	            dispatcher.forward(request, response);
	        }
	        // Nếu mọi thứ tốt đẹp.
	        // Redirect (chuyển hướng) sang trang danh sách sản phẩm.
	        else {
	            response.sendRedirect(request.getContextPath() + "/productList");
	        }
	    }
	    
	    private String extractFileName(Part part) {
	        // form-data; name="file"; filename="C:\file1.zip"
	        // form-data; name="file"; filename="C:\Note\file2.zip"
	        String contentDisp = part.getHeader("content-disposition");
	        String[] items = contentDisp.split(";");
	        for (String s : items) {
	            if (s.trim().startsWith("filename")) {
	                // C:\file1.zip
	                // C:\Note\file2.zip
	                String clientFileName = s.substring(s.indexOf("=") + 2, s.length() - 1);
	                clientFileName = clientFileName.replace("\\", "/");
	                int i = clientFileName.lastIndexOf('/');
	                // file1.zip
	                // file2.zip
	                return clientFileName.substring(i + 1);
	            }
	        }
	        return null;
	    }
}


