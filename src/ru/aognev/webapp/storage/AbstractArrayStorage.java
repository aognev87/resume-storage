package ru.aognev.webapp.storage;

import ru.aognev.webapp.model.Resume;
import java.util.Arrays;

/**
 * Created by aognev on 26.08.2016.
 */
public abstract class AbstractArrayStorage implements Storage {
    protected static final int MAX_SIZE = 10000;
    protected Resume[] storage = new Resume[MAX_SIZE];
    protected int size = 0;

    public void update(Resume resume) {
        int index = getIndex(resume.getUuid());

        if (index < 0) {
            System.out.format("<<< Here throws 'NotFoundException' for uuid='%s' >>>\n", resume.getUuid());
        } else {
            storage[index] = resume;
        }
    }

    public Resume get(String uuid) {
        int index = getIndex(uuid);

        if (index < 0) {
            System.out.format("<<< Here throws 'NotFoundException' for uuid='%s' >>> ", uuid);
            return null;
        } else {
            return storage[index];
        }
    }

    public void clear() {
        Arrays.fill(storage, 0, size, null);

        size = 0;
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

    protected abstract int getIndex(String uuid);
}
