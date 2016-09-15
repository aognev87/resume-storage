package ru.aognev.webapp.storage;


import ru.aognev.webapp.storage.serializer.ObjectStreamSerializer;

/**
 * Created by aognev on 02.09.2016.
 */
public class ObjectFileStorageTest extends AbstractStorageTest {
    public ObjectFileStorageTest() {
        super(new FileStorage(STORAGE_DIR, new ObjectStreamSerializer()));
    }
}