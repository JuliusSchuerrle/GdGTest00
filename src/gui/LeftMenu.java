package src.gui;

import controlP5.ControlP5;
import controlP5.Slider;
import processing.core.PApplet;
import src.MainClass;

import java.util.ArrayList;

public class LeftMenu {

    float[] parameter;





    private final int WIDTH;
    private final int HEIGHT;
    private PApplet applet;
    float cnt = 0;

    ArrayList<Slider> lMenu = new ArrayList<>();

    private ControlP5 menu;

    private int xPos;
    private int yPos;

    private final int height = 100;
    private final int length = 200;

    private boolean isMoving = false;
    private boolean isOut;




    public LeftMenu(PApplet applet, int height, int width)
    {
        this.WIDTH = width;
        this.HEIGHT = height;
        this.applet = applet;
        menu = new ControlP5(applet);

        //xPos = WIDTH/2+length*2;
        //yPos = -HEIGHT/2;

        xPos = WIDTH+length;
        yPos = 50;

        lMenu.add(menu.addSlider("mainCircleRotSpeed")  .setMax(1).setValue(100))  ;
        lMenu.add(menu.addSlider("mainCircleRadius")    .setMax(700) ) ;
        lMenu.add(menu.addSlider("pointRadius")         .setMax(200)  );
        lMenu.add(menu.addSlider("smallCircleRadius")   .setMax(300)  );
        lMenu.add(menu.addSlider("smallCircleRotSpeed") .setMax(9)    );
        lMenu.add(menu.addSlider("lifeSpan")            .setMax(10)   );



        for(Slider s : lMenu){
            s.setHeight(this.height).setWidth(this.length).setPosition(xPos,yPos+(lMenu.indexOf(s)*(this.height+50))).setVisible(true);
            s.update();

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
        xPos = (int) (applet.bezierPoint(WIDTH+length,WIDTH+length*2+10,WIDTH-length+10-100,WIDTH-length-100,cnt));
        System.out.println(xPos);
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
                System.out.println(s.getPosition()[0]+" "+s.getPosition()[1]);

            }
        }
        menu.update();


    };

    public ControlP5 getMenu(){
        return menu;
    }



}

