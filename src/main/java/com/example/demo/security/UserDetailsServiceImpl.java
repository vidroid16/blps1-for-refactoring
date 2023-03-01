package com.example.demo.security;

import com.example.demo.DataBase.UsersDB.User;
import com.example.demo.DataBase.UsersDB.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Класс для представления пользователя {@link User} в контексте Spring Security {@link SecurityUser}
 * @author Шаля Андрей
 * @version 2.0
 */
@Service("userDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsersRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UsersRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Функция ищет пользователя в БД по нику и создает пользователя {@link SecurityUser}
     * @param username
     * @return пользователя {@link SecurityUser}
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByLogin(username).orElseThrow(() ->
                new UsernameNotFoundException("User doesn't exists"));
        return SecurityUser.fromUser(user);
    }
}
