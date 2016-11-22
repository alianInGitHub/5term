import java.util.Scanner;
import java.util.concurrent.BrokenBarrierException;

/**
 * Created by anastasia on 18.11.16.
 */
public class Main {
    public static class Task implements Runnable{
        private CyclicBarrier barrier;

        public Task(CyclicBarrier barrier){
            this.barrier = barrier;
        }

        @Override
        public void run() {
            try {
                System.out.print(Thread.currentThread().getName() + " has reached the barrier\n");
                barrier.await();
                System.out.print(Thread.currentThread().getName() + " has crossed the barrier\n");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    private static  class Temp implements Runnable{

        @Override
        public void run() {
            System.out.print("Let's play!\n");
        }
    }

    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Threads number = ");
        int threadsNum = Integer.valueOf(scanner.next());
        CyclicBarrier barrier = new CyclicBarrier(threadsNum, new Temp());
        Task t = new Task(barrier);
        for(int i = 0; i < threadsNum; i++){
            (new Thread(t, "Thread " + (i + 1))).start();
        }
    }
}
