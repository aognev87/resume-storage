package ru.aognev.webapp.storage;

import ru.aognev.webapp.exception.StorageException;
import ru.aognev.webapp.model.Resume;

import java.io.*;

/**
 * Created by aognev on 12.09.2016.
 */
public class ObjectStreamStorage {

    private final File directory;
    private AbstractFileStorage abstractFileStorage;
    private AbstractPathStorage abstractPathStorage;

    public ObjectStreamStorage(File directory) {
        this.directory = directory;
    }

    public Storage getAbstractFileStorage() {
        if (abstractFileStorage == null) {
            abstractFileStorage = new ObjectStreamFileStorage(directory);
        }
        return abstractFileStorage;
    }

    public Storage getAbstractPathStorage() {
        if (abstractPathStorage == null) {
            String path = directory.getAbsolutePath().replaceAll("\\\\\\.\\\\", "\\\\");
            abstractPathStorage = new ObjectStreamPathStorage(path);
        }
        return abstractPathStorage;
    }

    private class ObjectStreamFileStorage extends AbstractFileStorage{
        protected ObjectStreamFileStorage(File directory) {
            super(directory);
        }

        @Override
        protected void doWrite(Resume r, OutputStream os) throws IOException {
            ObjectStreamStorage.this.doWrite(r, os);
        }

        @Override
        protected Resume doRead(InputStream is) throws IOException {
            return ObjectStreamStorage.this.doRead(is);
        }
    }

    private class ObjectStreamPathStorage extends AbstractPathStorage {
        protected ObjectStreamPathStorage(String dir) {
            super(dir);
        }

        @Override
        protected void doWrite(Resume r, OutputStream os) throws IOException {
            ObjectStreamStorage.this.doWrite(r, os);
        }

        @Override
        protected Resume doRead(InputStream is) throws IOException {
            return ObjectStreamStorage.this.doRead(is);
        }
    }

    private void doWrite(Resume r, OutputStream os) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(os)) {
            oos.writeObject(r);
        }
    }

    private Resume doRead(InputStream is) throws IOException {
        try (ObjectInputStream ois = new ObjectInputStream(is)) {
            return (Resume) ois.readObject();
        } catch (ClassNotFoundException e) {
            throw new StorageException("Error read resume", null, e);
        }
    }
}
