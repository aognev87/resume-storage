package ru.aognev.webapp.storage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.aognev.webapp.exception.ExistStorageException;
import ru.aognev.webapp.exception.NotExistStorageException;
import ru.aognev.webapp.exception.StorageException;
import ru.aognev.webapp.model.Resume;

/**
 * Created by aognev on 01.09.2016.
 */
public abstract class AbstractStorageTest {

    private final Storage storage;

    private static final String UUID_1 = "uuid1";
    private static final Resume RESUME_1 = new Resume(UUID_1);

    private static final String UUID_2 = "uuid2";
    private static final Resume RESUME_2 = new Resume(UUID_2);

    private static final String UUID_3 = "uuid3";
    private static final Resume RESUME_3 = new Resume(UUID_3);

    private static final String UUID_4 = "uuid4";
    private static final Resume RESUME_4 = new Resume(UUID_4);

    public AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));
    }

    @Test
    public void clear() throws Exception {
        storage.clear();
        Assert.assertEquals(0, storage.getSize());
    }

    @Test
    public void save() throws Exception {
        storage.save(RESUME_4);
        Assert.assertTrue(RESUME_4 == storage.get(UUID_4));
    }

    @Test
    public void update() throws Exception {
        storage.update(RESUME_3);
        Assert.assertTrue(RESUME_3 == storage.get(UUID_3));
    }

    @Test
    public void get() throws Exception {
        Assert.assertTrue(RESUME_1.equals(storage.get(UUID_1)));
        Assert.assertTrue(RESUME_2.equals(storage.get(UUID_2)));
        Assert.assertTrue(RESUME_3.equals(storage.get(UUID_3)));
    }

    @Test
    public void delete() throws Exception {
        storage.delete(UUID_1);
        Assert.assertEquals(2, storage.getSize());
        Assert.assertTrue(RESUME_2.equals(storage.get(UUID_2)));
        Assert.assertTrue(RESUME_3.equals(storage.get(UUID_3)));
    }

    @Test
    public void getAll() throws Exception {
        Assert.assertEquals(3, storage.getAll().length);
    }

    @Test
    public void getSize() throws Exception {
        Assert.assertEquals(3, storage.getSize());
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() throws Exception {
        storage.save(RESUME_1);
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() throws Exception {
        storage.update(RESUME_4);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws Exception {
        storage.get(UUID_4);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() throws Exception {
        storage.delete(UUID_4);
    }

    @Test(expected = StorageException.class)
    public void saveOverflow() throws Exception {
        for (int i = 3; i < AbstractStorage.STORAGE_LIMIT; i++) {
            storage.save(new Resume());
        }

        storage.save(RESUME_4);
    }

}