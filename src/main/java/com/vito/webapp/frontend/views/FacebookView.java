package com.vito.webapp.frontend.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

@Route(value = "facebook", layout = MainLayout.class)
@PageTitle("Facebook Posts")
public class FacebookView extends HorizontalLayout {
    public FacebookView() {
        add(new H5("facebook"));
    }
}
