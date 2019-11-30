package com.book.service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.book.beans.UserAccount;
import com.book.utils.DBUtils;
import com.book.utils.MyUtils;

@WebServlet(urlPatterns = { "/logout" })
public class LogoutServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	public LogoutServlet() {
		super();
	}
	
	// Hiển thị trang Login.
		@Override
		protected void doGet(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
			
			HttpSession session = request.getSession();
	    	 
	        // Kiểm tra người dùng đã đăng nhập (login) chưa.
	        UserAccount loginedUser = MyUtils.getLoginedUser(session);
	 
	        // Nếu chưa đăng nhập (login).
	        if (loginedUser == null) {
	            // Redirect (Chuyển hướng) tới trang login.
	            response.sendRedirect(request.getContextPath() + "/login");
	            return;
	        }
	        // Lưu thông tin vào request attribute trước khi forward (chuyển tiếp).
	        request.setAttribute("user", loginedUser);
	 
	        
			// Forward tới trang /WEB-INF/views/loginView.jsp
			// (Người dùng không thể truy cập trực tiếp
			// vào các trang JSP đặt trong thư mục WEB-INF).
			RequestDispatcher dispatcher //
			= this.getServletContext().getRequestDispatcher("/WEB-INF/views/logoutView.jsp");

			dispatcher.forward(request, response);
			
			session.invalidate();

		}

		
		// Khi người nhập userName & password, và nhấn Submit.
		// Phương thức này sẽ được thực thi.
		@Override
		protected void doPost(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
			doGet(request, response);
		}
}
