package com.progressoft.jip11.recdb;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseHandler {

    private final DataSource dataSource;

    public DatabaseHandler(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public boolean isValidLoginRequest(String name, String password) {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement query = connection.prepareStatement("select name, pass from rec_users")) {
                try (ResultSet resultSet = query.executeQuery()) {
                    boolean isNameFound = false;
                    while (resultSet.next()) {
                        String nameInResultSet = resultSet.getString(1);
                        if (name.equals(nameInResultSet)) {
                            isNameFound = true;
                            break;
                        }
                    }
                    if (!isNameFound) return false;
                    String deHashed = deHashPassword(resultSet.getString(2));
                    return password.equals(deHashed);
                }
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }
    }

    private String deHashPassword(String value) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < value.length(); i++)
            builder.append((char) (value.charAt(i) / 7));
        return builder.toString();
    }
}
