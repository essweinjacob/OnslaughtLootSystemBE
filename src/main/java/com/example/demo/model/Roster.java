package com.example.demo.model;

import javax.persistence.*;

@Table(name = "roster")
@Entity
public class Roster {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int charId;

    @Column(name = "charName")
    private String charName;
    @Column(name="charClass")
    private String charClass;
    @Column(name="notes")
    private String notes;
    @Column(name="password")
    private String password;
    @Column(name="perms")
    private String perms;


    private int attendCount;

    public Roster(){}

    public Roster(String charName, String charClass, int attendCount, String notes, String password, String perms) {
        super();
        this.charName = charName;
        this.charClass = charClass;
        this.attendCount = attendCount;
        this.notes = notes;
        this.password = password;
        this.perms = perms;
    }

    public int getCharId() {
        return charId;
    }

    public void setCharId(int charId) {
        this.charId = charId;
    }

    public String getCharName() {
        return charName;
    }

    public void setCharName(String charName) {
        this.charName = charName;
    }

    public String getCharClass() {
        return charClass;
    }

    public void setCharClass(String charClass) {
        this.charClass = charClass;
    }

    public int getAttendCount() {
        return attendCount;
    }

    public void setAttendCount(int attendCount) {
        this.attendCount = attendCount;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPerms() {
        return perms;
    }

    public void setPerms(String perms) {
        this.perms = perms;
    }
}
