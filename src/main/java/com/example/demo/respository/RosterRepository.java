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
        return jdbcTemplate.query("select roster.charId, charName, charClass, perms, count(*) from roster join attendance where roster.charId = attendance.charId and didAttend = 1 group by charName;", new RowMapper<Roster>() {
            @Override
            public Roster mapRow(ResultSet rs, int rowNum) throws SQLException {
                Roster r = new Roster();
                r.setCharId(rs.getInt(1));
                r.setCharName(rs.getString(2));
                r.setCharClass(rs.getString(3));
                r.setPerms((rs.getString(4)));
                r.setAttendCount(rs.getInt(5));
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

    public boolean loginAuth(Roster roster){
        String sql = "select count(*) from `roster` where BINARY `charName` = ? and BINARY `password` = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, roster.getCharName(), roster.getPassword());
        if(count != null && count > 0){
            return true;
        }else{
            return false;
        }
    }

    public boolean roleAuth(Roster roster){
        String sql = "select count(*) from `roster` where BINARY `charName` = ? and perms = 'admin'";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, roster.getCharName());
        if(count != null && count > 0){
            return true;
        }else{
            return false;
        }
    }

    public int addNewUser(Roster roster){
        String sql = "select count(*) from `roster` where BINARY `charName` = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, roster.getCharName());
        if(count != null && count == 0){
            sql = "insert into roster values(DEFAULT, ?, ?, ?, ?, ?, ?)";
            jdbcTemplate.update(sql, roster.getCharName(), roster.getCharClass(), roster.getPerms(), roster.getPassword(), roster.getNotes(), roster.getRole());
            sql = "select charId from roster where charName = ?";
            return jdbcTemplate.queryForObject(sql, new Object[]{roster.getCharName()}, Integer.class);
        }else{
            return -1;
        }
    }

    public boolean removeUser(String user){
        String sql = "select count(*) from `roster` where BINARY `charName` = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, user);
        if(count != null && count > 0){
            sql = "select charId from roster where charName = ?";
            int id = jdbcTemplate.queryForObject(sql, new Object[]{user}, Integer.class);
            sql = "delete from roster where charId = ?";
            jdbcTemplate.update(sql, id);
            sql = "delete from attendance where charId = ?";
            jdbcTemplate.update(sql, id);
            sql = "delete from lootsheet where charId = ?";
            jdbcTemplate.update(sql, id);
            return true;
        }else{
            return false;
        }
    }

    public boolean changePassword(Roster roster){
        String sql = "select count(*) from `roster` where BINARY `charName` = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, roster.getCharName());
        if(count != null && count > 0){
            sql = "update roster set password = ? where charName = ?";
            jdbcTemplate.update(sql, roster.getPassword(), roster.getCharName());
            return true;
        }else{
            return false;
        }
    }
}
