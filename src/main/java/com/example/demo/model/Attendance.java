package com.example.demo.model;

import javax.persistence.*;

@Table(name="attendance")
@Entity
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="charId")
    private int charId;
    @Column(name="charName")
    private String charName;
    @Column(name="charClass")
    private String charClass;
    @Column(name="raidDate")
    private String raidDate;
    @Column(name="didAttend")
    private boolean didAttend;

    public Attendance() {}

    public Attendance(int charId, String charName, String charClass, String raidDate, boolean didAttend){
        super();
        this.charId = charId;
        this.charName = charName;
        this.charClass = charClass;
        this.raidDate = raidDate;
        this.didAttend = didAttend;
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

    public String getRaidDate() {
        return raidDate;
    }

    public void setRaidDate(String raidDate) {
        this.raidDate = raidDate;
    }

    public boolean getDidAttend() {
        return didAttend;
    }

    public void setDidAttend(boolean didAttend) {
        this.didAttend = didAttend;
    }

    public int getCharId() {
        return charId;
    }

    public void setCharId(int charId) {
        this.charId = charId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
