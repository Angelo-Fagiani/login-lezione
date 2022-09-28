package it.develhope.loginlezione.order.entities;

import it.develhope.loginlezione.user.entities.User;
import it.develhope.loginlezione.utils.entities.BaseEntity;
import lombok.Data;
import javax.persistence.*;

@Data
@Entity
@Table(name = "orders")
public class Order extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String description;
    private String address;
    private String number;
    private String city;
    private String zipCode;
    private String state;

    private OrderStatusEnum status = OrderStatusEnum.CREATED;
    @ManyToOne
    private User restaurant;
    @ManyToOne
    private User rider;

    private double totalPrice;
}
