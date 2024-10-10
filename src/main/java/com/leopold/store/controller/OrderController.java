package com.leopold.store.controller;

import com.leopold.store.entity.Order;
import com.leopold.store.service.IOrderItemService;
import com.leopold.store.service.IOrderService;
import com.leopold.store.util.JsonResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/order")
public class OrderController extends BaseController {
    private final IOrderService orderService;
    private final IOrderItemService orderItemService;

    public OrderController(IOrderService orderService, IOrderItemService orderItemService) {
        this.orderService = orderService;
        this.orderItemService = orderItemService;
    }


    @PostMapping({"", "/"})
    public JsonResponse<Order> createOrder(@RequestParam List<Integer> cids,
                                    @RequestParam Integer aid,
                                    HttpSession session) {
        Integer uid = getUIDFromSession(session);
        String userName = getUsernameFromSession(session);
        Order order = orderService.createOrderFromCart(cids, uid, aid, userName);
        order.setUser(null); 
        return successResponse(order);
    }
}


