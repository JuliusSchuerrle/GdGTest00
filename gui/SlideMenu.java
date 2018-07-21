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
        left.update();
        middle.update();
        right.update();


        if(isMoving){
            isMoving=move();
        }
        menu.setPosition(xPos,yPos);

    }
    public boolean move(){

        middle.setPosition( middle.getPosition()[0]+ direction*1,middle.getPosition()[1]);
        left.setPosition( right.getPosition()[0]+ direction*1,right.getPosition()[1]);
        right.setPosition( right.getPosition()[0]+ direction*1,right.getPosition()[1]);
        if(cnt >= DISTANCE){
            if(direction>0){
                right=middle;
                middle=left;
                left=sliders.get(sliders.indexOf(middle)-1);
            }else{
                right=middle;
                middle=left;
                right=sliders.get((sliders.indexOf(middle)+1)%sliders.size());
            }
            return false;
        }
        cnt++;
        return true;

    }

    public void right(){
        cnt=0;
        if(!move()) {

            NxPos = xPos - DISTANCE;
            isMoving = true;
            direction = -1;
        }
    }
    public void left(){
        cnt=0;
        if(!move()) {
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

        sliders.add(s1);
        sliders.add(s2);
        sliders.add(s3);
        sliders.add(s4);

        left = s1;
        middle = s2;
        right = s3;

        left.setPosition(MIDPOSX-DISTANCE,MIDPOSY);
        middle.setPosition(MIDPOSX,MIDPOSY);
        right.setPosition(MIDPOSX+DISTANCE,MIDPOSY);
        left.setVisible(true);
        middle.setVisible(true);
        right.setVisible(true);
    }


    Slider s1,s2,s3,s4,s5;
}
