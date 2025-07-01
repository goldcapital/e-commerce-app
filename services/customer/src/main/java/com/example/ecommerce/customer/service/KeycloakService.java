package com.example.ecommerce.customer.service;

import com.example.ecommerce.customer.config.AuthProperties;
import com.example.ecommerce.customer.dto.CustomerRequest;
import com.example.ecommerce.customer.request.AuthTokenRequest;
import com.example.ecommerce.customer.response.AuthTokenResponse;
import com.example.ecommerce.exp.CustomerNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;
import uz.isd.commons.result.CommonResultData;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.ProcessingException;

import static com.example.ecommerce.customer.config.ErrorMessage.*;
import static com.example.ecommerce.customer.util.ConversionUtils.*;
import static java.lang.String.format;

@Slf4j
@Service
@RequiredArgsConstructor
public class KeycloakService {
    private final Keycloak keycloak;
    private final AuthProperties authProperties;


    public String creatKeycloakUser(CustomerRequest request) {
        try {
            var response = mainResource().create(makeUserRepresentation(request));

            if (response.getStatus() == 409) {
                throw new BadRequestException(format(KEYCLOAK_RESPONSE_STATUS_409, request.email()));
            }
            if (response.getStatus() != 201 && response.getStatus() != 204) {
                throw new RuntimeException(format(KEYCLOAK_RESPONSE_STATUS_201_AND_204, response.getStatus(), request.email()));
            }
            return getKeycloakUser(request.email()).getId();
        } catch (ProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateKeycloakUser(CustomerRequest request) {
        var user = getKeycloakUser(request.email());
        updateUserFields(user, request);
        try {
            mainResource().get(user.getId()).update(user);
        } catch (RuntimeException ex) {
            log.warn("Error while updating user in Keycloak", ex);
            throw new BadRequestException("Failed to update user in Keycloak: " + ex.getMessage());
        }
    }

    private UserRepresentation getKeycloakUser(String username) {
        var users = mainResource().search(username, true);
        if (users == null || users.isEmpty()) {

            log.warn(USERNAME_NOT_FOUND, username);
            throw new CustomerNotFoundException(format(USERNAME_NOT_FOUND, username));
        }
        if (users.size() > 1) {
            log.warn("More than one Keycloak user found with username/username: {}", username);
        }
        return users.get(0);

    }

    public CommonResultData<AuthTokenResponse> getToken(AuthTokenRequest request) {
        try (var keycloakForToken = keycloakBuilder(request, authProperties)) {
            var token = keycloakForToken.tokenManager().getAccessToken();

            log.info("RESPONSE from generateToken() -> SUCCESS");
            log.info("token life time -> {}", token.getExpiresIn());
            return new CommonResultData<>(convertResponseToAuthToken(token));
        } catch (Exception ex) {
            log.warn("Error occurred while generatingToken() -> {}", ex.getMessage());
            throw new BadRequestException(ex.getMessage());
        }
    }


    private UsersResource mainResource() {
        return keycloak.realm(authProperties.getRealm()).users();
    }
}
