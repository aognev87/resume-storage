package ru.aognev.webapp.sql;

import org.postgresql.util.PSQLException;
import ru.aognev.webapp.exception.ExistStorageException;
import ru.aognev.webapp.exception.StorageException;

import java.sql.SQLException;

/**
 * Created by aognev on 27.10.2016.
 */
public class ExceptionUtil {
    private ExceptionUtil() {
    }

    public static StorageException convertException(SQLException e) {
        if (e instanceof PSQLException) {
            if (e.getSQLState().equals("23505")) {
                return new ExistStorageException(null);
            }
        }

        return new StorageException(e);
    }
}
