package net.gakiteri.etsutils;

import org.bukkit.configuration.file.FileConfiguration;
import java.io.File;

public class Variables {

    // Basic
    public static String pluginName;
    public static File dirPlugin;
    public static FileConfiguration config;


    // Defaults
    public static String defPlayerRank;
    public static int defPlayerBalance;
    public static int defPlayerRankLevel = 0;

    public static boolean defCmdBalance = false;
    public static boolean defCmdPlayer = false;
    public static boolean defCmdPvp = false;
    public static boolean defCmdRank = false;

}
