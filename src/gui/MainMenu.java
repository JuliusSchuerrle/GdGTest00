package src.gui;
import processing.core.PApplet;
//import java.lang.Object;
import controlP5.*;
import src.MainClass;

import java.util.ArrayList;

public class MainMenu {

    private final int ANZ = 2;
    private float[] side = new float[ANZ];
    private PApplet applet;

    ArrayList<Slider> lMenu = new ArrayList<>();

    private ControlP5 menu;


    private final int height = 50;
    private final int length = 200;

    private float[][] pos = new float[ANZ][2];


    private boolean isMoving[] = new boolean[ANZ];
    private boolean isOut[] = new boolean[ANZ];
    private float[] way = new float[ANZ];
    private float moveTo[] = new float[ANZ];


    private final float[][][] POS;

    public MainMenu(PApplet applet, int height, int width)
    {
        this.applet = applet;
        menu = new ControlP5(applet);
        //POS = new float[][][] {{{0-length,0+length+100},{0}},{{width+length,0},{width-length-100,0}}};
        POS = new float[ANZ][2][2];
        POS[0][0]= new float[]{0-length-100,0};
        POS[0][1]= new float[]{0+length+100,0};
        POS[1][0]= new float[]{width+length,0};
        POS[1][1]= new float[]{width-100-length,0};


        for(int i = 0; i<pos.length;i++) {
            pos[i] = POS[i][0];

        }

        lMenu.add(menu.addSlider("mainCircleRotSpeed")  .setMax(1)  )  ;
        lMenu.add(menu.addSlider("mainCircleRadius")    .setMax(700) ) ;
        lMenu.add(menu.addSlider("pointRadius")         .setMax(200)  );
        lMenu.add(menu.addSlider("smallCircleRadius")   .setMax(300)  );
        lMenu.add(menu.addSlider("smallCircleRotSpeed") .setMax(9)    );
        lMenu.add(menu.addSlider("lifeSpan")            .setMax(10)   );

        for(Slider s : lMenu){
            s.setPosition(pos[1][0],pos[1][1]+lMenu.indexOf(s)*(height+50));
        }





    }

    public boolean move(int i){
        System.out.println("WAY: " + way[i]);
        System.out.println(pos[i][0]+" " + pos[i][1]);
        pos[i][0]=applet.bezierPoint(POS[i][0][0],POS[i][0][0]+side[i]*10f,POS[i][1][0],POS[i][1][0]-side[i]*10f,side[i]);
        System.out.println("X: "+pos[i][0]+" Y: " + pos[i][1]);
        way[i]=way[i]+(moveTo[i]*0.01f);
        if(way[i]>1){
            pos[i][0]=POS[i][1][0];
            way[i] = 1f;
            isOut[i] = true;
            return false;
        }
        if(way[i]<0){
            pos[i][0]=POS[i][0][0];
            way[i] = 0f;
            isOut[i] = false;
            return false;
        }
        return true;
    }

    public void update(){
        for(int i = 0; i<ANZ; i++){
            if(isMoving[i]){
                isMoving[i]=move(i);
                for(Slider s : lMenu){
                    s.setPosition(pos[i][0],s.getPosition()[1]);

                }
            }
            System.out.println(isMoving[i]);

        }



    }

    public void moveIn(int i){
        if(way[i]!=0) {
            moveTo[i] = -1;
            isMoving[i] = true;
        }

    }
    public void moveOut(int i){
        if(way[i]!=1) {
            moveTo[i] = 1;
            isMoving[i] = true;
        }
    }

    public ControlP5 getMenu(){
        return menu;
    }



}
