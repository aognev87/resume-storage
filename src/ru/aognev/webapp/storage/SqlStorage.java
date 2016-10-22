package ru.aognev.webapp.storage;

import ru.aognev.webapp.exception.ExistStorageException;
import ru.aognev.webapp.exception.NotExistStorageException;
import ru.aognev.webapp.model.Resume;
import ru.aognev.webapp.sql.ConnectionFactory;
import ru.aognev.webapp.sql.SqlHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by aognev on 21.10.2016.
 */
public class SqlStorage implements Storage {
    public final ConnectionFactory connectionFactory;
    private final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        sqlHelper = new SqlHelper(connectionFactory);
    }

    public SqlStorage(String[] creds) {
        this(creds[0], creds[1], creds[2]);
    }

    @Override
    public void clear() {
        /*try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement("DELETE FROM resume")) {
            ps.execute();
        } catch (SQLException e) {
            throw new StorageException(e);
        }*/

        sqlHelper.execute(
                "DELETE FROM resume",
                ps -> {
                    ps.execute();
                    return null;
                }
        );
    }

    @Override
    public void save(Resume resume) {
        /*try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?, ?)")) {
            ps.setString(1, resume.getUuid());
            ps.setString(2, resume.getFullName());
            ps.execute();
        } catch (SQLException e) {
            throw new StorageException(e);
        }*/

        try {
            sqlHelper.execute(
                    "INSERT INTO resume (uuid, full_name) VALUES (?, ?)",
                    ps -> {
                        ps.setString(1, resume.getUuid());
                        ps.setString(2, resume.getFullName());
                        ps.execute();
                        return null;
                    }
            );
        } catch (Exception e) {
            throw new ExistStorageException(resume.getUuid());
        }
    }

    @Override
    public void update(Resume resume) {
        /*try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement("UPDATE resume r SET full_name=? WHERE r.uuid=?")) {
            ps.setString(1, resume.getFullName());
            ps.setString(2, resume.getUuid());

            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(resume.getUuid());
            }

        } catch (SQLException e) {
            throw new StorageException(e);
        }*/

        sqlHelper.execute(
                "UPDATE resume r SET full_name=? WHERE r.uuid=?",
                ps -> {
                    ps.setString(1, resume.getFullName());
                    ps.setString(2, resume.getUuid());

                    if (ps.executeUpdate() == 0) {
                        throw new NotExistStorageException(resume.getUuid());
                    }

                    return null;
                }
        );
    }

    @Override
    public Resume get(String uuid) {
        /*try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM resume r WHERE r.uuid=?")) {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();

            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }

            Resume r = new Resume(uuid, rs.getString("full_name"));
            return r;
        } catch (SQLException e) {
            throw new StorageException(e);
        }*/

        return sqlHelper.execute(
                "SELECT * FROM resume r WHERE r.uuid=?",
                ps -> {
                    ps.setString(1, uuid);
                    ResultSet rs = ps.executeQuery();

                    if (!rs.next()) {
                        throw new NotExistStorageException(uuid);
                    }

                    return new Resume(uuid, rs.getString("full_name"));
                }
        );
    }

    @Override
    public void delete(String uuid) {
        /*try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement("DELETE FROM resume WHERE uuid=?")) {
            ps.setString(1, uuid);

            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }

        } catch (SQLException e) {
            throw new StorageException(e);
        }*/

        sqlHelper.execute(
                "DELETE FROM resume WHERE uuid=?",
                ps -> {
                    ps.setString(1, uuid);

                    if (ps.executeUpdate() == 0) {
                        throw new NotExistStorageException(uuid);
                    }

                    return null;
                }
        );
    }

    @Override
    public List<Resume> getAllSorted() {
        /*try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM resume ORDER BY uuid")) {
            ResultSet rs = ps.executeQuery();
            List<Resume> resumes = new ArrayList<>();

            while (rs.next()) {
                resumes.add(new Resume(rs.getString("uuid").trim(), rs.getString("full_name")));
            }

            return resumes;

        } catch (SQLException e) {
            throw new StorageException(e);
        }*/

        return sqlHelper.execute(
                "SELECT * FROM resume ORDER BY uuid",
                ps -> {
                    ResultSet rs = ps.executeQuery();
                    List<Resume> resumes = new ArrayList<>();

                    while (rs.next()) {
                        resumes.add(new Resume(rs.getString("uuid").trim(), rs.getString("full_name")));
                    }

                    return resumes;
                }
        );
    }

    @Override
    public int getSize() {
        /*try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT count(*) FROM resume")) {
            ResultSet rs = ps.executeQuery();

            rs.next();
            return rs.getInt("count");

        } catch (SQLException e) {
            throw new StorageException(e);
        }*/

        return sqlHelper.execute(
                "SELECT count(*) FROM resume",
                ps -> {
                    ResultSet rs = ps.executeQuery();
                    rs.next();
                    return rs.getInt("count");
                }
        );
    }
}