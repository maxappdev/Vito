package com.vito.webapp.backend.models;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
    @Length(min = 8, message = "minimum length is 8")
    private String confirmPassword; //not a column in db, used for form validation in frontend

    //now we have only one role, can be extended later
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<UserRole> result = new ArrayList<>();

        UserRole role = UserRole.USER;
        result.add(role);

        return result;
    }
}
