package com.ghkdtlwns987.apiserver.Global.Config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class GatewayConfig {
    @Value("${auth.authentication.url}")
    public String authenticationUrl;

    @Value("${auth.authorization.url}")
    public String authorizationUrl;
}
