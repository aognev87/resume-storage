package ru.aognev.webapp.storage;

import ru.aognev.webapp.model.*;
import ru.aognev.webapp.util.DateUtil;

import java.time.Month;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by aognev on 07.09.2016.
 */
public class TestData {
    public static final String PHONE = "+7(987)123-45-6";
    public static final String MOBILE = "+7(321)987-65-4";
    public static final String HOME_PHONE = "";
    public static final String SKYPE = "skype_user";
    public static final String MAIL = "@yandex.ru";
    public static final String LINKEDIN = "linkedin-user";
    public static final String GITHUB = "https://github.com/user";
    public static final String STACKOVERFLOW = "stackoverflow.user";
    public static final String HOME_PAGE = "www.user";

    public static final String PERSONAL = " personal";
    public static final String OBJECTIVE = " objective";
    public static final List<String> ACHIEVEMENT = new ArrayList<>();
    public static final List<String> QUALIFICATIONS = new ArrayList<>();
    public static final List<Organization> EXPERIENCE = new ArrayList<>();
    public static final List<Organization> EDUCATION = new ArrayList<>();

    public static final String UUID_1 = "uuid1";
    public static final Resume RESUME_1 = new Resume(UUID_1, "FullName1");

    public static final String UUID_2 = "uuid2";
    public static final Resume RESUME_2 = new Resume(UUID_2, "FullName2");

    public static final String UUID_3 = "uuid3";
    public static final Resume RESUME_3 = new Resume(UUID_3, "FullName3");

    public static final String UUID_4 = "uuid4";
    public static final Resume RESUME_4 = new Resume(UUID_4, "FullName4");

