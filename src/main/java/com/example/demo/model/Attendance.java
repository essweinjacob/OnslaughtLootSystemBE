package com.example.demo.model;

import javax.persistence.*;

@Table(name="attendance")
@Entity
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="charName")
    private String charName;
    @Column(name="raidDate")
    private String raidDate;
    @Column(name="didAttend")
    private boolean didAttend;

    public Attendance() {}

    public Attendance(String charName, String raidDate, boolean didAttend){
        super();
        this.charName = charName;
        this.raidDate = raidDate;
        this.didAttend = didAttend;
    }


    public String getCharName() {
        return charName;
    }

    public void setCharName(String charName) {
        this.charName = charName;
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
