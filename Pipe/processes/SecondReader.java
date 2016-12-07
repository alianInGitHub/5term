package processes;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Created by anastasia on 07.12.16.
 */
public class SecondReader {
    public static void main(String[] args) throws IOException, InterruptedException {
        int x = Integer.parseInt(args[0]);
        //x = Integer.parseInt(args[0]);
        Thread.sleep(20000);
        System.out.print(g(x));
    }

    private static int g(int x) {
        return (int) (Math.pow(x * 3, 2) - 24 * x);
    }
}
