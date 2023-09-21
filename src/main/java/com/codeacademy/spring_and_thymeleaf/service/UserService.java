package com.codeacademy.spring_and_thymeleaf.service;

import com.codeacademy.spring_and_thymeleaf.model.InfoMessage;
import com.codeacademy.spring_and_thymeleaf.model.Role;
import com.codeacademy.spring_and_thymeleaf.model.User;
import com.codeacademy.spring_and_thymeleaf.repository.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import net.bytebuddy.utility.RandomString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.List;

@Service
public class UserService implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(DeviceService.class);
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private JavaMailSender mailSender;

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
//        existingUser.setActive(user.isActive());
        userRepository.save(existingUser);
        infoMessage.setError(false);
        infoMessage.setMessageText("Įrenginys atnaujintas");
        logger.info("User update. User after update: {}", existingUser);
//        }
        return infoMessage;
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

//    public boolean addUser(User user) {
//        User userFromDb = userRepository.findByUsername(user.getUsername());
//
//        if (userFromDb != null) {
//            return false;
//        }
//
////        user.setActive(true);
//        user.setRoles(Collections.singleton(Role.USER));
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//
//        userRepository.save(user);
//
//        return true;
//    }

    public boolean register(User user, String siteURL) throws UnsupportedEncodingException, MessagingException {
        User userFromDb = userRepository.findByUsernameOrEmail(user.getUsername(), user.getEmail());
        if (userFromDb != null) {
            return false;
        }

        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        String randomCode = RandomString.make(64);
        user.setVerificationCode(randomCode);
        user.setVerified(false);
        userRepository.save(user);

        sendVerificationEmail(user, siteURL);

        return true;
    }

    private void sendVerificationEmail(User user, String siteURL)
            throws MessagingException, UnsupportedEncodingException {
        String toAddress = user.getEmail();
        String fromAddress = "mantas.sasnauskas@inbox.lt";
        String senderName = "Mantas";
        String subject = "Please verify your registration";
        String content = "Dear [[name]],<br>"
                + "Please click the link below to verify your registration:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
                + "Thank you,<br>"
                + "Your company name.";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        logger.info("Sending mail from {}", fromAddress);
        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);

        content = content.replace("[[name]]", user.getUsername());
        String verifyURL = siteURL + "/registration/verify?code=" + user.getVerificationCode();

        content = content.replace("[[URL]]", verifyURL);

        helper.setText(content, true);

        mailSender.send(message);

    }

    public boolean verify(String verificationCode) {
        User user = userRepository.findByVerificationCode(verificationCode);

        if (user == null || user.isVerified()) {
            return false;
        } else {
            user.setVerificationCode(null);
            user.setVerified(true);
            userRepository.save(user);

            return true;
        }

    }

}