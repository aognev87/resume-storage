package ru.aognev.webapp.storage;


import org.junit.Ignore;
import org.junit.Test;

/**
 * Created by aognev on 02.09.2016.
 */
public class ListStorageTest extends AbstractStorageTest {
    public ListStorageTest() {
        super(new ListStorage());
    }

    @Override
    @Test
    @Ignore
    public void saveOverflow() throws Exception {

    }
}