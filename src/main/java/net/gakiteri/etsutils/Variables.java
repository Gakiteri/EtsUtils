package net.gakiteri.etsutils;

import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class Variables {

    // Basic
    public static String pluginName;
    public static File dirPlugin;
    public static FileConfiguration config;

    public static Map<UUID, DataPlayer> playerData = new HashMap<>();
    public static ArrayList<String> regionsEnabled = new ArrayList<>();


}
