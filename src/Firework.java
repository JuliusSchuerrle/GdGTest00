package src;

import processing.core.PApplet;
import src.data.TimeLookupTable;

import java.util.ArrayList;

public class Firework {
    private static ArrayList <Firework> fireworks = new ArrayList<>();
    private static int counter=0;

    private int width;
    private int height = 1080;
    private int x;
    private int y;
    private int a;
    private float start;
    private float speed;
    private final int radius = 50;
    private ArrayList<int[]> tail = new ArrayList<>();
    private PApplet applet;

    private float strechX;
    private float strechY;
    private float verschiebung;
    private int direction;

//    public Firework(int a, float speed, float start, PApplet applet) {
//        this.speed = speed;
//        this.width = 1920;
//        this.height =1080;
//        this.a = a;
//        this.start = start;
//        this.applet = applet;
//
//    }

    public Firework(int d, PApplet applet, float time){

        this.applet = applet;
        this.start = time;
        this.verschiebung = (float) Math.random()*500f+50f;
        strechX = (float)((Math.random()+0.1)*(100/verschiebung));

        strechY = (float)(Math.random()+0.5/(verschiebung*0.1));
        //strechY = 1;


        this.direction = d;

    }

    public boolean move (float time){
        float t = (time-start)*0.01f;
        //x = (int) (speed *(float)Math.cos(Math.toRadians(a))*(t));
        //y= (int) -(((-9.81f/2)*t*t+speed*Math.sin(Math.toRadians(a)*t)));
        tail = new ArrayList<>();

        for(int i = 0; i<10; i++) {

            //double j = Math.random()*i*2-i*2;
            double j=0;
            x = (int) (t* 100);
            y = (int) ((Math.pow((x-i*20) - verschiebung, 2) - verschiebung * verschiebung) * 0.001f);
            tail.add(new int[] {(int) ((x-i*20)*strechX*direction+j),(int)(y*strechY)});

        }
        speed*=0.991f;

        //tail.add(new int[] {(int) (x*strechX*direction),(int)(y*strechY)});


        if(tail.size()>100)
            tail.remove(0);

        if (1080<tail.get(tail.size()-1)[1]){
            System.out.println("Stop@: " + y);
            return false;
        }
        return true;
    }
    public void draw(){

        for (int[] i : tail){
            applet.fill(255,255,255,255-255/tail.size()*(tail.indexOf(i)+1));
            applet.ellipse(i[0],i[1],radius,radius);        }
    }


    public int getX(){
        return x;
    }
    public int getY(){ return y;}

    public static void doFirework(float time,PApplet applet){
        int direction;
        if(time>TimeLookupTable.wonderful[counter]){
            if(counter%2==0){
                direction = -1;
            }else{
                direction = 1;
            }

            //fireworks.add(new Firework((int) ((Math.random()*45*direction+45)),100,time,applet));
            //fireworks.add(new Firework(Math.abs(direction+(int)Math.random()*45) ,100,time,applet));
            //fireworks.add(new Firework( 40 ,100,time,applet));
            fireworks.add(new Firework(direction,applet,time));
            counter=(counter+1)%TimeLookupTable.wonderful.length;
        }

        ArrayList<Firework> toDel = new ArrayList<>();

        for(Firework f : fireworks){
            if(f.move(time)){
                f.draw();
            }else{
                toDel.add(f);
            }
        }
        for(Firework f : toDel){
            fireworks.remove(f);
        }



    }

}


