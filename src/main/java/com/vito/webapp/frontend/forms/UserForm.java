package com.vito.webapp.frontend.forms;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.BinderValidationStatus;
import com.vito.webapp.backend.controllers.UserController;
import com.vito.webapp.backend.models.User;
import com.vito.webapp.backend.utils.PasswordUtils;
import com.vito.webapp.frontend.components.BR;
import com.vito.webapp.backend.utils.SpringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class UserForm extends FormLayout {

    private TextField firstname = new TextField("First name");
    private TextField lastname = new TextField("Last name");
    private EmailField email = new EmailField("Email");
    private TextField username = new TextField("Username");
    private PasswordField password = new PasswordField("Password");
    private PasswordField confirmPassword = new PasswordField("Confirm Password");
    private Button save = new Button("Register");
    private Binder<User> binder = new BeanValidationBinder<>(User.class);

    private UserController userController;
    private int createdUsers;

    public UserForm() {
        addClassName("user-form");
        binder.bindInstanceFields(this);
        addCustomValidators();

        userController = SpringUtils.getBeanInFrontend(UserController.class);
        createdUsers = 0;

        save.addClickListener(event -> validateAndSave());
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        HorizontalLayout buttons = new HorizontalLayout(save);

        add(firstname, lastname, email, username, password, confirmPassword);
        add(new BR());
        add(buttons);
    }

    private void addCustomValidators() {
        binder.forField(confirmPassword)
                .withValidator(input -> input.equals(password.getValue()), "not same as password")
                .bind("confirmPassword");
        binder.forField(username)
                .withValidator(input -> {
                    List<User> users = userController.allUsers();
                    for (User user : users) {
                        if (user.getUsername().equalsIgnoreCase(input)) {
                            return false;
                        }
                    }
                    return true;
                }, "must be unique")
                .bind("username");
        binder.forField(email)
                .withValidator(input -> {
                    List<User> users = userController.allUsers();
                    for (User user : users) {
                        if (user.getEmail().equalsIgnoreCase(input)) {
                            return false;
                        }
                    }
                    return true;
                }, "must be unique")
                .bind("email");
    }

    private void validateAndSave() {
        User user = new User();

        user.setFirstname(firstname.getValue());
        user.setLastname(lastname.getValue());
        user.setEmail(email.getValue());
        user.setUsername(username.getValue());
        user.setPassword(password.getValue());
        user.setConfirmPassword(confirmPassword.getValue());

        binder.setBean(user);
        BinderValidationStatus<User> validationStatus = binder.validate();

        if(validationStatus.isOk()){
            user.setPassword(PasswordUtils.encodePassword(user.getPassword()));
            ResponseEntity<String> response = userController.saveUser(user);
            if(response.getStatusCode().equals(HttpStatus.OK)){
                Notification.show("user saved");
                createdUsers++;
                clear();
            } else{
                Notification.show("server error");
            }
        }
    }

    private void clear() {
        binder.setBean(null);
    }

    public int getCreatedUsers() {
        return createdUsers;
    }
}
