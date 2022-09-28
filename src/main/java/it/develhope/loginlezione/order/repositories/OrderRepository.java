package it.develhope.loginlezione.order.repositories;

import it.develhope.loginlezione.order.entities.Order;
import it.develhope.loginlezione.user.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order>findByCreatedBy(User user);
    List<Order>findByRestaurant(User user);
    List<Order>findByRider(User user);
}
