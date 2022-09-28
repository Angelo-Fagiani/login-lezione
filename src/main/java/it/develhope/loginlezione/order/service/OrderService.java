package it.develhope.loginlezione.order.service;

import it.develhope.loginlezione.order.entities.Order;
import it.develhope.loginlezione.order.entities.OrderDTO;
import it.develhope.loginlezione.order.repositories.OrderRepository;
import it.develhope.loginlezione.user.entities.User;
import it.develhope.loginlezione.user.repositories.UserRepository;
import it.develhope.loginlezione.user.utils.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;

    public Order save(OrderDTO orderInput) throws Exception {
        if (orderInput == null) return null;
        //questo serve per indicare l'utente che ha fatto il login, che abbiamo indicato nel jwtTokenFilter
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Order order = new Order();
        order.setCreatedAt(LocalDateTime.now());
        order.setCreatedBy(user);
        order.setAddress(orderInput.getAddress());
        order.setCity(orderInput.getCity());
        order.setDescription(orderInput.getDescription());
        order.setState(orderInput.getState());
        order.setNumber(orderInput.getNumber());
        order.setTotalPrice(orderInput.getTotalPrice());
        order.setZipCode(orderInput.getZipCode());

        //check for restaurant
        if (orderInput.getRestaurant() == null) throw new Exception("Restaurant not found");
        Optional<User> restaurant = userRepository.findById(orderInput.getRestaurant());
        if (!restaurant.isPresent() || !Roles.hasRole(restaurant.get(),Roles.RESTAURANT)) throw new Exception("Restaurant not found");

        order.setRestaurant(restaurant.get());



        return orderRepository.save(order);
    }
    public Order update(Long id, Order orderInput){
        if (orderInput == null) return null;
        //questo serve per indicare lo User che fa l'ordine
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        orderInput.setId(id);
        orderInput.setUpdateAt(LocalDateTime.now());
        orderInput.setUpdatedBy(user);
        return orderRepository.save(orderInput);
    }

    public boolean canEdit(Long id) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional <Order> order = orderRepository.findById(id);
        if (!order.isPresent()) return false;
        return order.get().getCreatedBy().getId() == user.getId();
    }
}