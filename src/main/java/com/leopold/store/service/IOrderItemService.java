package com.leopold.store.service;

import com.leopold.store.entity.Order;
import com.leopold.store.entity.OrderItem;

import java.util.List;

public interface IOrderItemService {
    List<OrderItem> findOrderItemByOrder(Order order);
    List<OrderItem> findOrderItemByOid(Integer oid);
}
