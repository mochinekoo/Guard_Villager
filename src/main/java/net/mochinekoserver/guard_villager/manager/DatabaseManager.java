package net.mochinekoserver.guard_villager.manager;

import net.mochinekoserver.guard_villager.Main;

import java.sql.*;

public class DatabaseManager {

    private static DatabaseManager connector;

    private Connection connection;

    private DatabaseManager() {
    }

    public static DatabaseManager getInstance() {
        if (connector == null) {
            connector = new DatabaseManager();
            connector.connect();
        }
        return connector;
    }

    public void connect() {
        var plugin = Main.getPlugin(Main.class);
        var config = plugin.getConfig();
        String driver_name = "com.mysql.cj.jdbc.Driver";
        String host = config.getString("database.host");
        int port = config.getInt("database.port");
        String db_name = config.getString("database.db_name");
        String user_id = config.getString("database.user_name");
        String user_pass = config.getString("database.user_pass");
        String jdbc_url = "jdbc:mysql://" + host + ":" + port + "/" + db_name + "?characterEncoding=UTF-8&serverTimezone=Asia/Tokyo";

        try {
            Class.forName(driver_name);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("ドライバーが見つかりませんでした", e);
        }

        try {
            connection = DriverManager.getConnection(jdbc_url, user_id, user_pass);
        } catch (SQLException e) {
            throw new RuntimeException("データベース接続中に問題が発生しました", e);
        }
    }

    public String getData(int id) {
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM test_table");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int getId = rs.getInt("id");
                String getName = rs.getString("name");
                if (getId == id) {
                    return getName;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("SQLに問題が発生しました", e);
        }

        return null;
    }

}
