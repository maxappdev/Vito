package com.vito.webapp.backend.posting;

import com.vito.webapp.backend.controllers.FacebookController;
import com.vito.webapp.backend.entities.users.User;
import com.vito.webapp.backend.entities.users.UserSocialData;
import com.vito.webapp.backend.repositories.UserSocialDataRepository;
import com.vito.webapp.backend.utils.SpringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Service
public class FacebookService {

    @Autowired
    UserSocialDataRepository userSocialDataRepository;

    @Value("${spring.social.facebook.appId}")
    String facebookAppId;
    @Value("${spring.social.facebook.appSecret}")
    String facebookSecret;

    String accessToken;

    public String createAuthorizationURL() {
        FacebookConnectionFactory connectionFactory = new FacebookConnectionFactory(facebookAppId, facebookSecret);
        OAuth2Operations oauthOperations = connectionFactory.getOAuthOperations();
        OAuth2Parameters params = new OAuth2Parameters();
        String baseUrl = SpringUtils.getBaseUrl();
        params.setRedirectUri(baseUrl + FacebookController.SAVE_USER_TOKEN_MAPPING);
        params.setScope("pages_manage_posts, pages_read_engagement");
        return oauthOperations.buildAuthorizeUrl(params);
    }

    public void saveUserAccessToken(String code) {
        FacebookConnectionFactory connectionFactory = new FacebookConnectionFactory(facebookAppId, facebookSecret);
        String redirectUri = SpringUtils.getBaseUrl() + FacebookController.SAVE_USER_TOKEN_MAPPING;
        AccessGrant accessGrant = connectionFactory.getOAuthOperations().exchangeForAccess(code, redirectUri, null);
        accessToken = accessGrant.getAccessToken();
        User authUser = SpringUtils.getAuthUser();
        authUser.addFacebookAccessToken(accessToken, userSocialDataRepository);
    }
}
