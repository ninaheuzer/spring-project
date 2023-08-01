package grouped.usersfront.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "be.vinci.ipl.grouped")
public class CustomProperties {
    private String JWTSecret;
    private String JWTIssuer;
    private String cartFront;
    private String vehiclesFront;
    private String vehiclesDetails;

    public String getJWTSecret() {
        return JWTSecret;
    }

    public void setJWTSecret(String JWTSecret) {
        this.JWTSecret = JWTSecret;
    }

    public String getJWTIssuer() {
        return JWTIssuer;
    }

    public void setJWTIssuer(String JWTIssuer) {
        this.JWTIssuer = JWTIssuer;
    }

    public String getCartFront() {
        return cartFront;
    }

    public void setCartFront(String cartFront) {
        this.cartFront = cartFront;
    }

    public String getVehiclesFront() {
        return vehiclesFront;
    }

    public void setVehiclesFront(String vehiclesFront) {
        this.vehiclesFront = vehiclesFront;
    }

    public String getVehiclesDetails() {
        return vehiclesDetails;
    }

    public void setVehiclesDetails(String vehiclesDetails) {
        this.vehiclesDetails = vehiclesDetails;
    }
}
