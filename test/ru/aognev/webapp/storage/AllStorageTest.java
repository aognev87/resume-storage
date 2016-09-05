package ru.aognev.webapp.storage;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by aognev on 03.09.2016.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
            ArrayStorageTest.class,
            SortedArrayStorageTest.class,
            ListStorageTest.class,
            MapUuidStorageTest.class,
            MapResumeStorageTest.class
        })
public class AllStorageTest {
}
