package com.inyoucells.myproj.service.order;

import com.inyoucells.myproj.service.order.data.OrderEntity;
import com.inyoucells.myproj.service.order.data.OrderRepository;
import com.inyoucells.myproj.service.order.model.Order;
import com.inyoucells.myproj.service.order.model.OrderResponse;
import com.inyoucells.myproj.utils.ResponseUtils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OrderApiDelegateImpl implements OrderApiDelegate {

    private final OrderRepository orderRepository;

    @Override
    public ResponseEntity<Void> createOrder(Long userId, Order order) {
        orderRepository.saveAndFlush(new OrderEntity(null, order.getName(), order.getDate(), userId));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<OrderResponse> getOrderById(Long orderId) {
        OrderEntity orderEntity = orderRepository.getById(orderId);
        OrderResponse orderResponse = new OrderResponse().id(orderEntity.getId()).name(orderEntity.getName())
                .date(orderEntity.getDate()).userId(orderEntity.getUserId());
        return ResponseUtils.withResponse(orderResponse);
    }

    @Override
    public ResponseEntity<List<OrderResponse>> listOrders(Long userId, Integer page, Integer size) {
        Pageable pageable = (page != null && size != null) ? PageRequest.of(page, size) : PageRequest.of(0, 10);
        List<OrderResponse> orders = orderRepository.findAllByUserId(userId, pageable).stream()
                .map(orderEntity -> new OrderResponse().id(orderEntity.getId()).name(orderEntity.getName())
                        .date(orderEntity.getDate()).userId(orderEntity.getUserId())).collect(
                        Collectors.toList());
        return ResponseUtils.withResponse(orders);
    }
}
