package src.gui;

import controlP5.*;
import controlP5.Button;
import processing.core.PFont;
import src.MainClass;
import src.data.data;

import java.awt.*;

/**
 * Created by js148 on 08.08.2018.
 */
import controlP5.CColor;
import controlP5.ControlP5;
import controlP5.Slider;
import processing.core.PFont;
import src.MainClass;
import src.data.data;

import java.awt.*;
import java.util.ArrayList;


public class ColorMenu {
    public   ControlP5 menu;

    //Settings
    CColor color = data.cColors[0];

    //private final int LENGTH = 60;
    private final int LENGTH = 120;
    //private final int HEIGHT = 250;
    private final int HEIGHT = 160;
    private float speed = 0.03f;



    //Settings

    private Button left;
    private Button middle;
    private Button right;

    private final int MIDPOSX;
    private final int MIDPOSY;
    //private final int DISTANCE = 1920/2+LENGTH;
    private final int DISTANCE = ((1920/2)+LENGTH*2);
    MainClass applet;

    ArrayList<Button> sliders = new ArrayList<>();

    public boolean isButtonSelected=false;



    Button s1,s2,s3,s4,s5,s6,s7;
    private float i = 0f;
    private int direction = 0;
    public boolean isMoving = false;


    public boolean isMovingIn=true;
    private float moveInPos=1.f;
    public boolean isMovingOut=false;
    private float moveOutPos=0.f;


    Font f = new Font(Font.SANS_SERIF,1,20);
    PFont font = new PFont(f,false);

    private float timePressed=0.f;


    public ColorMenu(MainClass applet, int FRAMEHEIGHT, int FRAMEWIDTH){
        this.applet = applet;
        MIDPOSX = FRAMEWIDTH/2-LENGTH/2;
        MIDPOSY = FRAMEHEIGHT-350;
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
        if(isMovingIn){
            isMovingIn=moveIn();
        }
        if(isMovingOut){
            isMovingOut=moveOut();
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
            isButtonSelected=false;
        }
    }
    public void left(float speed){

        if(!isMoving) {
            this.speed=speed;
            i=0;
            isMoving = true;
            direction = 1;
            isButtonSelected=false;
        }
    }



    public void press(){
        isMovingOut=true;
    }


    public boolean moveIn(){
        middle.setPosition(MIDPOSX,MIDPOSY+300*moveInPos);
        if(moveInPos<0.0f){
            moveInPos=1;
            return false;
        }
        moveInPos-=0.05f;
        return true;
    }

    public boolean moveOut(){
        middle.setPosition(MIDPOSX,MIDPOSY+300*moveOutPos);
        if(moveOutPos>1.0f){
            moveOutPos=0;
            middle.setValue(1);
            return false;
        }
        moveOutPos+=0.05f;
        return true;
    }

    private void setupMenu(){
        s1=menu.addButton("endColorMenu").setLabel("color1");
        s2=menu.addButton("endColorMenu1").setLabel("color2");
        s3=menu.addButton("endColorMenu2").setLabel("color3");
        s4=menu.addButton("color4");
        s5=menu.addButton("color5");
        s6=menu.addButton("color6");
        sliders.add(s1);
        sliders.add(s2);
        sliders.add(s3);
        sliders.add(s4);
        sliders.add(s5);
        sliders.add(s6);

        for(Button s: sliders){
            s.setWidth(LENGTH).setHeight(HEIGHT).setVisible(false);
            s.setFont(font).setColor(color);
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





}


