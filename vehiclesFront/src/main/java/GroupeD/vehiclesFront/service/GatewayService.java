package GroupeD.vehiclesFront.service;

import GroupeD.vehiclesFront.config.CustomProperties;
import GroupeD.vehiclesFront.model.Cart;
import GroupeD.vehiclesFront.model.User;
import GroupeD.vehiclesFront.model.Vehicle;
import GroupeD.vehiclesFront.model.VehicleDTO;
import GroupeD.vehiclesFront.proxy.GatewayProxy;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Service;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static GroupeD.vehiclesFront.service.ServiceConstants.NONE;

@Service
public class GatewayService {
    private static final String USER = "user";
    private final CustomProperties properties;
    private final Algorithm jwtAlgorithm;
    private final JWTVerifier verifier;
    private final GatewayProxy proxy;

    public GatewayService(GatewayProxy gatewayProxy, CustomProperties customProperties){
        this.proxy = gatewayProxy;
        this.properties = customProperties;
        this.jwtAlgorithm = Algorithm.HMAC256(properties.getJWTSecret());
        this.verifier = JWT.require(jwtAlgorithm).withIssuer("auth0").build();
    }

    /******** Vehicles **********/
    public List<Vehicle> findAll(String category, Integer minPrice, Integer maxPrice){
        return proxy.findAll(category,minPrice,maxPrice);
    }

    public Vehicle findById(int id) {
        return proxy.findVehicleById(id);
    }

    public Vehicle createVehicle(Vehicle v, String token){
        return proxy.createVehicle(v, token);
    }

    public Vehicle deleteVehicle(int id, String token){
        return proxy.deleteVehicle(id, token);
    }

    public List<Vehicle> vehiclesSearch(VehicleDTO vehicleDTO){
        List<Vehicle> vehicles = this.findAll(null,null, null);
        if (vehicleDTO.getCategory() != null) {
            if (!vehicleDTO.getCategory().isEmpty()) {
                String category = vehicleDTO.getCategory();
                vehicles = vehicles.stream().filter(v -> v.getCategory().equals(category)).collect(Collectors.toList());
            }
        }
        if (vehicleDTO.getMinPrice() != null && vehicleDTO.getMaxPrice() != null){
            Double minPrice = vehicleDTO.getMinPrice();
            Double maxPrice = vehicleDTO.getMaxPrice();
            vehicles = vehicles.stream().filter(v->v.getPrice() >= minPrice && v.getPrice() <= maxPrice).collect(Collectors.toList());
        }

        String tri = vehicleDTO.getTri();
        if (tri != null && !tri.isEmpty()){
            if (tri.equals("decroissant")){
                vehicles = vehicles.stream().sorted(Comparator.comparing(Vehicle::getPrice).reversed()).collect(Collectors.toList());
            }
            else {
                vehicles = vehicles.stream().sorted(Comparator.comparing(Vehicle::getPrice)).collect(Collectors.toList());
            }
        }
        return vehicles;
    }

    /******** Users **********/
    public User findUserById(int id){
        return proxy.findUserById(id);
    }

    public User getUserFromToken(String token) {
        if (token == null || token.equals(NONE)) return null;
        try {
            DecodedJWT decodedJWT = verifier.verify(token);
            User user = findUserById(decodedJWT.getClaim(USER).asInt());
            user.setAdmin(user.getUserType() == User.USER_TYPES.ADMIN);
            return user;
        } catch (Exception e) {
            return null;
        }
    }


    public boolean isUserAdmin(String token) {
        if (token == null || token.equals(NONE)) {
            return false;
        }
        User user = getUserFromToken(token);
        if (user == null) return false;
        return user.getUserType() == User.USER_TYPES.ADMIN;
    }

    public void addConnectionInModel(Model model, String value) {
        if (!model.containsAttribute("connected"))
            model.addAttribute("connected", value);
    }

    public void addAdminConnectionInModel(Model model, String value){
        if (!model.containsAttribute("admin"))
            model.addAttribute("admin", value);
    }


}

