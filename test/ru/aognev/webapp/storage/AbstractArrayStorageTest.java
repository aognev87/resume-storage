package ru.aognev.webapp.storage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.aognev.webapp.exception.ExistStorageException;
import ru.aognev.webapp.exception.NotExistStorageException;
import ru.aognev.webapp.exception.StorageException;
import ru.aognev.webapp.model.Resume;

import java.util.Arrays;

/**
 * Created by aognev on 31.08.2016.
 */
public abstract class AbstractArrayStorageTest {

    protected Storage storage;
    protected static final String UUID_1 = "uuid1";
    protected static final String UUID_2 = "uuid2";
    protected static final String UUID_3 = "uuid3";
    protected static final String UUID_4 = "uuid4";

    @Before
    public void setUp() {
        storage.clear();
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));
    }

    @Test
    public void getSize() throws Exception {
        Assert.assertEquals(3, storage.getSize());
    }

    @Test
    public void clear() throws Exception {
        storage.clear();
        Assert.assertEquals(0, storage.getSize());
    }

    @Test
    public void update() throws Exception {
        storage.update(new Resume(UUID_2));

        Assert.assertArrayEquals(
                new Resume[]{new Resume(UUID_1), new Resume(UUID_2), new Resume(UUID_3)},
                storage.getAll()
        );
    }

    @Test
    public void getAll() throws Exception {
        Assert.assertArrayEquals(
                new Resume[]{new Resume(UUID_1), new Resume(UUID_2), new Resume(UUID_3)},
                storage.getAll()
        );
    }

    @Test
    public void save() throws Exception {
        storage.save(new Resume(UUID_4));
        Assert.assertArrayEquals(
                new Resume[]{new Resume(UUID_1), new Resume(UUID_2), new Resume(UUID_3), new Resume(UUID_4)},
                storage.getAll()
        );
    }

    @Test
    public void delete() throws Exception {
        storage.delete(UUID_3);
        Assert.assertArrayEquals(new Resume[]{new Resume(UUID_1), new Resume(UUID_2)}, storage.getAll());
    }

    @Test
    public void get() throws Exception {
        Assert.assertEquals(new Resume(UUID_2), storage.get(UUID_2));
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        storage.save(new Resume(UUID_1));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws Exception {
        storage.get("dummy");
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() throws Exception {
        storage.update(new Resume(UUID_4));
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete(UUID_4);
    }

    @Test(expected = StorageException.class)
    public void saveOverflow() throws Exception{

        Arrays.fill(
                (Resume[])(storage.getClass().getSuperclass().getDeclaredField("storage").get(storage)),
                new Resume(UUID_1)
        );

        storage.getClass().getSuperclass().getDeclaredField("size").set(storage, 10000);

        storage.save(new Resume(UUID_4));
    }

}