package grouped.vehiclesdetails.model;

import lombok.Data;

@Data
public class Vehicle {
    private int id;
    private String name;
    private String brief_description;
    private String detailed_description;
    private double price;
    private String category; //à modifier avec un enum ?
}
