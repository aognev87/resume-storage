package ru.aognev.webapp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aognev on 15.10.2016.
 */
public class MainConcurrency {
    private static final int THREAD_COUNT = 5000;

    private static final Object LOCK_A = new Object();
    private static final Object LOCK_B = new Object();

    private static int counter;

    public static void main(String[] args) throws InterruptedException {
        System.out.println(Thread.currentThread().getName());

        Thread thread0 = new Thread() {
            @Override
            public void run() {
                System.out.println(getName() + ", " + getState());
            }
        };

        thread0.start();

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + ", " + Thread.currentThread().getState());
            }
        });

        thread1.start();

        List<Thread> threads = new ArrayList<>(THREAD_COUNT);

        for (int i = 0; i < THREAD_COUNT; i++) {
            Thread thread = new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    inc();
                }
            });

            thread.start();
            threads.add(thread);
        }

        threads.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        System.out.println(counter);

        thread0 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 1000; i++) {
                    deadLock0();
                }
            }
        });

        thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 1000; i++) {
                    deadLock1();
                }
            }
        });

        thread0.start();
        thread1.start();

        Thread.sleep(500);

        System.out.println(thread0.getName() + ", " + thread0.getState());
        System.out.println(thread1.getName() + ", " + thread1.getState());
    }

    private static synchronized void inc() {
        counter++;
    }

    private static void deadLock0() {
        synchronized (LOCK_A) {
            delay();
            deadLock1();
        }
    }

    private static void deadLock1() {
        synchronized (LOCK_B) {
            delay();
            deadLock0();
        }

    }

    private static void delay() {
        for (int i = 0; i < 10; i++) {
            double sin = Math.sin(0.8);
        }
    }
}
