import gui.SlideMenu;
import com.jogamp.opengl.GL;
import controlP5.*;
import controlP5.ControlP5;
import controlP5.ControlEvent;
import controlP5.DropdownList;
import controlP5.Slider;
import ddf.minim.AudioPlayer;
import ddf.minim.Minim;
import ddf.minim.analysis.FFT;
import gui.SlideMenu;
import javafx.scene.shape.Circle;
import processing.core.PApplet;
import src.CircleCalc;
import src.Point;
import src.RenderPoint;
import src.TimeLookupTable;

import javax.naming.SizeLimitExceededException;
import java.util.ArrayList;
import java.util.Vector;

public class MainClass extends PApplet {

    private final int HEIGHT = 1080;
    private final int WIDTH = 1920;

    Minim minim;
    AudioPlayer jingle;
    FFT fft;
    damkjer.ocd.Camera cam;
    SlideMenu menu;
    ControlP5 jControl;
    ControlP5 j2;
    Slider sTest;
    //circles setup
    CircleCalc mainCircle;


    private boolean isPlaying = true;

    private float mainCircleRadius=400;
    private float mainCircleRotSpeed=0.1f;  //0.1f

    private int numberOfCircles=5;

    private float smallCircleRadius=20;
    private float smallCircleRotSpeed=1f;
    private int numberOfSmallPoints=2;

    private float pointRadius=50.f;

    //private float lifeSpan=30.f;
    private float lifeSpan=1.f;

    //FFT variables
    float pos=135;
    float weight = 150;

    float dt;
    float dtc;


    int beatCounter=0;
    int partCounter=0;

    float r=255;
    float g=255;
    float b=255;

    Slider s3;



    ArrayList<CircleCalc> smallCircles;
    ArrayList<RenderPoint> renderPoints;
    ArrayList<RenderPoint> renderPointsToRemove;
    public static void main(String[] args){

        PApplet.main("MainClass");
        System.out.println("hello");
    }

    public void settings(){
        size(WIDTH , HEIGHT,P3D );

    }



    public void setup(){

        cam = new damkjer.ocd.Camera(this,200,-250,300);


        //Menu
        menu = new SlideMenu(this);
        j2 = menu.getMenu();
        j2.setAutoDraw(false);


        jControl=new ControlP5(this);
        jControl.setAutoDraw(false);

        //Big circle
        Knob k1 = jControl.addKnob("mainCircleRotSpeed").setRange(0,1).setValue(mainCircleRotSpeed).setPosition(100,100).setRadius(50).setSize(200,200).setLabel("Kreisgeschwindigkeit");
        Slider s4=jControl.addSlider("mainCircleRadius").setPosition(100,350).setMin(0).setMax(700).setValue(400).setHeight(50).setWidth(200).setLabel("Kreisradius");
        //Small circle

        Knob k2 = jControl.addKnob("smallCircleRotSpeed").setRange(0,9).setValue(smallCircleRotSpeed).setPosition(WIDTH-300,100).setRadius(50).setSize(200,200).setLabel("Kreisgeschwindigkeit");
        Slider s2=jControl.addSlider("smallCircleRadius").setPosition(WIDTH-300,350).setMin(0).setMax(200).setHeight(50).setWidth(200).setLabel("Kreisradius").setValue(smallCircleRadius);
        Slider s1=jControl.addSlider("pointRadius").setPosition(WIDTH-300,450).setMin(0).setMax(200).setHeight(200).setWidth(75).setLabel("Punktgroeße");
         s3=jControl.addSlider("lifeSpan").setPosition(WIDTH-175,450).setMin(0).setMax(10).setValue(lifeSpan).setHeight(200).setWidth(75).setLabel("Lebensdauer");

        //DropdownList d1 = jControl.addDropdownList("TESCHD").setPosition(100, 100).setBarHeight(30);



        sTest=s2;




        minim = new Minim(this);
        jingle = minim.loadFile("song.mp3", 2048);
        jingle.loop();
        fft = new FFT(jingle.bufferSize(), jingle.sampleRate());
        mainCircle=new CircleCalc(numberOfCircles,0,mainCircleRotSpeed,mainCircleRadius);
        smallCircles=new ArrayList<CircleCalc>();
        for(int i=0;i<numberOfCircles;i++){
            smallCircles.add(new CircleCalc(numberOfSmallPoints,0,smallCircleRotSpeed,smallCircleRadius));
        }
        renderPoints=new ArrayList<RenderPoint>();
        renderPointsToRemove=new ArrayList<RenderPoint>();

    }
    int x = 0;
    public void draw(){

        test();


        if(!isPlaying)
            return;

        //Debug
        /*
        System.out.println(frameRate+"    "+renderPoints.size());
        System.out.println(beatCounter);
           */



        dt = 1 / frameRate;
        dtc = dt * 60;

        runFFT();
        runBeat();
        updateData();
        updateColors();
        background(0);
        strokeWeight(0);

        for(int i=0;i<numberOfCircles;i++){
            for(int k=0;k<numberOfSmallPoints;k++){
                float x=mainCircle.getPoints().get(i).getX()
                        +smallCircles.get(i).getPoints().get(k).getX();
                float y=mainCircle.getPoints().get(i).getY()+smallCircles.get(i).getPoints().get(k).getY();


            }
            smallCircles.get(i).update(1/frameRate);

        }
        for(int i=0;i<renderPoints.size();i++){



            renderPoints.get(i).update(dt);
            if (renderPoints.get(i).isDead){
                renderPointsToRemove.add(renderPoints.get(i));
            }

            fill(renderPoints.get(i).getR(), renderPoints.get(i).getG(), renderPoints.get(i).getB(), renderPoints.get(i).getA());

            if(i>renderPoints.size()-(numberOfCircles*numberOfSmallPoints+1)){
                ellipse(renderPoints.get(i).getX(),renderPoints.get(i).getY(),pointRadius+weight,pointRadius+weight);

            }else {
                ellipse(renderPoints.get(i).getX(), renderPoints.get(i).getY(), renderPoints.get(i).getRadius(), renderPoints.get(i).getRadius());
            }
        }



        for(RenderPoint p:renderPointsToRemove){
            renderPoints.remove(p);
        }
        renderPointsToRemove.clear();
        //System.out.println(circle.getNumberOfPoints());
        mainCircle.update(1/frameRate);
        //line(0,0,100,100);


        camera();
        //jControl.draw();
        j2.draw();
        cam.aim(0,0,0);
        cam.jump(0, 0,200+1*1000.f);
        cam.feed();

    }


