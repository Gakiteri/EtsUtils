package net.gakiteri.etsutils.data;

public class DataRank {

    private String name;
    private String display;

    /** INITIALISE WITH DEFAULT VALUES **/
    public DataRank() {
        name = "";
        display = name;
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
