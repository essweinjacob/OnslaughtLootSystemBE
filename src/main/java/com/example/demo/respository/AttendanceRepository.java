package com.example.demo.respository;

import com.example.demo.model.Attendance;
import com.example.demo.model.AttendanceItemEntry;
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
        return jdbcTemplate.query("select charName, raidDate, didAttend from attendance", new RowMapper<Attendance>(){
            @Override
            public Attendance mapRow(ResultSet rs, int rn) throws SQLException {
                Attendance a = new Attendance();
                a.setCharName(rs.getString(1));
                a.setRaidDate(rs.getString(2));
                a.setDidAttend(rs.getBoolean(3));
                return a;
            }
        });
    }

    public List<AttendanceItemEntry>getAttendanceItemEntries() {
        return jdbcTemplate.query("select charName, count(*) from attendance where didAttend = 1 group by charName", new RowMapper<AttendanceItemEntry>(){
            @Override
            public AttendanceItemEntry mapRow(ResultSet rs, int rn) throws SQLException {
                AttendanceItemEntry a = new AttendanceItemEntry();
                a.setCharName(rs.getString(1));
                a.setRaidsAttended(rs.getInt(2));
                return a;
            }
        });
    }

    public List<String> getUniqueRaidDates(){
        List<String> uniqueRaidDatesList = new ArrayList<>();
        uniqueRaidDatesList.addAll(jdbcTemplate.queryForList("select distinct raidDate from attendance group by raidDate order by max(id) desc;", String.class));
        return uniqueRaidDatesList;
    }

    public List<String> getUniquePlayerNames(){
        List<String> uniquePlayerNames = new ArrayList<>();
        uniquePlayerNames.addAll(jdbcTemplate.queryForList("select distinct charName from attendance;", String.class));
        return uniquePlayerNames;
    }

    public void updateAttendance(Attendance attendance){
        jdbcTemplate.update("update attendance set didAttend = ? where charName = ? and raidDate = ?", attendance.getDidAttend(), attendance.getCharName(), attendance.getRaidDate());

    }

    public int[] addRaidDate(List<Attendance> attendanceList){
        return jdbcTemplate.batchUpdate("insert into attendance values (DEFAULT, ?, ?, true)",
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setString(1,attendanceList.get(i).getCharName());
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

    public int[] addRaidDatesForNewUser(List<Attendance> attendanceList){
        String sql = "insert into attendance values(DEFAULT, ?, ?, false)";
        return jdbcTemplate.batchUpdate(sql,
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setString(1, attendanceList.get(i).getCharName());
                        ps.setString(2, attendanceList.get(i).getRaidDate());
                    }

                    @Override
                    public int getBatchSize() {
                        return attendanceList.size();
                    }
                });
    }
}
