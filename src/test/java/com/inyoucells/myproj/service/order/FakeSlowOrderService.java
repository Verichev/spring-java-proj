package com.inyoucells.myproj.service.order;

import com.inyoucells.myproj.service.order.data.OrderRepository;
import com.inyoucells.myproj.service.order.model.OrderResponse;
import com.inyoucells.myproj.utils.ResponseUtils;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

public class FakeSlowOrderService extends OrderApiDelegateImpl {

    public FakeSlowOrderService(OrderRepository orderRepository) {
        super(orderRepository);
    }

    // Don't do this at home
    private void simulateSlowService() {
        try {
            long time = 3000L;
            Thread.sleep(time);
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    @Cacheable("orders")
    public ResponseEntity<List<OrderResponse>> listOrders(Long userId, Integer page, Integer size) {
        simulateSlowService();
        return ResponseUtils.withResponse(Collections.emptyList());
    }
}