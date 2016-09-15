package ru.aognev.webapp.storage;


import ru.aognev.webapp.storage.serializer.ObjectStreamSerializer;

/**
 * Created by aognev on 02.09.2016.
 */
public class ObjectPathStorageTest extends AbstractStorageTest {
    public ObjectPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getAbsolutePath(), new ObjectStreamSerializer()));
    }
}