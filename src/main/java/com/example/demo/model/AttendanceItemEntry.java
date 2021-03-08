package com.example.demo.model;

import javax.persistence.*;

@Table(name = "attendance")
@Entity
public class AttendanceItemEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "charName")
    private String charName;

    private int raidsAttended;

    public AttendanceItemEntry(){}
    public AttendanceItemEntry(String charName, int raidsAttended) {
        super();
        this.charName = charName;
        this.raidsAttended = raidsAttended;
    }

    public String getCharName() {
        return charName;
    }

    public void setCharName(String charName) {
        this.charName = charName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRaidsAttended() {
        return raidsAttended;
    }

    public void setRaidsAttended(int raidsAttended) {
        this.raidsAttended = raidsAttended;
    }
}
