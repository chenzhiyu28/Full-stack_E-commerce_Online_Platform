package com.leopold.store.persistence.repository;

import com.leopold.store.entity.Order;
import com.leopold.store.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Integer>, JpaSpecificationExecutor<OrderItem> {
    List<OrderItem> findOrderItemByOrder(Order order);
}
