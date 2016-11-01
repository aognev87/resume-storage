package ru.aognev.webapp.storage;

import ru.aognev.webapp.exception.NotExistStorageException;
import ru.aognev.webapp.model.ContactType;
import ru.aognev.webapp.model.Resume;
import ru.aognev.webapp.sql.SqlHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by aognev on 21.10.2016.
 */
public class SqlStorage implements Storage {
    private final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    public SqlStorage(String[] creds) {
        this(creds[0], creds[1], creds[2]);
    }

    @Override
    public void clear() {
        sqlHelper.execute("DELETE FROM resume");
    }

    @Override
    public void save(Resume resume) {
        sqlHelper.<Void>execute("INSERT INTO resume (uuid, full_name) VALUES (?, ?)", ps -> {
            ps.setString(1, resume.getUuid());
            ps.setString(2, resume.getFullName());
            ps.execute();
            return null;
        });

        for (Map.Entry<ContactType, String> e : resume.getContacts().entrySet()) {
            sqlHelper.<Void>execute("INSERT INTO contact (resume_uuid, type, value) VALUES (?, ?, ?)", ps -> {
                ps.setString(1, resume.getUuid());
                ps.setString(2, e.getKey().name());
                ps.setString(3, e.getValue());
                ps.execute();
                return null;
            });
        }
    }

    @Override
    public void update(Resume resume) {
        sqlHelper.execute("UPDATE resume r SET full_name=? WHERE r.uuid=?", ps -> {
            ps.setString(1, resume.getFullName());
            ps.setString(2, resume.getUuid());

            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(resume.getUuid());
            }

            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.execute("" +
                        "   SELECT * FROM resume r " +
                        "LEFT JOIN contact c " +
                        "       ON r.uuid=c.resume_uuid " +
                        "    WHERE r.uuid=?",
                ps -> {
                    ps.setString(1, uuid);
                    ResultSet rs = ps.executeQuery();

                    if (!rs.next()) {
                        throw new NotExistStorageException(uuid);
                    }

                    Resume r = new Resume(uuid, rs.getString("full_name"));

                    do {
                        String value = rs.getString("value");
                        ContactType type = ContactType.valueOf(rs.getString("type"));
                        r.addContact(type, value);
                    } while (rs.next());

                    return r;
                });
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.execute("DELETE FROM resume WHERE uuid=?", ps -> {
            ps.setString(1, uuid);

            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }

            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.execute("SELECT * FROM resume ORDER BY full_name, uuid", ps -> {
            ResultSet rs = ps.executeQuery();
            List<Resume> resumes = new ArrayList<>();

            while (rs.next()) {
                resumes.add(new Resume(rs.getString("uuid"), rs.getString("full_name")));
            }

            return resumes;
        });
    }

    @Override
    public int getSize() {
        return sqlHelper.execute("SELECT count(*) FROM resume", ps -> {
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt("count");
        });
    }
}