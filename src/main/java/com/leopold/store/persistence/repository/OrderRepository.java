package com.leopold.store.persistence.repository;

import com.leopold.store.entity.Order;
import com.leopold.store.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer>, JpaSpecificationExecutor<Order> {
    Order findOrderById(Integer id);

    List<Order> findOrderByUser(User user);
}
