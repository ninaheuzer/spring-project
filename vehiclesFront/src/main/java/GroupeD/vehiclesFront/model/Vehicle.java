package GroupeD.vehiclesFront.model;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Vehicle {
    private int id;
    private String name;
    private String brief_description;
    private String detailed_description;
    private double price;
    private String category;

    private static int COUNT = 3;

    public Vehicle(String name, String brief_description, String detailed_description, double price, String category) {
        this.id = COUNT++;
        this.name = name;
        this.brief_description = brief_description;
        this.detailed_description = detailed_description;
        this.price = price;
        this.category= category;
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

    public String getBrief_description() {
        return brief_description;
    }

    public void setBrief_description(String brief_description) {
        this.brief_description = brief_description;
    }

    public String getDetailed_description() {
        return detailed_description;
    }

    public void setDetailed_description(String detailed_description) {
        this.detailed_description = detailed_description;
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

    public String toString() {
        return "id: " + this.id + " name: " + this.name + " brief description: " + this.brief_description +
                " detailed description: " + this.detailed_description + " price: " + this.price + " category: " +
                this.category;
    }
}
