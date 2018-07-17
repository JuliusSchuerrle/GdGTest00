import ddf.minim.AudioPlayer;
import ddf.minim.Minim;
import ddf.minim.analysis.FFT;
import processing.core.PApplet;

public class MainClass extends PApplet {
    Minim minim;
    AudioPlayer jingle;
    FFT fft;
    public static void main(String[] args){

        PApplet.main("MainClass");
        System.out.println("hello");
    }

    public void settings(){
        size(1000,1000);
    }

    public void setup(){
        minim = new Minim(this);
        jingle = minim.loadFile("song.mp3", 2048);
        jingle.loop();
        fft = new FFT(jingle.bufferSize(), jingle.sampleRate());
    }
    public void draw(){
        line(0,0,100,100);
    }
}
