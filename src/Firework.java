package src;

public class Firework {
    private int width;
    private int height;

    private int x;
    private int y;
    private int a;
    private float start;
    private float speed;

    public Firework(int width, int height, int a, float speed,float start) {
        this.speed = speed;
        this.width = width;
        this.height = height;
        this.a = a;
        this.start = start;

    }

    public boolean move (float time){
        float t = (time-start)*0.01f;
        System.out.println(t);
        x = (int) (speed *(float)Math.cos(Math.toRadians(a))*(t));
        y= (int) -((-9.81f/2)*t*t+speed*Math.sin(Math.toRadians(a)*t));
        System.out.println("X: "+x);
        System.out.println("Y: "+y);
        speed*=0.991f;

        if (y>height+200){
            return false;
        }
        return true;
    }


    public int getX(){
        return x;
    }
    public int getY(){ return y;}

}
