/*
This class is here to transfer the minPrice, maxPrice, category fields for the filter
 */
package GroupeD.vehiclesFront.model;
import lombok.Data;

@Data
public class VehicleDTO extends Vehicle {

    private Double minPrice;
    private Double maxPrice;
    private String category;
    private String tri;
}
