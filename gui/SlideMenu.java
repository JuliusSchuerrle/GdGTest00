package gui;
import processing.core.PApplet;
import controlP5.ControlP5;
import controlP5.Slider;

public class SlideMenu {
    ControlP5 menu;
    private final int MINPOS=0;
    private final int MAXPOS=-1920;
    public boolean isMoving = false;
    private int direction = 0;
    private final int STDLENGTH = 200;

    private int xPos=0;
    private int yPos=0;
    private int NxPos=xPos;
    private int NyPos=yPos;


    private final int MIDPOSX = 1920/2-STDLENGTH/2;
    private final int MIDPOSY = 1080-150;
    private final int DISTANCE = 1920/2+STDLENGTH;

    public SlideMenu(PApplet applet){
        menu = new ControlP5(applet);
        setupMenu();


    }
    public ControlP5 getMenu(){
        return menu;
    }
    public void update(){
        if(isMoving){
            isMoving=move();
        }
        menu.setPosition(xPos,yPos);

    }
    public boolean move(){
        System.out.println("X "+xPos);
        System.out.println("NX "+NxPos);
        if(xPos==NxPos){
            return false;
        }
        xPos += direction*5;
        if(Math.abs((NxPos-xPos))<6){
            xPos=NxPos;
            return false;
        }
        return true;
    }

    public void right(){
        if(!move()) {
            NxPos = xPos - DISTANCE;
            isMoving = true;
            direction = -1;
        }
    }
    public void left(){
        if(!move()) {
            NxPos = xPos + DISTANCE;
            isMoving = true;
            direction = 1;
        }
    }

    private void setupMenu(){
        s1=menu.addSlider("mainCircleRotSpeed").setPosition(MIDPOSX,MIDPOSY).setWidth(STDLENGTH).setHeight(50);
        s2=menu.addSlider("mainCircleRadius").setPosition(MIDPOSX+DISTANCE,MIDPOSY).setWidth(STDLENGTH).setHeight(50);
    }


    Slider s1,s2,s3,s4,s5;
}
