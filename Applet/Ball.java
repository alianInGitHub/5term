import java.awt.*;
import java.util.Random;

/**
 * Created by anastasia on 16.11.16.
 */
public class Ball implements Runnable{
    private Position pos;
    private Vector vector;
    private Graphics graphics;
    private Color color;
    public Ball(){
        pos = new Position();
        vector = new Vector();
    }
    public Ball(Position position, Vector vector, Graphics graphics, Color color){
        this.pos = position;
        this.vector = vector;
        this.graphics = graphics;
        this.color = color;
    }

    public Position getPos() {
        return pos;
    }

    public void setPos(Position pos) {
        this.pos = pos;
    }

    public Vector getVector() {
        return vector;
    }

    public void setVector(Vector vector) {
        this.vector = vector;
    }

    private void update(){
        pos.setI(pos.getI() + Applet.step * vector.getI());
        pos.setJ(pos.getJ() + Applet.step * vector.getJ());
        if((pos.getI() <= Applet.ballRadius) ||
                (pos.getI() >= Applet.sceneSize)){
            vector.setI(-vector.getI());
        }
        if((pos.getJ() <= Applet.ballRadius) ||
                (pos.getJ() >= Applet.sceneSize)){
            vector.setJ(-vector.getJ());
        }
    }

    private void draw(){
        synchronized (graphics){
            graphics.setColor(color);
            graphics.drawOval((int) (pos.getI() - Applet.ballRadius), (int) (pos.getJ() - Applet.ballRadius),
                    Applet.ballRadius, Applet.ballRadius);
        }
    }

    public String toString(){
        return new String(Ball.class.getName() + "\n" + pos.toString() + vector.toString());
    }

    @Override
    public void run() {
        update();
        draw();
    }
}