    public void runFFT(){

        fft.forward(jingle.mix);
        float tmp = 0;
        for (int i = 6; i < 25; i++) {
            tmp += fft.getBand(i) * dtc;
        }

        tmp /= 20;
        if (tmp > weight && tmp > 40) {
            weight = tmp;
        }

        if (weight > 0) {
            float speedup = (weight * weight) / (40.f * 40.f);
            speedup = constrain(speedup, 1, 3);
            weight -= 1 * dtc;

        }
    }

    public void runBeat(){



        if (jingle.position() + 10 > TimeLookupTable.d[beatCounter] && jingle.position() - TimeLookupTable.d[beatCounter] < 10000) {

            for(int i=0;i<numberOfCircles;i++){
                for(int k=0;k<numberOfSmallPoints;k++){
                    float x=mainCircle.getPoints().get(i).getX()
                            +smallCircles.get(i).getPoints().get(k).getX();
                    float y=mainCircle.getPoints().get(i).getY()+smallCircles.get(i).getPoints().get(k).getY();

                    renderPoints.add(new RenderPoint(x,y,0,r,g,b,255,pointRadius,lifeSpan));


                }


            }
            beatCounter++;
            if (beatCounter >= TimeLookupTable.d.length) {
                beatCounter = 0;
            }


        }
    }
    public void updateData(){
        menu.update();
        jControl.update();
        mainCircle.setRadius(mainCircleRadius);

        for(CircleCalc circle:smallCircles){
            circle.setRadius(smallCircleRadius);
            circle.setSpeed(smallCircleRotSpeed);
        }
        final int START = 80;
        final int END = START+32;
        if(beatCounter>START&&beatCounter<END){
            mainCircle.setSpeed(0);
        }else{
            mainCircle.setSpeed(mainCircleRotSpeed);
        }
    }

    private int colorList[][]={{2,3,255},{1,2,255},{2,3,255},{3,2,255},{2,3,255}};

    public void updateColors(){
        if(jingle.position()>=TimeLookupTable.acts[partCounter]){

            //TODO Interpolation
            //TODO Chose better colors

            partCounter=(partCounter+1)%5;
            this.r = (int)(Math.random()*(255/2)+(255/4));
            this.g = (int)(Math.random()*(255/2)+(255/4));
            this.b = 255;

        }
    }


    @Override
    public void keyReleased(){
        if(key==' ') {
            if (isPlaying) {
                jingle.pause();
                isPlaying = false;
            }
            else {
                jingle.play();

                isPlaying = true;
            }

        }
        if(key=='m'){
            jControl.setVisible(!jControl.isVisible());
        }
        if(key=='d'){
            menu.right();

        }
        if(key=='a'){
            menu.left();

        }


    }
    private void test(){
        if(beatCounter%5==0) {
            smallCircleRadius = (int) (Math.random() * 20);
            //sTest.update();
            sTest.setValue(smallCircleRadius);
        }
    }

}
