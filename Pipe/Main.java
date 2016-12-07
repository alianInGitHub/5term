import java.io.*;
import java.util.Arrays;
import java.util.Properties;
import java.util.Scanner;

/**
 * Created by anastasia on 22.11.16.
 */
public class Main {

    public static boolean stopp = false;

    public static void preproccess(Process F, Process G) throws InterruptedException, IOException {
        ByteArrayOutputStream currentArrayF = new ByteArrayOutputStream();
        ByteArrayOutputStream currentArrayG = new ByteArrayOutputStream();
        Pipe pipeF = new Pipe(F.getInputStream(), currentArrayF);
        Pipe pipeG = new Pipe(G.getInputStream(), currentArrayG);
        if(stopp){
            F.destroy();
            G.destroy();
        }
        Thread t1 = new Thread(pipeF);
        Thread t2 = new Thread(pipeG);
        t1.start();
        t2.start();
        t1.join();
        t2.join();

        if((currentArrayF.toString() == null) || (currentArrayG.toString() == null)){
            throw new NullPointerException("The output is null");
        }

        BufferedReader readerF = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(currentArrayF.toByteArray())));
        BufferedReader readerG = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(currentArrayG.toByteArray())));

        int fx = Integer.valueOf(readerF.readLine());
        int gx = Integer.valueOf(readerG.readLine());
        if ((fx == 0) || (gx == 0)) {
            System.out.print("NULL\n");
        }

        System.out.print("f(x) * g(x) = " + fx + " * " + gx + " = " + gx * fx + "\n");

    }
    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("x = ");
            int x = Integer.valueOf(scanner.next());
            Runtime runtime = Runtime.getRuntime();

            String workdir = "//home/anastasia/Documents/IdeaProjects/Pipe/out/production/Pipe";
            Process F = runtime.exec("java processes/FirstReader " + x, null, new File(workdir));
            Process G = runtime.exec("java processes/SecondReader " + x, null, new File(workdir));

            System.out.print("Cancel?(Y/N)\n");
            String ans = scanner.next();
            if(ans.equals("Y") || ans.equals("y"))
            {
                Thread.sleep(1000);
                stopp = true;
                preproccess(F, G);
                break;
            }
            while (F.isAlive() || G.isAlive()){
                System.out.print("Waiting...\n");
                Thread.sleep(1000);
            }
            preproccess(F, G);

            System.out.print("Continue?(Y/N)");
            //System.out.print("Continue?(Y/N)");
            ans = scanner.next();
            if(ans.equals("N") || ans.equals("n"))
                break;
        }
    }
}
