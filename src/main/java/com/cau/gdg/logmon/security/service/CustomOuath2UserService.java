package com.cau.gdg.logmon.security.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
public class CustomOuath2UserService extends DefaultOAuth2UserService {
    public CustomOuath2UserService() {
        super();
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest)
            throws OAuth2AuthenticationException
    {
        log.debug("===유저 정보 획득 완료===");
        // 여기 회원가입 로직 넣어도 될듯.
        return super.loadUser(userRequest);
    }

    @Override
    public void setAttributesConverter(
            Converter<OAuth2UserRequest, Converter<Map<String, Object>, Map<String, Object>>> attributesConverter) {

        log.debug("=== 이건 보통 뭐에 씀? ");
        super.setAttributesConverter(attributesConverter);
    }
}
