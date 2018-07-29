package src;

import org.openkinect.processing.Kinect;
import processing.core.PApplet;
import processing.core.PVector;

public class Tracker {
    Kinect kinect;
    PApplet applet;
    int[] depth;
    int position;
    int positionImportant;


    PVector loc=new PVector(0,0);
    PVector lerpedLoc=new PVector(0,0);
    PVector oldPos=new PVector(0,0);

    private float speed;
    private float lerpedSpeed=0;

    public Tracker(Kinect kinect, PApplet applet){
        this.kinect=kinect;
        this.applet=applet;
    }


    public void update( float dt){
        try {
            depth = kinect.getRawDepth();
        }catch (Exception e){

        }
        if(depth==null){
            System.out.println("is broken");
            return;
        }
        int step=10;

            positionImportant=position;
            position=2000;

        int pixelCounter=0;
        float sumX = 0;
        float sumY = 0;
        float count = 0;

        for(int x = 0; x < kinect.width; x += step){
            for(int y = 0; y < kinect.height;y += step){
                int offset = x + y*kinect.width;

                // Convert kinect data to world xyz coordinate

                int rawDepth = depth[offset];


                    if (rawDepth < position) {
                        position = rawDepth;
                    }
                    if (rawDepth < positionImportant + 10) {
                        applet.ellipse(x + 500, y + 500, 5, 5);
                        pixelCounter++;
                        sumX += x;
                        sumY += y;
                        count++;
                    }


            }
        }
        if (count != 0) {
             loc = new PVector(sumX/count, sumY/count);
        }
        lerpedLoc.x = PApplet.lerp(lerpedLoc.x, loc.x, 0.3f);
        lerpedLoc.y = PApplet.lerp(lerpedLoc.y, loc.y, 0.3f);
        applet.fill(255,0,0,255);
        applet.ellipse(lerpedLoc.x+500,lerpedLoc.y+500,50,50);

        speed= lerpedLoc.x-oldPos.x;
        lerpedSpeed=PApplet.lerp(lerpedSpeed,speed,0.3f);
        System.out.println("speed: "+lerpedSpeed);
        oldPos.x=lerpedLoc.x;
        if(lerpedSpeed>30){
            applet.fill(0,255,0,255);
            applet.ellipse(500,500,30,30);
        }
        if(lerpedSpeed<-20){
            applet.fill(255,0,0,255);
            applet.ellipse(500,500,30,30);
        }

        applet.fill(255,0,0,255);

        if(pixelCounter<60){
            applet.ellipse(300,700,20,20);
        }
    }

}
