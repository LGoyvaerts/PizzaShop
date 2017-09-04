package ch.ti8m.iptiQ.aws.waf;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by gol on 24.01.2017.
 */
public class JDBCExample {

    // JDBC driver name and database URL
    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";

    // Database credentials
    private static final String DB_HOST = "localhost";
    private static final String DB_NAME = "pizzakurier";
    private static final String DB_USER = "lorris";
    private static final String DB_PASS = "L2o0g0o1";
    private static final String DB_URL = "jdbc:mysql://" + DB_HOST + "/" + DB_NAME + "?useSSL=false&serverTimezone=UTC";


    public static void main(String[] args) throws Exception {
        Class.forName(JDBC_DRIVER);
        deleteExample();
        insertExample();
        updateExample();
        selectExample();
    }

    private static void selectExample() throws Exception {


        // create a connection
        // hint: try-with-resources (since Java 7)
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {

            // create a statement
            try (Statement statement = connection.createStatement()) {

                String sql = "select id, name, price from pizza order by id asc";
                try (ResultSet resultSet = statement.executeQuery(sql)) {

                    while (resultSet.next()) {
                        long id = resultSet.getLong("id");
                        String name = resultSet.getString("name");
                        double price = resultSet.getDouble("price");
                        System.out.println(id + ": " + name + " (" + price + ")");
                    }
                }
            }
        }
    }

    private static void insertExample() throws Exception {

        // create a connection
        // hint: try-with-resources (since Java 7)
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {

            // create a statement
            try (Statement statement = connection.createStatement()) {

                String sql = "insert into pizza (name, price) values ('Funghi', 17.00);";
                int rowsUpdated = statement.executeUpdate(sql);

            }
        }
    }

    private static void updateExample() throws Exception {

        // create a connection
        // hint: try-with-resources (since Java 7)
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {

            // create a statement
            try (Statement statement = connection.createStatement()) {

                String sql = "update pizza set price=16 WHERE name='Funghi';";
                int rowsUpdated = statement.executeUpdate(sql);

            }
        }
    }

    private static void deleteExample() throws Exception {

        // create a connection
        // hint: try-with-resources (since Java 7)
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {

            // create a statement
            try (Statement statement = connection.createStatement()) {

                String sql = "delete from pizza WHERE name='Funghi';";
                int rowsUpdated = statement.executeUpdate(sql);

            }
        }
    }
}
