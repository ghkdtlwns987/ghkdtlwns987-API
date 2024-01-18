package com.ghkdtlwns987.apiserver.Order.Config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class OrderConfig {
    @Value("${order.url}")
    private String orderUrl;
}
