package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
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
public class ItemEntryRepository{
    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<ItemEntry> getAllEntries(){
        return jdbcTemplate.query("select charName, itemName, prioValue, hasItem from lootsheet left join roster ON lootsheet.charID = roster.charID order by hasItem, prioValue DESC;", new RowMapper<ItemEntry>(){
            @Override
            public ItemEntry mapRow(ResultSet rs, int rn) throws SQLException{
                ItemEntry ie = new ItemEntry();
                ie.setCharName(rs.getString(1));
                ie.setItemName(rs.getString(2));
                ie.setPrioValue(rs.getInt(3));
                ie.setHasItem(rs.getBoolean(4));
                return ie;
            }
        });
    }

    public List<String> getUniqueItemNames(){
        List<String> uniqueItemNameList = new ArrayList<>();
        uniqueItemNameList.addAll(jdbcTemplate.queryForList("select distinct itemName from lootsheet;", String.class));
        return uniqueItemNameList;
    }

    public int[] updateLootsheet(List<ItemEntry> itemEntryList){
        return this.jdbcTemplate.batchUpdate(
                "update lootsheet set hasItem = ? where charId = ? and itemName = ? and prioValue = ?",
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setBoolean(1, itemEntryList.get(i).getHasItem());
                        ps.setInt(2, itemEntryList.get(i).getCharId());
                        ps.setString(3, itemEntryList.get(i).getItemName());
                        ps.setInt(4, itemEntryList.get(i).getPrioValue());
                    }

                    @Override
                    public int getBatchSize() {
                        return itemEntryList.size();
                    }
                });
    }
}