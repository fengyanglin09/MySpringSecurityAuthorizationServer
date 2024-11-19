package diy.mqml.sessionsecuritybackend.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;


/**
 *
 * This method creates an instance of DefaultCookieSerializer (a class that implements the CookieSerializer interface)
 * and configures its various properties before returning it.
 *
 * The CookieSerializer object returned will be used by Spring Security or Spring Session (depending on the context)
 * to customize how session cookies are created and managed.
 *
 * */

@Configuration
public class SessionConfig {

    @Bean
    public CookieSerializer cookieSerializer() {
        DefaultCookieSerializer serializer = new DefaultCookieSerializer();
        serializer.setCookieName("SESSIONID");  // Custom session cookie name - By default, Spring uses a cookie named "JSESSIONID", but this configuration changes it to "SESSIONID". This is useful if you want to customize the name of the session cookie.
        serializer.setCookiePath("/");          // Define the cookie path
        serializer.setDomainNamePattern("^.+?\\.(\\w+\\.[a-z]+)$");  // Optional: Set domain pattern - This is an optional regular expression pattern used to specify the domain for which the cookie is valid. In this case, it’s matching a domain pattern that could be any subdomain (e.g., example.com or www.example.com). This would allow the cookie to be sent across different subdomains.
        serializer.setUseHttpOnlyCookie(true);  // HttpOnly for security - When set, the cookie cannot be accessed via JavaScript, which improves security by protecting against cross-site scripting (XSS) attacks.


        /**
         *
         * The SameSite attribute controls whether cookies should be sent with cross-site requests:
         *
         * "Lax" allows cookies to be sent in some cross-site contexts (e.g., GET requests), but blocks them in others (e.g., POST requests from a different site).
         * The other possible values are "Strict" (cookies only sent in same-site requests) and "None" (cookies sent in all cross-site requests, usually requiring the cookie to also be Secure).
         *
         * */
        serializer.setSameSite("Lax");          // SameSite attribute

        serializer.setUseSecureCookie(true);    // Secure cookie for HTTPS - This configures the cookie to be sent only over secure (HTTPS) connections. If the application is accessed over HTTP, the cookie will not be sent,

        serializer.setCookieMaxAge(30*60);

        return serializer;
    }
}
