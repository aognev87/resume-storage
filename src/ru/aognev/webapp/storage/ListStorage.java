package ru.aognev.webapp.storage;

import ru.aognev.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aognev on 01.09.2016.
 */
public class ListStorage extends AbstractStorage {
    private final List<Resume> resumes = new ArrayList<>();

    @Override
    protected void clearStorage() {
        resumes.clear();
    }

    @Override
    protected boolean addElement(Resume resume) {
        int index = getIndex(resume.getUuid());

        if (index < 0) {
            return resumes.add(resume);
        }

        return false;
    }

    @Override
    protected boolean updateElement(Resume resume) {
        int index = getIndex(resume.getUuid());

        if (index > -1) {
            resumes.set(index, resume);
            return true;
        }

        return false;
    }

    @Override
    protected Resume getElement(String uuid) {
        int index = getIndex(uuid);

        if (index > -1) {
            return resumes.get(index);
        }

        return null;
    }

    @Override
    protected boolean deleteElement(String uuid) {
        int index = getIndex(uuid);

        if (index > -1) {
            resumes.remove(index);
            return true;
        }

        return false;
    }

    @Override
    protected Resume[] getResumesArray() {
        return resumes.toArray(new Resume[resumes.size()]);
    }

    private int getIndex(String uuid) {
        for (int i = 0; i < resumes.size(); i++) {
            if (uuid.equals(resumes.get(i).getUuid())) {
                return i;
            }
        }

        return -1;
    }
}
