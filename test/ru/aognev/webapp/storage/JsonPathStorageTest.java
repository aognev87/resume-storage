package ru.aognev.webapp.storage;

import ru.aognev.webapp.storage.serializer.JsonStreamSerializer;

/**
 * Created by aognev on 02.09.2016.
 */
public class JsonPathStorageTest extends AbstractStorageTest {
    public JsonPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getAbsolutePath(), new JsonStreamSerializer()));
    }
}