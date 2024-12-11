package com.example.todo_oath2.service;

import com.example.todo_oath2.model.Role;
import com.example.todo_oath2.model.User;
import com.example.todo_oath2.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SocialAppService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(SocialAppService.class);

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);
        logger.info("User from google: {}", oAuth2User);

        String email = oAuth2User.getAttribute("email");
        Optional<User> optUser = userRepository.findByEmail(email);
        User result = optUser.orElseGet(() -> {
            User user = new User();
            user.setEmail(email);
            user.setRole(Role.USER);
            User user1 = userRepository.save(user);
            logger.info("Create new User:{}", user1);
            return user1;
        });
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(Role.ADMIN.name()));

        logger.info("User:{} with role{} authenticated", email, result.getRole().name());
        return new DefaultOAuth2User(authorities, oAuth2User.getAttributes(), "email");
    }



}
