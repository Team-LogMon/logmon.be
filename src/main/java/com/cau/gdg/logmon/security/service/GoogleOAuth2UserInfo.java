
package com.cau.gdg.logmon.security.service;

import com.cau.gdg.logmon.security.util.OauthConstants;

import java.util.Map;

public class GoogleOAuth2UserInfo extends OAuth2UserInfo {

    public GoogleOAuth2UserInfo(
            Map<String, Object> attributes
    ) {
        super(attributes);
    }

    @Override
    public String getNickname() {
        return (String) attributes.get(OauthConstants.Google.NAME);
    }

    @Override
    public String getImageUrl() {
        return (String) attributes.get(OauthConstants.Google.PICTURE);
    }

    @Override
    public String getEmail() {
        return (String) attributes.get(OauthConstants.Google.EMAIL);
    }

}
