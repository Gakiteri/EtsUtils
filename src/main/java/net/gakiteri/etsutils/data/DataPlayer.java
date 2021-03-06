package net.gakiteri.etsutils.data;

import net.gakiteri.etsutils.Variables;
import java.util.UUID;

public class DataPlayer {

    private UUID uuid;
    private String name;
    private String state;
    private DataRank rank;
    private boolean pvp;

    /** INITIALISE WITH DEFAULT VALUES **/
    public DataPlayer() {
        uuid = null;
        name = "";
        state = "off";
        rank = new DataRank();
        pvp = Variables.defPlayerPvp;
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


    /** STATE **/
    public void setState(String val) {
        state = val;
    }
    public String getState() {
        return state;
    }


    /** RANK **/
    public void setRank(DataRank val) {
        rank = val;
    }
    public DataRank getRank() {
        return rank;
    }


    /** PVP **/
    public void setPvp(boolean val) {
        pvp = val;
    }
    public boolean getPvp() {
        return pvp;
    }

}