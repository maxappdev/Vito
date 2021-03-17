package com.vito.webapp.backend.entities.users;

import com.vito.webapp.backend.entities.BasicEntity;
import com.vito.webapp.backend.entities.DataEncryptConverter;
import lombok.Data;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "user_social_data")
public class UserSocialData extends BasicEntity {

    @Convert(converter = DataEncryptConverter.class)
    private String pageAccessTokenFacebook;

    @OneToOne
    private User user;

}
