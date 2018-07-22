package gui;
import javazoom.jl.decoder.Control;
import processing.core.PApplet;
import controlP5.ControlP5;
import controlP5.Slider;

import java.util.ArrayList;
import java.util.Dictionary;

public class SlideMenu {
    ControlP5 menu;
    private final int MINPOS=0;
    private final int MAXPOS=-1920;
    public boolean isMoving = false;
    private int direction = 0;
    private final int STDLENGTH = 200;

    private int cnt = 0;
    private int xPos=0;
    private int yPos=0;
    private int NxPos=xPos;
    private int NyPos=yPos;
    private Slider left;
    private Slider middle;
    private Slider right;


    private final int MIDPOSX = 1920/2-STDLENGTH/2;
    private final int MIDPOSY = 1080-150;
    private final int DISTANCE = 1920/2+STDLENGTH;

    ArrayList<Slider> sliders = new ArrayList<>();

    public SlideMenu(PApplet applet){

        menu = new ControlP5(applet);
        menu.setUpdate(true);
        setupMenu();





    }
    public ControlP5 getMenu(){
        return menu;
    }

    public void update(){

        if(isMoving){

            isMoving=move();
        }
        left.update();
        middle.update();
        right.update();

    }
    public boolean move(){
        middle.setPosition( middle.getPosition()[0]+ direction*5,middle.getPosition()[1]);
        left.setPosition( right.getPosition()[0]+ direction*5,right.getPosition()[1]);
        right.setPosition( right.getPosition()[0]+ direction*5,right.getPosition()[1]);
        System.out.println((-1)%4);
        if(cnt >= DISTANCE){
            if(direction>0){
                right.setVisible(false);
                right=middle;
                middle=left;

                /*int i=sliders.indexOf(middle)-1;
                if(i==-1)
                    i=sliders.size()-1;
                left=sliders.get(i);
                System.out.println((i));
                */

                //TODO Wenn -1 dann slider.size()-1
                left=sliders.get((sliders.indexOf(middle)-1)%sliders.size()+sliders.size()%sliders.size());


                setDisplay();
            }else if(direction<0){
                left.setVisible(false);
                left=middle;
                middle=right;
                right=sliders.get((sliders.indexOf(middle)+1)%sliders.size());
                setDisplay();


            }

            direction = 0;
            left.update();
            middle.update();
            right.update();

            return false;


        }
        cnt+=5;
        return true;

    }

    public void right(){
        cnt=0;
        if(!isMoving) {

            NxPos = xPos - DISTANCE;
            isMoving = true;
            direction = -1;
        }
    }
    public void left(){
        cnt=0;
        if(!isMoving) {
            NxPos = xPos + DISTANCE;
            isMoving = true;
            direction = 1;
        }
    }

    private void setupMenu(){
        s1=menu.addSlider("mainCircleRotSpeed").setWidth(STDLENGTH).setHeight(50).setVisible(false);
        s2=menu.addSlider("mainCircleRadius").setWidth(STDLENGTH).setHeight(50).setVisible(false);
        s3=menu.addSlider("pointRadius").setWidth(STDLENGTH).setHeight(50).setVisible(false);
        s4=menu.addSlider("smallCircleRadius").setWidth(STDLENGTH).setHeight(50).setVisible(false);
        s5=menu.addSlider("smallCircleSpeed").setWidth(STDLENGTH).setHeight(50).setVisible(false);

        sliders.add(s1);
        sliders.add(s2);
        sliders.add(s3);
        sliders.add(s4);
        sliders.add(s5);

        left = s2;
        middle = s3;
        right = s4;

        setDisplay();
    }

    private void setDisplay(){
        left.setPosition(MIDPOSX-DISTANCE,MIDPOSY);
        middle.setPosition(MIDPOSX,MIDPOSY);
        right.setPosition(MIDPOSX+DISTANCE,MIDPOSY);
        left.setVisible(true);
        middle.setVisible(true);
        right.setVisible(true);
    }


    Slider s1,s2,s3,s4,s5;
}
