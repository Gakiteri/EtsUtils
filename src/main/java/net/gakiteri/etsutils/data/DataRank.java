package net.gakiteri.etsutils.data;

import net.gakiteri.etsutils.Variables;

public class DataRank {

    private int level;
    private String name;
    private String display;

    /** INITIALISE WITH DEFAULT VALUES **/
    public DataRank() {
        level = Variables.defPlayerRankLevel;
        name = Variables.defPlayerRank;
        display = name;
    }


    /** LEVEL **/
    public void setLevel(int val) {
        level = val;
    }
    public int getLevel() {
        return level;
    }


    /** NAME **/
    public void setName(String val) {
        name = val;
    }
    public String getName() {
        return name;
    }


    /** DISPLAY **/
    public void setDisplay(String val) {
        display = val;
    }
    public String getDisplay() {
        return display;
    }

}
