package ru.aognev.webapp.storage;

import ru.aognev.webapp.exception.ExistStorageException;
import ru.aognev.webapp.exception.NotExistStorageException;
import ru.aognev.webapp.exception.StorageException;
import ru.aognev.webapp.model.Resume;

/**
 * Created by aognev on 01.09.2016.
 */
public abstract class AbstractStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10000;
    protected int size = 0;

    @Override
    public void clear() {
        clearStorage();
        size = 0;
    }

    @Override
    public void save(Resume resume) {
        if (size == STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", resume.getUuid());
            //System.out.println("Storage overflow " + resume.getUuid());
        } else {
            if (addElement(resume)) {
                size++;
            } else {
                throw new ExistStorageException(resume.getUuid());
                //System.out.println("NotExistStorageException " + resume.getUuid());
            }
        }
    }

    @Override
    public void update(Resume resume) {
        if (!updateElement(resume)) {
            throw new NotExistStorageException(resume.getUuid());
            //System.out.println("NotExistStorageException " + resume.getUuid());
        }
    }

    @Override
    public Resume get(String uuid) {
        Resume resume = getElement(uuid);

        if (resume == null) {
            throw new NotExistStorageException(uuid);
            //System.out.println("NotExistStorageException " + uuid);
        }

        return resume;
    }

    @Override
    public void delete(String uuid) {
        if (deleteElement(uuid)) {
            size--;
        } else {
            throw new NotExistStorageException(uuid);
            //System.out.println("NotExistStorageException " + uuid);
        }
    }

    @Override
    public Resume[] getAll() {
        return getResumesArray();
    }

    @Override
    public int getSize() {
        return size;
    }

    protected abstract void clearStorage();

    protected abstract Resume[] getResumesArray();

    protected abstract boolean addElement(Resume resume);

    protected abstract boolean updateElement(Resume resume);

    protected abstract Resume getElement(String uuid);

    protected abstract boolean deleteElement(String uuid);

}
