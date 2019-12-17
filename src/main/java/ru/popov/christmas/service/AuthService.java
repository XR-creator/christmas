package ru.popov.christmas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import ru.popov.christmas.model.UserGoogle;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

@Service
public class AuthService extends AbstractService {

    @Autowired
    private OAuth2AuthorizedClientService authorizedClientService;

    public UserGoogle getLoginInfo(OAuth2AuthenticationToken authentication) {
        OAuth2AuthorizedClient client = authorizedClientService.loadAuthorizedClient(authentication.getAuthorizedClientRegistrationId(), authentication.getName());

        String userInfoEndpointUri = client.getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUri();

        if (!StringUtils.isEmpty(userInfoEndpointUri)) {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + client.getAccessToken()
                    .getTokenValue());

            HttpEntity<String> entity = new HttpEntity<String>("", headers);

            ResponseEntity<Map> response = restTemplate.exchange(userInfoEndpointUri, HttpMethod.GET, entity, Map.class);
            Map userAttributes = response.getBody();

            URI picture = null;
            if (userAttributes.get("picture") != null) {
                try {
                    picture = new URI(userAttributes.get("picture").toString());
                } catch (URISyntaxException e) {}
            }
            UserGoogle result = new UserGoogle(
                    userAttributes.get("sub") != null ? userAttributes.get("sub").toString() : null,
                    userAttributes.get("name") != null ? userAttributes.get("name").toString() : null,
                    userAttributes.get("given_name") != null ? userAttributes.get("given_name").toString() : null,
                    userAttributes.get("family_name") != null ? userAttributes.get("family_name").toString() : null,
                    picture,
                    userAttributes.get("email") != null ? userAttributes.get("email").toString() : null,
                    userAttributes.get("email_verified") != null ? Boolean.parseBoolean(userAttributes.get("email_verified").toString()) : false,
                    userAttributes.get("locale") != null ? userAttributes.get("locale").toString() : null);

            return result;
        }

        return null;
    }
}
