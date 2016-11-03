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
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        sqlHelper.execute("DELETE FROM resume");
    }

    @Override
    public void save(Resume resume) {
        sqlHelper.transactionalExecute(conn -> {
            sqlHelper.execute(conn, "INSERT INTO resume (uuid, full_name) VALUES (?, ?)", ps -> {
                ps.setString(1, resume.getUuid());
                ps.setString(2, resume.getFullName());
                ps.execute();
                return null;
            });

            sqlHelper.execute(conn, "INSERT INTO contact (resume_uuid, type, value) VALUES (?, ?, ?)", ps -> {
                for (Map.Entry<ContactType, String> e : resume.getContacts().entrySet()) {
                    ps.setString(1, resume.getUuid());
                    ps.setString(2, e.getKey().name());
                    ps.setString(3, e.getValue());
                    ps.addBatch();
                }
                ps.executeBatch();
                return null;
            });

            sqlHelper.execute(conn, "INSERT INTO text_section (resume_uuid, type, value) VALUES (?, ?, ?)", ps -> {
                for (Map.Entry<SectionType, Section> e : resume.getSections().entrySet()) {
                    if (e.getValue() instanceof TextSection) {
                        ps.setString(1, resume.getUuid());
                        ps.setString(2, e.getKey().name());
                        ps.setString(3, ((TextSection) e.getValue()).getContent());
                    }
                    ps.addBatch();
                }
                ps.executeBatch();
                return null;
            });

            return null;
        });
    }

    @Override
    public void update(Resume resume) {
        sqlHelper.transactionalExecute(conn -> {
            sqlHelper.execute(conn, "UPDATE resume SET full_name=? WHERE uuid=?", ps -> {
                ps.setString(1, resume.getFullName());
                ps.setString(2, resume.getUuid());
                if (ps.executeUpdate() == 0) {
                    throw new NotExistStorageException(resume.getUuid());
                }
                return null;
            });

            sqlHelper.execute(conn, "DELETE FROM contact WHERE resume_uuid=?", ps -> {
                ps.setString(1, resume.getUuid());
                ps.executeUpdate();
                return null;
            });

            sqlHelper.execute(conn, "UPDATE contact SET type=?, value=? WHERE resume_uuid=?", ps -> {
                for (Map.Entry<ContactType, String> e : resume.getContacts().entrySet()) {
                    ps.setString(1, e.getKey().name());
                    ps.setString(2, e.getValue());
                    ps.setString(3, resume.getUuid());
                    ps.addBatch();
                }
                ps.executeBatch();
                return null;
            });

            sqlHelper.execute(conn, "DELETE FROM text_section WHERE resume_uuid=?", ps -> {
                ps.setString(1, resume.getUuid());
                ps.executeUpdate();
                return null;
            });

            sqlHelper.execute(conn, "UPDATE text_section SET type=?, value=? WHERE resume_uuid=?", ps -> {
                for (Map.Entry<SectionType, Section> e : resume.getSections().entrySet()) {
                    if (e.getValue() instanceof TextSection) {
                        ps.setString(1, e.getKey().name());
                        ps.setString(2, ((TextSection) e.getValue()).getContent());
                        ps.setString(3, resume.getUuid());
                    }
                    ps.addBatch();
                }
                ps.executeBatch();
                return null;
            });

            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.transactionalExecute(conn -> {
            Resume resume = sqlHelper.execute(conn,
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
                            String type = rs.getString("type");

                            if (Objects.nonNull(type)) {
                                r.addContact(ContactType.valueOf(type), rs.getString("value"));
                            }
                        } while (rs.next());

                        return r;
                    });

            sqlHelper.execute(conn, "SELECT * FROM text_section WHERE resume_uuid=?", ps -> {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();

                Map<SectionType, Section> sectionMap = new HashMap<>();

                while (rs.next()) {
                    resume.addSection(SectionType.valueOf(rs.getString("type")), new TextSection(rs.getString("value")));
                }

                return sectionMap;
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
            List<Resume> resumes = sqlHelper.execute(conn, "SELECT * FROM resume ORDER BY full_name, uuid", ps -> {
                ResultSet rs = ps.executeQuery();
                List<Resume> resumeList = new ArrayList<>();

                while (rs.next()) {
                    resumeList.add(new Resume(rs.getString("uuid"), rs.getString("full_name")));
                }

                return resumeList;
            });

            resumes.forEach(resume -> {
                String uuid = resume.getUuid();

                sqlHelper.execute(conn, "SELECT * FROM contact WHERE resume_uuid=?", ps -> {
                    ps.setString(1, uuid);
                    ResultSet rs = ps.executeQuery();

                    while (rs.next()) {
                        resume.addContact(ContactType.valueOf(rs.getString("type")), rs.getString("value"));
                    }

                    return null;
                });

                sqlHelper.execute(conn, "SELECT * FROM text_section WHERE resume_uuid=?", ps -> {
                    ps.setString(1, uuid);
                    ResultSet rs = ps.executeQuery();

                    while (rs.next()) {
                        resume.addSection(SectionType.valueOf(rs.getString("type")), new TextSection(rs.getString("value")));
                    }

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
}