package ru.aognev.webapp.storage;

import ru.aognev.webapp.exception.StorageException;

import java.io.File;

/**
 * Created by aognev on 13.09.2016.
 */
public class StorageFactory {
    private StorageFactory() {
    }

    public enum Realization {
        SERIALIZATION,
        JSON,
        XML
    }

    public enum SuperType {
        FILE,
        PATH
    }

    public static Storage getStorage(SuperType type, Realization realization, File directory) {
        switch (type) {
            case FILE:
                switch (realization) {
                    case SERIALIZATION:
                        return new ObjectStreamStorage(directory).getAbstractFileStorage();
                    case JSON:
                        break;
                    case XML:
                        break;
                }
                break;
            case PATH:
                switch (realization) {
                    case SERIALIZATION:
                        return new ObjectStreamStorage(directory).getAbstractPathStorage();
                    case JSON:
                        break;
                    case XML:
                        break;
                }
                break;
            default:
                throw new StorageException("Wrong parameters", null);
        }
        return null;
    }

}
