package com.book.controller;

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

@WebServlet(urlPatterns = { "/GetMessages" })
public class JSONServlet1 extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	public JSONServlet1() {
		super();
		}
	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		PrintWriter out = response.getWriter();
    	HttpSession session = request.getSession();
    	 
        // Kiểm tra ngư�?i dùng đã đăng nhập (login) chưa.
        UserAccount loginedUser = MyUtils.getLoginedUser(session);
 
        // Nếu chưa đăng nhập (login).
        if (loginedUser == null) {
            // Redirect (Chuyển hướng) tới trang login.
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        // Lưu thông tin vào request attribute trước khi forward (chuyển tiếp).
        request.setAttribute("user", loginedUser);
 
    	
        Connection conn = MyUtils.getStoredConnection(request);
 
        String errorString = null;
        List<Product> list = null;
        try {
            list = DBUtils.queryProduct(conn);
            Gson gson = new Gson();
            String info = gson.toJson(list);
            out.println("{\"JSON Display\":"+info+"}" +"\n");
        } catch (SQLException e) {
            e.printStackTrace();
            errorString = e.getMessage();
            out.println("Error: " + e.getMessage());
        }
        // Lưu thông tin vào request attribute trước khi forward sang views.
        request.setAttribute("errorString", errorString);
        request.setAttribute("productList", list);
        
        
         
        // Forward sang /WEB-INF/views/productListView.jsp
//        RequestDispatcher dispatcher = request.getServletContext()
//                .getRequestDispatcher("/WEB-INF/views/indexService.jsp");
//        dispatcher.forward(request, response);
    }
 
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

}
