package GroupeD.cartFront.proxy;

import GroupeD.cartFront.loadBalancer.LoadBalancerConfig;
import GroupeD.cartFront.model.Cart;
import GroupeD.cartFront.model.User;
import GroupeD.cartFront.model.Vehicle;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Component
@FeignClient(name="gateway")
@LoadBalancerClient(name="gateway", configuration = LoadBalancerConfig.class)
public interface GatewayProxy {

    String TOKEN = "Authorization";
    /***********cart**********/

    @GetMapping("/cart/{idUser}")
    List<Cart> findCartByIdUser(@RequestHeader(name = TOKEN) String token, @PathVariable("idUser") int idUser);

    @PostMapping("/cart")
    void saveCart(@RequestHeader(name = TOKEN) String token, @RequestBody Cart cart);

    @PutMapping("/cart/{id}")
    void updateCart(@RequestHeader(name = TOKEN) String token, @PathVariable("id") int id, @RequestBody Cart cart);

    @DeleteMapping("/cart/{id}")
    void deleteCart(@RequestHeader(name = TOKEN) String token, @PathVariable("id") int id);

    @DeleteMapping("/cart/deleteAll/{idUser}")
    void deleteAllCart(@RequestHeader(name = TOKEN) String token, @PathVariable("idUser") int id);

    /**************users*********/

    @GetMapping("/users/{id}")
    User findUserById(@PathVariable("id") int id);

    /************vehicles************/

    @GetMapping("/vehicles/{id}")
    Vehicle findVehicleById(@PathVariable("id") int id);
}
