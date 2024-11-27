package diy.mqml.securitylayer.configs.security.userConfig.azureGraphService;

import com.azure.identity.OnBehalfOfCredential;
import com.azure.identity.OnBehalfOfCredentialBuilder;
import com.microsoft.graph.serviceclient.GraphServiceClient;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

@Service
public class AzureWebSecurityGraphService {

    private final AzureWebSecurityService azureService;

    public AzureWebSecurityGraphService(AzureWebSecurityService azureService) {
        this.azureService = azureService;
    }

    public AzureGraphClient getGraphClient(OidcUser oidcUser) {
        return new AzureGraphClient(getGraphServiceClient(oidcUser));
    }

    private GraphServiceClient getGraphServiceClient(OidcUser oidcUser) {
        OnBehalfOfCredential onBehalfOfCredential = new OnBehalfOfCredentialBuilder()
                .clientId(azureService.findClientId().orElseThrow(() -> new IllegalStateException("Azure Client ID not found")))
                .clientSecret(azureService.findClientSecret().orElseThrow(() -> new IllegalStateException("Azure Client Secret not found")))
                .tenantId(azureService.findTenantId().orElseThrow(() -> new IllegalStateException("Azure Tenant ID not found")))
                .userAssertion(oidcUser.getIdToken().getTokenValue())
                .build();
        return new GraphServiceClient(onBehalfOfCredential, "User.Read");
    }
}
