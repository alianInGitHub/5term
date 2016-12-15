package com.mycompany.app;

import java.util.Random;

/**
 * Created by anastasia on 07.12.16.
 */
public class SkipListTest {
    private volatile static Random random = new Random();

    private static class User implements Runnable{
        MySkipList list;

        public User(MySkipList list){
            this.list = list;
        }

        public void run() {
            for(int i = 0; i < 5; i++){
                int n = random.nextInt(100);
                list.add(n);
                System.out.print(Thread.currentThread().getName() + " added " + n + "\n" + list.getString() + "\n");
                try {
                    Thread.sleep(random.nextInt(2000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    private static class Reducer implements Runnable{
        MySkipList skipList;

        public Reducer(MySkipList skipList){
            this.skipList = skipList;
        }

        public void run() {
            for(int i = 0; i < 10; i++){
                int n = random.nextInt(100);
                if(skipList.contains(n)){
                    skipList.remove(n);
                    System.out.print(Thread.currentThread().getName() + " removed " + n + "\n" + skipList.getString() + "\n");
                }
                try {
                    Thread.sleep(random.nextInt(1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main( String[] args ) throws InterruptedException {
        //System.out.println( "Hello World!");
        MySkipList skipList = new MySkipList();
        User[] users = new User[5];
        Reducer[] reducers = new Reducer[5];
        Thread[] threads = new Thread[10];
        for(int i = 0; i < 5; i++){
            users[i] = new User(skipList);
            threads[i] = new Thread(users[i], new String("Thread-" + i));
            threads[i].start();
        }
        for(int i = 0; i < 5; i++){
            reducers[i] = new Reducer(skipList);
            threads[5 + i] = new Thread(reducers[i], new String("Thread-" + (5 + i)));
            threads[5 + i].start();
        }

        for (int i = 0; i < 10; i++){
            threads[i].join();
        }

        System.out.print("All threads has finished successfully.\n\n");

        System.out.print(skipList.getString() + "\n");
    }

}
