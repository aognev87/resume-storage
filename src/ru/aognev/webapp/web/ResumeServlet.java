package ru.aognev.webapp.web;

import ru.aognev.webapp.Config;
import ru.aognev.webapp.model.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by aognev on 08.11.2016.
 */
public class ResumeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "text/html; charset=UTF-8");
        /*String name = request.getParameter("name");
        response.getWriter().write(name == null ? "Hello Resumes!" : "Hello " + name + '!');*/
        String uuid = request.getParameter("uuid");
        if (uuid != null) {
            Resume resume = Config.get().getStorage().get(uuid);
            response.getWriter().write(createHtmlPage(resume));
        }
    }

    private String createHtmlPage(Resume resume) {
        StringBuilder sb = new StringBuilder();
        sb.append(
                "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Title</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<table border=\"1\">"
        );

        sb.append(addTableRaw("Full name", resume.getFullName()));

        for (Map.Entry<ContactType, String> e : resume.getContacts().entrySet()) {
            sb.append(addTableRaw(e.getKey().getTitle(), e.getValue()));
        }

        Section objective = resume.getSection(SectionType.OBJECTIVE);
        Section personal = resume.getSection(SectionType.PERSONAL);
        Section achievement = resume.getSection(SectionType.ACHIEVEMENT);
        Section qualifications = resume.getSection(SectionType.QUALIFICATIONS);

        if (objective != null) {
            sb.append(addTableRaw(SectionType.OBJECTIVE.getTitle(), ((TextSection)objective).getContent()));
        }

        if (personal != null) {
            sb.append(addTableRaw(SectionType.PERSONAL.getTitle(), ((TextSection)personal).getContent()));
        }

        if (achievement != null) {
            sb.append(addTableRaw(SectionType.ACHIEVEMENT.getTitle(), ((ListSection)achievement).getItems()));
        }

        if (qualifications != null) {
            sb.append(addTableRaw(SectionType.QUALIFICATIONS.getTitle(), ((ListSection)qualifications).getItems()));
        }

        sb.append(
                "</table>" +
                "</body>\n" +
                "</html>");

        return sb.toString();
    }

    private String addTableRaw(String caption, String value) {
        return makeRaw(caption, value);
    }

    private String addTableRaw(String caption, List<String> items) {
        StringBuilder sb = new StringBuilder();

        sb.append("<ul>").append("\n");
        for (String s : items) {
            sb.append("<li>").append(s).append("</li>").append('\n');
        }
        sb.append("</ul>").append("\n");

        return makeRaw(caption, sb.toString());
    }

    private String makeRaw(String caption, String content) {
        StringBuilder sb = new StringBuilder();
        sb.append("<tr>").append('\n');
        sb.append("<td>").append(caption).append("</td>").append('\n');
        sb.append("<td>").append(content).append("</td>").append('\n');
        sb.append("</tr>").append('\n');
        return sb.toString();
    }
}