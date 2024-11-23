# Getting Started

### authentication flow
1. The frontend redirects users to the backend for login.
2. The backend uses Spring Security to authenticate the user with Azure Entra ID.
3. After authentication, the backend issues a session or token for the Angular frontend to use in subsequent requests.

### Maven Parent overrides

Due to Maven's design, elements are inherited from the parent POM to the project POM.
While most of the inheritance is fine, it also inherits unwanted elements like `<license>` and `<developers>` from the parent.
To prevent this, the project POM contains empty overrides for these elements.
If you manually switch to a different parent and actually want the inheritance, you need to remove those overrides.

### The main filters used in an OAuth2/OIDC flow are:

OAuth2AuthorizationRequestRedirectFilter (redirect to the auth server).
OAuth2LoginAuthenticationFilter (process the callback).
OidcAuthorizationCodeAuthenticationProvider (handle OIDC-specific logic).

These filters are automatically wired by Spring Security when you use oauth2Login(). 
You can customize them if needed or add new filters for additional behavior. 
