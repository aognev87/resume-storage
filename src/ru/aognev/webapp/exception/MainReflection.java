package ru.aognev.webapp.exception;

import ru.aognev.webapp.model.Resume;

/**
 * Created by aognev on 31.08.2016.
 */
public class MainReflection {
    public static void main(String[] args) {
        Resume r = new Resume();

        System.out.println(r.toString());//Direct invoke toString

        try {
            System.out.println(r.getClass().getDeclaredMethod("toString").invoke(r));//Invo
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
