package com.androidlearing.getworlddata;

public class WorldData {
    private int id;
    private String countryname;
    private String confirmed;
    private String suspected;
    private String dead;
    private String healed;
    private String lastUpdateTime;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCountryname() {
        return countryname;
    }

    public void setCountryname(String countryname) {
        this.countryname = countryname;
    }

    public String getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(String confirmed) {
        this.confirmed = confirmed;
    }

    public String getSuspected() {
        return suspected;
    }

    public void setSuspected(String suspected) {
        this.suspected = suspected;
    }

    public String getDead() {
        return dead;
    }

    public void setDead(String dead) {
        this.dead = dead;
    }

    public String getHealed() {
        return healed;
    }

    public void setHealed(String healed) {
        this.healed = healed;
    }

    public String getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }
}
