package com.example.demo.respository;

import com.example.demo.model.User;
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

    public String getPassword(String username){
        String sql = "select count(*) from users where username = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, username);
        if(count != null && count > 0){
            sql = "select password from users where username = ?";
            return jdbcTemplate.queryForObject(sql, new String[]{username}, String.class);
        }else{
            return null;
        }
    }

    public void changePassword(String username, String password){
        String sql = "update users set password = ? where username = ?";
        jdbcTemplate.update(sql, password, username);
    }
}
