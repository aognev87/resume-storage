package ru.aognev.webapp.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * com.urise.webapp.model.ru.aognev.webapp.model.Resume class
 */
public class Resume implements Comparable<Resume> {

    // Unique identifier
    private final String uuid;

    private final String fullName;

    private final Map<String, String> contacts = new HashMap<>();

    private final Map<SectionType, Section> sectionMap = new HashMap<>();

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        Objects.requireNonNull(uuid, "uuid must not be null");
        Objects.requireNonNull(fullName, "fullName must not be null");
        this.uuid = uuid;
        this.fullName = fullName;
    }

    public String getUuid() {
        return uuid;
    }

    public String getFullName() {
        return fullName;
    }

    public Map<String, String> getAllContacts() {
        return contacts;
    }

    public void addContact(String type, String contact) {
        contacts.put(type, contact);
    }

    public String getContact(String type) {
        return contacts.get(type);
    }

    public Map<SectionType, Section> getAllSections() {
        return sectionMap;
    }

    public void addSection(SectionType type, Section section) {
        sectionMap.put(type, section);
    }

    public void getSection(SectionType type) {
        sectionMap.get(type);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Resume resume = (Resume) o;

        if (!uuid.equals(resume.uuid)) return false;
        return fullName.equals(resume.fullName);

    }

    @Override
    public int hashCode() {
        int result = uuid.hashCode();
        result = 31 * result + fullName.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return uuid + '(' + fullName + ')';
    }

    @Override
    public int compareTo(Resume o) {
        int compare = fullName.compareTo(o.fullName);
        return compare != 0 ? compare : uuid.compareTo(o.uuid);
    }
}
