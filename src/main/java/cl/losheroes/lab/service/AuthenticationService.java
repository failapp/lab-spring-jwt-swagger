package cl.losheroes.lab.service;

import cl.losheroes.lab.exception.BusinessException;
import cl.losheroes.lab.persistence.dto.CredentialDto;
import cl.losheroes.lab.security.JwtResponse;
import cl.losheroes.lab.security.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AuthenticationService implements UserDetailsService {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Value("${username.mock}")
    private String USERNAME_MOCK;
    @Value("${password.mock}")
    private String PASSWORD_MOCK;

    public UserDetails loadUserByUsernameAndPassword(String username, String password) {

        if (username.equals(USERNAME_MOCK) && password.equals(PASSWORD_MOCK)) {
            final String pwd = "{noop}"+PASSWORD_MOCK;
            UserDetails userDetails = User.builder().username(username).password(pwd).roles("USER").build();
            return userDetails;
        } else {
            throw new BusinessException(HttpStatus.NOT_FOUND, "user not found");
        }
    }


    public JwtResponse getToken(CredentialDto credentialDto) {

        final UserDetails userDetails = loadUserByUsernameAndPassword(credentialDto.getUsername(), credentialDto.getPassword());
        //log.info("[x] userDetails -> {}", userDetails);

        Set<String> authorities = userDetails.getAuthorities().stream().map(e -> e.getAuthority())
                .collect(Collectors.toSet());
        //log.info("[x] authorities -> {}", authorities);

        final String token = jwtTokenUtil.generateToken(userDetails, authorities);
        return new JwtResponse(token);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //UserDetails userDetails = User.builder().username("lab").password("{noop}lab").roles("USER").build();
        UserDetails userDetails = User.builder().username("lab").password("{noop}lab").roles("ADMIN","USER").build();
        return userDetails;
    }

}
