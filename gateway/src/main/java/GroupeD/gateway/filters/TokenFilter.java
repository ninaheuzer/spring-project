package GroupeD.gateway.filters;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import GroupeD.gateway.config.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class TokenFilter extends AbstractGatewayFilterFactory<TokenFilter.Config> {
    private static CustomProperties properties;

    public TokenFilter(CustomProperties props) {
        super(Config.class);
        properties = props;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            HttpHeaders headers = exchange.getRequest().getHeaders();
            if (!headers.containsKey("Authorization") || !config.verify(headers.getFirst("Authorization"))) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
            }
            return chain.filter(exchange).then();
        };
    }

    public static class Config {
        private final Algorithm jwtAlgorithm;
        private final JWTVerifier verifier;


        public Config() {

            this.jwtAlgorithm = Algorithm.HMAC256(properties.getJWTSecret());
            this.verifier = JWT.require(jwtAlgorithm).withIssuer(properties.getJWTIssuer()).build();
        }

        public boolean verify(String token) {
            try {
                verifier.verify(token);
            } catch (Exception e) {
                return false;
            }
            return true;
        }
    }
}
