import java.util.NoSuchElementException;

/**
 * Created by anastasia on 10.10.16.
 */

/**
 * ai*yi-1 + ci*yi + bi*yi+1 = fi
 */
public class TridiagonalLinearSystem extends Thread {
    private float[][] matrix;

    private int colNum, rowNum;

    private float[] solution;
    float[] iAlpha, iBeta;

    private boolean method = true;      // true - up-dow, false - down-up
    private boolean flag = true;

    TridiagonalLinearSystem(float[][] XY, int colNum, int rowNum, boolean method){
        this.matrix = XY;
        this.colNum = colNum;
        this.rowNum = rowNum;
        this.method = method;
        solution = new float[this.rowNum];
        iAlpha = new float[this.rowNum];
        iBeta = new float[this.rowNum];
    }

    float a(int i){
        if(method)
        return matrix[i - 1][i];
        int j = i - rowNum + colNum;
        return matrix[j - 1][i];
    }

    float c(int i){
        if (method)
        return matrix[i][i];
        int j = i - rowNum + colNum;
        return matrix[j][i];
    }

    float b(int i){
        if(method)
        return matrix[i + 1][i];
        int j = i - rowNum + colNum;
        return matrix[j + 1][i];
    }

    float f(int i){
        return matrix[colNum][i];
    }

    float alpha(int i, float alphaPrev){
        if(method){
            if(i == 1){
                return -b(i - 1) / c(i - 1);
            }

            return - b(i - 1) / (a(i - 1) * alphaPrev + c(i - 1));
        }else
        {
            if(i == rowNum - 1){
                return -a(i) / c(i);
            }
            float p = c(i);
            p = b(i);
            return -a(i) / (c(i) + b(i) * alphaPrev);
        }
    }

    float beta(int i, float betaPrev, float alphaPrev){
        if(method){
            if(i == 1){
                return f(i - 1) / c(i - 1);
            }
            return (f(i - 1) - a(i - 1) * betaPrev) / (c(i - 1) + a(i - 1) * alphaPrev);
        }else {
            if(i == rowNum - 1){
                return f(i) / c(i);
            }

            return (f(i) - betaPrev * b(i)) / (c(i) + b(i) * alphaPrev);
        }
    }

    public void putPriorSolution(float value){
        if(method)
            solution[rowNum - 1] = value;
        else
            solution[0] = value;
    }

    public float[] getiAlpha(){
        return iAlpha;
    }

    public  float[] getiBeta(){
        return iBeta;
    }

    public float[] getSolution(){
        return solution;
    }

    public void run(){
        if(matrix == null){
            throw new NullPointerException();
        }
        if(flag){
            if(method){
                System.out.print(rowNum + "\n");
                iAlpha[0] = alpha(1, 0);
                iBeta[0] = beta(1, 0, 0);
                for(int i = 1; i < rowNum ; i++){
                    iAlpha[i] = alpha(i + 1, iAlpha[i - 1]);
                    iBeta[i] = beta(i + 1, iBeta[i - 1], iAlpha[i - 1]);
                }
            } else {
                iAlpha[rowNum - 1] = alpha(rowNum - 1, 0);
                iBeta[rowNum - 1] = beta(rowNum - 1, 0, 0);
                for (int i = rowNum - 2; i >= 0; i--) {
                    iAlpha[i] = alpha(i, iAlpha[i + 1]);
                    iBeta[i] = beta(i,iBeta[i + 1], iAlpha[i + 1]);
                }
            }
        } else {
            if(method){
                //we put the first x into solution earlier
                for (int i = rowNum - 2; i >= 0; i--){
                    solution[i] = iAlpha[i] * solution[i + 1] + iBeta[i];
                }
            } else {
                for(int i = 1; i < rowNum; i++)
                solution[i] = iAlpha[i] * solution[i - 1] + iBeta[i];
            }
        }
        flag = !flag;
    }


    public void showValues(){
        for(int i = 0; i < rowNum; i++){
            System.out.print(iAlpha[i] + " ");
        }
        System.out.print("\n");
        for(int i = 0; i < rowNum; i++){
            System.out.print(iBeta[i] + " ");
        }
        System.out.print("\n");
    }

}
