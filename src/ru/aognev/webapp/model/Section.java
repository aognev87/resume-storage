package ru.aognev.webapp.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by aognev on 05.09.2016.
 */


public class Section {
    
    /*
    PERSONAL("Личные качества")
    OBJECTIVE("Позиция")
    ACHIEVEMENT("Достижения")
    QUALIFICATIONS("Квалификация")
    */
    private final List<String> strings = new ArrayList<>();

    /*
    EXPERIENCE("Опыт работы")
    EDUCATION("Образование")
    */
    private final List<Place> places = new ArrayList<>();

    public Section(String... strings) {
        this.strings.addAll(Arrays.asList(strings));
    }

    public Section(Place... places) {
        this.places.addAll(Arrays.asList(places));
    }

    public String getText() {
        if (strings.size() == 1) {
            return strings.get(0);
        }

        return null;
    }

    public List<String> getList() {
        return strings;
    }

    public List<Place> getPlaces() {
        return places;
    }
}