package org.example.service;

import org.example.model.User;
import org.example.model.dto.LoginUserRequestDTO;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User saveUser(User user){
        if(validatePassword(user.getPassword()) && validateEmail(user.getEmail())
                && validateNames(user.getFirstname()) && validateNames(user.getLastname())){
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return this.userRepository.save(user);
        }

        return null;
    }

    public User loginUser(LoginUserRequestDTO loginUserRequestDTO) {

        User temp_user = this.userRepository.findByEmail(loginUserRequestDTO.getEmail());
        if (temp_user == null)
            return null;
        if (Objects.equals(temp_user.getEmail(), loginUserRequestDTO.getEmail()) &&
                Objects.equals(temp_user.getPassword(), passwordEncoder.encode(loginUserRequestDTO.getPassword())))
        {
            return temp_user;
        }
        return temp_user;
    }

    public boolean validateEmail(String mail) {
        if (mail == null)
            return false;

        List<User> users = this.userRepository.findAll();
        for(User user: users){
            if(Objects.equals(user.getEmail(), mail))
                return false;
        }

        Pattern pattern = Pattern.compile("^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
        Matcher matcher = pattern.matcher(mail);
        return matcher.find();
    }

    public boolean validatePassword(String password) {
        if (password == null)
            return false;
        Pattern pattern = Pattern.compile("^(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!])(.{8,})$");
        Matcher matcher = pattern.matcher(password);
        return matcher.find();

    }

    public boolean validateNames(String name) {
        if (name == null)
            return false;
        Pattern pattern = Pattern.compile("^[A-Z][a-z]+$");
        Matcher matcher = pattern.matcher(name);
        return matcher.find();

    }
}