package GroupeD.cartFront.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("grouped")
public class CustomProperties {
    private String JWTSecret;

    public String getJWTSecret(){return JWTSecret;}

    public void setJWTSecret(String JWTSecret){
        this.JWTSecret = JWTSecret;
    }
}
