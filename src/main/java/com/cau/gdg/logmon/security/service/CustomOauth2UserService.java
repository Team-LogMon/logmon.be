package com.cau.gdg.logmon.security.service;

import com.cau.gdg.logmon.app.user.User;
import com.cau.gdg.logmon.app.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import static com.cau.gdg.logmon.app.user.User.SocialType;
import static com.cau.gdg.logmon.app.user.User.of;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOauth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;


    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest)
            throws OAuth2AuthenticationException {
        log.trace("===유저 정보 획득 완료===");
        // 1. social Type 획득
        ClientRegistration clientRegistration = userRequest.getClientRegistration();
        SocialType socialType = getSocialType(clientRegistration.getRegistrationId());
        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName(); // OAuth2 로그인 시 키(PK)가 되는 값


        // 2. 유저 정보 추출
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);
        Map<String, Object> attributes = oAuth2User.getAttributes();

        // 3. SocialType 에 맞게끔 유저 정보 추출
        OAuth2UserInfo oAuth2UserInfo = getOauth2UserInfo(socialType, attributes);
        String email = oAuth2UserInfo.getEmail();

        // 4. 유저 존재하는지 확인하기
        Optional<User> optionalUser = userRepository.findByEmail(email);
        User user = optionalUser.orElseGet(() -> register(oAuth2UserInfo));

        // 4-2. 유저 통과 시키기
        return new CustomOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getRoles().getKey())), // 역할
                attributes, // 사용자 정보
                userNameAttributeName, // 사용자 이름 식별 키
                user
        );
    }

    private User register(OAuth2UserInfo oAuth2UserInfo) {
        log.trace("유저 회원 가입 시작 = {}", oAuth2UserInfo.getEmail());
        User user = of(
                oAuth2UserInfo.getNickname(),
                oAuth2UserInfo.getEmail(),
                oAuth2UserInfo.getSocialType(),
                oAuth2UserInfo.getImageUrl()
        );

        userRepository.save(user);
        return user;
    }

    private OAuth2UserInfo getOauth2UserInfo(SocialType socialType, Map<String, Object> attributes) {
        return new GoogleOAuth2UserInfo(attributes);
    }

    private SocialType getSocialType(String registrationId) {
        return SocialType.GOOGLE;
    }
}
