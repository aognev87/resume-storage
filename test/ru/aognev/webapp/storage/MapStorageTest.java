package ru.aognev.webapp.storage;

import org.junit.Ignore;
import org.junit.Test;

/**
 * Created by aognev on 03.09.2016.
 */
public class MapStorageTest extends AbstractStorageTest {
    public MapStorageTest() {
        super(new MapStorage());
    }

    @Override
    @Test
    @Ignore
    public void saveOverflow() throws Exception {
    }
}