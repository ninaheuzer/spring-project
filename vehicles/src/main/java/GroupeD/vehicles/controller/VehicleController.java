package GroupeD.vehicles.controller;

import GroupeD.vehicles.model.Vehicle;
import GroupeD.vehicles.service.VehicleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/vehicles")
public class VehicleController {

    private VehicleService service;
    private static final String TOKEN = "Authorization";

    @GetMapping
    public List<Vehicle> getVehicles(@RequestParam(required = false) String category,
                                     @RequestParam(required = false) Double minPrice,
                                     @RequestParam(required = false) Double maxPrice) {
        if (category != null) {
            return (List<Vehicle>) service.findByCategory(category);
        }
        else if (minPrice != null & maxPrice != null){
            System.out.println(minPrice + " " + maxPrice);
            return (List<Vehicle>) service.findByPriceRange(minPrice, maxPrice);
        }
        else return (List<Vehicle>) service.findAll();
    }

    @GetMapping("/{id}")
    public Vehicle getVehicle(@PathVariable("id") int id) {
        return service.findById(id);
    }

    @PostMapping("/secure")
    public ResponseEntity<Void> getVehicle(@RequestBody Vehicle vehicle, @RequestHeader(name = TOKEN) String token) {
        Vehicle v = service.saveVehicle(vehicle);
        if (v == null){
            return ResponseEntity.noContent().build();
        }
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/id")
                .buildAndExpand(v.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public Vehicle getVehicleKit(@PathVariable("id") int id, @RequestBody Vehicle vehicle) {
        return service.updateVehicle(id, vehicle);
    }

    @DeleteMapping("secure/{id}")
    public void deleteVehicle(@PathVariable("id") int id, @RequestHeader(name = TOKEN) String token){
        service.deleteVehicle(id);
    }

    public VehicleController(VehicleService service) {
        this.service = service;
    }

}
