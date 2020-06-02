package net.gakiteri.etsutils;

import net.gakiteri.etsutils.data.DataLocationLog;
import org.bukkit.configuration.file.FileConfiguration;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Variables {

    // Basic
    public static String pluginName;
    public static File dirPlugin;
    public static FileConfiguration config;


    // Defaults
    public static String defPlayerRank;
    public static boolean defPlayerPvp = false;

    public static boolean defCmdPlayer = false;
    public static boolean defCmdPvp = false;
    public static boolean defCmdRank = false;
    public static boolean defCmdGet = false;
    public static boolean defCmdPermissions = false;


    // Data
    public static boolean dtaNoSleep;
    public static Map<String, DataLocationLog> dtaLocationLogs = new HashMap<>();

}
