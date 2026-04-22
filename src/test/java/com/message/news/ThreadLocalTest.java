package com.message.news;

import org.junit.jupiter.api.Test;

public class ThreadLocalTest {
    String name;

    @Test
    public void testThreadLocalTest() {

        ThreadLocalTest tl = new ThreadLocalTest();

        new Thread(() -> {
            tl.set("萧炎");
            System.out.println(Thread.currentThread().getName() + ": " + tl.get(name));
            System.out.println(Thread.currentThread().getName() + ": " + tl.get(name));
            System.out.println(Thread.currentThread().getName() + ": " + tl.get(name));
        }, "蓝色").start();

        new Thread(() -> {
            tl.set("药尘");
            System.out.println(Thread.currentThread().getName() + ": " + tl.get(name));
            System.out.println(Thread.currentThread().getName() + ": " + tl.get(name));
            System.out.println(Thread.currentThread().getName() + ": " + tl.get(name));
        }, "绿色").start();

    }

    private String get(String string) {
        return this.name;
    }

    private String set(String string) {
        this.name = string;
        return this.name;
    }
}
