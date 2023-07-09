package com.example.demo;    

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import com.example.data.UserWeb;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service    
public class UserWebService   
{    
    @Autowired    
    private UserWebRepository userRepository;

    public List<UserWeb> getAllUsers()  
    {    
        List<UserWeb>userRecords = new ArrayList<>();    
        userRepository.findAll().forEach(userRecords::add);    
        return userRecords;    
    }

    public void addUser(UserWeb users)  
    {    
        userRepository.save(users);    
    }

    public Optional<UserWeb> getUser(int id) {
        return userRepository.findById(id);
    }

    public List<UserWeb> findByUsername(String chars){
        return userRepository.findByUsername(chars);
    }


    

}    