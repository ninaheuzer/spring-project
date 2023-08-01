package GroupeD.cartFront.service;

import GroupeD.cartFront.config.CustomProperties;
import GroupeD.cartFront.model.Cart;
import GroupeD.cartFront.model.User;
import GroupeD.cartFront.model.Vehicle;
import GroupeD.cartFront.proxy.GatewayProxy;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
public class GatewayService {


    private final static String USER = "user";
    private final CustomProperties properties;
    private final Algorithm jwtAlgorithm;
    private final JWTVerifier verifier;
    private final GatewayProxy gatewayProxy;

    public GatewayService(GatewayProxy gatewayProxy, CustomProperties properties){
        this.gatewayProxy = gatewayProxy;
        this.properties = properties;
        this.jwtAlgorithm = Algorithm.HMAC256(properties.getJWTSecret());
        this.verifier = JWT.require(jwtAlgorithm).withIssuer("auth0").build();
    }

    public boolean checkQuantityCart(Cart cart){
        return cart.getQuantity()>0;
    }

    public int verifyToken(String token) {
        try {
            DecodedJWT decodedToken = verifier.verify(token);
            int idUser = decodedToken.getClaim(USER).asInt();
            return idUser;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return -1;
        }
    }

    /***********cart**********/

    public List<Cart> findByIdUser(String token, int idUser){
        return gatewayProxy.findCartByIdUser(token, idUser);
    }

    public void saveCart(String token,Cart cart){
        gatewayProxy.saveCart(token, cart);
    }

    public void updateCart(String token, int id, Cart cart){
        gatewayProxy.updateCart(token, id, cart);
    }

    public void deleteCart(String token,int id){
        gatewayProxy.deleteCart(token, id);
    }

    public void deleteAllCart(String token,int id){
        gatewayProxy.deleteAllCart(token, id);
    }

    /**************users*********/

    public User findUserById(int id){
        return gatewayProxy.findUserById(id);
    }

    /************vehicles************/
    public Vehicle findVehicleById(int id){
        return gatewayProxy.findVehicleById(id);
    }



}
