package src.gui;

import controlP5.CColor;
import controlP5.ControlP5;
import controlP5.Slider;
import processing.core.PApplet;
import src.MainClass;
import processing.core.PFont;

import java.awt.*;
import java.util.ArrayList;




public class RightMenu {

    float[] parameter;
    Font f = new Font(Font.SANS_SERIF,1,15);
    PFont font = new PFont(f,false);
    CColor color;


    private final int WIDTH;
    private final int HEIGHT;
    private MainClass applet;
    float cnt = 0;

     public ArrayList<Slider> lMenu = new ArrayList<>();

    private ControlP5 menu;

    private int xPos;
    private int yPos;

    private final int height = 100;
    private final int length = 200;

    private boolean isMoving = false;
    private boolean isOut;




    public RightMenu(MainClass applet, int height, int width)
    {

        color = new CColor(0x50100000,0xffff0000,0x50ffffff,0xffffffff,0xffffffff);
        this.WIDTH = width;
        this.HEIGHT = height;
        this.applet = applet;
        menu = new ControlP5(applet);

        //xPos = WIDTH/2+length*2;
        //yPos = -HEIGHT/2;

        xPos = WIDTH+length;
        yPos = 50;

        lMenu.add(menu.addSlider("mainCircleRotSpeed")  .setMax(1).setValue(100) .setCaptionLabel("Main Speed")    );
        lMenu.add(menu.addSlider("mainCircleRadius")    .setMax(700)             .setCaptionLabel("Main Radius")    );
        lMenu.add(menu.addSlider("pointRadius")         .setMax(200)             .setCaptionLabel("Point Size")    );
        lMenu.add(menu.addSlider("smallCircleRadius")   .setMax(300)             .setCaptionLabel("Small Radius")    );
        lMenu.add(menu.addSlider("smallCircleRotSpeed") .setMax(9)               .setCaptionLabel("Small Speed")    );
        lMenu.add(menu.addSlider("lifeSpan")            .setMax(30)              .setCaptionLabel("Lifespan")    );



        for(Slider s : lMenu){
            s.setHeight(this.height).setWidth(this.length).setPosition(xPos,yPos+(lMenu.indexOf(s)*(this.height+50))).setVisible(true);
            s.update();
            s.setColor(color);
            s.setFont(font);




        }
        menu.update();




    }

    public boolean move(int i){
        if(cnt>1){
            cnt=1;
            isOut=true;
            }
        if(cnt<0){
            cnt=0;
            isOut=false;
        }

        //xPos = (int) (applet.bezierPoint(WIDTH/2+length*2,WIDTH/2+length*2+10,WIDTH/2-length+10,WIDTH/2-length,cnt));
        xPos = (int) (applet.bezierPoint(WIDTH+length,WIDTH+length*2+10,WIDTH-length+50-200,WIDTH-length-200,cnt));
       // System.out.println(xPos);
        cnt+=i*0.1;
        return true;


    }

    public void update(boolean mouse,float mainCircleRadius, float mainCircleRotSpeed,int numberOfCircles,float smallCircleRadius, float smallCircleRotSpeed, int numberOfSmallPoints, float pointRadius, float lifeSpan){
        //parameter=new float[]{mainCircleRadius,mainCircleRotSpeed,numberOfCircles,smallCircleRadius,smallCircleRotSpeed,numberOfSmallPoints,pointRadius,lifeSpan};

        if(mouse && !isOut){
            isMoving=move(1);
        }
        if(!mouse&& isOut)
            isMoving=move(-1);

        if(true) {
            for (Slider s : lMenu) {
                s.setHeight(this.height).setWidth(this.length).setPosition(xPos,s.getPosition()[1]);
                //s.setValue(parameter[lMenu.indexOf(s)]);
                s.update();
              //  System.out.println(s.getPosition()[0]+" "+s.getPosition()[1]);


            }
        }

        lMenu.get(0).setValue(applet.lookupTable.value1[applet.positionInArray]);
        lMenu.get(1).setValue(applet.lookupTable.value2[applet.positionInArray]);
        lMenu.get(2).setValue(applet.lookupTable.value3[applet.positionInArray]);
        lMenu.get(3).setValue(applet.lookupTable.value4[applet.positionInArray]);
        lMenu.get(4).setValue(applet.lookupTable.value5[applet.positionInArray]);
        lMenu.get(5).setValue(applet.lookupTable.value6[applet.positionInArray]);
       // lMenu.get(6).setValue(applet.lookupTable.value7[applet.positionInArray]);
        menu.update();
    };

    public ControlP5 getMenu(){
        return menu;
    }



}

