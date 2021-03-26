package com.vito.webapp.backend.entities.users;

import com.vito.webapp.backend.entities.BasicEntity;
import com.vito.webapp.backend.entities.DataEncryptConverter;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;

@Entity
@Data
public class UserSocialData extends BasicEntity {

    @Convert(converter = DataEncryptConverter.class)
    @Column(length = 1024)
    private String userAccessTokenFacebook;

    @OneToOne
    private User user;

}
