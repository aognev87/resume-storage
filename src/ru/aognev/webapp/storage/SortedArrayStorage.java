package ru.aognev.webapp.storage;

import ru.aognev.webapp.model.Resume;
import java.util.Arrays;

/**
 * Created by aognev on 26.08.2016.
 */
public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    public void save(Resume resume) {
        if (size == MAX_SIZE) {
            System.out.println("<<< Here throws 'NotEnoughDbSizeException' >>> ");
            return;
        }

        int index = getIndex(resume.getUuid());

        if (index < 0) {
            index = -1 * (index + 1);
            System.arraycopy(storage, index, storage, index + 1, size - index);
            storage[index] = resume;
            size++;
        } else {
            System.out.format("<<< Here throws 'NoteAlreadyExists' for uuid='%s' >>> ", resume.getUuid());
        }
    }


    @Override
    public void delete(String uuid) {
        int index = getIndex(uuid);

        if (index < 0) {
            System.out.format("<<< Here throws 'NotFoundException' for uuid='%s' >>>\n", uuid);
        } else {
            System.arraycopy(storage, index + 1, storage, index, size - index);
            storage[size - 1] = null;
            size--;
        }
    }

    @Override
    protected int getIndex(String uuid) {
        return Arrays.binarySearch(storage, 0, size, new Resume(uuid));
    }
}
