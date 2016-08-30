package ru.aognev.webapp.exception;

/**
 * Created by aognev on 30.08.2016.
 */
public class ExistStorageException extends StorageException {
    public ExistStorageException(String uuid) {
        super("Resume " + uuid + " already exists", uuid);
    }
}
