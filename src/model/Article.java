package model;

public class Article {
    private String name;
    private String description;
    private double price;




    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    private String brand;

    // Constructor
    public Article(String name, String description, double price, String brand) {

        this.name = name;
        this.description = description;
        this.price = price;
        this.brand = brand;
    }
}
