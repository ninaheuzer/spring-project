package grouped.users.controller;

import grouped.users.model.User;
import grouped.users.service.UserService;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/users")
public class UserController {

    private UserService service;

    public UserController(UserService service){
        this.service = service;
    }

    @GetMapping("/secure")
    public Iterable<User> findAll(@RequestHeader(name="Authorization") String token){
        return service.findAll();
    }

    @GetMapping("/{id}")
    public User findById(@PathVariable("id") int id){
        return service.findById(id);
    }

    @GetMapping("/email/{email}")
    public User findByEmail(@PathVariable("email") String email){
        return service.findByEmail(email);
    }

    @PostMapping
    public User createUser(@RequestBody User user){
        return service.saveUser(user);
    }

    @PutMapping("/secure/{id}")
    public User updateUser(@RequestHeader(name="Authorization") String token, @PathVariable("id") int id, @RequestBody User user){
        return service.updateUser(user);
    }

    @DeleteMapping("/secure/{id}")
    public void deleteUser(@RequestHeader(name="Authorization") String token, @PathVariable("id") int id){
        service.deleteUser(id);
    }
}