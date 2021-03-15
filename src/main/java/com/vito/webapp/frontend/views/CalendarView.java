package com.vito.webapp.frontend.views;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vito.webapp.backend.security.MyUserPrincipal;
import com.vito.webapp.backend.utils.SpringUtils;

@Route(value = "calendar", layout = MainLayout.class)
@PageTitle("Calendar")
public class CalendarView extends HorizontalLayout {
    public CalendarView() {
        MyUserPrincipal authUser = SpringUtils.getAuthUser();
        add(new H5(authUser.toString()));
    }
}