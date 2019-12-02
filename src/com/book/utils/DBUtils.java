package com.book.utils;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.book.DAO.BookTableGateWay;
import com.book.beans.Product;
import com.book.beans.UserAccount;


public class DBUtils {


	public static UserAccount findUser(Connection conn, String userName, String password) throws SQLException {

		BookTableGateWay gw = new BookTableGateWay();
		ResultSet rs = gw.findUsersByNamePass(conn, userName, password);

		if (rs.next()) {
			String gender = rs.getString("Gender");
			UserAccount user = new UserAccount();
			user.setUserName(userName);
			user.setPassword(password);
			user.setGender(gender);
			return user;
		}
		return null;
	}

	public static UserAccount findUser(Connection conn, String userName) throws SQLException {

		BookTableGateWay gw = new BookTableGateWay();
		ResultSet rs = gw.findUsersByName(conn, userName);

		if (rs.next()) {
			String password = rs.getString("Password");
			String gender = rs.getString("Gender");
			UserAccount user = new UserAccount();
			user.setUserName(userName);
			user.setPassword(password);
			user.setGender(gender);
			return user;
		}
		return null;
	}

	public static List<Product> queryProduct(Connection conn) throws SQLException {

		BookTableGateWay gw = new BookTableGateWay();
		ResultSet rs = gw.queryProduct(conn);

		List<Product> list = new ArrayList<Product>();
		while (rs.next()) {
			String code = rs.getString("Code");
			String name = rs.getString("Name");
			int price = rs.getInt("Price");
			Product product = new Product();
			product.setCode(code);
			product.setName(name);
			product.setPrice(price);
			list.add(product);
		}
		return list;
	}

	public static Product findProduct(Connection conn, String code) throws SQLException {

		BookTableGateWay gw = new BookTableGateWay();
		ResultSet rs = gw.findProduct(conn, code);

		while (rs.next()) {
			String name = rs.getString("Name");
			int price = rs.getInt("Price");
			Product product = new Product(code, name, price);
			return product;
		}
		return null;
	}

	public static void updateProduct(Connection conn, Product product) throws SQLException {

		BookTableGateWay gw = new BookTableGateWay();
		gw.updateProduct(conn, product);
	}

	public static void insertProduct(Connection conn, Product product, InputStream is, String fileName) throws SQLException {
		BookTableGateWay gw = new BookTableGateWay();
		gw.insertProduct(conn,  product, is, fileName);

	}

	public static void deleteProduct(Connection conn, String code) throws SQLException {
		BookTableGateWay gw = new BookTableGateWay();
		gw.deleteProduct(conn, code);
	}

	public static Product getImageInTable(Connection conn, String code) throws SQLException{
		BookTableGateWay gw = new BookTableGateWay();
		ResultSet rs = gw.getImageInTable(conn, code);

		if (rs.next()) {
			String name = rs.getString("Name");
			int price = rs.getInt("Price");
			byte[] imageData = rs.getBytes("Image");
			String imageFileName = rs.getString("Image_File_Name");
			return new Product(code, name,price, imageFileName, imageData);
		}
		return null;

	}




}
