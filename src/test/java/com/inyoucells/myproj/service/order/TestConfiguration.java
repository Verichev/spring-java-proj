package com.inyoucells.myproj.service.order;

import com.inyoucells.myproj.service.order.data.OrderRepository;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@org.springframework.boot.test.context.TestConfiguration
public class TestConfiguration {

    @Bean
    @Primary
    public OrderApiDelegate getOrderService(OrderRepository orderRepository) {
        return new FakeSlowOrderService(orderRepository);
    }

}
