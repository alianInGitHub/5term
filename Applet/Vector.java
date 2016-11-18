import java.util.Map;

/**
 * Created by anastasia on 16.11.16.
 */
public class Vector extends Position {
    private float length;
    public Vector(){super();
    length = 0;}
    public Vector(float i, float j){ super(i,j); calcLength();}

    public void normalize(){
        double length = Math.sqrt(Math.pow(getI(), 2) + Math.pow(getJ(), 2));
        super.setI((float) (getI() / length));
        super.setJ((float) (getJ() / length));
        this.length = 1;
    }
    private void calcLength(){
        length = (float)(Math.sqrt(Math.pow(getI(), 2) + Math.pow(getJ(), 2)));
    }
    public float getLength(){
        return length;
    }

    public void setJ(float j) {
        super.setJ(j);
        calcLength();
    }

    public void setI(float i) {
        super.setI(i);
        calcLength();
    }
}
