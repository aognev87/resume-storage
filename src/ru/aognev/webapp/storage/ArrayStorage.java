package ru.aognev.webapp.storage;

import ru.aognev.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    public void save(Resume resume) {
        if (size == MAX_SIZE) {
            System.out.println("<<< Here throws 'NotEnoughDbSizeException' >>> ");
            return;
        }

        int index = getIndex(resume.getUuid());

        if (index == -1) {
            storage[size] = resume;
            size++;
        } else {
            System.out.format("<<< Here throws 'NoteAlreadyExists' for uuid='%s' >>> ", resume.getUuid());
        }
    }

    public void delete(String uuid) {
        int index = getIndex(uuid);

        if (index == -1) {
            System.out.format("<<< Here throws 'NotFoundException' for uuid='%s' >>>\n", uuid);
        } else {
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

