package src.gui;

import controlP5.ControlP5;
import controlP5.Slider;
import src.MainClass;

import java.util.ArrayList;

/**
 * Created by js148 on 08.08.2018.
 */
public class CircleMenu {
    private final int LENGTH = 100;
    private final int HEIGHT = 400;
    private float speed = 0.03f;
    Slider s1,s2,s3,s4,s5,s6;
    private float animPos=0.f;
    MainClass applet;
    private int MIDPOSX;
    private int MIDPOSY;
    ControlP5 menu;
    ArrayList<Slider> sliders = new ArrayList<>();
    private Slider left;
    private Slider middle;
    private Slider right;


    public CircleMenu(MainClass applet, int FRAMEHEIGHT, int FRAMEWIDTH){
        MIDPOSY=FRAMEHEIGHT-300;
        MIDPOSX=FRAMEWIDTH+150;
        menu = new ControlP5(applet);
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


    }

    private void setDisplay(){

    }

}
