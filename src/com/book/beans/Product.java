package com.book.beans;

import java.beans.Transient;
import java.util.Base64;

public class Product {
	private String code;
	private String name;
	private int price;
	private byte[] image; 
	private String imageFileName;
	//private String base64Image;

	public Product() {

	}

	public Product(String code, String name, int price) {
		this.code = code;
		this.name = name;
		this.price = price;
	}

	public Product(String code, String name, int price, String imageFileName, byte[] image) {
		this.code = code;
		this.name = name;
		this.price = price;
		this.imageFileName = imageFileName;
		this.image = image;
	}
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}


	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public String getImageFileName() {
		return imageFileName;
	}

	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
	}

//	@Transient
//	public String getBase64Image() {
//		this.base64Image = Base64.getEncoder().encodeToString(this.image);
//		return this.base64Image;
//	}
//	
//	@Transient
//	public void setBase64Image(String base64Image) {
//		this.base64Image = base64Image;
//	}

}
