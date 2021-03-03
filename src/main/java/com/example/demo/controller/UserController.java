package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.respository.UserRepositoryRoleCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.UnsupportedEncodingException;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("api/")
public class UserController {
    @Autowired
    private UserRepositoryRoleCheck userRepositoryRoleCheck;

    @Autowired
    PasswordEncoder bcrpytEncoder;

    @PostMapping("/isAdmin")
    public boolean isAdmin(@RequestBody String username){
        username = username.replace("\"username\":\"", "");
        username = username.replaceAll("\"", "").replace("{","").replace("}", "");
        return userRepositoryRoleCheck.isAdmin(username);
    }

    @PostMapping("/changePassword")
    public boolean changePassword(@RequestBody String changePasswordString){
        String[] data = changePasswordString.split(",");
        String username = data[0];
        username = username.replace("\"username\":\"", "");
        username = username.replaceAll("\"", "").replace("{","").replace("}", "");

        String oldPassword = data[1];
        oldPassword = oldPassword.replace("\"oldPassword\":\"", "");
        oldPassword = oldPassword.replaceAll("\"", "").replace("{","").replace("}", "");

        String password = data[2];
        password = password.replace("\"password\":\"", "");
        password = password.replaceAll("\"", "").replace("{","").replace("}", "");

        if(bcrpytEncoder.matches(oldPassword, userRepositoryRoleCheck.getPassword(username))){
            String encryptedNewPassword = (bcrpytEncoder.encode(password));
            userRepositoryRoleCheck.changePassword(username, encryptedNewPassword);
            return true;
        }else{
            return false;
        }
    }

    @PutMapping("/addNewUser")
    public boolean addNewUser(@RequestBody User user){
        if(userRepositoryRoleCheck.addNewUser(user)){
            return true;
        }
        return false;
    }
}
