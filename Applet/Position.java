/**
 * Created by anastasia on 16.11.16.
 */
public class Position {
    private float i, j;
    public Position(){
        i = j = 0;
    }
    public Position(float i, float j){
        this.i = i;
        this.j = j;
    }

    public float getI() {
        return i;
    }

    public void setI(float i) {
        this.i = i;
    }

    public float getJ() {
        return j;
    }

    public void setJ(float j) {
        this.j = j;
    }

    public String toString(){
        return new String(Position.class.getName() + ": " + i + ", " + j +"\n");
    }
}
