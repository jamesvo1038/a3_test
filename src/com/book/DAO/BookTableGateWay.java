package com.book.DAO;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.book.beans.Product;

public class BookTableGateWay {
	// SQL Queries
	static String findUser = "Select a.User_Name, a.Password, a.Gender from USER_ACCOUNT a " //
			+ " where a.User_Name = ? and a.password= ?";

	static String findUser1 = "Select a.User_Name, a.Password, a.Gender from USER_ACCOUNT a "//
			+ " where a.User_Name = ? ";

	static String queryProduct = "Select a.Code, a.Name, a.ISBN from PRODUCT a ";

	static String findProduct = "Select a.Code, a.Name, a.ISBN, a.Image_File_Name from PRODUCT a where a.Code=?";

	static String updateProduct = "Update PRODUCT set Name =?, ISBN=? where Code=? ";

	static String insertProduct = "Insert into PRODUCT(Code, Name,ISBN,image, Image_File_Name) values (?,?,?,?,?)";

	static String deleteProduct = "Delete From PRODUCT where Code= ?";

	static String getImageInTable = "Select p.Code,p.Name,p.ISBN,p.Image,p.Image_File_Name "//
			+ " from PRODUCT p where p.code = ?";

	// Methods for all interaction with the database.
	public ResultSet findUsersByNamePass(Connection conn,String userName, String password) throws SQLException {
		PreparedStatement pstm = conn.prepareStatement(findUser);
		pstm.setString(1, userName);
		pstm.setString(2, password);
		ResultSet rs = pstm.executeQuery();

		return rs;

	}

	public ResultSet findUsersByName(Connection conn,String userName) throws SQLException {
		PreparedStatement pstm = conn.prepareStatement(findUser1);
		pstm.setString(1, userName);

		ResultSet rs = pstm.executeQuery();

		return rs;

	}

	public ResultSet queryProduct(Connection conn) throws SQLException {
		PreparedStatement pstm = conn.prepareStatement(queryProduct);

		ResultSet rs = pstm.executeQuery();
		return rs;
	}

	public ResultSet findProduct(Connection conn, String code) throws SQLException {
		PreparedStatement pstm = conn.prepareStatement(findProduct);
		pstm.setString(1, code);

		ResultSet rs = pstm.executeQuery();
		return rs;
	}

	public void updateProduct(Connection conn, Product product) throws SQLException{
		PreparedStatement pstm = conn.prepareStatement(updateProduct);

		pstm.setString(1, product.getName());
		pstm.setInt(2, product.getPrice());
		pstm.setString(3, product.getCode());
		pstm.executeUpdate();

	}

	public void insertProduct(Connection conn, Product product, InputStream is, String fileName) throws SQLException {
		PreparedStatement pstm = conn.prepareStatement(insertProduct);

		pstm.setString(1, product.getCode());
		pstm.setString(2, product.getName());
		pstm.setInt(3, product.getPrice());
		if(is != null) {
			// fetches input stream of the upload file for the blob column
			pstm.setBlob(4, is);
		}
		pstm.setString(5,fileName);

		pstm.executeUpdate();
	}

	public void deleteProduct(Connection conn, String code) throws SQLException {
		PreparedStatement pstm = conn.prepareStatement(deleteProduct);

		pstm.setString(1, code);

		pstm.executeUpdate();
	}

	public ResultSet getImageInTable(Connection conn, String code) throws SQLException {
		PreparedStatement pstm = conn.prepareStatement(getImageInTable);
		pstm.setString(1, code);
		ResultSet rs = pstm.executeQuery();

		return rs;
	}

}
