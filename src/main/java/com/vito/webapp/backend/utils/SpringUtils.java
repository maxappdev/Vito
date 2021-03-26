package com.vito.webapp.backend.utils;

import com.vaadin.flow.server.VaadinServlet;
import com.vito.webapp.backend.entities.users.User;
import com.vito.webapp.backend.security.MyUserPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public class SpringUtils {

    public static <T> T getBeanInFrontend(Class<T> serviceType) {
        return WebApplicationContextUtils
                .getWebApplicationContext(VaadinServlet.getCurrent().getServletContext())
                .getBean(serviceType);
    }

    public static User getAuthUser(){
        User result = null;
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        if (authentication != null) {
            Object principal = authentication.getPrincipal();
            if(principal instanceof MyUserPrincipal){
                result = ((MyUserPrincipal) principal).getUser();
            }
        }
        return result;
    }

    public static String getBaseUrl(){
        return ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
    }

}
