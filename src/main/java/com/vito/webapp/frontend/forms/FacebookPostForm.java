package com.vito.webapp.frontend.forms;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vito.webapp.backend.entities.posts.FacebookPost;


public class FacebookPostForm extends FormLayout {

    private TextArea text = new TextArea("Text");
    private DateTimePicker publishAt = new DateTimePicker();
    private Button save = new Button("Save");
    private Binder<FacebookPost> binder = new BeanValidationBinder<>(FacebookPost.class);

    public FacebookPostForm() {
        addClassName("facebook-post-form");
        binder.bindInstanceFields(this);

        this.text.setPlaceholder("Write here...");
        this.text.setWidthFull();
        this.text.setHeightFull();
        this.publishAt.setLabel("Publish At");
        this.publishAt.setDatePlaceholder("Date");
        this.publishAt.setTimePlaceholder("Time");

        save.addClickListener(event -> validateAndSave());
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        HorizontalLayout buttons = new HorizontalLayout(save);

        add(new VerticalLayout(text, publishAt, buttons));
    }

    private void validateAndSave() {
        binder.validate();
        Notification.show("save clicked");
    }
}
