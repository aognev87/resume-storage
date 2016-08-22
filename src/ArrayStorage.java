import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    int lastItem = -1;

    void clear() {
        storage = new Resume[10000];
        lastItem = -1;
    }

    void save(Resume r) {
        if (lastItem > 9998) {
            System.out.print("<<< Here throws 'NotEnoughDbSizeException' o:) >>> ");
        } else {
            lastItem++;
            storage[lastItem] = r;
        }
    }

    Resume get(String uuid) {
        for (int i = 0; i < lastItem + 1; i++) {
            if (storage[i] != null && storage[i].uuid.equals(uuid)) {
                return storage[i];
            }
        }

        System.out.print("<<< Here throws 'NotFoundException' o:) >>> ");
        return null;
    }

    void delete(String uuid) {
        for (int i = 0; i < lastItem; i++) {
            if (storage[i] != null && storage[i].uuid.equals(uuid)) {
                storage[i] = storage[lastItem];
                storage[lastItem] = null;
                lastItem--;
                return;
            }
        }

        if (storage[lastItem] != null && storage[lastItem].uuid.equals(uuid)) {
            storage[lastItem] = null;
            lastItem--;
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, lastItem + 1);
    }

    int size() {
        return lastItem + 1;
    }
}
