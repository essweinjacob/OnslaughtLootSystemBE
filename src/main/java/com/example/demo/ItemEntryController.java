package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins =  "http://localhost:4200")
@RequestMapping("api/")
public class ItemEntryController {

    @Autowired
    ItemEntryRepository itemEntryRepository;

    @GetMapping("itemEntries")
    public List <ItemEntry> getEntries() {
        return this.itemEntryRepository.findAll();
    }
}
