package ru.aognev.webapp.storage.serializer;

import ru.aognev.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by aognev on 12.09.2016.
 */
public class DataStreamSerializer implements StreamSerializer {

    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());

            Map<ContactType, String> contacts = r.getContacts();
            dos.writeInt(contacts.size());

            for (Map.Entry<ContactType, String> entry: contacts.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                writeStr(dos, entry.getValue());
            }

            writeSections(r.getSections(), dos);
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();

            Resume resume = new Resume(uuid, fullName);

            int size = dis.readInt();
            for (int i = 0; i < size; i++) {
                resume.addContact(ContactType.valueOf(dis.readUTF()), readStr(dis));
            }

            readSections(resume, dis);

            return resume;
        }
    }

    private void writeSections (Map<SectionType, Section> sections, DataOutputStream dos) throws IOException {
        dos.writeInt(sections.size());

        for (Map.Entry<SectionType, Section> entry: sections.entrySet()) {
            SectionType type = entry.getKey();
            dos.writeUTF(type.name());

            Section section = entry.getValue();

            switch (type) {
                case PERSONAL:
                case OBJECTIVE:
                    writeStr(dos, ((TextSection) section).getContent());
                    break;

                case ACHIEVEMENT:
                case QUALIFICATIONS:
                    List<String> items = ((ListSection) section).getItems();
                    dos.writeInt(items.size());

                    for (String item: items) {
                        writeStr(dos, item);
                    }

                    break;

                case EXPERIENCE:
                case EDUCATION:
                    List<Organization> organizations = ((OrganizationSection) section).getOrganizations();
                    dos.writeInt(organizations.size());

                    for (Organization organization: organizations) {
                        writeStr(dos, organization.getHomePage().getName());
                        writeStr(dos, organization.getHomePage().getUrl());

                        List<Organization.Position> positions = organization.getPositions();

                        dos.writeInt(positions.size());

                        for (Organization.Position position: positions) {
                            dos.writeUTF(position.getStartDate().toString());
                            dos.writeUTF(position.getEndDate().toString());
                            writeStr(dos, position.getTitle());
                            writeStr(dos, position.getDescription());
                        }
                    }

                    break;
            }
        }
    }

    private void readSections(Resume r, DataInputStream dis) throws IOException {
        int sectionsSize = dis.readInt();

        for (int i = 0; i < sectionsSize; i++) {
            SectionType type = SectionType.valueOf(dis.readUTF());

            switch (type) {
                case PERSONAL:
                case OBJECTIVE:
                    r.addSection(type, new TextSection(readStr(dis)));
                    break;

                case ACHIEVEMENT:
                case QUALIFICATIONS:
                    int itemSize = dis.readInt();
                    List<String> items = new ArrayList<>();

                    for (int j = 0; j < itemSize; j++) {
                        items.add(readStr(dis));
                    }
                    r.addSection(type, new ListSection(items));
                    break;

                case EXPERIENCE:
                case EDUCATION:
                    int organizationsSize = dis.readInt();
                    List<Organization> organizations = new ArrayList<>();

                    for (int j = 0; j < organizationsSize; j++) {
                        String name = readStr(dis);
                        String url = readStr(dis);

                        int positionsSize = dis.readInt();
                        List<Organization.Position> positions = new ArrayList<>();

                        for (int k = 0; k < positionsSize; k++) {
                            positions.add(
                                new Organization.Position(
                                        LocalDate.parse(dis.readUTF()),
                                        LocalDate.parse(dis.readUTF()),
                                        readStr(dis),
                                        readStr(dis)
                                ));
                        }

                        organizations.add(new Organization(new Link(name, url), positions));
                    }

                    r.addSection(type, new OrganizationSection(organizations));
            }
        }
    }

    private void writeStr(DataOutputStream dos, String str) throws IOException {
        if (str == null) {
            dos.writeUTF("null");
        } else {
            dos.writeUTF(str);
        }
    }

    private String readStr(DataInputStream dis) throws IOException {
        String str = dis.readUTF();

        if (str.equals("null")) {
            return null;
        }

        return str;
    }
}
