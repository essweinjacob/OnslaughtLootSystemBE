package com.example.demo.respository;

import com.example.demo.model.ItemEntry;
import org.springframework.beans.factory.annotation.Autowired;
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
        return jdbcTemplate.query("select charName, itemName, prioValue, hasItem from lootsheet order by hasItem, prioValue DESC;", new RowMapper<ItemEntry>(){
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

    public void updateLootsheet(ItemEntry itemEntry){
        jdbcTemplate.update("update lootsheet set hasItem = ? where charName = ? and itemName = ? and prioValue = ? ", itemEntry.getHasItem(), itemEntry.getCharName(), itemEntry.getItemName(), itemEntry.getPrioValue());
    }

    public void updatePrioValue(ItemEntry itemEntry){
        jdbcTemplate.update("update lootsheet set prioValue = ? where charName = ? and itemName = ? and hasItem = ? ", itemEntry.getPrioValue(), itemEntry.getCharName(), itemEntry.getItemName(), itemEntry.getHasItem());
    }

    public void cleanLootSheet(){
        String sql = "delete from lootsheet where hasItem = true";
        jdbcTemplate.update(sql);
    }
}