package ru.onebet.exampleproject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.onebet.exampleproject.dao.userdao.UserDAOImpl;
import ru.onebet.exampleproject.model.users.Admin;
import ru.onebet.exampleproject.model.users.ClientImpl;

import java.util.Collections;

@Service
public class Authenticator implements UserDetailsService {
    private final UserDAOImpl daoUser;

    @Autowired
    public Authenticator(UserDAOImpl daoUser) {
        this.daoUser = daoUser;
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ClientImpl client = daoUser.findClient(username);
        if (client != null) {
            return new User(username, (client.getPassword()), Collections.singletonList(new SimpleGrantedAuthority("ROLE_CLIENT")));
        } else {
            Admin admin = daoUser.findAdmin(username);
            if (admin != null) {
                if (admin.getLogin().equals("root")) {
                    return new User(username, (admin.getPassword()), Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMINROOT")));
                } else
                    return new User(username, (admin.getPassword()), Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN")));
            } else throw new UsernameNotFoundException("User doesen't exist");
        }
    }
}
