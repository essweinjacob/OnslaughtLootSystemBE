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
        return jdbcTemplate.query("select charName, roster.charId, itemName, prioValue, hasItem from lootsheet left join roster ON lootsheet.charID = roster.charID order by hasItem, prioValue DESC;", new RowMapper<ItemEntry>(){
            @Override
            public ItemEntry mapRow(ResultSet rs, int rn) throws SQLException{
                ItemEntry ie = new ItemEntry();
                ie.setCharName(rs.getString(1));
                ie.setCharId(rs.getInt(2));
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

    public void updateLootsheet(ItemEntry itemEntry){
        jdbcTemplate.update("update lootsheet set hasItem = ? where charId = ? and itemName = ? and prioValue = ? ", itemEntry.getHasItem(), itemEntry.getCharId(), itemEntry.getItemName(), itemEntry.getPrioValue());
    }

    public void updatePrioValue(ItemEntry itemEntry){
        jdbcTemplate.update("update lootsheet set prioValue = ? where charId = ? and itemName = ? and hasItem = ? ", itemEntry.getPrioValue(), itemEntry.getCharId(), itemEntry.getItemName(), itemEntry.getHasItem());
    }

    public void cleanLootSheet(){
        String sql = "delete from lootsheet where hasItem = true";
        jdbcTemplate.update(sql);
    }
}