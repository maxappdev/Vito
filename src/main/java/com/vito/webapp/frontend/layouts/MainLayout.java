package com.vito.webapp.frontend.layouts;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.RouterLink;
import com.vito.webapp.frontend.views.CalendarView;
import com.vito.webapp.frontend.views.FacebookView;

public class MainLayout extends AppLayout {
    public MainLayout() {
        H3 img = new H3("Vito");
        Tabs tabs = new Tabs(createTab("Facebook Posts", FacebookView.class), createTab("Calendar", CalendarView.class));
        addToNavbar(img, tabs);
    }

    private static Tab createTab(String text, Class<? extends Component> navigationTarget) {
        final Tab tab = new Tab();
        tab.add(new RouterLink(text, navigationTarget));
        ComponentUtil.setData(tab, Class.class, navigationTarget);
        return tab;
    }

}
