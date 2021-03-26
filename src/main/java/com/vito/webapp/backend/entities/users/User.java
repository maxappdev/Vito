package com.vito.webapp.backend.entities.users;

import com.vito.webapp.backend.entities.BasicEntity;
import com.vito.webapp.backend.repositories.UserSocialDataRepository;
import com.vito.webapp.backend.utils.VitoUtils;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity(name = "user_entity") //it appears table with name "user" can not be created in Postgres
@Data
public class User extends BasicEntity {

    @NotBlank
    private String firstname;

    @NotBlank
    private String lastname;

    @NotBlank
    @Email
    @Column(unique = true)
    private String email;

    @Column(unique = true)
    @Length(min = 3, max = 25)
    private String username;

    @Length(min = 8, message = "minimum length is 8")
    private String password;

    @Transient
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "social_data_id", referencedColumnName = "id")
    private UserSocialData socialData;

    @Transient
    @Length(min = 8, message = "minimum length is 8")
    private String confirmPassword; //not a column in db, used for form validation in frontend

    //now we have only one role, can be extended later
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<UserRole> result = new ArrayList<>();

        UserRole role = UserRole.USER;
        result.add(role);

        return result;
    }

    public void addFacebookAccessToken(String token, UserSocialDataRepository socialDataRepository) {
        UserSocialData socialData = getSocialData();
        if (socialData == null) {
            UserSocialData newSocialData = new UserSocialData();
            newSocialData.setUserAccessTokenFacebook(token);
            newSocialData.setUser(this);
            this.setSocialData(newSocialData);

            socialDataRepository.save(newSocialData);
        } else {
            if (token != socialData.getUserAccessTokenFacebook()) {
                socialData.setUserAccessTokenFacebook(token);

                socialDataRepository.save(socialData);
            }
        }
    }

    public boolean hasFacebookAccessToken() {
        boolean result = false;

        UserSocialData socialData = this.getSocialData();

        if (socialData != null) {
            String accessTokenFacebook = socialData.getUserAccessTokenFacebook();
            if (VitoUtils.ok(accessTokenFacebook)) {
                result = true;
            }
        }

        return result;
    }
}
