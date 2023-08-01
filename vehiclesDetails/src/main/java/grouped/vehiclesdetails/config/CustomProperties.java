package grouped.vehiclesdetails.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "be.vinci.ipl.grouped")
public class CustomProperties {
    private String JWTSecret;
    private String JWTIssuer;
    private String cartFront;
    private String vehiclesFront;
    private String login;
    private String register;
    private String account;

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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getRegister() {
        return register;
    }

    public void setRegister(String register) {
        this.register = register;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