    static {
        RESUME_1.addContact(ContactType.PHONE, PHONE + '1');
        RESUME_1.addContact(ContactType.MOBILE, MOBILE + '1');
        RESUME_1.addContact(ContactType.HOME_PHONE, HOME_PHONE + '1');
        RESUME_1.addContact(ContactType.SKYPE, SKYPE + '1');
        RESUME_1.addContact(ContactType.MAIL, "user1" + MAIL);
        RESUME_1.addContact(ContactType.LINKEDIN, LINKEDIN + '1');
        RESUME_1.addContact(ContactType.GITHUB, GITHUB + '1');
        RESUME_1.addContact(ContactType.STACKOVERFLOW, STACKOVERFLOW + '1');
        RESUME_1.addContact(ContactType.HOME_PAGE, HOME_PAGE + "1.com");

        ACHIEVEMENT.clear();
        QUALIFICATIONS.clear();
        EXPERIENCE.clear();
        EDUCATION.clear();

        for (int i = 0; i < 7; i++) {
            ACHIEVEMENT.add("user1" + " achievement" + i);
            QUALIFICATIONS.add("user1" + " qualifications" + i);
            EXPERIENCE.add(new Organization(
                    "User1 organization" + i,
                    "www.user1-org" + i + ".com",
                    DateUtil.of(2000 + i, Month.of(i + 1)),
                    DateUtil.of(2000 + i + 1, Month.of(i + 1)),
                    "Title" + i,
                    "User1 experience description in organization" + i
            ));
        }

        EDUCATION.add(new Organization(
                "User1 University",
                "www.university-user1.edu",
                DateUtil.of(1994, Month.of(9)),
                DateUtil.of(1999, Month.of(7)),
                "User1-Specialist",
                ""
        ));

        RESUME_1.addSection(SectionType.PERSONAL, new TextSection("user1" + PERSONAL));
        RESUME_1.addSection(SectionType.OBJECTIVE, new TextSection("user1" + OBJECTIVE));
        RESUME_1.addSection(SectionType.ACHIEVEMENT, new ListSection(ACHIEVEMENT));
        RESUME_1.addSection(SectionType.QUALIFICATIONS, new ListSection(QUALIFICATIONS));

        RESUME_2.addContact(ContactType.PHONE, PHONE + '2');
        RESUME_2.addContact(ContactType.MOBILE, MOBILE + '2');
        RESUME_2.addContact(ContactType.HOME_PHONE, HOME_PHONE + '2');
        RESUME_2.addContact(ContactType.SKYPE, SKYPE + '2');
        RESUME_2.addContact(ContactType.MAIL, "user2" + MAIL);
        RESUME_2.addContact(ContactType.LINKEDIN, LINKEDIN + '2');
        RESUME_2.addContact(ContactType.GITHUB, GITHUB + '2');
        RESUME_2.addContact(ContactType.STACKOVERFLOW, STACKOVERFLOW + '2');
        RESUME_2.addContact(ContactType.HOME_PAGE, HOME_PAGE + "2.com");

        ACHIEVEMENT.clear();
        QUALIFICATIONS.clear();
        EXPERIENCE.clear();
        EDUCATION.clear();

        for (int i = 0; i < 7; i++) {
            ACHIEVEMENT.add("user2" + " achievement" + i);
            QUALIFICATIONS.add("user2" + " qualifications" + i);
            EXPERIENCE.add(new Organization(
                    "User2 organization" + i,
                    "www.user2org" + i + ".com",
                    DateUtil.of(2000 + i, Month.of(i + 1)),
                    DateUtil.of(2000 + i + 1, Month.of(i + 1)),
                    "Title" + i,
                    "User3 experience description in organization" + i
            ));
        }

        EDUCATION.add(new Organization(
                "User2 University",
                "www.university-user2.edu",
                DateUtil.of(1994, Month.of(9)),
                DateUtil.of(1999, Month.of(7)),
                "User2-Specialist",
                ""
        ));

        RESUME_2.addSection(SectionType.PERSONAL, new TextSection("user2" + PERSONAL));
        RESUME_2.addSection(SectionType.OBJECTIVE, new TextSection("user2" + OBJECTIVE));
        RESUME_2.addSection(SectionType.ACHIEVEMENT, new ListSection(ACHIEVEMENT));
        RESUME_2.addSection(SectionType.QUALIFICATIONS, new ListSection(QUALIFICATIONS));

        RESUME_3.addContact(ContactType.PHONE, PHONE + '3');
        RESUME_3.addContact(ContactType.MOBILE, MOBILE + '3');
        RESUME_3.addContact(ContactType.HOME_PHONE, HOME_PHONE + '3');
        RESUME_3.addContact(ContactType.SKYPE, SKYPE + '3');
        RESUME_3.addContact(ContactType.MAIL, "user3" + MAIL);
        RESUME_3.addContact(ContactType.LINKEDIN, LINKEDIN + '3');
        RESUME_3.addContact(ContactType.GITHUB, GITHUB + '3');
        RESUME_3.addContact(ContactType.STACKOVERFLOW, STACKOVERFLOW + '3');
        RESUME_3.addContact(ContactType.HOME_PAGE, HOME_PAGE + "3.com");

        ACHIEVEMENT.clear();
        QUALIFICATIONS.clear();
        EXPERIENCE.clear();
        EDUCATION.clear();

        for (int i = 0; i < 7; i++) {
            ACHIEVEMENT.add("user3" + " achievement" + i);
            QUALIFICATIONS.add("user3" + " qualifications" + i);
            EXPERIENCE.add(new Organization(
                    "User3 organization" + i,
                    "www.user3org" + i + ".com",
                    DateUtil.of(2000 + i, Month.of(i + 1)),
                    DateUtil.of(2000 + i + 1, Month.of(i + 1)),
                    "Title" + i,
                    "User3 experience description in organization" + i
            ));
        }

        EDUCATION.add(new Organization(
                "User3 University",
                "www.university-user3.edu",
                DateUtil.of(1994, Month.of(9)),
                DateUtil.of(1999, Month.of(7)),
                "User3-Specialist",
                ""
        ));

        RESUME_3.addSection(SectionType.PERSONAL, new TextSection("user3" + PERSONAL));
        RESUME_3.addSection(SectionType.OBJECTIVE, new TextSection("user3" + OBJECTIVE));
        RESUME_3.addSection(SectionType.ACHIEVEMENT, new ListSection(ACHIEVEMENT));
        RESUME_3.addSection(SectionType.QUALIFICATIONS, new ListSection(QUALIFICATIONS));

        RESUME_4.addContact(ContactType.PHONE, PHONE + '4');
        RESUME_4.addContact(ContactType.MOBILE, MOBILE + '4');
        RESUME_4.addContact(ContactType.HOME_PHONE, HOME_PHONE + '4');
        RESUME_4.addContact(ContactType.SKYPE, SKYPE + '4');
        RESUME_4.addContact(ContactType.MAIL, "user4" + MAIL);
        RESUME_4.addContact(ContactType.LINKEDIN, LINKEDIN + '4');
        RESUME_4.addContact(ContactType.GITHUB, GITHUB + '4');
        RESUME_4.addContact(ContactType.STACKOVERFLOW, STACKOVERFLOW + '4');
        RESUME_4.addContact(ContactType.HOME_PAGE, HOME_PAGE + "4.com");

        ACHIEVEMENT.clear();
        QUALIFICATIONS.clear();
        EXPERIENCE.clear();
        EDUCATION.clear();

        for (int i = 0; i < 7; i++) {
            ACHIEVEMENT.add("user4" + " achievement" + i);
            QUALIFICATIONS.add("user4" + " qualifications" + i);
            EXPERIENCE.add(new Organization(
                    "User4 organization" + i,
                    "www.user4org" + i + ".com",
                    DateUtil.of(2000 + i, Month.of(i + 1)),
                    DateUtil.of(2000 + i + 1, Month.of(i + 1)),
                    "Title" + i,
                    "User4 experience description in organization" + i
            ));
        }

        EDUCATION.add(new Organization(
                "User4 University",
                "www.university-user4.edu",
                DateUtil.of(1994, Month.of(9)),
                DateUtil.of(1999, Month.of(7)),
                "User4-Specialist",
                ""
        ));

        RESUME_4.addSection(SectionType.PERSONAL, new TextSection("user4" + PERSONAL));
        RESUME_4.addSection(SectionType.OBJECTIVE, new TextSection("user4" + OBJECTIVE));
        RESUME_4.addSection(SectionType.ACHIEVEMENT, new ListSection(ACHIEVEMENT));
        RESUME_4.addSection(SectionType.QUALIFICATIONS, new ListSection(QUALIFICATIONS));
    }
}
