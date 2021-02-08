package com.example.demo;

import javax.persistence.*;


@Table(name ="lootsheet")
@Entity
public class ItemEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="charName")
    private String charName;
    @Column(name="charId")
    private int charId;
    @Column(name="itemName")
    private String itemName;
    @Column(name="prioValue")
    private int prioValue;
    @Column(name="hasItem")
    private boolean hasItem;

    public ItemEntry(){}

    public ItemEntry(String charName, String itemName, int prioValue, boolean hasItem){
        super();
        this.charName = charName;
        this.itemName = itemName;
        this.prioValue = prioValue;
        this.hasItem = hasItem;
    }


    public String getCharName() {
        return charName;
    }

    public void setCharName(String charName) {
        this.charName = charName;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getPrioValue() {
        return prioValue;
    }

    public void setPrioValue(int prioValue) {
        this.prioValue = prioValue;
    }

    public boolean getHasItem() {
        return hasItem;
    }

    public void setHasItem(boolean hasItem) {
        this.hasItem = hasItem;
    }

    public int getCharId() {
        return charId;
    }

    public void setCharId(int charId) {
        this.charId = charId;
    }
}
