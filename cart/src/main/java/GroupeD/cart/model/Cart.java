package GroupeD.cart.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name="carts")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; //pk
    private int idUser;
    private int idProduct;
    private int quantity;

}
