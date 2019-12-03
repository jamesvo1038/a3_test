package com.book.controller;

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

	// Show product creation page.
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		RequestDispatcher dispatcher = request.getServletContext()
				.getRequestDispatcher("/WEB-INF/views/createProductView.jsp");
		dispatcher.forward(request, response);
	}

	// When the user enters the product information, and click Submit.
	// This method will be called.
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

		// Product ID is the string literal [a-zA-Z_0-9]
        // with at least 1 character
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

		 // Store infomation to request attribute, before forward to views.
		request.setAttribute("errorString", errorString);
		request.setAttribute("product", product);

		 // If error, forward to Edit page.
		if (errorString != null) {
			RequestDispatcher dispatcher = request.getServletContext()
					.getRequestDispatcher("/WEB-INF/views/createProductView.jsp");
			dispatcher.forward(request, response);
		}
		 // If everything nice.
        // Redirect to the product listing page.
		else {
			response.sendRedirect(request.getContextPath() + "/productList");
		}
	}

	private String extractFileName(Part part) {
		// form-data; name="file"; filename="C:\file1.jpg"
		// form-data; name="file"; filename="C:\Note\file2.jpg"
		String contentDisp = part.getHeader("content-disposition");
		String[] items = contentDisp.split(";");
		for (String s : items) {
			if (s.trim().startsWith("filename")) {
				// C:\file1.jpg
				// C:\Note\file2.jpg
				String clientFileName = s.substring(s.indexOf("=") + 2, s.length() - 1);
				clientFileName = clientFileName.replace("\\", "/");
				int i = clientFileName.lastIndexOf('/');
				// file1.jpg
				// file2.jpg
				return clientFileName.substring(i + 1);
			}
		}
		return null;
	}
}


