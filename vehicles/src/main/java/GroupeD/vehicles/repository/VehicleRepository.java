package GroupeD.vehicles.repository;

import GroupeD.vehicles.model.Vehicle;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRepository extends CrudRepository<Vehicle, Integer> {
    Iterable<Vehicle> findByCategory(String category);

    @Query("select v from vehicles v where v.price >= ?1 and v.price < ?2")
    Iterable<Vehicle> findByPriceRange(double minPrice, double maxPrice);


}
