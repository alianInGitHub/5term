import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by anastasia on 10.10.16.
 */
class LinearSystem {

    private int max(int a, int b){
        return (a > b) ? a : b;
    }

    private float[] calculate(float a, float b, float e, float n){
        float[] xy = new float[2];
        float p1 = 1.0f - a * e;
        float p2 = a * n + b;
        xy[0] = p2 / p1;
        xy[1] = e * xy[0] + n;
        return xy;
    }

    public float[] getSolution(float[][] X, float[] Y, int n) throws InterruptedException {
        float[] solution = new float[n];
        int nUp = n / 2;
        int nLo = n - nUp;
        float[][] XUpper = new float[n + 1][nUp];
        float[][] XLower = new float[n + 1][nLo];
        for(int i = 0; i < n; i++) {
            for (int j = 0; j < nUp; j++) {
                XUpper[i][j] = X[i][j];
                XLower[i][j] = X[i][nUp + j];
            }
            if(nUp < nLo) XLower[i][nLo - 1] = X[i][n - 1];
        }
        for(int j = 0; j < nUp; j++){
            XUpper[n][j] = Y[j];
            XLower[n][j] = Y[nUp + j];
        }
        if(nUp < nLo) XLower[n][nLo - 1] = Y[n - 1];

        for(int j = 0; j < nUp; j++) {
            for (int i = 0; i < n + 1; i++)
                System.out.print(XUpper[i][j] + " ");
            System.out.print("\n");
        }
        for(int j = 0; j < nLo; j++) {
            for (int i = 0; i < n + 1; i++)
                System.out.print(XLower[i][j] + " ");
            System.out.print("\n");
        }

        TridiagonalLinearSystem UpperSolution = new TridiagonalLinearSystem(XUpper, n, nUp, true);
        TridiagonalLinearSystem LowerSolution = new TridiagonalLinearSystem(XLower, n, nLo, false);
        UpperSolution.start();
        LowerSolution.start();
        UpperSolution.join();
        LowerSolution.join();

        //show alpha and beta
        UpperSolution.showValues();
        LowerSolution.showValues();

        //calculate middle values
        float [] xy = calculate(UpperSolution.getiAlpha()[nUp - 1], UpperSolution.getiBeta()[nUp - 1],
                LowerSolution.getiAlpha()[0], LowerSolution.getiBeta()[0]);
        System.out.print("Up = " + xy[0] + "\t" + xy[1] + "\n");
        UpperSolution.putPriorSolution(xy[0]);
        LowerSolution.putPriorSolution(xy[1]);
        UpperSolution.run();
        LowerSolution.run();
        UpperSolution.join();
        LowerSolution.join();

        float[] temp = UpperSolution.getSolution();
        for(int i = 0; i < nUp; i++){
            solution[i] = temp[i];
        }
        temp = LowerSolution.getSolution();
        for(int i = 0; i < nLo; i++){
            solution[i + nUp] = temp[i];
        }
       return solution;
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        //read data
        float[][] X;
        float[] Y;
        int n = 0;
        FileReader file = new FileReader("data.txt");
        BufferedReader reader = new BufferedReader(file);
        String s = reader.readLine();
        n = Integer.valueOf(s);
        X = new float[n][n];
        Y = new float[n];
        int j = 0;
        while ((s = reader.readLine()) != null){
            String[] strings = s.split(" ");
            if(strings.length != (n + 1)){
                throw new IOException("The input is not valid. Check your database for a typo.");
            }
            for(int i = 0; i < n; i++)
                X[i][j] = Float.valueOf(strings[i]);
            Y[j] = Float.valueOf(strings[n]);
            j++;
        }

        LinearSystem ls = new LinearSystem();
        float[] solution = ls.getSolution(X, Y, n);
        for(int i = 0; i < solution.length; i++){
            System.out.print(solution[i] + " ");
        }

        System.out.print("\nChecking : \n");
        for(int i = 0; i < n; i++){
            float res = 0;
            for(j = 0; j < n; j++)
                res += solution[j] * X[j][i];
            System.out.print(res + " = " + Y[i] + "\n");
        }
        System.out.println();
    }
}
