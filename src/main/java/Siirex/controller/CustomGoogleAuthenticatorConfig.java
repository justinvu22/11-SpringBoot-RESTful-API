package JustinVu.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.ICredentialRepository;

import JustinVu.User.UserRepository;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class CustomGoogleAuthenticatorConfig {

    private final UserRepository userCredentialService;

    @Bean
    public GoogleAuthenticator gAuth() {
        GoogleAuthenticator googleAuthenticator = new GoogleAuthenticator();
        googleAuthenticator.setCredentialRepository((ICredentialRepository) userCredentialService);
        return googleAuthenticator;
    }
}
