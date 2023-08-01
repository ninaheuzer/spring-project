package GroupeD.cart.controller;

import GroupeD.cart.model.Cart;
import GroupeD.cart.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService service;
    private static final String TOKEN = "Authorization";

    public CartController(CartService service){this.service = service;}

    @GetMapping("/{idUser}")
    public List<Cart> getCart(@RequestHeader(name=TOKEN) String token, @PathVariable("idUser") int idUser){
        return (List<Cart>) (service.findByIdUser(idUser));
    }

    @PostMapping("/{id}")
    public ResponseEntity<Void> getCart(@RequestHeader(name=TOKEN) String token, @RequestBody Cart cart){
        Cart cartToSave = service.saveCart(cart);
        if (cartToSave == null){
            return ResponseEntity.noContent().build();
        }
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/id")
                .buildAndExpand(cartToSave.getId())
                        .toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/{id}")
    public void deleteCart(@RequestHeader(name=TOKEN) String token, @PathVariable("id") int idCart){ service.deleteProduct(idCart);}

    @PutMapping("/{id}")
    public Cart updateCart(@RequestHeader(name=TOKEN) String token, @PathVariable("id") int idCart, @RequestBody Cart cart){
        return service.updateCart(idCart, cart);
    }

    @DeleteMapping("/deleteAll/{idUser}")
    public void deleteAllCart(@RequestHeader(name=TOKEN) String token, @PathVariable("idUser") int idUser){service.deleteAllProduct(idUser);}




}
