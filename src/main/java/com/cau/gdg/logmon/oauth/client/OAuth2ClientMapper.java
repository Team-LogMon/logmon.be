package com.cau.gdg.logmon.oauth.client;

import com.cau.gdg.logmon.oauth.OAuth2Client;
import com.cau.gdg.logmon.oauth.OAuth2Provider;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class OAuth2ClientMapper {

    private Map<OAuth2Provider, OAuth2Client> clientMap;

    public OAuth2ClientMapper(List<OAuth2Client> clients) {
        clientMap = clients.stream()
                .collect(Collectors.toMap(OAuth2Client::getProvider, Function.identity()));
    }

    public OAuth2Client getClient(OAuth2Provider provider) {
        return clientMap.get(provider);
    }
}

