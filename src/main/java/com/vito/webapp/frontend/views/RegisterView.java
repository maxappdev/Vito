package com.vito.webapp.frontend.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vito.webapp.backend.security.SecurityConfiguration;
import com.vito.webapp.frontend.forms.UserForm;
import org.aspectj.weaver.ast.Not;
import org.springframework.web.socket.server.standard.SpringConfigurator;

@Route("register")
@PageTitle("Register | Vito")
public class RegisterView extends VerticalLayout {

    private UserForm register = new UserForm();

    public RegisterView(){

        addClassName("register-view");
        setSizeFull();
        setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

        add(new H1("Vito Social Management"), register);
        add(new H2("Create New Account"), register);

        register.addClickListener(formLayoutClickEvent -> {
            if(register.getCreatedUsers() > 0){
                UI.getCurrent().navigate("login");
                Notification.show("now you can login with your data");
            }
        });
    }
}
