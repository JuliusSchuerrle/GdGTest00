package src.gui;
import processing.core.PApplet;
import controlP5.ControlP5;
import controlP5.Slider;
import src.MainClass;

import java.util.ArrayList;


public class SlideMenu {
    ControlP5 menu;

    //Settings

    private final int LENGTH = 100;
    private final int HEIGHT = 400;
    private float speed = 0.03f;

    //Settings

    private Slider left;
    private Slider middle;
    private Slider right;

    private final int MIDPOSX;
    private final int MIDPOSY;
    //private final int DISTANCE = 1920/2+LENGTH;
    private final int DISTANCE = 1920/2+LENGTH;
    MainClass applet;

    ArrayList<Slider> sliders = new ArrayList<>();

    public SlideMenu(MainClass applet, int FRAMEHEIGHT, int FRAMEWIDTH){
        this.applet = applet;
        MIDPOSX = FRAMEWIDTH/2-LENGTH/2;
        MIDPOSY = FRAMEHEIGHT-150;
        menu = new ControlP5(applet);
        //menu.setUpdate(true);



        setupMenu();





    }
    public ControlP5 getMenu(){
        return menu;
    }

    public void update(){
        //middle.update();
        if(isMoving){

            isMoving=move();
        }
        //left.update();
        //middle.update();
        //right.update();

    }





    public boolean move(){
        float xMove =  applet.bezierPoint(0,-10,DISTANCE-100f,DISTANCE,i);
        float yMoveU = applet.bezierPoint(0,100,110,100,i);;
        float yMoveD = applet.bezierPoint(0,-10,90,100,i);;




        //yMove = applet.bezierPoint(0,10,90,100, i);
        left.setPosition( MIDPOSX-DISTANCE + direction *xMove,MIDPOSY+100-direction*yMoveU);
        middle.setPosition( MIDPOSX + direction*xMove,MIDPOSY+yMoveD);
        right.setPosition( MIDPOSX+DISTANCE+ direction*xMove,MIDPOSY+100+direction*yMoveU);

        if(i > 1.0f){
        //if(i>DISTANCE)  {
            if(direction>0){
                right.setVisible(false);
                right=middle;
                middle=left;
                left=sliders.get(((sliders.indexOf(middle)-1)%sliders.size()+sliders.size()) %sliders.size());
                setDisplay();

            }else if(direction<0){
                left.setVisible(false);
                left=middle;
                middle=right;
                right=sliders.get((sliders.indexOf(middle)+1)%sliders.size());
                setDisplay();
            }

            direction = 0;
            //left.update();
            //middle.update();
            //right.update();

            return false;


        }
        i+= speed;
        return true;

    }

    public void right(float speed){

        if(!isMoving) {
            this.speed=speed;
            i=0;
            isMoving = true;
            direction = -1;
        }
    }
    public void left(float speed){

        if(!isMoving) {
            this.speed=speed;
            i=0;
            isMoving = true;
            direction = 1;
        }
    }

    public void setValue(float input){
         float v=   middle.getMin()+(input*(middle.getMax()-middle.getMin()));
         middle.setValue(v);
         middle.update();
         middle.updateInternalEvents(applet);

    }





    private void setupMenu(){
        s1=menu.addSlider("mainCircleRotSpeed").setMax(1);
        s2=menu.addSlider("mainCircleRadius").setMax(700);
        s3=menu.addSlider("pointRadius").setMax(200);
        s4=menu.addSlider("smallCircleRadius").setMax(300);
        s5=menu.addSlider("smallCircleRotSpeed").setMax(9);
        s6=menu.addSlider("lifeSpan").setMax(10);

        sliders.add(s1);
        sliders.add(s2);
        sliders.add(s3);
        sliders.add(s4);
        sliders.add(s5);
        sliders.add(s6);

        for(Slider s: sliders){
            s.setWidth(LENGTH).setHeight(HEIGHT).setVisible(false);
        }


        left = s2;
        middle = s3;
        right = s4;

        setDisplay();
    }

    private void setDisplay(){
        left.setPosition(MIDPOSX-DISTANCE,MIDPOSY+100);
        middle.setPosition(MIDPOSX,MIDPOSY);
        right.setPosition(MIDPOSX+DISTANCE,MIDPOSY+100);
        left.setVisible(true);
        middle.setVisible(true);
        right.setVisible(true);
    }


    private float interpolation(int start, int end , float i){
        return applet.bezierPoint(start,start-10,end+100,end,i);

        /*
        float y = (float) Math.sin(i*Math.PI-0.5f*Math.PI)*(end/2f)+(end/2f);
        System.out.println(y);
        return y;
        */





        /* float speed;
        if(i<end-start/2)
            speed = i-start+0.1f;
        else
            speed= end-i+0.1f;
        return i+0.1f*speed;
        */


    }


    Slider s1,s2,s3,s4,s5,s6;
    private float i = 0f;
    private int direction = 0;
    public boolean isMoving = false;


}


