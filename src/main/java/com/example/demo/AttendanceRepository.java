package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AttendanceRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<Attendance> getAllAttendanceEntries(){
                return jdbcTemplate.query("select attendance.charId, charName, charClass, raidDate, didAttend from attendance left join roster on attendance.charID = roster.charId order by roster.charClass;", new RowMapper<Attendance>(){
            @Override
            public Attendance mapRow(ResultSet rs, int rn) throws SQLException {
                Attendance a = new Attendance();
                a.setCharId(rs.getInt(1));
                a.setCharName(rs.getString(2));
                a.setCharClass(rs.getString(3));
                a.setRaidDate(rs.getString(4));
                a.setDidAttend(rs.getBoolean(5));
                return a;
            }
        });
    }

    public List<String> getUniqueRaidDates(){
        List<String> uniqueRaidDatesList = new ArrayList<>();
        uniqueRaidDatesList.addAll(jdbcTemplate.queryForList("select distinct raidDate from attendance order by id desc;", String.class));
        return uniqueRaidDatesList;
    }

    public List<String> getUniquePlayerNames(){
        List<String> uniquePlayerNames = new ArrayList<>();
        uniquePlayerNames.addAll(jdbcTemplate.queryForList("select distinct charName from roster;", String.class));
        return uniquePlayerNames;
    }

    public void updateAttendance(Attendance attendance){
        jdbcTemplate.update("update attendance set didAttend = ? where charId = ? and raidDate = ?", attendance.getDidAttend(), attendance.getCharId(), attendance.getRaidDate());

    }

    public int[] addRaidDate(List<Attendance> attendanceList){
        return jdbcTemplate.batchUpdate("insert into attendance values (DEFAULT, ?, ?, true)",
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setInt(1,attendanceList.get(i).getCharId());
                        ps.setString(2, attendanceList.get(i).getRaidDate());
                    }

                    @Override
                    public int getBatchSize() { return attendanceList.size(); }
                });
    }

    public boolean removeRaidDate(String raidDate){
        Object[] args = new Object[] {raidDate};
        return jdbcTemplate.update("delete from attendance where raidDate = ?", args) == 1;
    }
}
