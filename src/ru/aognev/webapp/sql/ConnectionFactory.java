package ru.aognev.webapp.sql;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by aognev on 21.10.2016.
 */
public interface ConnectionFactory {
    Connection getConnection() throws SQLException;
}
