import java.util.Random;
import java.util.Scanner;

/**
 * Created by anastasia on 22.11.16.
 */
public class PhaserTest {
    public static Random random = new Random(42);
    private static class Event implements Runnable{
        @Override
        public void run() {
            System.out.print("Let's play!\n");
        }
    }

    public static class Task1 implements Runnable{
        private Phaser phaser;

        public Task1(Phaser phaser){
            this.phaser = phaser;
        }

        @Override
        public void run() {
            try {
                int i = Math.abs(random.nextInt() / 300000);
                Thread.sleep(i);
                System.out.print(Thread.currentThread().getName() + " has reached the phaser\n");
                phaser.arrive();
                System.out.print("Arrived: " + phaser.getArrived() + "\n");
                System.out.print(Thread.currentThread().getName() + " has crossed the phaser\n");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static class Task2 implements Runnable{
        private Phaser phaser;

        public Task2(Phaser phaser){
            this.phaser = phaser;
        }

        @Override
        public void run() {
            try {
                int i = Math.abs(random.nextInt() / 300000);
                Thread.sleep(i);
                System.out.print(Thread.currentThread().getName() + " has reached the phaser and is waiting\n");
                phaser.arriveAndWaitAdvance();
                System.out.print(Thread.currentThread().getName() + " has crossed the phaser\n");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    public static class Task3 implements Runnable{
        private Phaser phaser;

        public Task3(Phaser phaser){
            this.phaser = phaser;
        }

        @Override
        public void run() {
            try {
                int i = Math.abs(random.nextInt() / 300000);
                Thread.sleep(i);
                System.out.print(Thread.currentThread().getName() + " has arived and deregistered the phaser\n");
                phaser.arriveAndDeregister();
                System.out.print("Parties: " + phaser.getParties() + "\n");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }



    public static void main(String[] args) throws InterruptedException {
        Phaser phaser = new Phaser(6);
        Event event = new Event();
        phaser.setPhaserEvent(event);
        Runnable[] tasks = new Runnable[6];
        Thread[] threads = new Thread[6];
        for(int i = 0; i < 2; i++){
            tasks[i] = new Task1(phaser);
        }
        for(int i = 2; i < 4; i++)
            tasks[i] = new Task2(phaser);
        for(int i = 4; i < 6; i++)
            tasks[i] = new Task3(phaser);
        for(int i = 0 ; i < 6; i++){
            threads[i] = new Thread(tasks[i], "Thread" + (i + 1));
            threads[i].start();
        }
        for(int i = 0; i < 6; i++){
            threads[i].join();
        }
        System.out.print("Continue?(Y/N) ");
        Scanner scanner = new Scanner(System.in);
        String s = scanner.next();
        if(s.equals("N") || s.equals("n"))
            return;
        phaser.register();
        int n = phaser.getParties();
        for(int i = 0; i < n; i++){
            threads[i] = new Thread(tasks[(i + 2) % n]);
            threads[i].start();
        }
        for(int i = 0; i < n; i++){
            threads[i].join();
        }
    }
}