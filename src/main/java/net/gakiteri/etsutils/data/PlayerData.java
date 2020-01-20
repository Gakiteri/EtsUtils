package net.gakiteri.etsutils.data;

import java.util.UUID;

public class PlayerData {

    private UUID uuid;
    private String name;
    private boolean online;
    private String rank;
    private int balance;
    private boolean pvp;

    /** INITIALISE WITH DEFAULT VALUES **/
    public PlayerData() {
        uuid = null;
        name = "";
        online = false;
        rank = "";
        balance = 0;
        pvp = false;
    }

    /** UUID **/
    public void setUuid(UUID val) {
        uuid = val;
    }
    public UUID getUuid() {
        return uuid;
    }


    /** NAME **/
    public void setName(String val) {
        name = val;
    }
    public String getName() {
        return name;
    }


    /** ONLINE **/
    public void setOnline(boolean val) {
        online = val;
    }
    public boolean getOnline() {
        return online;
    }


    /** RANK **/
    public void setRank(String val) {
        rank = val;
    }
    public String getRank() {
        return rank;
    }


    /** BALANCE **/
    public void setBalance(int val) {
        balance = val;
    }
    public int getBalance() {
        return balance;
    }


    /** PVP **/
    public void setPvp(boolean val) {
        pvp = val;
    }
    public boolean getPvp() {
        return pvp;
    }

}
