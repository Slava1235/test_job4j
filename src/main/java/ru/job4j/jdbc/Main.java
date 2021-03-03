package ru.job4j.jdbc;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;

public class Main {
    public static void main(String[] args) {
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement("INSERT INTO filedatabase(name) VALUES (?)")) {
            System.out.printf("Connection to Store BD ");
            File file = new File("/home/svyatoslav/Рабочий стол/SQL/file.txt");
            BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file));

            byte[] buffer = new byte[bufferedInputStream.available()];
//            System.out.println(buffer);
            bufferedInputStream.read(buffer, 0, bufferedInputStream.available());
            pstmt.setBytes(1, buffer);
            pstmt.executeUpdate();
            pstmt.close();

            bufferedInputStream.close();
            conn.close();
            pstmt.close();

        } catch (Exception ex) {
            System.out.println("Connection failed...");
            System.out.println(ex);
        }
    }

    private static Connection getConnection() {
        final String url = "jdbc:postgresql://localhost:5432/filedatabase";
        final String username = "postgres";
        final String password = "password";
        try {
            Class.forName("org.postgresql.Driver").getDeclaredConstructor().newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            return DriverManager.getConnection(url, username, password);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
}