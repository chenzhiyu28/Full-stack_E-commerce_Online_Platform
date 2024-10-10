package com.leopold.store.service.impl;

import com.leopold.store.entity.Address;
import com.leopold.store.entity.DTO.CartDTO;
import com.leopold.store.entity.Order;
import com.leopold.store.entity.OrderItem;
import com.leopold.store.entity.User;
import com.leopold.store.persistence.repository.OrderItemRepository;
import com.leopold.store.persistence.repository.OrderRepository;
import com.leopold.store.persistence.repository.ProductRepository;
import com.leopold.store.service.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements IOrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final IAddressService addressService;
    private final ICartService cartService;
    private final IUserService userService;
    private final IProductService productService;


    public OrderServiceImpl(OrderRepository orderRepository,
                            IAddressService addressService,
                            ICartService cartService,
                            IUserService userService,
                            IProductService productService,
                            OrderItemRepository orderItemRepository) {
        this.orderRepository = orderRepository;
        this.addressService = addressService;
        this.cartService = cartService;
        this.userService = userService;
        this.productService = productService;
        this.orderItemRepository = orderItemRepository;
    }

    @Override
    @Transactional
    public Order createOrderFromCart(List<Integer> cids, Integer uid, Integer aid, String userName) {
        List<CartDTO> carts = cartService.findCartDTOsByCIDs(cids, uid);
        Date time = new Date();
        Order order = new Order();
        Address address = addressService.findAddressByID(aid);

        Long totalPrice = 0L;
        for (CartDTO cartDTO : carts) {
            totalPrice += cartDTO.getProductPrice();
        }
        order.setUser(userService.findUserById(uid));
        order.setCreatedTime(time);
        order.setModifiedTime(time);
        order.setCreatedUser(userName);
        order.setModifiedUser(userName);
        order.setRecvName(address.getName());
        order.setRecvPhone(address.getPhone());
        order.setRecvProvince(address.getProvinceName());
        order.setRecvCity(address.getCityName());
        order.setRecvArea(address.getAreaName());
        order.setRecvAddress(address.getAddress());
        order.setStatus(0);
        order.setOrderTime(time);
        order.setTotalPrice(totalPrice);

        Order savedOrder = orderRepository.save(order);

        for (CartDTO cartDTO : carts) {

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(savedOrder);
            orderItem.setProduct(productService.findProductByID(cartDTO.getProductID()));
            orderItem.setTitle(cartDTO.getProductTitle());
            orderItem.setImage(cartDTO.getProductImage());
            orderItem.setPrice(cartDTO.getProductPrice());
            orderItem.setNum(cartDTO.getCartNum());
            orderItemRepository.save(orderItem);
        }


        return savedOrder;
    }

    @Override
    @Transactional(readOnly = true)
    public Order findOrderByID(Integer id) {
        return orderRepository.findOrderById(id);
    }

    @Override
    public List<Order> findOrdersByUser(User user) {
        return orderRepository.findOrderByUser(user);
    }

    @Override
    public List<Order> findOrdersByUser(Integer uid) {
        return orderRepository.findOrderByUser(userService.findUserById(uid));
    }
}
