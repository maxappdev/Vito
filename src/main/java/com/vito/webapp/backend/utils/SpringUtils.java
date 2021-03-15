package com.vito.webapp.backend.utils;

import com.vaadin.flow.server.VaadinServlet;
import com.vito.webapp.backend.security.MyUserPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class SpringUtils {

    public static <T> T getBeanInFrontend(Class<T> serviceType) {
        return WebApplicationContextUtils
                .getWebApplicationContext(VaadinServlet.getCurrent().getServletContext())
                .getBean(serviceType);
    }

    public static MyUserPrincipal getAuthUser(){
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        if (authentication != null) {
            Object principal = authentication.getPrincipal();
            return principal instanceof MyUserPrincipal ? (MyUserPrincipal) principal : null;
        }
        return null;
    }

}
