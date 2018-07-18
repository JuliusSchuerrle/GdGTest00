import controlP5.ControlP5;
import controlP5.Slider;
import ddf.minim.AudioPlayer;
import ddf.minim.Minim;
import ddf.minim.analysis.FFT;
import javafx.scene.shape.Circle;
import processing.core.PApplet;
import src.CircleCalc;
import src.Point;

public class MainClass extends PApplet {
    Minim minim;
    AudioPlayer jingle;
    FFT fft;
    damkjer.ocd.Camera cam;
    ControlP5 jControl;



    CircleCalc circle;
    public static void main(String[] args){

        PApplet.main("MainClass");
        System.out.println("hello");
    }

    public void settings(){
        size(1000,1000);
    }

    public void setup(){

        cam = new damkjer.ocd.Camera(this,200,-250,300);

        jControl=new ControlP5(this);
        jControl.setAutoDraw(false);
        Slider s1=jControl.addSlider("pointdist").setPosition(100,100).setMin(0).setMax(100).setHeight(50).setWidth(200);

        minim = new Minim(this);
        jingle = minim.loadFile("song.mp3", 2048);
        jingle.loop();
        fft = new FFT(jingle.bufferSize(), jingle.sampleRate());
        circle=new CircleCalc(5,0,0.1f,300);
    }
    public void draw(){

        for(Point point:circle.getPoints()){
         ellipse(point.getX()+500,point.getY()+500,50,50);
        }
        //System.out.println(circle.getNumberOfPoints());
        circle.update(1/frameRate);
        //line(0,0,100,100);
    }
}
