/**
 * Created by anastasia on 18.11.16.
 */
public class Phaser {

    /*..............................PRIVATE FIELDS..............................*/
    private int phase;
    private int parties;
    private int arrived, unarrived;
    private Runnable phaserEvent;

    /*.............................PRIVATE METHODS..............................*/

    private void phaserNotify() throws InterruptedException {
        arrived = 0;
        unarrived = parties;
        phase++;
        Thread t = new Thread(phaserEvent);
        t.start();
        t.join();
        notifyAll();
    }


    /*..............................PUBLIC METHODS..............................*/

    public Phaser(int parties){
        phase = 0;
        this.parties = parties;
        arrived = 0;
        unarrived = parties;
    }

    public synchronized void register(){
        parties++;
        unarrived++;
    }
    public synchronized void arrive() throws InterruptedException {
        if(arrived < parties){
            arrived++;
            unarrived--;
        } else
            phaserNotify();
    }
    public synchronized void arriveAndWaitAdvance() throws InterruptedException {
        arrived++;
        if(arrived < parties)
            this.wait();
        else
            phaserNotify();
    }
    public synchronized void arriveAndDeregister() throws InterruptedException {
        parties--;
        if(arrived < parties)
            unarrived--;
        else
            phaserNotify();
    }

    public int getPhase() {
        return phase;
    }

    public synchronized int getParties() {
        return parties;
    }

    public synchronized int getArrived() {
        return arrived;
    }

    public synchronized int getUnarrived() {
        return unarrived;
    }

    public void setPhaserEvent(Runnable phaserEvent) {
        this.phaserEvent = phaserEvent;
    }
}
