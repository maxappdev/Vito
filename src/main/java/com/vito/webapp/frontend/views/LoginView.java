package com.vito.webapp.frontend.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.H6;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.VaadinRequest;
import com.vaadin.flow.server.VaadinService;
import org.atmosphere.interceptor.AtmosphereResourceStateRecovery;

import java.text.Normalizer;
import java.util.List;
import java.util.Map;

@Route("login")
@PageTitle("Login | Vito")
public class LoginView extends VerticalLayout implements BeforeEnterObserver, HasUrlParameter<String> {

    private LoginForm login = new LoginForm();

    public LoginView(){
        addClassName("login-view");
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        login.setAction("login");

        add(new H1("Vito Social Management"), login);

        Button signInButton = new Button("Create New Account");
        signInButton.addClickListener(event -> UI.getCurrent().navigate("register"));
        add(new H6("Not registered yet?"));
        add(signInButton);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        // inform the user about an authentication error
        if(beforeEnterEvent.getLocation()
                .getQueryParameters()
                .getParameters()
                .containsKey("error")) {
            login.setError(true);
        }
        VaadinRequest currentRequest = VaadinService.getCurrentRequest();
        currentRequest = currentRequest; //TODO maybe try this workaround
    }


    @Override
    public void setParameter(BeforeEvent event,
                             @OptionalParameter String parameter) {

        Location location = event.getLocation();
        QueryParameters queryParameters = location
                .getQueryParameters();

        Map<String, List<String>> parametersMap =
                queryParameters.getParameters();

        parametersMap = parametersMap; //TODO add parameter after registration
    }
}
