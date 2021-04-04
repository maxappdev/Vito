package com.vito.webapp.backend.posting;

import com.vito.webapp.backend.controllers.FacebookController;
import com.vito.webapp.backend.entities.posts.FacebookPage;
import com.vito.webapp.backend.entities.users.User;
import com.vito.webapp.backend.entities.users.UserSocialData;
import com.vito.webapp.backend.repositories.FacebookPageRepository;
import com.vito.webapp.backend.repositories.UserRepository;
import com.vito.webapp.backend.repositories.UserSocialDataRepository;
import com.vito.webapp.backend.utils.SpringUtils;
import com.vito.webapp.backend.utils.VitoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;

@Service
public class FacebookService {

    @Autowired
    UserSocialDataRepository userSocialDataRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    FacebookPageRepository facebookPageRepository;

    @Value("${spring.social.facebook.appId}")
    String facebookAppId;
    @Value("${spring.social.facebook.appSecret}")
    String facebookSecret;

    public String createAuthorizationURL() {
        FacebookConnectionFactory connectionFactory = new FacebookConnectionFactory(facebookAppId, facebookSecret);
        OAuth2Operations oauthOperations = connectionFactory.getOAuthOperations();
        OAuth2Parameters params = new OAuth2Parameters();
        String baseUrl = SpringUtils.getBaseUrl();
        params.setRedirectUri(baseUrl + FacebookController.SAVE_USER_TOKEN_MAPPING);
        params.setScope("pages_manage_posts, pages_read_engagement, public_profile");
        return oauthOperations.buildAuthorizeUrl(params);
    }

    public void saveUserAccessToken(String code) {
        FacebookConnectionFactory connectionFactory = new FacebookConnectionFactory(facebookAppId, facebookSecret);
        String redirectUri = SpringUtils.getBaseUrl() + FacebookController.SAVE_USER_TOKEN_MAPPING;
        AccessGrant accessGrant = connectionFactory.getOAuthOperations().exchangeForAccess(code, redirectUri, null);
        String accessToken = accessGrant.getAccessToken();
        User authUser = SpringUtils.getAuthUser();
        authUser.addFacebookAccessToken(accessToken, userSocialDataRepository, userRepository);
    }

    public void savePages() { // TODO logging
        User authUser = SpringUtils.getAuthUser();
        Facebook facebook = getFacebookObj();
        Object pages = facebook.fetchObject(authUser.getFacebookUserId() + "/accounts", Object.class);

        if (pages instanceof LinkedHashMap) {
            LinkedHashMap map = (LinkedHashMap) pages;
            Object data = map.get("data");
            if ((data != null) && data instanceof ArrayList) {
                ArrayList list = (ArrayList) data;
                if (!list.isEmpty()) {
                    Object pageData = list.get(0);
                    if ((pageData != null) && pageData instanceof LinkedHashMap) {
                        LinkedHashMap<String, String> pageDataMap = (LinkedHashMap<String, String>) pageData;

                        if (pageDataMap != null) {
                            String accessToken = pageDataMap.get("access_token");
                            String name = pageDataMap.get("name");
                            String id = pageDataMap.get("id");
                            String category = pageDataMap.get("category");

                            FacebookPage facebookPage = new FacebookPage();
                            facebookPage.setAccessToken(accessToken);
                            facebookPage.setName(name);
                            facebookPage.setPageId(id);
                            facebookPage.setCategory(category);

                            authUser.addFacebookPage(facebookPage, facebookPageRepository, userRepository);
                        }
                    }
                }
            }
        }

    }

    public void saveUserId(){ //TODO add errors here, error handling in whole app
        Facebook facebook = getFacebookObj();
        String userId = null;

        if(facebook != null){
            String [] fields = { "id", "email",  "first_name", "last_name" };
            org.springframework.social.facebook.api.User profile = facebook.fetchObject
                    ("me", org.springframework.social.facebook.api.User.class, fields);

            userId = profile.getId();
        }

        if(userId != null){
            User authUser = SpringUtils.getAuthUser();
            authUser.saveFacebookUserId(userId, userSocialDataRepository, userRepository);
        }
    }

    private Facebook getFacebookObj() {
        User authUser = SpringUtils.getAuthUser();
        UserSocialData socialData = authUser.getSocialData();
        Facebook result = null;

        if(socialData != null){
            String accessToken = socialData.getUserAccessTokenFacebook();
            if(VitoUtils.ok(accessToken)){
                result = new FacebookTemplate(accessToken);
            }
        }

        return result;
    }
}
