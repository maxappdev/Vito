package com.vito.webapp.backend.entities.posts;

import com.vito.webapp.backend.entities.BasicEntity;
import com.vito.webapp.backend.entities.users.User;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Data
public class FacebookGroup extends BasicEntity {

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;
}
