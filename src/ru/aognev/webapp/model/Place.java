package ru.aognev.webapp.model;

/**
 * Created by aognev on 05.09.2016.
 */

/*
EXPERIENCE("Опыт работы"),
EDUCATION("Образование");
 */
public class Place {
    private String placeName;

    private String started;

    private String finished;

    private String position;

    private String text;

    public Place(String placeName, String started, String finished, String position, String text) {
        this.placeName = placeName;
        this.started = started;
        this.finished = finished;
        this.position = position;
        this.text = text;
    }

    public String getPlaceName() {
        return placeName;
    }

    public String getStarted() {
        return started;
    }

    public String getFinished() {
        return finished;
    }

    public String getPosition() {
        return position;
    }

    public String getText() {
        return text;
    }
}
