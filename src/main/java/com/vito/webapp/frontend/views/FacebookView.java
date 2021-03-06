package com.vito.webapp.frontend.views;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.listbox.ListBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vito.webapp.backend.controllers.UserController;
import com.vito.webapp.backend.entities.posts.FacebookPage;
import com.vito.webapp.backend.entities.users.User;
import com.vito.webapp.backend.utils.SpringUtils;
import com.vito.webapp.frontend.forms.FacebookAuthForm;
import com.vito.webapp.frontend.forms.FacebookPostForm;
import com.vito.webapp.frontend.layouts.MainLayout;

import java.util.ArrayList;
import java.util.List;

@Route(value = "", layout = MainLayout.class)
@PageTitle("Facebook Posts")
public class FacebookView extends HorizontalLayout {
    private FacebookPostForm facebookPostForm = new FacebookPostForm();
    private FacebookAuthForm facebookAuthForm = new FacebookAuthForm();
    private UserController userController;

    public FacebookView() {
        userController = SpringUtils.getBeanInFrontend(UserController.class);
        boolean authUserHasFacebook = userController.hasFacebookData();
        if(authUserHasFacebook){
            addGroups();
            //addCreatePost();
        }else{
            add(facebookAuthForm);
        }
    }

    private void addGroups() {
        User authUser = SpringUtils.getAuthUser();
        List<FacebookPage> facebookPages = authUser.getFacebookPages();

        ListBox<String> listBox = new ListBox<>();

        List<String> pages = new ArrayList<>();
        facebookPages.forEach(facebookPage -> {
            pages.add(facebookPage.toString());
        });

        listBox.setItems(pages);

        add(listBox);
    }

    private void addCreatePost() {
        HorizontalLayout heading = new HorizontalLayout();
        heading.add(new H3("Create New Facebook Post"));

        HorizontalLayout form = new HorizontalLayout();
        form.add(facebookPostForm);

        VerticalLayout layout = new VerticalLayout();

        layout.add(heading, form);

        add(layout);
    }
}
