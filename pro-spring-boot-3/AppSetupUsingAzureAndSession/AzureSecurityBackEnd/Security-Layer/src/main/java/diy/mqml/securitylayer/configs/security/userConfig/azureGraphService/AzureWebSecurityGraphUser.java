package diy.mqml.securitylayer.configs.security.userConfig.azureGraphService;

public record AzureWebSecurityGraphUser(String id,
                                        String lanId,
                                        String personId,
                                        String systemId,
                                        String fullName,
                                        String lastName,
                                        String firstName,
                                        String department,
                                        String jobTitle,
                                        String workSiteState,
                                        String workSiteCity,
                                        String mailLocation,
                                        String emailAddress) {
}
