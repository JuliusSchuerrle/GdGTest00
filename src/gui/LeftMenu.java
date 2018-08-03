package src.gui;

import controlP5.ControlP5;
import controlP5.Slider;
import processing.core.PApplet;

import java.util.ArrayList;

public class LeftMenu {
    private PApplet applet;

    ArrayList<Slider> lMenu = new ArrayList<>();

    private ControlP5 menu;


    private final int height = 50;
    private final int length = 200;





    public LeftMenu(PApplet applet, int height, int width)
    {
        this.applet = applet;
        menu = new ControlP5(applet);


        lMenu.add(menu.addSlider("mainCircleRotSpeed")  .setMax(1)  )  ;
        lMenu.add(menu.addSlider("mainCircleRadius")    .setMax(700) ) ;
        lMenu.add(menu.addSlider("pointRadius")         .setMax(200)  );
        lMenu.add(menu.addSlider("smallCircleRadius")   .setMax(300)  );
        lMenu.add(menu.addSlider("smallCircleRotSpeed") .setMax(9)    );
        lMenu.add(menu.addSlider("lifeSpan")            .setMax(10)   );






    }

    public boolean move(int i){
        return true;
    }

    public void moveIn(int i){

    }
    public void moveOut(int i){

    }

    public ControlP5 getMenu(){
        return menu;
    }



}

