package GroupeD.gateway.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "be.vinci.ipl.grouped")
public class CustomProperties {
    private String JWTSecret;
    private String JWTIssuer;

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
}
