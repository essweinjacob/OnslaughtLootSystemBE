package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.respository.UserRepositoryRoleCheck;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("api/")
public class UserController {
    @Autowired
    private UserRepositoryRoleCheck userRepositoryRoleCheck;

    @PostMapping("/isAdmin")
    public boolean isAdmin(@RequestBody String username){
        username = username.replace("\"username\":\"", "");
        username = username.replaceAll("\"", "").replace("{","").replace("}", "");
        return userRepositoryRoleCheck.isAdmin(username);
    }
}
