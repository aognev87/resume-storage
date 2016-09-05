package ru.aognev.webapp;

import ru.aognev.webapp.model.Resume;

/**
 * Created by aognev on 31.08.2016.
 */
public class MainReflection {
    public static void main(String[] args) {
        Resume r = new Resume("FullName");

        System.out.println(r.toString());//Direct invoke toString

        try {
            System.out.println(r.getClass().getDeclaredMethod("toString").invoke(r));//Invoke toString with reflection
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
