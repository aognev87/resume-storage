package ru.aognev.webapp.sql;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by aognev on 01.11.2016.
 */
public interface SqlTransaction<T> {
    T execute(Connection conn) throws SQLException;
}