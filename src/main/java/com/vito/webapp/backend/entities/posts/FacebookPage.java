package com.vito.webapp.backend.entities.posts;

import com.vito.webapp.backend.entities.BasicEntity;
import com.vito.webapp.backend.entities.DataEncryptConverter;
import com.vito.webapp.backend.entities.users.User;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class FacebookPage extends BasicEntity {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Convert(converter = DataEncryptConverter.class)
    @Column(length = 1024)
    private String accessToken;

    private String pageId;

    private String name;

    private String category;


}
