package it.develhope.loginlezione.user.repositories;


import it.develhope.loginlezione.user.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    User findByActivationCode(String activationCode);
    User findByPasswordResetCode(String passwordResetCode);
    @Query(nativeQuery = true,value = "SELECT * FROM (\n" +
            "SELECT u.*, COUNT(busyOrders.id) AS numberOfOrders\n" +
            "FROM user AS u\n" +
            "LEFT JOIN user_roles AS ur ON ur.user_Id = u.id\n" +
            "LEFT JOIN (SELECT * FROM orders WHERE status IN(4)) AS busyOrders ON busyOrders.rider_id = u.id \n" +
            "WHERE ur.role_id = 3 AND u.is_active = 1\n" +
            "group by u.id\n" +
            ") AS allRiders\n" +
            "WHERE allRiders.numberOfOrders = 0\n" +
            "LIMIT_1")
    Optional<User> pickRider();
}
