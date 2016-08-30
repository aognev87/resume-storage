package ru.aognev.webapp.exception;

/**
 * Created by aognev on 30.08.2016.
 */
public class NotExistStorageException extends StorageException {
    public NotExistStorageException(String uuid) {
        super("Resume " + uuid + " not exists", uuid);
    }
}
