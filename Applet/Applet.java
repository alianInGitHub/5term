import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by anastasia on 16.11.16.
 */
public class Applet extends java.applet.Applet {

    public static final int ballRadius = 50;
    public static final int sceneSize = 700;
    public static final float step = 1f;

    private Dimension dimension;
    private Image offscreen;
    private Graphics bufferGraphics;
    private ArrayList<Ball> balls;
    private Color[] colors;

    public void init(){
        colors = new Color[5];
        colors[0] = Color.BLUE;
        colors[1] = Color.CYAN;
        colors[2] = Color.GREEN;
        colors[3] = Color.MAGENTA;
        colors[4] = Color.ORANGE;

        setBackground(Color.white);
        dimension = getSize();
        offscreen = createImage(dimension.width, dimension.height);
        bufferGraphics = offscreen.getGraphics();
        balls = new ArrayList<>();
        Random rand = new Random();
        for(int i = 0; i < 11; i++){
            Ball b = new Ball(new Position((i * 2 * ballRadius + 50) % (sceneSize - ballRadius), (i * 2 * ballRadius + 50) % (sceneSize - ballRadius)),
                    new Vector((float) Math.sin(rand.nextDouble()), (float)Math.sin(rand.nextDouble())), bufferGraphics, colors[i % 5]);
            b.getVector().normalize();
            balls.add(b);
        }
    }

    private boolean collided(int i, int j){
        Position first = balls.get(i).getPos();
        Position second = balls.get(j).getPos();
        double distance = Math.sqrt(Math.pow(first.getI() - second.getI(), 2) + Math.pow(first.getJ() - second.getJ(), 2));
        if(distance - 0.5 <= ballRadius)
            return true;
        return false;
    }

    private static float projection(Vector a, Vector  b){
        return (a.getI() * b.getI() + a.getJ() * b.getJ()) / b.getLength();
    }

    private static void changeVectors(Ball a, Ball b){
        // for Vector a
        Vector x = new Vector(a.getPos().getI() - b.getPos().getI(), a.getPos().getJ() - b.getPos().getJ());
        x.normalize();
        float p = projection(a.getVector(), x);
        Vector projectionX = new Vector(x.getI() * p, x.getJ() * p);
        a.getVector().setI(- 2 * projectionX.getI() + a.getVector().getI());
        a.getVector().setJ(- 2 * projectionX.getJ() + a.getVector().getJ());
        a.getVector().normalize();

        //for vector b
        x = new Vector(-x.getI(), - x.getJ());
        p = projection(b.getVector(), x);
        projectionX = new Vector(x.getI() * p, x.getJ() * p);
        b.getVector().setI(- 2 * projectionX.getI() + b.getVector().getI());
        b.getVector().setJ(- 2 * projectionX.getJ() + b.getVector().getJ());
        b.getVector().normalize();
    }

    public void updateVectors(){
        boolean[] viewed = new boolean[balls.size()];
        for(int i = 0; i < balls.size(); i++){
            viewed[i] = true;

            for (int j = 0; j < balls.size(); j++){
                if(!viewed[j] && collided(i, j)){
                    changeVectors(balls.get(i), balls.get(j));
                    viewed[j] = true;
                }
            }
        }
    }

    public void update(Graphics g){
        Graphics2D graphics = (Graphics2D)bufferGraphics;
        graphics.setStroke(new BasicStroke(3));
        graphics.clearRect(0,0,sceneSize,sceneSize);
        graphics.setColor(Color.darkGray);
        graphics.drawRect(0,0,sceneSize,sceneSize);

        Thread[] threads = new Thread[balls.size()];
        for(int i = 0; i < balls.size(); i++){
            threads[i] = new Thread(balls.get(i));
            threads[i].start();
        }
        try {
            for(int i = 0; i < balls.size(); i++)
                threads[i].join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        g.drawImage(offscreen, 0, 0, this);
        updateVectors();
    }

    public void paint(Graphics graphics){
        while (true) {
            update(graphics);
        }
    }
}
