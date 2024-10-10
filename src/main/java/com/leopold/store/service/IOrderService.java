package com.leopold.store.service;

import com.leopold.store.entity.Order;
import com.leopold.store.entity.User;

import java.util.List;

public interface IOrderService {
    Order createOrderFromCart(List<Integer> cids, Integer uid, Integer aid, String userName);

    Order findOrderByID(Integer id);

    List<Order> findOrdersByUser(User user);
    List<Order> findOrdersByUser(Integer uid);
}
