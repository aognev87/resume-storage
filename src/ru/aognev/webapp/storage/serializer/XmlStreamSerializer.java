package ru.aognev.webapp.storage.serializer;

import ru.aognev.webapp.exception.StorageException;
import ru.aognev.webapp.model.*;
import ru.aognev.webapp.util.XmlParser;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * Created by aognev on 12.09.2016.
 */
public class XmlStreamSerializer implements StreamSerializer {
    private XmlParser xmlParser;

    public XmlStreamSerializer() {
        this.xmlParser = new XmlParser(
                Resume.class, Organization.class,
                OrganizationSection.class, Organization.Position.class,
                Link.class, TextSection.class, ListSection.class);
    }

    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (Writer writer = new OutputStreamWriter(os, StandardCharsets.UTF_8)) {
            xmlParser.marshall(r, writer);
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (Reader reader = new InputStreamReader(is, StandardCharsets.UTF_8)) {
            return (Resume) xmlParser.unmarshall(reader);
        }
    }
}
