package ru.aognev.webapp.storage;

import ru.aognev.webapp.exception.StorageException;
import ru.aognev.webapp.model.Resume;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by aognev on 07.09.2016.
 */
public abstract class AbstractFileStorage extends AbstractStorage<File> {
    private File directory;

    protected abstract void doWrite(Resume r, File file) throws IOException;

    protected abstract Resume doRead(File file) throws IOException;

    protected AbstractFileStorage(File directory) {
        Objects.requireNonNull(directory, "Directory must not be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory");
        }

        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " directory is not readable/writable");
        }

        this.directory = directory;
    }

    @Override
    public void clear() {
        try {
            for (File file : directory.listFiles()) {
                doDelete(file);
            }
        } catch (NullPointerException e) {
            throw new StorageException("Storage directory clear error", directory.getName(), e);
        }
    }

    @Override
    public int getSize() {
        try {
            return directory.listFiles().length;
        } catch (NullPointerException e) {
            throw new StorageException("Storage directory read error", directory.getName(), e);
        }
    }

    @Override
    protected File getSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected boolean isExist(File file) {
        return file.exists();
    }

    @Override
    protected void doUpdate(Resume r, File file) {
        try {
            doDelete(file);
            doWrite(r, file);
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    @Override
    protected void doSave(Resume r, File file) {
        try {
            file.createNewFile();
            doWrite(r, file);
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    @Override
    protected void doDelete(File file) {
        file.delete();
    }

    @Override
    protected Resume doGet(File file) {
        try {
            return doRead(file);
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    @Override
    protected List<Resume> doCopyAll() {
        try {
            List<Resume> resumes = new ArrayList<>();

            for (File file : directory.listFiles()) {
                resumes.add(doRead(file));
            }

            return resumes;
        } catch (IOException e) {
            throw new StorageException("IO error", directory.getName(), e);
        } catch (NullPointerException e) {
            throw new StorageException("Storage directory read error", directory.getName(), e);
        }
    }
}
