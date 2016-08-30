package ru.aognev.webapp.storage;

import org.junit.Before;
import ru.aognev.webapp.model.Resume;

/**
 * Created by aognev on 31.08.2016.
 */
public class SortedArrayStorageTest extends AbstractArrayStorageTest {
    public SortedArrayStorageTest() {
        storage = new SortedArrayStorage();
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(new Resume(UUID_3));
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
    }
}