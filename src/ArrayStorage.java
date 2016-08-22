import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private static final int MAX_SIZE = 10000;
    Resume[] storage = new Resume[MAX_SIZE];
    int size = 0;

    void clear() {
        for (int i = 0; i < size; i++) {
            storage[i] = null;
        }

        size = 0;
    }

    void save(Resume r) {
        if (size == MAX_SIZE) {
            System.out.print("<<< Here throws 'NotEnoughDbSizeException' o:) >>> ");
            return;
        }

        Resume resume = getResume(r.uuid);

        if (resume != null) {
            System.out.format("<<< Here throws 'NoteAlreadyExists' for uuid='%s' o:) >>> ", r.uuid);
        } else {
            storage[size] = r;
            size++;
        }
    }

    Resume get(String uuid) {
        Resume resume = getResume(uuid);

        if (resume == null) {
            System.out.format("<<< Here throws 'NotFoundException' for uuid='%s' o:) >>> ", uuid);
        }

        return resume;
    }

    void delete(String uuid) {
        for (int i = 0; i < size - 1; i++) {
            if (storage[i].uuid.equals(uuid)) {
                storage[i] = storage[size - 1];
                storage[size - 1] = null;
                size--;
                return;
            }
        }

        if (storage[size - 1].uuid.equals(uuid)) {
            storage[size - 1] = null;
            size--;
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    int size() {
        return size;
    }

    private Resume getResume(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].uuid.equals(uuid)) {
                return storage[i];
            }
        }

        return null;
    }
}

