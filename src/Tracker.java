package src;

import org.openkinect.processing.Kinect;
import processing.core.PApplet;
import processing.core.PVector;

public class Tracker {
    Kinect kinect;
    MainClass applet;
    int[] depth;
    int position;
    int positionImportant;


    PVector loc=new PVector(0,0);
    PVector lerpedLoc=new PVector(0,0);
    PVector oldPos=new PVector(0,0);

    private float speed;
    private float lerpedSpeed=0;

    private float depthSpeed=0;
    private float lerpedDepthSpeed=0;

    int xImp;
    int yImp;

    public boolean isButtonSelected=false;


    public Tracker(Kinect kinect, MainClass applet){
        this.kinect=kinect;
        this.applet=applet;
        xImp=kinect.width/2;
        yImp=kinect.height/2;
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

        int xOld=xImp;
        int yOld=yImp;
        int pixelCounter=0;
        float sumX = 0;
        float sumY = 0;
        float count = 0;


        for(int x = 0; x < kinect.width; x += step){
            for(int y = 0; y < kinect.height;y += step){
                int offset = x + y*kinect.width;
                int rawDepth = depth[offset];


                    if(rawDepth>80000000){          //filters out stuff too close/ far away
                        applet.fill(0,255,255,255);
                        applet.ellipse(x + 500, y + 500, 5, 5);
                        continue;
                    }


                    applet.fill(0,0,255,255);

                    if (rawDepth < position) {



                            position = rawDepth;
                            xImp = x;
                            yImp = y;

                      //  if(position-rawDepth>100){
                      //      position = rawDepth;
                      //      xImp = x;
                      //      yImp = y;
                      //  }
                    }



            }
        }

        float distImp = ((xImp-xOld)*(xImp-xOld))+((yImp-yOld)*(yImp-yOld));
        distImp=(float)Math.sqrt((double)distImp);
       // System.out.println("distImp : "+distImp);
        if(distImp>130){
            position=positionImportant;
            xImp=xOld;
            yImp=yOld;
        }
        int xS=xImp-50;
        int xE=xImp+50;
        int yS=yImp-50;
        int yE=yImp+50;

        if(xS<0){
            xS=0;
        }
        if(xE>kinect.width){
            xE=kinect.width;
        }
        if(yS<0){
            yS=0;
        }
        if(yE>kinect.height){
            yE=kinect.height;
        }




        for(int x = xS; x < xE; x += step) {
            for (int y = yS; y < yE; y += step) {

                int offset = x + y * kinect.width;
                int rawDepth = depth[offset];

                if (rawDepth < position + 10) {
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
       // System.out.println("speed: "+lerpedSpeed);

        depthSpeed=positionImportant-position;
        lerpedDepthSpeed=PApplet.lerp(lerpedDepthSpeed,depthSpeed,0.3f);
        //System.out.println("lerped depth: "+lerpedDepthSpeed);
        if(lerpedDepthSpeed>10){
            applet.fill(255,0,0,255);
            applet.ellipse(600,200,50,50);
            isButtonSelected=true;
            applet.menu.isButtonSelected=true;

        }



        oldPos.x=lerpedLoc.x;
        if(lerpedSpeed>20&&lerpedSpeed<50){
            applet.fill(0,255,0,255);
            applet.ellipse(500,500,30,30);
            applet.menu.left(0.03f);
            isButtonSelected=false;
            applet.menu.isButtonSelected=false;
        }
        if(lerpedSpeed<-20&&lerpedSpeed>-50){
            applet.fill(255,0,0,255);
            applet.ellipse(500,500,30,30);
            applet.menu.right(0.03f);
            isButtonSelected=false;
            applet.menu.isButtonSelected=false;

        }

        applet.fill(255,0,0,255);

        if(pixelCounter<30){
            applet.ellipse(300,700,20,20);
        }

        if(isButtonSelected){
            float inp=(float)lerpedLoc.y/(float)kinect.height;
            inp=1.f-inp;
            System.out.println("input value for button: "+kinect.height+"  "+lerpedLoc.y);
            applet.menu.setValue(inp);
        }
    }

}
