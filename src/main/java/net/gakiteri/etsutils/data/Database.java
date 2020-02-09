package net.gakiteri.etsutils.data;

public class Database {

    public static Boolean active;
    public static boolean canConnect;

    public static String host;
    public static int port;
    public static String database;
    public static String username;
    public static String password;

    Database() {
        canConnect = false;
        host = "";
        port = 0;
        database = "";
        username = "";
        password = "";
    }

}
