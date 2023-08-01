package GroupeD.cartFront.model;

import lombok.Data;

@Data
public class CartDTO extends Cart{
    private String productName;
    private double productPrice;
    private String address;


}
