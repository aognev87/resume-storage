package ru.aognev.webapp.storage;

import ru.aognev.webapp.model.Resume;

import java.util.List;

/**
 * Created by aognev on 26.08.2016.
 */
public interface Storage {

    void clear();

    void save(Resume resume);

    void update(Resume resume);

    Resume get(String uuid);

    void delete(String uuid);

    List<Resume> getAllSorted();

    int getSize();

}
