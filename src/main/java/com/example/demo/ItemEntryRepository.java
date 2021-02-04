package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ItemEntryRepository{
    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<ItemEntry> getAllEntries(){
        return jdbcTemplate.query("select * from lootsheet order by hasItem, prioValue DESC", new RowMapper<ItemEntry>(){
            @Override
            public ItemEntry mapRow(ResultSet rs, int rn) throws SQLException{
                ItemEntry ie = new ItemEntry();
                ie.setId((rs.getInt(1)));
                ie.setCharName(rs.getString(2));
                ie.setItemName(rs.getString(3));
                ie.setPrioValue(rs.getInt(4));
                ie.setHasItem(rs.getBoolean(5));
                return ie;
            }
        });
    }

    public List<String> getUniqueItemNames(){
        List<String> uniqueItemNameList = new ArrayList<>();
        uniqueItemNameList.addAll(jdbcTemplate.queryForList("select distinct itemName from lootsheet;", String.class));
        return uniqueItemNameList;
    }
}