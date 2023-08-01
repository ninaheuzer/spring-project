package GroupeD.vehicles.service;

import GroupeD.vehicles.model.Vehicle;
import GroupeD.vehicles.repository.VehicleRepository;
import org.springframework.stereotype.Service;

@Service
public class VehicleService {

    private final VehicleRepository repo;

    public VehicleService(VehicleRepository repo) {
        this.repo = repo;
    }

    public Vehicle findById(int id) {
        return repo.findById(id).orElseThrow(InternalError::new);
    }

    public Iterable<Vehicle> findAll() {
        return repo.findAll();
    }

    public Iterable<Vehicle> findByCategory(String category) {
        return repo.findByCategory(category);
    }

    public Iterable<Vehicle> findByPriceRange(double minPrice, double maxPrice) {
        return repo.findByPriceRange(minPrice, maxPrice);
    }

    public Vehicle saveVehicle(Vehicle vehicle) {
        return repo.save(vehicle);
    }

    public Vehicle updateVehicle(int id, Vehicle vehicle) {
        Vehicle v = repo.findById(id).orElseThrow(InternalError::new);
        v.setName(vehicle.getName());
        v.setBrief_description(vehicle.getBrief_description());
        v.setDetailed_description(vehicle.getDetailed_description());
        v.setPrice(vehicle.getPrice());
        v.setCategory(vehicle.getCategory());

        return repo.save(v);
    }

    public void deleteVehicle(int id) {
        Vehicle v = repo.findById(id).orElseThrow(InternalError::new);
        repo.delete(v);
    }
}
