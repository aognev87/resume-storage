package ru.aognev.webapp.storage;

import org.junit.Before;
import ru.aognev.webapp.model.Resume;

/**
 * Created by aognev on 31.08.2016.
 */
public class SortedStorageTest extends AbstractStorageTest {
    public SortedStorageTest() {
        super(new SortedArrayStorage());
    }
}