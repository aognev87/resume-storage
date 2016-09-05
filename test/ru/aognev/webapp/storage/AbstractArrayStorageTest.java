package ru.aognev.webapp.storage;

import org.junit.Assert;
import org.junit.Test;
import ru.aognev.webapp.exception.StorageException;
import ru.aognev.webapp.model.Resume;

/**
 * Created by aognev on 31.08.2016.
 */
public abstract class AbstractArrayStorageTest extends AbstractStorageTest {

    protected AbstractArrayStorageTest(Storage storage) {
        super(storage);
    }

    @Test(expected = StorageException.class)
    public void saveOverflow() throws Exception{

        try {
            for (int i = 3; i < AbstractArrayStorage.STORAGE_LIMIT; i++) {
                storage.save(new Resume("Name" + i));
            }
        } catch (StorageException e) {
            Assert.fail();
        }

        storage.save(new Resume("Overflow"));
    }

}