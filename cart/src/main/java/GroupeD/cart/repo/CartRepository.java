package GroupeD.cart.repo;

import GroupeD.cart.model.Cart;
import org.springframework.data.repository.CrudRepository;


public interface CartRepository extends CrudRepository<Cart, Integer> {

    Iterable<Cart> findByIdUser(int idUser);

    Cart findByIdUserAndIdProduct(int idUser, int idProduct);

}
