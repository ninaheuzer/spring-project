package GroupeD.cart.service;

import GroupeD.cart.model.Cart;
import GroupeD.cart.repo.CartRepository;
import org.springframework.stereotype.Service;

@Service
public class CartService {
    private final CartRepository repo;


    public CartService(CartRepository repo) {
        this.repo = repo;
    }

    public Iterable<Cart> findAll(){ return repo.findAll();} //never used

    public Iterable<Cart> findByIdUser(int idUser){ return repo.findByIdUser(idUser);}

    public Cart saveCart(Cart cart){
        Cart existingCart = repo.findByIdUserAndIdProduct(cart.getIdUser(), cart.getIdProduct());
        if(existingCart != null){
            existingCart.setQuantity(existingCart.getQuantity()+cart.getQuantity());
            return repo.save(existingCart);
        }
        return repo.save(cart);}

    public void deleteProduct(int idCart){
        Cart cart = repo.findById(idCart).orElseThrow(InternalError::new);
        repo.delete(cart);
    }

    public Cart updateCart(int idCart, Cart cart){
        Cart cartToUpdate = repo.findById(idCart).orElseThrow(InternalError::new);
        cartToUpdate.setQuantity(cart.getQuantity());
        return repo.save(cartToUpdate);
    }

    public void deleteAllProduct(int idUser){
        Iterable<Cart> listToDelete = repo.findByIdUser(idUser);
        for(Cart c:listToDelete){
            repo.delete(c);
        }
    }
}
