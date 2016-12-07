package processes;

import java.io.*;

/**
 * Created by anastasia on 22.11.16.
 */
public class FirstReader {
    public static int f(int x){
        return (int) (Math.pow(x, 3) * 3 + 2 * x - 4);
    }
    public static void main(String[] args) throws IOException, InterruptedException {
        int x = Integer.parseInt(args[0]);
        BufferedWriter writer = new BufferedWriter(new FileWriter("data.txt"));
        writer.write(String.valueOf(f(x)));
        writer.close();
        Thread.sleep(1000);
        System.out.print(f(x));
    }
}
