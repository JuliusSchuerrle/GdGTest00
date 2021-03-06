package src.gui;
import controlP5.CColor;
import controlP5.ControlP5;
import controlP5.Slider;
import processing.core.PFont;
import src.MainClass;
import src.data.data;

import java.awt.*;
import java.util.ArrayList;


public class SlideMenu {
    ControlP5 menu;

    //Settings
    CColor color = data.cColors[0];

    //private final int LENGTH = 60;
    private final int LENGTH = 120;
    //private final int HEIGHT = 250;
    private final int HEIGHT = 160;
    private float speed = 0.03f;



    //Settings

    private Slider left;
    private Slider middle;
    private Slider right;

    private final int MIDPOSX;
    private final int MIDPOSY;
    //private final int DISTANCE = 1920/2+LENGTH;
    private final int DISTANCE = ((1920/2)+LENGTH*2);
    MainClass applet;

    ArrayList<Slider> sliders = new ArrayList<>();

public boolean isButtonSelected=false;



    Slider s1,s2,s3,s4,s5,s6,s7,s8;
    private float i = 0f;
    private int direction = 0;
    public boolean isMoving = false;



    public boolean isMovingIn=true;
    private float moveInPos=1.f;





    Font f = new Font(Font.SANS_SERIF,1,20);
    PFont font = new PFont(f,false);

    private float timePressed=0.f;


    public SlideMenu(MainClass applet, int FRAMEHEIGHT, int FRAMEWIDTH){
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
        if(isMovingIn){
            isMovingIn=moveIn();
            if(isMovingIn){
                isMoving=false;
                isButtonSelected=false;
                return;
            }
        }

        if(isMoving){

            isMoving=move();
        }



        //left.update();
        //middle.update();
        //right.update();
        if(!isButtonSelected){
            middle.setValue(applet.lookupTable.table.get(sliders.indexOf(middle))[applet.positionInArray]);
        }
        left.setValue(applet.lookupTable.table.get(sliders.indexOf(left))[applet.positionInArray]);
        right.setValue(applet.lookupTable.table.get(sliders.indexOf(right))[applet.positionInArray]);
        for(int i=0;i<6;i++){
            if(isButtonSelected){
                if(sliders.get(i).equals(middle)){
                    continue;
                }
            }
            sliders.get(i).setValue(applet.lookupTable.table.get(i)[applet.positionInArray]);
        }

    }








    public boolean moveIn(){
        middle.setPosition(MIDPOSX,MIDPOSY+400*moveInPos);
        if(moveInPos<0.0f){
            moveInPos=1.f;
            return false;
        }
        moveInPos-=0.04f;
        return true;
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

    public void setValue(float input){
         float v=   middle.getMin()+(input*(middle.getMax()-middle.getMin()));
         middle.setValue(v);
         middle.update();
         middle.updateInternalEvents(applet);
         applet.lookupTable.setValues(sliders.indexOf(middle),applet.positionInArray,v);

    }
    public float getValue(){
        float v=middle.getMax()-middle.getMin();
        float tmp=middle.getValue()-middle.getMin();
        v=tmp/v;
        System.out.println("this is the baseline; "+v);
        return v;
    }

    public void up(){
        float v=middle.getValue();
        v+=(middle.getMax()-middle.getMin())*0.005f;
        middle.setValue(v);
        middle.update();
        applet.lookupTable.setValues(sliders.indexOf(middle),applet.positionInArray,v);
        isButtonSelected=true;

    }

    public void down(){
        float v=middle.getValue();
        v-=(middle.getMax()-middle.getMin())*0.005f;
        middle.setValue(v);
        middle.update();
        applet.lookupTable.setValues(sliders.indexOf(middle),applet.positionInArray,v);
        isButtonSelected=true;

    }




    private void setupMenu(){
        s1=menu.addSlider("mainCircleRotSpeed")  .setMax(1).setValue(100) .setCaptionLabel("Main Speed"    );
        s2=menu.addSlider("mainCircleRadius")    .setMax(700)             .setCaptionLabel("Main Radius"    );
        s3=menu.addSlider("pointRadius")         .setMax(200)             .setCaptionLabel("Point Size"    );
        s4=menu.addSlider("smallCircleRadius")   .setMax(300)             .setCaptionLabel("Small Radius"    );
        s5=menu.addSlider("smallCircleRotSpeed") .setMax(9)               .setCaptionLabel("Small Speed"    );
        s6=menu.addSlider("lifeSpan")            .setMax(30)              .setCaptionLabel("Lifespan"    );
        s7=menu.addSlider("");
        s8=menu.addSlider("selectedColor").setMax(1).setValue(0).setCaptionLabel("color");
        sliders.add(s1);
        sliders.add(s2);
        sliders.add(s3);
        sliders.add(s4);
        sliders.add(s5);
        sliders.add(s6);
        sliders.add(s7);
        sliders.add(s8);
        for(Slider s: sliders){
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
        if(left==s7){
            left.setVisible(false);
        }
        if(middle==s7){
            middle.setVisible(false);
        }
        if(right==s7){
            right.setVisible(false);
        }
    }





}


