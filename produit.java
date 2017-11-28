package com.example.aziz.pidev.entity;

/**
 * Created by AZIZ on 25/11/2017.
 */

public class produit {
    private int id;

    private String name;

    private int quantity;
    private Double price;
    private String description;
    private String picture;
    private String Categorie;


    public produit() {
    }

    public produit(int id, String name, int quantity, Double price, String description, String categorie) {
         this.id=id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.description = description;

        Categorie = categorie;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getCategorie() {
        return Categorie;
    }

    public void setCategorie(String categorie) {
        Categorie = categorie;
    }
}
