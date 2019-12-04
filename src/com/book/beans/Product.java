package com.book.beans;

public class Product {
	private String code;
	private String name;
	private int isbn;
	private byte[] image; 
	private String imageFileName;
	//private String base64Image;

	public Product() {

	}

	public Product(String code, String name, int price) {
		this.code = code;
		this.name = name;
		this.isbn = price;
	}

	public Product(String code, String name, int price, String imageFileName, byte[] image) {
		this.code = code;
		this.name = name;
		this.isbn = price;
		this.imageFileName = imageFileName;
		this.image = image;
	}
	public Product(String code, String name, int price, String imageFileName) {
		this.code = code;
		this.name = name;
		this.isbn = price;
		this.imageFileName = imageFileName;
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
		return isbn;
	}

	public void setPrice(int price) {
		this.isbn = price;
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
