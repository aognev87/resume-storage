package ru.aognev.webapp.storage;

import ru.aognev.webapp.exception.NotExistStorageException;
import ru.aognev.webapp.model.*;
import ru.aognev.webapp.sql.SqlHelper;

import java.sql.*;
import java.util.*;

/**
 * Created by aognev on 21.10.2016.
 */
public class SqlStorage implements Storage {
    private final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SqlHelper(() -> {
            try {
                Class.forName("org.postgresql.Driver");
            } catch (ClassNotFoundException e) {}

            return DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        });
    }

    @Override
    public void clear() {
        sqlHelper.execute("DELETE FROM resume");
    }

    @Override
    public void save(Resume resume) {
        sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?, ?)")) {
                ps.setString(1, resume.getUuid());
                ps.setString(2, resume.getFullName());
                ps.execute();
            }

            insertContacts(conn, resume);
            insertSections(conn, resume);

            return null;
        });
    }

    @Override
    public void update(Resume resume) {
        sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("UPDATE resume SET full_name=? WHERE uuid=?")) {
                ps.setString(1, resume.getFullName());
                ps.setString(2, resume.getUuid());
                if (ps.executeUpdate() != 1) {
                    throw new NotExistStorageException(resume.getUuid());
                }
            }

            deleteContacts(resume);
            insertContacts(conn, resume);

            deleteSections(resume);
            insertSections(conn, resume);

            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.transactionalExecute(conn -> {
            Resume resume = sqlHelper.execute("" +
                    "   SELECT * FROM resume r " +
                            " LEFT JOIN contact c " +
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
                            addContact(rs, r);
                        } while (rs.next());

                        return r;
                    });


            sqlHelper.execute("SELECT * FROM section WHERE resume_uuid=?", ps -> {
                addSections(ps, resume);
                return null;
            });

            return resume;
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
        return sqlHelper.transactionalExecute(conn -> {
            List<Resume> resumes = sqlHelper.execute("SELECT * FROM resume ORDER BY full_name, uuid", ps -> {
                ResultSet rs = ps.executeQuery();
                List<Resume> resumeList = new ArrayList<>();

                while (rs.next()) {
                    resumeList.add(new Resume(rs.getString("uuid"), rs.getString("full_name")));
                }

                return resumeList;
            });

            resumes.forEach(resume -> {
                String uuid = resume.getUuid();

                sqlHelper.execute("SELECT * FROM contact WHERE resume_uuid=?", ps -> {
                    ps.setString(1, uuid);
                    ResultSet rs = ps.executeQuery();

                    while (rs.next()) {
                        addContact(rs, resume);
                    }

                    return null;
                });

                sqlHelper.execute("SELECT * FROM section WHERE resume_uuid=?", ps -> {
                    addSections(ps, resume);
                    return null;
                });
            });

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

    private void insertContacts(Connection conn, Resume resume) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO contact (resume_uuid, type, value) VALUES (?, ?, ?)")) {
            for (Map.Entry<ContactType, String> e : resume.getContacts().entrySet()) {
                ps.setString(1, resume.getUuid());
                ps.setString(2, e.getKey().name());
                ps.setString(3, e.getValue());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void deleteContacts(Resume resume) {
        sqlHelper.execute("DELETE FROM contact WHERE resume_uuid=?", ps -> {
            ps.setString(1, resume.getUuid());
            ps.execute();
            return null;
        });
    }

    private void addContact(ResultSet rs, Resume resume) throws SQLException {
        String type = rs.getString("type");
        if (Objects.nonNull(type)) {
            resume.addContact(ContactType.valueOf(type), rs.getString("value"));
        }
    }

    private void insertSections(Connection conn, Resume resume) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO section (resume_uuid, type, list, value) VALUES (?, ?, ?, ?)")) {
            for (Map.Entry<SectionType, Section> e : resume.getSections().entrySet()) {
                ps.setString(1, resume.getUuid());
                ps.setString(2, e.getKey().name());
                ps.setBoolean(3, e.getValue() instanceof ListSection);
                if (e.getValue() instanceof ListSection) {
                    StringBuilder sb = new StringBuilder();
                    for (String s : ((ListSection) e.getValue()).getItems()) {
                        sb.append(s);
                        sb.append("\n");
                    }
                    ps.setString(4, sb.substring(0, sb.length() - 1));
                } else {
                    ps.setString(4, ((TextSection) e.getValue()).getContent());
                }
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void deleteSections(Resume resume) {
        sqlHelper.execute("DELETE FROM section WHERE resume_uuid=?", ps -> {
            ps.setString(1, resume.getUuid());
            ps.executeUpdate();
            return null;
        });
    }

    private void addSections(PreparedStatement ps, Resume resume) throws SQLException {
        ps.setString(1, resume.getUuid());
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            String value = rs.getString("value");
            resume.addSection(
                    SectionType.valueOf(rs.getString("type")),
                    rs.getBoolean("list") ? new ListSection(value.split("\n")) : new TextSection(value));
        }
    }
}