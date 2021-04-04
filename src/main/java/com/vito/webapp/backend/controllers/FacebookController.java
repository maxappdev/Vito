package com.vito.webapp.backend.controllers;

import com.vito.webapp.backend.posting.FacebookService;
import com.vito.webapp.backend.utils.SpringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class FacebookController {

    public final static String SAVE_USER_TOKEN_MAPPING = "/facebook/auth/token";
    public final static String FACEBOOK_AUTH_MAPPING = "/facebook/auth";

    @Autowired
    FacebookService facebookService;

    @GetMapping(FACEBOOK_AUTH_MAPPING)
    public RedirectView authViaFacebook() {
        String authorizationURL = facebookService.createAuthorizationURL();
        return new RedirectView(authorizationURL);
    }

    @GetMapping(SAVE_USER_TOKEN_MAPPING) //TODO error handling
    public RedirectView saveUserData(@RequestParam("code") String code) {
        facebookService.saveUserAccessToken(code);
        facebookService.saveUserId();
        facebookService.savePages();

        return new RedirectView(SpringUtils.getBaseUrl());
    }
}
