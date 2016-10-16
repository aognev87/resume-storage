package ru.aognev.webapp.util;

/**
 * Created by aognev on 16.10.2016.
 */
public class LazySingleton {
    volatile private static LazySingleton instance = new LazySingleton();

    private LazySingleton() {
    }

    private static class LazySingletonHolder {
        private static final LazySingleton INSTANCE = new LazySingleton();
    }

    public static LazySingleton getInstance() {
        /*
        if (instance == null) {
            synchronized (LazySingleton.class) {
                if (instance == null) {
                    instance = new LazySingleton();
                }
            }
        }

        return instance;
        */

        return LazySingletonHolder.INSTANCE;
    }


}
