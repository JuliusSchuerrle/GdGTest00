package src;

public class Firework {
    private int width;
    private int height;

    private int x;
    private int y;
    private int a;
    private float start;
    private float speed;

    public Firework(int width, int height, int a, int speed,float start) {
        this.width = width;
        this.height = height;
        this.a = a;
        this.start = start;

    }

    public boolean move (float time){
        x = (int) (speed *(float)Math.cos(a)*(time-start));
        y = (int) (10/2*speed*speed)*x*x;

        if (y>height+200){
            return false;
        }
        return true;
    }

    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
}
