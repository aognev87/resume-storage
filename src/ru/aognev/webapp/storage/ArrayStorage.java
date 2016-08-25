package ru.aognev.webapp.storage;

import ru.aognev.webapp.model.Resume;
import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private static final int MAX_SIZE = 10000;
    private Resume[] storage = new Resume[MAX_SIZE];
    private int size = 0;

    public void clear() {
        for (int i = 0; i < size; i++) {
            storage[i] = null;
        }

        size = 0;
    }

    public void save(Resume resume) {
        if (size == MAX_SIZE) {
            System.out.println("<<< Here throws 'NotEnoughDbSizeException' >>> ");
            return;
        }

        Integer index = getIndex(resume.getUuid());

        if (index == null) {
            storage[size] = resume;
            size++;
        } else {
            System.out.format("<<< Here throws 'NoteAlreadyExists' for uuid='%s' >>> ", resume.getUuid());
        }
    }

    public void update(Resume resume) {
        Integer index = getIndex(resume.getUuid());

        if (index == null) {
            System.out.format("<<< Here throws 'NotFoundException' for uuid='%s' >>>\n", resume.getUuid());
        } else {
            storage[index] = resume;
        }
    }

    public Resume get(String uuid) {
        Integer index = getIndex(uuid);

        if (index == null) {
            System.out.format("<<< Here throws 'NotFoundException' for uuid='%s' >>> ", uuid);
            return null;
        } else {
            return storage[index];
        }
    }

    public void delete(String uuid) {
        Integer index = getIndex(uuid);

        if (index == null) {
            System.out.format("<<< Here throws 'NotFoundException' for uuid='%s' >>>\n", uuid);
        } else {
            storage[index] = storage[size - 1];
            storage[size - 1] = null;
            size--;
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    public int getSize() {
        return size;
    }

    private Integer getIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }

        return null;
    }
}

