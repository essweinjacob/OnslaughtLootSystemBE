package com.example.demo;

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
        return jdbcTemplate.query("select roster.charId, charName, charClass, count(*) from roster join attendance where roster.charId = attendance.charId and didAttend = 1 group by charName;", new RowMapper<Roster>() {
            @Override
            public Roster mapRow(ResultSet rs, int rowNum) throws SQLException {
                Roster r = new Roster();
                r.setCharId(rs.getInt(1));
                r.setCharName(rs.getString(2));
                r.setCharClass(rs.getString(3));
                r.setAttendCount(rs.getInt(4));
                return r;
            }
        });
    }

    public List<String> getUniqueClassNames(){
        List<String> uniqueClassList = new ArrayList<>();
        uniqueClassList.addAll(jdbcTemplate.queryForList("select distinct charClass from roster;", String.class));
        return uniqueClassList;
    }
}
