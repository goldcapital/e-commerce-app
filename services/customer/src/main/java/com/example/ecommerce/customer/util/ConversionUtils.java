package com.example.ecommerce.customer.util;

import com.example.ecommerce.customer.config.AuthProperties;
import com.example.ecommerce.customer.dto.CustomerRequest;
import com.example.ecommerce.customer.request.AuthTokenRequest;
import com.example.ecommerce.customer.response.AuthTokenResponse;
import jakarta.validation.Valid;
import lombok.experimental.UtilityClass;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;

import java.util.List;

@UtilityClass
public class ConversionUtils {
    public static AuthTokenResponse convertResponseToAuthToken(AccessTokenResponse accessToken) {
        return new AuthTokenResponse(accessToken.getToken(), accessToken.getTokenType(), accessToken.getRefreshToken(), accessToken.getExpiresIn(), accessToken.getScope(), 0, accessToken.getSessionState());
    }

    public void updateUserFields(UserRepresentation userRepresentations, CustomerRequest request) {
        if (request.email() != null) {
            userRepresentations.setEmail(request.email());
            userRepresentations.setUsername(request.email());
        }
        if (request.firstname() != null) {
            userRepresentations.setFirstName(request.firstname());
        }
        if (request.lastname() != null) {
            userRepresentations.setLastName(request.lastname());
        }
        if (request.password() != null) {
            var credential = new CredentialRepresentation();
            credential.setValue(request.password());
            credential.setTemporary(Boolean.FALSE);
            credential.setType(CredentialRepresentation.PASSWORD);
            userRepresentations.setCredentials(List.of(credential));
        }

    }

    public static UserRepresentation makeUserRepresentation(CustomerRequest registrationRecord) {
        var user = new UserRepresentation();
        user.setEmailVerified(true);
        user.setEmail(registrationRecord.email());
        user.setEnabled(true);
        user.setUsername(registrationRecord.email());
        user.setLastName(registrationRecord.lastname());
        user.setFirstName(registrationRecord.firstname());

        var credential = new CredentialRepresentation();
        credential.setValue(registrationRecord.password());
        credential.setTemporary(Boolean.FALSE);
        credential.setType(CredentialRepresentation.PASSWORD);

        user.setCredentials(List.of(credential));
        return user;
    }

    public static Keycloak keycloakBuilder(AuthTokenRequest request, AuthProperties properties) {
        return KeycloakBuilder.builder()
                .username(request.name())
                .password(request.password())
                .serverUrl(properties.getServerUrl())
                .realm(properties.getRealm())
                .clientId(properties.getClientId())
                .clientSecret(properties.getClientSecret())
                .grantType(OAuth2Constants.PASSWORD)
                .build();
    }
}
