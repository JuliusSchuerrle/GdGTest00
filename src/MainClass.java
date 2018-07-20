
import com.jogamp.opengl.GL;
import controlP5.ControlP5;
import controlP5.Slider;
import ddf.minim.AudioPlayer;
import ddf.minim.Minim;
import ddf.minim.analysis.FFT;
import javafx.scene.shape.Circle;
import processing.core.PApplet;
import src.CircleCalc;
import src.Point;
import src.RenderPoint;
import src.TimeLookupTable;

import java.util.ArrayList;
import java.util.Vector;

public class MainClass extends PApplet {
    Minim minim;
    AudioPlayer jingle;
    FFT fft;
    damkjer.ocd.Camera cam;
    ControlP5 jControl;
    //DasIstEinTest
    //circles setup
    CircleCalc mainCircle;
    private float mainCircleRadius=400;
    private float mainCircleRotSpeed=0.0f;  //0.1f

    private int numberOfCircles=5;

    private float smallCircleRadius=20;
    private float smallCircleRotSpeed=0.4f;
    private int numberOfSmallPoints=2;

    private float pointRadius=100.f;

    //private float lifeSpan=30.f;
    private float lifeSpan=1.f;

    //FFT variables
    float pos=135;
    float weight = 150;

    float dt;
    float dtc;


    int beatCounter=0;

    float r=255;
    float g=255;
    float b=255;





    ArrayList<CircleCalc> smallCircles;
    ArrayList<RenderPoint> renderPoints;
    ArrayList<RenderPoint> renderPointsToRemove;
    public static void main(String[] args){

        PApplet.main("MainClass");
        System.out.println("hello");
    }

    public void settings(){
        size(1920 , 1080,P3D );

    }

    public void setup(){

        cam = new damkjer.ocd.Camera(this,200,-250,300);


        //Menu
        jControl=new ControlP5(this);
        jControl.setAutoDraw(false);
        Slider s1=jControl.addSlider("pointRadius").setPosition(100,100).setMin(0).setMax(200).setHeight(50).setWidth(200);
        Slider s2=jControl.addSlider("smallCircleRadius").setPosition(100,200).setMin(0).setMax(200).setHeight(50).setWidth(200);
        Slider s3=jControl.addSlider("r").setPosition(100,400).setMin(0).setMax(255).setHeight(50).setWidth(200);
        Slider s4=jControl.addSlider("g").setPosition(100,500).setMin(0).setMax(255).setHeight(50).setWidth(200);
        Slider s5=jControl.addSlider("b").setPosition(100,600).setMin(0).setMax(255).setHeight(50).setWidth(200);

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
    public void draw(){
        //Philipp GIT Test


        System.out.println(frameRate+"    "+renderPoints.size());
        dt = 1 / frameRate;
        dtc = dt * 60;
        runFFT();
        runBeat();
        updateData();
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
        jControl.draw();
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
        mainCircle.setRadius(mainCircleRadius);
        for(CircleCalc circle:smallCircles){
            circle.setRadius(smallCircleRadius);
        }
    }
}
