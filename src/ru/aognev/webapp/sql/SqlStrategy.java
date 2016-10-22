package ru.aognev.webapp.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by aognev on 22.10.2016.
 */
@FunctionalInterface
public interface SqlStrategy<T> {
    T execute(PreparedStatement ps) throws SQLException;
}