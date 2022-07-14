package xyz.cglzwz.chatroom.service;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import xyz.cglzwz.chatroom.controller.WsController;
import xyz.cglzwz.chatroom.dao.UserMapper;
import xyz.cglzwz.chatroom.domain.SysRole;
import xyz.cglzwz.chatroom.domain.SysUser;

import java.util.ArrayList;
import java.util.List;


@Service
public class CustomUserService implements UserDetailsService {
    private static final Log log = LogFactory.getLog(CustomUserService.class);

    @Autowired
    private UserMapper userMapper;

    /**
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser user = userMapper.findByUsername(username);
        if (user != null) {
            log.info("username exists");
            log.info("Print: " + user);

            // User rights
            List<SimpleGrantedAuthority> authorities = new ArrayList<>();
            //Permissions for adding users. Just add the user permission to the authorities [this project is fixed to the user role]
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

            return new org.springframework.security.core.userdetails.User(user.getUsername(),
                    user.getPassword(), authorities);
        } else {
            throw new UsernameNotFoundException("admin: " + username + " do not exist!");
        }
    }
}
