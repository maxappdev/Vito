package com.vito.webapp.backend.controllers;

import com.vito.webapp.backend.models.User;
import com.vito.webapp.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.transaction.Transactional;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public ResponseEntity<String> saveUser(User user){
        ResponseEntity result = null;

        try{
            userRepository.save(user);
        }catch (Exception e){
            result = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        result = new ResponseEntity<>(user.toString(), HttpStatus.OK);

        return result;
    }

    public List<User> allUsers(){
        List<User> all = userRepository.findAll();
        return all;
    }
}
