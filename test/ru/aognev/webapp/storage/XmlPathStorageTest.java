package ru.aognev.webapp.storage;

import ru.aognev.webapp.storage.serializer.XmlStreamSerializer;

/**
 * Created by aognev on 02.09.2016.
 */
public class XmlPathStorageTest extends AbstractStorageTest {
    public XmlPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getAbsolutePath(), new XmlStreamSerializer()));
    }
}