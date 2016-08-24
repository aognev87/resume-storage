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

        Integer resumeNumber = getResumeNumber(resume.getUuid());

        if (resumeNumber == null) {
            storage[size] = resume;
            size++;
        } else {
            System.out.format("<<< Here throws 'NoteAlreadyExists' for uuid='%s' >>> ", resume.getUuid());
        }
    }

    public void update(Resume oldResume, Resume newResume) {
        Integer resumeNumber = getResumeNumber(oldResume.getUuid());

        if (resumeNumber == null) {
            System.out.format("<<< Here throws 'NotFoundException' for uuid='%s' >>>\n", oldResume.getUuid());
        } else {
            storage[resumeNumber] = newResume;
        }
    }

    public Resume get(String uuid) {
        Integer resumeNumber = getResumeNumber(uuid);

        if (resumeNumber == null) {
            System.out.format("<<< Here throws 'NotFoundException' for uuid='%s' >>> ", uuid);
            return null;
        } else {
            return storage[resumeNumber];
        }
    }

    public void delete(String uuid) {
        Integer resumeNumber = getResumeNumber(uuid);

        if (resumeNumber == null) {
            System.out.format("<<< Here throws 'NotFoundException' for uuid='%s' >>>\n", uuid);
        } else {
            storage[resumeNumber] = storage[size - 1];
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

    private Integer getResumeNumber(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }

        return null;
    }
}

