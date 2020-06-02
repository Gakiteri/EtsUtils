package net.gakiteri.etsutils.data;

import org.bukkit.entity.Player;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DataLocationLog {

    private String date;
    private String time;
    private String uuid;
    private String dimension;
    private int cordX;
    private int cordY;
    private int cordZ;
    private boolean afk;
    private int afkCount;
    private boolean afkUpdated;


    public DataLocationLog(Player player) {
        uuid = player.getUniqueId().toString();
        dimension = player.getWorld().getName();
        afk = false;
        afkCount = 0;
        afkUpdated = true;

        updateData(player);
    }

    /** UPDATE **/
    public void updateData(Player player) {

        // Gets time and date
        date = new SimpleDateFormat("YY-MM-dd").format(new Date());
        time = new SimpleDateFormat("HH:mm:ss").format(new Date());

        // Gets new players cords
        int newCordX = player.getLocation().getBlockX();
        int newCordY = player.getLocation().getBlockY();
        int newCordZ = player.getLocation().getBlockZ();

        // Checks if player moved
        if ((cordX == newCordX) &&(cordY == newCordY) && (cordZ == newCordZ)) {
            // Count is incremented
            afkCount ++;
            // If limit reached, state is changed to afk and set DB change var
            if (afkCount == 4) {
                afk = true;
                afkUpdated = false;
            }
        } else {
            // Resets count and applies new coordinates
            afkCount = 0;
            cordX = newCordX;
            cordY = newCordY;
            cordZ = newCordZ;

            // If player was afk, change state and set DB change var
            if (afk) {
                afkUpdated = false;
                afk = false;
            }
        }

    }

    /** DATE **/
    public String getDate() {
        return date;
    }

    /** TIME **/
    public String getTime() {
        return time;
    }

    /** UUID **/
    public String getUuid() {
        return uuid;
    }

    /** DIMENSION **/
    public String getDimension() {
        return dimension;
    }

    /** CORD X **/
    public String getX() {
        return String.valueOf(cordX);
    }

    /** CORD Y **/
    public String getY() {
        return String.valueOf(cordY);
    }

    /** CORD Z **/
    public String getZ() {
        return String.valueOf(cordZ);
    }

    /** AFK **/
    public boolean getAfk() {
        return afk;
    }

    /** AFK UPDATED **/
    public boolean getAfkUpdated() {
        return afkUpdated;
    }
    public void setAfkUpdated(boolean val) {
        afkUpdated = val;
    }
}
