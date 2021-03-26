package com.vito.webapp.backend.controllers;

import com.vito.webapp.backend.entities.users.User;
import com.vito.webapp.backend.repositories.UserRepository;
import com.vito.webapp.backend.repositories.UserSocialDataRepository;
import com.vito.webapp.backend.utils.SpringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import javax.transaction.Transactional;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserSocialDataRepository socialDataRepository;

    @Transactional
    public ResponseEntity<String> saveUser(User user) {
        ResponseEntity result = null;

        try {
            userRepository.save(user);
        } catch (Exception e) {
            result = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        result = new ResponseEntity<>(user.toString(), HttpStatus.OK);

        return result;
    }

    @Transactional
    public List<User> allUsers() {
        List<User> all = userRepository.findAll();
        return all;
    }

    @Transactional
    public boolean hasFacebookData() {
        boolean result = false;

        User user = SpringUtils.getAuthUser();
        if (user != null) {
            result = user.hasFacebookAccessToken();
        }

        return result;
    }
}
