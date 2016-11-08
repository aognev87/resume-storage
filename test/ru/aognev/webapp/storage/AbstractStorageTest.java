package ru.aognev.webapp.storage;

import org.junit.*;
import ru.aognev.webapp.Config;
import ru.aognev.webapp.exception.ExistStorageException;
import ru.aognev.webapp.exception.NotExistStorageException;
import ru.aognev.webapp.model.*;

import java.io.File;
import java.time.Month;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;


/**
 * Created by aognev on 31.08.2016.
 */
public abstract class AbstractStorageTest {

    //protected static final File STORAGE_DIR = new File (".\\file-storage");
    protected static final File STORAGE_DIR = Config.get().getStorageDir();

    protected Storage storage;

    public static final String UUID_1 = UUID.randomUUID().toString();//"uuid1";
    public static final String UUID_2 = UUID.randomUUID().toString();//"uuid2";
    public static final String UUID_3 = UUID.randomUUID().toString();//"uuid3";
    public static final String UUID_4 = UUID.randomUUID().toString();//"uuid4";

    public static final Resume R1;
    public static final Resume R2;
    public static final Resume R3;
    public static final Resume R4;

    static {
        R1 = new Resume(UUID_1, "name1");
        R2 = new Resume(UUID_2, "name2");
        R3 = new Resume(UUID_3, "name3");
        R4 = new Resume(UUID_4, "name4");

        R1.addContact(ContactType.MAIL, "mail@ya.ru");
        R1.addContact(ContactType.PHONE, "11111");
        /*R1.addSection(SectionType.OBJECTIVE, new TextSection("Objective1"));
        R1.addSection(SectionType.PERSONAL, new TextSection("Personal data"));
        R1.addSection(SectionType.ACHIEVEMENT, new ListSection("Achievement11", "Achievement12", "Achievement13"));
        R1.addSection(SectionType.QUALIFICATIONS, new ListSection("Java", "SQL", "JavaScript"));
        R1.addSection(SectionType.EXPERIENCE,
                new OrganizationSection(
                        new Organization("Organization11", "http://Organization11.ru",
                            new Organization.Position(2005, Month.JANUARY, "position1", "content1"),
                            new Organization.Position(2001, Month.MARCH, 2005, Month.JANUARY, "position2", "content2"))));
        R1.addSection(SectionType.EDUCATION,
                new OrganizationSection(
                    new Organization("Institute", null,
                            new Organization.Position(1996, Month.JANUARY, 2000, Month.DECEMBER, "aspirant", null),
                            new Organization.Position(2001, Month.MARCH, 2005, Month.JANUARY, "student", "IT facultet")),
                    new Organization("Organization12", "http://Organization12.ru")));*/

        R2.addContact(ContactType.SKYPE, "skype2");
        R2.addContact(ContactType.PHONE, "22222");
        /*R2.addSection(SectionType.EXPERIENCE,
                new OrganizationSection(
                        new Organization("Organization21", "http://Organization21.ru",
                                new Organization.Position(2015, Month.JANUARY, "position1", "content1"))));*/

        R4.addContact(ContactType.PHONE, "4444");
        R4.addContact(ContactType.SKYPE, "Skype");
    }

    protected AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(R2);
        storage.save(R3);
        storage.save(R1);
    }

    @Test
    public void getSize() throws Exception {
        assertSize(3);
    }

    @Test
    public void clear() throws Exception {
        storage.clear();
        assertSize(0);
    }

    @Test
    public void update() throws Exception {
        Resume updated = new Resume(UUID_2, "FullName2-updated");
        updated.addContact(ContactType.MAIL, "mail@google.com");
        updated.addContact(ContactType.SKYPE, "NewSkype");
        updated.addContact(ContactType.MOBILE, "+7 921 222-2222");

        storage.update(updated);
        assertGet(updated);
    }

    @Test
    public void getAll() throws Exception {
        List<Resume> allSorted = storage.getAllSorted();
        assertSize(3);
        Assert.assertEquals(R1, allSorted.get(0));
        Assert.assertEquals(R2, allSorted.get(1));
        Assert.assertEquals(R3, allSorted.get(2));
    }

    @Test
    public void getAllSorted() throws Exception {
        List<Resume> list = storage.getAllSorted();
        assertSize(3);
        List<Resume> sortedResumes = Arrays.asList(R1, R2, R3);
        Collections.sort(sortedResumes);
        Assert.assertEquals(sortedResumes, list);
    }

    @Test
    public void save() throws Exception {
        storage.save(R4);
        assertSize(4);
        Assert.assertEquals(R4, storage.get(UUID_4));
    }

    @Test
    public void delete() throws Exception {
        storage.delete(UUID_3);
        assertSize(2);
    }

    @Test
    public void get() throws Exception {
        assertGet(R1);
        assertGet(R2);
        assertGet(R3);
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        storage.save(R2);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws Exception {
        storage.get("dummy");
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() throws Exception {
        storage.update(R4);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete(UUID_4);
    }

    private void assertSize(int size) {
        Assert.assertEquals(size, storage.getSize());
    }

    private void assertGet(Resume r) {
        Assert.assertEquals(r, storage.get(r.getUuid()));
    }
}