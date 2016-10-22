package ru.aognev.webapp.storage;

import ru.aognev.webapp.Config;

/**
 * Created by aognev on 22.10.2016.
 */
public class SqlStorageTest extends AbstractStorageTest {
    public SqlStorageTest() {
        super(new SqlStorage(Config.get().getCreds()));
    }
}