package ru.aognev.webapp.storage;

import org.junit.*;
import ru.aognev.webapp.exception.ExistStorageException;
import ru.aognev.webapp.exception.NotExistStorageException;
import ru.aognev.webapp.model.*;
import ru.aognev.webapp.util.DateUtil;

import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static ru.aognev.webapp.storage.TestData.*;


/**
 * Created by aognev on 31.08.2016.
 */
public abstract class AbstractStorageTest {

    protected Storage storage;

    protected AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(RESUME_2);
        storage.save(RESUME_3);
        storage.save(RESUME_1);
    }

    @Test
    public void getSize() throws Exception {
        assertSize(3);
    }

    @Test
    public void clear() throws Exception {
        storage.clear();
        assertSize(0);
    }

    @Test
    public void update() throws Exception {
        Resume updated = new Resume(UUID_2, "FullName2-updated");
        storage.update(updated);
        assertGet(updated);
    }

    @Test
    public void getAll() throws Exception {
        List<Resume> allSorted = storage.getAllSorted();
        assertSize(3);
        Assert.assertEquals(RESUME_1, allSorted.get(0));
        Assert.assertEquals(RESUME_2, allSorted.get(1));
        Assert.assertEquals(RESUME_3, allSorted.get(2));
    }

    @Test
    public void save() throws Exception {
        storage.save(RESUME_4);
        assertSize(4);
        Assert.assertEquals(RESUME_4, storage.get(UUID_4));
    }

    @Test
    public void delete() throws Exception {
        storage.delete(UUID_3);
        assertSize(2);
    }

    @Test
    public void get() throws Exception {
        assertGet(RESUME_1);
        assertGet(RESUME_2);
        assertGet(RESUME_3);
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        storage.save(RESUME_2);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws Exception {
        storage.get("dummy");
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() throws Exception {
        storage.update(RESUME_4);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete(UUID_4);
    }

    private void assertSize(int size) {
        Assert.assertEquals(size, storage.getSize());
    }

    private void assertGet(Resume r) {
        Assert.assertEquals(r, storage.get(r.getUuid()));
    }

}