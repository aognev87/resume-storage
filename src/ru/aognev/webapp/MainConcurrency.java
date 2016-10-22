package ru.aognev.webapp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by aognev on 15.10.2016.
 */
public class MainConcurrency {
    private static final int THREAD_COUNT = 10000;
    private final AtomicInteger atomicCounter = new AtomicInteger();
    private static int counter;

    private static final Object LOCK_A = new Object();
    private static final Object LOCK_B = new Object();
    private static final Lock LOCK = new ReentrantLock();

    private static final ThreadLocal<SimpleDateFormat> threadLocal = new ThreadLocal<>();

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

        CountDownLatch latch = new CountDownLatch(THREAD_COUNT);
        //ExecutorService executorService = Executors.newCachedThreadPool();
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        //CompletionService service = new ExecutorCompletionService(executorService);

        //List<Thread> threads = new ArrayList<>(THREAD_COUNT);

        MainConcurrency mainConcurrency = new MainConcurrency();

        for (int i = 0; i < THREAD_COUNT; i++) {
            //Thread thread = new Thread(() -> {
            executorService.submit(() -> {
                for (int j = 0; j < 100; j++) {
                    mainConcurrency.inc();
                }
                latch.countDown();
            });

            //thread.start();
            //threads.add(thread);
        }

        /*
        threads.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        */
        latch.await(5, TimeUnit.SECONDS);
        executorService.shutdown();

        System.out.println(mainConcurrency.atomicCounter.get());

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

        final String lock1 = "lock1";
        final String lock2 = "lock2";
        deadLock(lock1, lock2);
        deadLock(lock2, lock1);
    }

    private void inc() {
        /*LOCK.lock();
        try {
            counter++;
        } finally {
            LOCK.unlock();
        }*/

        atomicCounter.incrementAndGet();

    }

    private static void deadLock(String lock1, String lock2) {
        new Thread(() -> {
            System.out.println("Waiting " + lock1);
            synchronized (lock1) {
                System.out.println("Holding " + lock1);
                delay(50);
                System.out.println("Waiting " + lock2);
                synchronized (lock2) {
                    System.out.println("Holding " + lock2);
                }
            }
        }).start();
    }

    private static void deadLock0() {
        synchronized (LOCK_A) {
            delay(50);
            deadLock1();
        }
    }

    private static void deadLock1() {
        synchronized (LOCK_B) {
            delay(50);
            deadLock0();
        }

    }

    private static void delay(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
