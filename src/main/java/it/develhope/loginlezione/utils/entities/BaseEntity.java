package it.develhope.loginlezione.utils.entities;

import it.develhope.loginlezione.user.entities.User;
import lombok.Data;

import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
@Data
public class BaseEntity {

    private LocalDateTime createdAt;
    private LocalDateTime updateAt;

    @ManyToOne
    private User createdBy;
    @ManyToOne
    private User updatedBy;
}
