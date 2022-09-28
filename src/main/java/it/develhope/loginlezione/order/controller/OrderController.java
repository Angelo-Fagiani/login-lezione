package it.develhope.loginlezione.order.controller;

import it.develhope.loginlezione.order.entities.Order;
import it.develhope.loginlezione.order.entities.OrderDTO;
import it.develhope.loginlezione.order.repositories.OrderRepository;
import it.develhope.loginlezione.order.service.OrderService;
import it.develhope.loginlezione.user.entities.Role;
import it.develhope.loginlezione.user.entities.User;
import it.develhope.loginlezione.user.utils.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/orders")
//@PreAuthorize("hasRole('" + Roles.REGISTERED + "')")
public class OrderController {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderService orderService;

    @PostMapping("")
    @PreAuthorize("hasRole('ROLE_REGISTERED')")
    public ResponseEntity<Order> create(@RequestBody OrderDTO order) throws Exception {
        return ResponseEntity.ok(orderService.save(order));
    }

    //NON FUNZIONA
    @GetMapping("/{id}")
    //@PostAuthorize("hasRole(' "+ Roles.ADMIN + "') OR returnObject.body == null OR returnObject.body.createdBy.id == authentication.principal.id")
    public ResponseEntity<Order> getSingle(@PathVariable Long id, Principal principal) {
        Optional<Order> singleOrder = orderRepository.findById(id);
        if (!singleOrder.isPresent()) return ResponseEntity.notFound().build();
        User user = (User) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        if (Roles.hasRole(user, Roles.REGISTERED) && singleOrder.get().getCreatedBy().getId() == user.getId()) {
            return ResponseEntity.ok(singleOrder.get());
        } else if (Roles.hasRole(user, Roles.RESTAURANT) && singleOrder.get().getRestaurant().getId() == user.getId()) {
            return ResponseEntity.ok(singleOrder.get());
        } else if (Roles.hasRole(user, Roles.RIDER) && singleOrder.get().getRider().getId() == user.getId()) {
            return ResponseEntity.ok(singleOrder.get());

        }
        return ResponseEntity.notFound().build();

    }




    @GetMapping("/")
    public ResponseEntity<List<Order>> getAll(Principal principal) {
        User user = (User) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();

        if (Roles.hasRole(user,Roles.REGISTERED)){
            return ResponseEntity.ok(orderRepository.findByCreatedBy(user));
        }else if (Roles.hasRole(user,Roles.RESTAURANT)){
            return ResponseEntity.ok(orderRepository.findByRestaurant(user));
        }else if (Roles.hasRole(user,Roles.RIDER)){
            return ResponseEntity.ok(orderRepository.findByRider(user));
        }
        return null;
    }
}

