package ru.aognev.webapp.storage;

import ru.aognev.webapp.storage.serializer.DataStreamSerializer;

/**
 * Created by aognev on 02.09.2016.
 */
public class DataPathStorageTest extends AbstractStorageTest {
    public DataPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getAbsolutePath(), new DataStreamSerializer()));
    }
}