package ru.netology;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static AtomicInteger counter1 = new AtomicInteger();
    public static AtomicInteger counter2 = new AtomicInteger();
    public static AtomicInteger counter3 = new AtomicInteger();

    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }
        List<Thread> threads = new ArrayList<>();
        Thread palindromeThread = new Thread(() -> {
            for (String text : texts) {
                if (isPalindrome(text)) {
                    incrementCounter(text.length());
                }
            }
        });

        palindromeThread.start();
        threads.add(palindromeThread);

        Thread repeatedThread = new Thread(() -> {
            for (String text : texts) {
                if (wordWithSameLetter(text)) {
                    incrementCounter(text.length());
                }
            }
        });
        repeatedThread.start();
        threads.add(repeatedThread);

        Thread alphabetThread = new Thread(() -> {
            for (String text : texts) {
                if (isAscending(text)){
                    incrementCounter(text.length());
                }
            }
        });
        alphabetThread.start();
        threads.add(alphabetThread);
        for (Thread thr : threads) {
            thr.join();
        }
        System.out.println("Красивых слов с длиной 3: \n" + counter1);
        System.out.println("Красивых слов с длиной 4: \n" + counter2);
        System.out.println("Красивых слов с длиной 5: \n" + counter3);

    }


    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    public static boolean isPalindrome(String text) {
        return text.equals(new StringBuilder(text).reverse().toString());

    }

    public static boolean wordWithSameLetter(String text) {
        for (int i = 1; i < text.length(); i++) {
            if (text.charAt(i) != text.charAt(i - 1))
                return false;
        }
        return true;
    }

    public static boolean isAscending(String text) {
        for (int i = 1; i < text.length(); i++) {
            if (text.charAt(i) < text.charAt(i - 1))
            return false;
        }
        return true;
    }
    public static void incrementCounter(int textLength) {
        if (textLength == 3) {
            counter1.getAndIncrement();
        } else if (textLength == 4) {
            counter2.getAndIncrement();
        } else {
            counter3.getAndIncrement();
        }
    }
}
