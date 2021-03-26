package com.vito.webapp.frontend.forms;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vito.webapp.backend.controllers.FacebookController;
import com.vito.webapp.backend.utils.SpringUtils;
import com.vito.webapp.frontend.html.BR;

public class FacebookAuthForm extends FormLayout {

    private Button authButton = new Button("Add Facebook Groups");

    public FacebookAuthForm(){

        Text infoText = new Text("Please share your Facebook credentials and add your groups");
        authButton.addClickListener(event -> UI.getCurrent().getPage().setLocation(SpringUtils.getBaseUrl() + FacebookController.FACEBOOK_AUTH_MAPPING));

        add(new VerticalLayout(new BR(), infoText, authButton));
    }

}
