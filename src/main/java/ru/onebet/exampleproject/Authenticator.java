package ru.onebet.exampleproject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.onebet.exampleproject.dao.userdao.UserDAOImpl;

import java.util.Arrays;

@Service
public class Authenticator implements UserDetailsService {
    private final UserDAOImpl daoUser;

    @Autowired
    public Authenticator(UserDAOImpl daoUser) {
        this.daoUser = daoUser;
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            if (daoUser.findClient(username) != null) {
                return new User(username, (daoUser.findClient(username).getPassword()), Arrays.asList(new SimpleGrantedAuthority("ROLE_CLIENT")));
            } else if (daoUser.findAdmin(username).getLogin().equals("root")) {
                return new User(username, (daoUser.findAdmin(username).getPassword()), Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMINROOT")));
            } else if (daoUser.findAdmin(username) != null) {
                return new User(username, (daoUser.findAdmin(username).getPassword()), Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN")));
            }
        else throw  new UsernameNotFoundException("User doesen't exist");
    }
}
