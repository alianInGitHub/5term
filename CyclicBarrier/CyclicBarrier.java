
/**
 * Created by anastasia on 18.11.16.
 */
public class CyclicBarrier {
    private int threadsNum; 
    private int threadsAwait; //threadsNum yet to arrive
    private Runnable cyclicBarrierEvent;

    public CyclicBarrier(int threadsNum, Runnable cyclicBarrierEvent) {
        this.threadsNum = threadsNum;
        this.threadsAwait = threadsNum;
        this.cyclicBarrierEvent=cyclicBarrierEvent;
    }

    public synchronized void await() throws InterruptedException {
        threadsAwait--;
        if(threadsAwait>0){
            this.wait();
        }
        else{
            threadsAwait=threadsNum;
            notifyAll();
            cyclicBarrierEvent.run();
        }
    }
}
