package grouped.usersfront.proxy;

import grouped.usersfront.loadBalancer.LoadBalancerConfig;
import grouped.usersfront.model.User;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static grouped.usersfront.service.ServiceConstants.TOKEN;


@Component
@FeignClient(name = "gateway")
@LoadBalancerClient(name = "gateway", configuration = LoadBalancerConfig.class)
@RequestMapping("/users")
public interface UsersFrontProxy {

    @GetMapping("/secure")
    List<User> findAll(@RequestHeader(name=TOKEN) String token);

    @GetMapping("/{id}")
    User findById(@PathVariable("id") int id);

    @GetMapping("/email/{email}")
    User findByEmail(@PathVariable("email") String email);

    @PostMapping
    User createUser(@RequestBody User user);

    @PutMapping("/secure/{id}")
    User updateUser(@RequestHeader(name=TOKEN) String token, @PathVariable("id") int id, @RequestBody User user);

    @DeleteMapping("/secure/{id}")
    User deleteUser(@RequestHeader(name=TOKEN) String token, @PathVariable("id") int id);
}
