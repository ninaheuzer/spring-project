package GroupeD.vehiclesFront.proxy;

import GroupeD.vehiclesFront.loadBalancer.LoadBalancerConfig;
import GroupeD.vehiclesFront.model.Cart;
import GroupeD.vehiclesFront.model.User;
import GroupeD.vehiclesFront.model.Vehicle;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import static GroupeD.vehiclesFront.service.ServiceConstants.TOKEN;

@Component
@FeignClient(name="gateway")
@LoadBalancerClient(name="gateway", configuration = LoadBalancerConfig.class)
public interface GatewayProxy {

    /******** Vehicles **********/
    // GET ALL
    @GetMapping("/vehicles/")
    List<Vehicle> findAll(@RequestParam(required=false) String category,
                          @RequestParam(required=false) Integer minPrice,
                          @RequestParam(required=false) Integer maxPrice);

    // GET BY ID
    @GetMapping("/vehicles/{id}")
    Vehicle findVehicleById(@PathVariable("id") int id);

    // CREATE A VEHICULE (POST)
    @PostMapping("/vehicles/secure")
    Vehicle createVehicle(@RequestBody Vehicle v, @RequestHeader(name = TOKEN) String token);

    // DELETE VEHICULE BASED ON ITS ID (DELETE)
    @DeleteMapping("/vehicles/secure/{id}")
    Vehicle deleteVehicle(@PathVariable("id") int id, @RequestHeader(name = TOKEN) String token);

    /******** Users **********/
    @GetMapping("/users/{id}")
    User findUserById(@PathVariable("id") int id);

}
