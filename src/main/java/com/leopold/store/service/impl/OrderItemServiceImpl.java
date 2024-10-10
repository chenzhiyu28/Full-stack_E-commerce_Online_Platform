package com.leopold.store.service.impl;

import com.leopold.store.entity.Order;
import com.leopold.store.entity.OrderItem;
import com.leopold.store.persistence.repository.OrderItemRepository;
import com.leopold.store.persistence.repository.OrderRepository;
import com.leopold.store.service.IOrderItemService;
import com.leopold.store.service.IOrderService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemServiceImpl implements IOrderItemService {
    private final OrderItemRepository orderItemRepository;
    private final IOrderService orderService;

    public OrderItemServiceImpl(OrderItemRepository orderItemRepository, IOrderService orderService) {
        this.orderItemRepository = orderItemRepository;
        this.orderService = orderService;
    }

    @Override
    public List<OrderItem> findOrderItemByOrder(Order order) {
        return orderItemRepository.findOrderItemByOrder(order);
    }

    @Override
    public List<OrderItem> findOrderItemByOid(Integer oid) {
        return findOrderItemByOrder(orderService.findOrderByID(oid));
    }
}
