package com.myclass.service;

import com.myclass.entity.User;
import com.myclass.exceptions.UsernameAlreadyExistsException;
import com.myclass.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository  userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }
    public User saveUser(User user){
        try {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            //        username has to be unique
            //        Make sure that password and confirmPassword match

            user.setUsername(user.getUsername());
            //        We don't persist or show the confirmPassword
            user.setConfirmPassword("");
            return userRepository.save(user);


        }catch (Exception e){
            throw new UsernameAlreadyExistsException("User '"+user.getUsername()+"' already exists ");
        }

    }

}
