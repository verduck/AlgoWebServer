package kr.ac.jj.algo.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConstructorBinding
@ConfigurationProperties("jwt")
public class JwtProperties {
    private final long expiration;

    public JwtProperties(long expiration) {
        this.expiration = expiration;
    }

    public long getExpiration() {
        return expiration;
    }
}
