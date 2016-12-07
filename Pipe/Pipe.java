import java.io.*;

/**
 * Created by anastasia on 22.11.16.
 */
public class Pipe implements Runnable{
    private InputStream input;
    private OutputStream output;

    public Pipe(InputStream input, OutputStream output){
        this.input = input;
        this.output = output;
    }

    @Override
    public void run(){
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            String s = reader.readLine();
            int r = Integer.parseInt(s);
            output.write(String.valueOf(r).getBytes());

        } catch (IOException e) {
            System.out.print("Pipe has been broken\n" + e.getMessage() + "\n");
        }
        finally {
            try {
                input.close();
                output.close();
            } catch (IOException e) {
                System.out.print("Failed to close\n");
            }
        }
    }
}
