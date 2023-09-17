package com.codeacademy.spring_and_thymeleaf.service;

import com.codeacademy.spring_and_thymeleaf.model.Device;
import com.codeacademy.spring_and_thymeleaf.model.InfoMessage;
import com.codeacademy.spring_and_thymeleaf.repository.UserRepository;
import com.codeacademy.spring_and_thymeleaf.model.Role;
import com.codeacademy.spring_and_thymeleaf.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class UserService implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(DeviceService.class);
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepo, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> getAllUsers() {
        List<User> users = userRepository.findAll();
        users.remove(0);
        return users;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return user;
    }

    public boolean addUser(User user) {
        User userFromDb = userRepository.findByUsername(user.getUsername());

        if (userFromDb != null) {
            return false;
        }

        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(user);

        return true;
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public InfoMessage updateUser(User user) {
        logger.info("User update. Data for user update: {}", user);
        InfoMessage infoMessage = new InfoMessage();
//        if (!user.getId().equals(userRepository.findById(user.getId()).get(0).getId())) {
//            infoMessage.setError(true);
//            infoMessage.setMessageText("Įrenginys su tokiu įrenginio ID jau egzistuoja.\nAtnaujinimas negalimas.");
//        } else {
        User existingUser = userRepository.findById(user.getId()).get();
        logger.info("User update. Existing user: {}", existingUser);
        existingUser.setUsername(user.getUsername());
//        existingUser.setRoles(user.getRoles());
        existingUser.setActive(user.isActive());
        userRepository.save(existingUser);
        infoMessage.setError(false);
        infoMessage.setMessageText("Įrenginys atnaujintas");
        logger.info("User update. User after update: {}", existingUser);
//        }
        return infoMessage;
    }
}