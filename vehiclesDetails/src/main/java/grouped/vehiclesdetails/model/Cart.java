package grouped.vehiclesdetails.model;

import lombok.Data;

@Data
public class Cart {
    private int id; //pk
    private int idUser;
    private int idProduct;
    private int quantity;
}