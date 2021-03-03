package com.example.demo.controller;

import com.example.demo.respository.ItemEntryRepository;
import com.example.demo.model.ItemEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins =  "http://localhost:4200")
@RequestMapping("api/")
public class ItemEntryController {

    @Autowired
    ItemEntryRepository itemEntryRepository;

    // Gets all data from lootSheet sorted first by if the entity is marked as hasItem and second by Descending Prio Value
    @GetMapping("/itemEntries")
    public List <ItemEntry> getAllEntries() {
        return itemEntryRepository.getAllEntries();
    }

    // Returns item names with no repeats
    @GetMapping("/uniqueItemNames")
    public List <String> getUniqueItemNames() {
        return itemEntryRepository.getUniqueItemNames();
    }

    @PutMapping("/updateLootSheet")
    public void updateLootsheet(@RequestBody ItemEntry itemEntry){
        itemEntryRepository.updateLootsheet(itemEntry);
    }

    @PutMapping("/updatePrioValue")
    public void updatePrioValue(@RequestBody ItemEntry itemEntry){
        itemEntryRepository.updatePrioValue(itemEntry);
    }

    @GetMapping("/cleanLootSheet")
    public void cleanLootSheet(){
        itemEntryRepository.cleanLootSheet();
    }
}
