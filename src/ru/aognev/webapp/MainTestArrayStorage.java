package ru.aognev.webapp;

import ru.aognev.webapp.model.Resume;
import ru.aognev.webapp.storage.ArrayStorage;
import ru.aognev.webapp.storage.SortedArrayStorage;
import ru.aognev.webapp.storage.Storage;

/**
 * Test for com.urise.webapp.storage.ru.aognev.webapp.storage.ArrayStorage
 */
public class MainTestArrayStorage {
    private final static Storage ARRAY_STORAGE = new ArrayStorage();
    private final static Storage SORTED_ARRAY_STORAGE = new SortedArrayStorage();

    public static void main(String[] args) {
        testStorage(ARRAY_STORAGE);
        testStorage(SORTED_ARRAY_STORAGE);
    }

    private static void testStorage(Storage storage) {
        System.out.println("\n\n------------------------------------------------------------------------------------");
        System.out.println("Testing '" + storage.getClass().getSimpleName() + "'\n");
        Resume r0 = new Resume("uuid0");
        Resume r1 = new Resume("uuid1");
        Resume r2 = new Resume("uuid2");
        Resume r3 = new Resume("uuid3");
        Resume r4 = new Resume("uuid4");
        Resume r5 = new Resume("uuid5");

        storage.save(r1);
        storage.save(r5);
        storage.save(r2);
        storage.save(r3);
        storage.save(r0);

        System.out.println("Get r1: " + storage.get(r1.getUuid()));
        System.out.println("Size: " + storage.getSize());

        System.out.println("Get dummy: " + storage.get("dummy"));

        printAll(storage);
        System.out.println("Update: " + r4);
        storage.update(r4);

        printAll(storage);
        System.out.println("Update: " + r2);
        storage.update(r2);

        printAll(storage);
        System.out.println("Delete: " + r2);
        storage.delete(r2.getUuid());

        printAll(storage);
        System.out.println("Save: " + r4);
        storage.save(r4);

        printAll(storage);
        System.out.println("Delete: " + r1);
        storage.delete(r1.getUuid());

        printAll(storage);
        System.out.println("Clear");
        storage.clear();

        printAll(storage);
        System.out.println("Size: " + storage.getSize());
    }

    static void printAll(Storage storage) {
        System.out.println("\nGet All");
        for (Resume r : storage.getAllSorted()) {
            System.out.println(r);
        }

        System.out.println();
    }
}
