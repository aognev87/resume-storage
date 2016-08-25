package ru.aognev.webapp;

import ru.aognev.webapp.model.Resume;
import ru.aognev.webapp.storage.ArrayStorage;

/**
 * Test for com.urise.webapp.storage.ru.aognev.webapp.storage.ArrayStorage
 */
public class MainTestArrayStorage {
    static final ArrayStorage ARRAY_STORAGE = new ArrayStorage();

    public static void main(String[] args) {
        Resume r1 = new Resume();
        r1.setUuid("uuid1");
        Resume r2 = new Resume();
        r2.setUuid("uuid2");
        Resume r3 = new Resume();
        r3.setUuid("uuid3");
        Resume r4 = new Resume();
        r4.setUuid("uuid4");

        ARRAY_STORAGE.save(r1);
        ARRAY_STORAGE.save(r2);
        ARRAY_STORAGE.save(r3);

        System.out.println("Get r1: " + ARRAY_STORAGE.get(r1.getUuid()));
        System.out.println("Size: " + ARRAY_STORAGE.getSize());

        System.out.println("Get dummy: " + ARRAY_STORAGE.get("dummy"));

        printAll();
        System.out.println("Update: " + r4);
        ARRAY_STORAGE.update(r4);

        printAll();
        System.out.println("Update: " + r2);
        ARRAY_STORAGE.update(r2);

        printAll();
        System.out.println("Delete: " + r1);
        ARRAY_STORAGE.delete(r1.getUuid());
        System.out.println("Delete: " + r2);
        ARRAY_STORAGE.delete(r2.getUuid());

        printAll();
        System.out.println("Clear");
        ARRAY_STORAGE.clear();

        printAll();
        System.out.println("Size: " + ARRAY_STORAGE.getSize());
    }

    static void printAll() {
        System.out.println("\nGet All");
        for (Resume r : ARRAY_STORAGE.getAll()) {
            System.out.println(r);
        }
    }
}
