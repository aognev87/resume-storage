package ru.aognev.webapp.storage;

import ru.aognev.webapp.model.Resume;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by aognev on 01.09.2016.
 */
public class MapStorage extends AbstractStorage {
    private final Map<String, Resume> resumeMap = new HashMap<>();

    @Override
    protected void clearStorage() {
        resumeMap.clear();
    }

    @Override
    protected boolean addElement(Resume resume) {
        return resumeMap.putIfAbsent(resume.getUuid(), resume) == null;
    }

    @Override
    protected boolean updateElement(Resume resume) {
        return resumeMap.replace(resume.getUuid(), resume) != null;
    }

    @Override
    protected Resume getElement(String uuid) {
        return resumeMap.get(uuid);
    }

    @Override
    protected boolean deleteElement(String uuid) {
        return resumeMap.remove(uuid) != null;
    }

    @Override
    protected Resume[] getResumesArray() {
        return resumeMap.values().toArray(new Resume[resumeMap.size()]);
    }
}
