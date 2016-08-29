package ru.aognev.webapp.storage;

import ru.aognev.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    public void save(Resume resume) {
        super.save(resume);

        if (size < MAX_SIZE && index < 0) {
            storage[size] = resume;
            size++;
        }
    }

    public void delete(String uuid) {
        super.delete(uuid);

        if (index > -1) {
            storage[index] = storage[size - 1];
            storage[size - 1] = null;
            size--;
        }
    }

    @Override
    protected int getIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }

        return -1;
    }
}

