package com.example.demo.respository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryRoleCheck {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public boolean isAdmin(String username){
        String sql = "select count(*) from users where username = ? and perms = 'admin'";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, username);
        if(count == 1){
            return true;
        }else{
            return false;
        }
    }
}
