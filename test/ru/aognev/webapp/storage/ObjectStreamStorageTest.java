package ru.aognev.webapp.storage;


/**
 * Created by aognev on 12.09.2016.
 */
public class ObjectStreamStorageTest extends AbstractStorageTest {

    public ObjectStreamStorageTest() {
        super(
                StorageFactory.getStorage(
                        StorageFactory.SuperType.PATH,
                        StorageFactory.Realization.SERIALIZATION,
                        STORAGE_DIR
                )
        );
    }
}