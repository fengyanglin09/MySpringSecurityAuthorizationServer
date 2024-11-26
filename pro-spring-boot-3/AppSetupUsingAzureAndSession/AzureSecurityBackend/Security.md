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


### authentication flow

1. Validating Cookies
   Session-Based Authentication
   How It Works:

When a user logs in, the backend creates a session and stores it on the server (in memory, database, or a distributed session store like Redis).
The backend sends a session ID to the client as a cookie (e.g., JSESSIONID).
On subsequent requests, the browser includes this session cookie in the headers.
http
Copy code
Cookie: JSESSIONID=ABC123
Validation:

The backend looks up the session ID in the session store to retrieve user information (e.g., user ID, roles, etc.).
If the session is found and valid, the user is authenticated.
If the session is expired or invalid, the server denies the request.
Handling Multiple Users:

Each user gets a unique session ID stored in their browser. The backend isolates sessions based on the session ID in the cookie.
Requests are stateless because the session store is external and shared among all requests.
2. Validating Authorization Tokens
   Token-Based Authentication (e.g., JWT)
   How It Works:

When a user logs in, the backend generates a JSON Web Token (JWT) containing user information (e.g., user ID, roles) and signs it with a secret key.
The token is sent to the client and stored (e.g., in a cookie or local storage).
On subsequent requests, the client includes the token in the Authorization header.
http
Copy code
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR...
Validation:

The backend decodes and verifies the JWT using the secret key.
If the signature is valid and the token has not expired, the user is authenticated.
If the token is invalid or expired, the request is rejected.
Handling Multiple Users:

Each user has their own unique token.
The backend processes each request independently by verifying the token in the Authorization header.
3. Handling Concurrent Requests
   Modern web backends are designed to handle multiple simultaneous requests efficiently:

Concurrency Management:
Each request is processed independently because HTTP is stateless.
For session-based authentication, the session store (e.g., Redis, database) is thread-safe and supports concurrent access.
For token-based authentication, JWT validation is stateless and does not require server-side storage, making it highly scalable.
Scenarios with Multiple Users:
User A's request contains their session ID or token.
User B's request contains their session ID or token.
The backend validates each request independently, isolating the users' credentials and data.
4. Security Measures to Handle Credentials
   Cookie-Based Security:

Use HttpOnly cookies to prevent client-side scripts from accessing session cookies.
Use Secure cookies to ensure cookies are only sent over HTTPS.
Use SameSite=None (with Secure) for cross-origin requests.
Token-Based Security:

Tokens should be signed and optionally encrypted.
Use a short expiration time for tokens and refresh tokens to extend sessions securely.
Session Management:

Implement idle timeout and session expiration to reduce the risk of misuse.
Use a distributed session store for scalability in multi-node setups.
Rate Limiting:

Prevent abuse by limiting the number of requests a user can make in a given time window.
User-Specific Scopes and Roles:

Use roles or scopes in tokens or session data to ensure users only have access to permitted resources.
Example: How It Works in Practice
User A and User B send requests at the same time.
Backend Workflow:
Request from User A contains Cookie: JSESSIONID=1234.
Request from User B contains Authorization: Bearer abcdef.
The backend validates each independently:
For User A: Look up session ID 1234 in the session store.
For User B: Decode and verify the JWT.
If both are valid, the server processes their requests concurrently and responds.
Summary
The backend validates credentials based on session IDs or tokens.
For multiple users, each request is handled independently, ensuring isolation.
Proper concurrency handling and stateless authentication mechanisms (like JWT) make backends scalable and reliable.
Security measures ensure credentials are protected from misuse.
