package com.leopold.store.service;

import com.leopold.store.entity.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class OrderServiceTest {
    @Autowired
    private IOrderService orderService;
    @Autowired
    private IOrderItemService orderItemService;

    @Test
    public void testCreateOrderFromCart() {
        List<Integer> cids = new ArrayList<>();
        cids.add(5);
        cids.add(6);
        cids.add(7);

        Order order = orderService.createOrderFromCart(cids, 13, 15, "leopold");

        assertEquals(orderService.findOrdersByUser(13).size(), 6);
        assertEquals(orderItemService.findOrderItemByOrder(order).size(), 3);

        System.out.println(order);
    }
}
