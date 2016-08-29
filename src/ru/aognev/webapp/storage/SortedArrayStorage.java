package ru.aognev.webapp.storage;

import ru.aognev.webapp.model.Resume;
import java.util.Arrays;

/**
 * Created by aognev on 26.08.2016.
 */
public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    public void save(Resume resume) {
        super.save(resume);

        if (size < MAX_SIZE && index < 0) {
            index = -1 * (index + 1);

            for (int i = size; i > index; i--) {
                storage[i] = storage[i - 1];
            }

            storage[index] = resume;
            size++;
        }
    }


    @Override
    public void delete(String uuid) {
        super.delete(uuid);

        if (index > -1) {
            for (int i = index; i < size; i++) {
                storage[i] = storage[i + 1];
            }

            storage[size - 1] = null;
            size--;
        }
    }

    @Override
    protected int getIndex(String uuid) {
        Resume key = new Resume(uuid);

        int low = 0;
        int high = size;
        int mid;

        while (low < high) {
            mid = (low + high) / 2;

            if (key.compareTo(storage[mid]) == 0) {
                return mid;
            } else {
                if (key.compareTo(storage[mid]) < 0) {
                    high = mid;
                } else {
                    low = mid + 1;
                }
            }

        }

        return -1 * (low + 1);
    }
}
