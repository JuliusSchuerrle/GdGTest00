package gui;
import javazoom.jl.decoder.Control;
import processing.core.PApplet;
import controlP5.ControlP5;
import controlP5.Slider;
import java.util.ArrayList;
import java.util.Dictionary;

public class SlideMenu {
    ControlP5 menu;

    //Settings

    private final int LENGTH = 400;
    private final int HEIGHT = 100;
    private final float SPEED  = 0.05f;

    //Settings

    private Slider left;
    private Slider middle;
    private Slider right;

    private final int MIDPOSX = 1920/2-LENGTH/2;
    private final int MIDPOSY = 1080-150;
    private final int DISTANCE = 1920/2+LENGTH;

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
        float way =  interpolation(i);

        left.setPosition( MIDPOSX-DISTANCE + direction *way,left.getPosition()[1]);
        middle.setPosition( MIDPOSX + direction*way,middle.getPosition()[1]);
        right.setPosition( MIDPOSX+DISTANCE+ direction*way,right.getPosition()[1]);

        if(i > 1.0f){
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
            left.update();
            middle.update();
            right.update();

            return false;


        }
        i+=SPEED;
        return true;

    }

    public void right(){
        i=0;
        if(!isMoving) {
            isMoving = true;
            direction = -1;
        }
    }
    public void left(){
        i=0;
        if(!isMoving) {
            isMoving = true;
            direction = 1;
        }
    }

    private void setupMenu(){
        s1=menu.addSlider("mainCircleRotSpeed").setWidth(LENGTH).setHeight(HEIGHT).setVisible(false);
        s2=menu.addSlider("mainCircleRadius").setWidth(LENGTH).setHeight(HEIGHT).setVisible(false);
        s3=menu.addSlider("pointRadius").setWidth(LENGTH).setHeight(HEIGHT).setVisible(false);
        s4=menu.addSlider("smallCircleRadius").setWidth(LENGTH).setHeight(HEIGHT).setVisible(false);
        s5=menu.addSlider("smallCircleSpeed").setWidth(LENGTH).setHeight(HEIGHT).setVisible(false);

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
    private float interpolation(float i){
        float y = (float) Math.sin(i*Math.PI-0.5f*Math.PI)*(DISTANCE/2f)+(DISTANCE/2f);
        System.out.println(y);
        return y;
    }


    Slider s1,s2,s3,s4,s5;
    private float i = 0f;
    private int direction = 0;
    public boolean isMoving = false;
}
