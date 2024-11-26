package diy.mqml.backend.configs.security.userConfig.azureGraphService;

import com.azure.spring.cloud.autoconfigure.implementation.aad.configuration.properties.AadAuthenticationProperties;
import com.azure.spring.cloud.autoconfigure.implementation.aad.configuration.properties.AadCredentialProperties;
import com.azure.spring.cloud.autoconfigure.implementation.aad.configuration.properties.AadProfileProperties;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AzureWebSecurityService {

    private final AadAuthenticationProperties authenticationProperties;

    public AzureWebSecurityService(AadAuthenticationProperties authenticationProperties) {
        this.authenticationProperties = authenticationProperties;
    }

    public Optional<String> findClientId() {
        return findCredentialProperties()
                .map(AadCredentialProperties::getClientId);
    }

    public Optional<String> findClientSecret() {
        return findCredentialProperties()
                .map(AadCredentialProperties::getClientSecret);
    }

    public Optional<String> findTenantId() {
        return findProfileProperties()
                .map(AadProfileProperties::getTenantId);
    }

    private Optional<AadCredentialProperties> findCredentialProperties() {
        return Optional.ofNullable(authenticationProperties.getCredential());
    }

    public Optional<AadProfileProperties> findProfileProperties() {
        return Optional.ofNullable(authenticationProperties.getProfile());
    }
}
