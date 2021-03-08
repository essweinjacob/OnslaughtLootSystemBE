package com.example.demo.respository;

import com.example.demo.model.Roster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RosterRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<Roster> getAllEntities(){
        return jdbcTemplate.query("select charName, charClass from roster;", new RowMapper<Roster>() {
            @Override
            public Roster mapRow(ResultSet rs, int rowNum) throws SQLException {
                Roster r = new Roster();
                r.setCharName(rs.getString(1));
                r.setCharClass(rs.getString(2));
                return r;
            }
        });
    }

    public List<Roster> getHasAttendForEachPlayer() {
        return jdbcTemplate.query("select charName, count(*) from attendance where didAttend = 1 group by charName;", new RowMapper<Roster>() {
            @Override
            public Roster mapRow(ResultSet rs, int rowNum) throws SQLException {
                Roster r = new Roster();
                r.setCharName(rs.getString(1));
                r.setCharClass(rs.getString(2));
                return r;
            }
        });
    }

    public List<String> getUniqueClassNames(){
        List<String> uniqueClassList = new ArrayList<>();
        uniqueClassList.addAll(jdbcTemplate.queryForList("select distinct charClass from roster;", String.class));
        return uniqueClassList;
    }

    public String getNoteByName(String charName) {
        return (String) jdbcTemplate.queryForObject("select notes from roster where charName = ?", new Object[] {charName}, String.class);
    }

    public void updateNote(Roster roster){
        jdbcTemplate.update("update roster set notes = ? where charName = ?", roster.getNotes(), roster.getCharName());
    }

    public int addNewUser(Roster roster){
        String sql = "select count(*) from `roster` where BINARY `charName` = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, roster.getCharName());
        if(count != null && count == 0){
            sql = "insert into roster values(DEFAULT, ?, ?, ?)";
            jdbcTemplate.update(sql, roster.getCharName(), roster.getCharClass(), roster.getNotes());
            return 1;
        }else{
            return -1;
        }
    }

    public boolean removeUser(String user){
        String sql = "select count(*) from `roster` where BINARY `charName` = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, user);
        if(count != null && count > 0){
            sql = "delete from roster where charName = ?";
            jdbcTemplate.update(sql, user);
            sql = "delete from attendance where charName = ?";
            jdbcTemplate.update(sql, user);
            sql = "delete from lootsheet where charName = ?";
            jdbcTemplate.update(sql, user);
            sql = "delete from users where username = ?";
            jdbcTemplate.update(sql, user);
            return true;
        }else{
            return false;
        }
    }
}
