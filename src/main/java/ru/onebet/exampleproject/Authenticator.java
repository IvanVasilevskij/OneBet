package ru.onebet.exampleproject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.onebet.exampleproject.dao.userdao.UserDAOImpl;

import java.util.Arrays;

@Service
public class Authenticator implements UserDetailsService {
    private  PasswordEncoder encoder;
    private  UserDAOImpl daoUser;

    @Autowired
    public Authenticator(PasswordEncoder encoder,
                         UserDAOImpl daoUser) {
        this.encoder = encoder;
        this.daoUser = daoUser;
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            if (daoUser.findClient(username) != null) {
                return new User(username, encoder.encode(daoUser.findClient(username).getPassword()), Arrays.asList(new SimpleGrantedAuthority("ROLE_CLIENT")));
            } else if (daoUser.findAdmin(username) != null) {
                return new User(username, encoder.encode(daoUser.findAdmin(username).getPassword()), Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN")));
            }
        else throw  new UsernameNotFoundException("User doesen't exist");
    }
}
