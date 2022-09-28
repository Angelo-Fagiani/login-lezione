package it.develhope.loginlezione.order.service;

import it.develhope.loginlezione.order.entities.Order;
import it.develhope.loginlezione.order.entities.OrderStatusEnum;
import it.develhope.loginlezione.order.repositories.OrderRepository;
import it.develhope.loginlezione.user.entities.User;
import it.develhope.loginlezione.user.service.RiderService;
import jdk.jshell.spi.ExecutionControl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class OrderStateService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private RiderService riderService;

    public Order setAccept(Order order) throws Exception{
        if (order == null) throw new NullPointerException("");
        if (order.getStatus() != OrderStatusEnum.CREATED) throw new Exception("Cannot edit order");

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (order.getRestaurant().getId() != user.getId()) throw new Exception("This is not your order");
        //Go a head one step
        order.setStatus(OrderStatusEnum.ACCEPTED);
        order.setUpdateAt(LocalDateTime.now());
        order.setUpdatedBy(user);
        return orderRepository.save(order);
    }

    public Order setInPreparation(Order order) throws Exception{
        if (order == null) throw new NullPointerException("");
        if (order.getStatus() != OrderStatusEnum.ACCEPTED) throw new Exception("Cannot edit order");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (order.getRestaurant().getId() != user.getId()) throw new Exception("This is not your order");

        order.setStatus(OrderStatusEnum.IN_PREPARATION);
        order.setUpdateAt(LocalDateTime.now());
        order.setUpdatedBy(user);
        return orderRepository.save(order);
    }

    public Order setReady(Order order)throws Exception {
        if (order == null) throw new NullPointerException("");
        if (order.getStatus() != OrderStatusEnum.IN_PREPARATION) throw new Exception("Cannot edit order");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (order.getRestaurant().getId() != user.getId()) throw new Exception("This is not your order");
        //Rider selection
        User rider = riderService.pickRider();
        order.setRider(rider);
        //Go ahead one step
        order.setStatus(OrderStatusEnum.READY);
        order.setUpdateAt(LocalDateTime.now());
        order.setUpdatedBy(user);
        return orderRepository.save(order);
    }

    public Order setDelivering(Order order)throws Exception {
        if (order == null) throw new NullPointerException("");
        if (order.getStatus() != OrderStatusEnum.READY) throw new Exception("Cannot edit order");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (order.getRider().getId() != user.getId()) throw new Exception("This is not your order");

        order.setStatus(OrderStatusEnum.DELIVERING);
        order.setUpdateAt(LocalDateTime.now());
        order.setUpdatedBy(user);
        return orderRepository.save(order);
    }

    public Order setComplete(Order order) throws Exception{
        if (order == null) throw new NullPointerException("");
        if (order.getStatus() != OrderStatusEnum.DELIVERING) throw new Exception("Cannot edit order");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (order.getRider().getId() != user.getId()) throw new Exception("This is not your order");

        order.setStatus(OrderStatusEnum.COMPLETED);
        order.setUpdateAt(LocalDateTime.now());
        order.setUpdatedBy(user);
        return orderRepository.save(order);
    }
}


