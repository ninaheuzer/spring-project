package grouped.vehiclesdetails.proxy;

import grouped.vehiclesdetails.loadBalancer.LoadBalancerConfig;
import grouped.vehiclesdetails.model.Cart;
import grouped.vehiclesdetails.model.Comment;
import grouped.vehiclesdetails.model.User;
import grouped.vehiclesdetails.model.Vehicle;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import static grouped.vehiclesdetails.service.ServiceConstants.TOKEN;

@Component
@FeignClient(name = "gateway")
@LoadBalancerClient(name="gateway", configuration = LoadBalancerConfig.class)
public interface GatewayProxy {
    /******** Vehicles **********/
    @GetMapping("/vehicles/{id}")
    Vehicle findVehicleById(@PathVariable("id") int id);

    @DeleteMapping("/vehicles/secure/{id}")
    Vehicle deleteVehicle(@PathVariable("id") int id, @RequestHeader(name=TOKEN) String token);

    @PutMapping("/vehicles/secure/{id}")
    void updateVehicle(@PathVariable("id") int id, Vehicle vehicle, @RequestHeader(name=TOKEN) String token);

    /******** Users **********/
    @GetMapping("/users/{id}")
    User findUserById(@PathVariable("id") int id);

    /******** Comments **********/
    @GetMapping("/comments/")
    Iterable<Comment> findAll();

    @GetMapping("/comments/{id}")
    Comment findCommentById(@PathVariable("id") int id);

    @GetMapping("/comments/vehicle/{id}")
    Iterable<Comment> findAllCommentsByVehicle(@PathVariable("id") int vehicleId);

    @GetMapping("/comments/vehicle/{id}/average")
    double findAverageForVehicle(@PathVariable("id") int id);

    @PutMapping("/comments/secure/{id}")
    Comment deleteComment(@PathVariable("id") int id, Comment comment, @RequestHeader(name=TOKEN) String token);

    @PutMapping("/comments/secure/{id}")
    Comment updateComment(@PathVariable("id") int id, Comment comment, @RequestHeader(name=TOKEN) String token);

    @PostMapping("/comments/secure")
    Comment createComment(Comment comment, @RequestHeader(name=TOKEN) String token);

    /******** Cart **********/
    @GetMapping("/cart/{id}")
    Iterable<Cart> getCart(@PathVariable("id") int id, @RequestHeader(name=TOKEN) String token);

    @PostMapping("/cart/{id}")
    void createCart(@PathVariable("id") int id, Cart cart, @RequestHeader(name=TOKEN) String token);
}
