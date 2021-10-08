package com.crud.app.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;
 

@Entity(name = "product")

public class Item {

	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	private int id;

	@Column(name = "name")
	private String nome;

	 
	@Column(name = "description")
	private String description;
 
	@Column(name = "price")
	private double price;
	@Column(name = "category")
	private String category;
	@Column(name = "quantity")
	private int quantity;
	@Column(name = "rating")
	private int rating;

	public Item(String nome ) {
		this();
		this.nome = nome;
 	}

	public Item() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	 

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	 

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	 
	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

}
