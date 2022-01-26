package org.oze.hospital.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class Properties {
    @Autowired Environment environment;

    public String getSecret() {
        return environment.getProperty("secret");
    }

    public long getJwtExpirationInMs() {
        return Long.valueOf(environment.getProperty("jwt_expiration_in_ms"));
    }

    public long getRefreshExpirationInMs() {
        return 0;
    }
    
}
