package src;

import damkjer.ocd.Camera;

import org.openkinect.processing.Kinect;
import processing.core.PImage;
import src.data.LookupTables;
import src.data.TimeLookupTable;
import src.gui.*;
import controlP5.*;
import controlP5.ControlP5;
import controlP5.Slider;
import ddf.minim.AudioPlayer;
import ddf.minim.Minim;
import ddf.minim.analysis.FFT;
import processing.core.PApplet;
import src.*;

import java.util.ArrayList;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class MainClass extends PApplet {

    private final int HEIGHT = 1080;
    private final int WIDTH = 1920;



   public LookupTables lookupTable;


    Kinect kinect;
    public Tracker tracker;


    Minim minim;
    AudioPlayer jingle;
    FFT fft;
    damkjer.ocd.Camera cam;
    RightMenu menu2;
    SlideMenu menu;
    StartMenu startMenu;
    ColorMenu colorMenu;
    PresetsMenu presetsMenu;

    ControlP5 rightMenu;
    ControlP5 slideMenu;


    //circles setup
    CircleCalc mainCircle;

    public int positionInArray=0;
    private int positionCounter=0;


    private boolean menuTest=false;
    private boolean isPaused=true;

    private boolean isColorMenu=false;
    private boolean isPresetsMenu=false;


    private static boolean isPlaying = true;

    private float mainCircleRadius=200;
    private float mainCircleRotSpeed=0.1f;  //0.1f

    private int numberOfCircles=5;

    private float smallCircleRadius=20;
    private float smallCircleRotSpeed=1f;
    private int numberOfSmallPoints=2;

    private static float pointRadius=50.f;

    //private float lifeSpan=30.f;
    private static float lifeSpan=1.f;

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



    Firework f1;

    ArrayList<CircleCalc> smallCircles;
    ArrayList<RenderPoint> renderPoints;
    ArrayList<RenderPoint> renderPointsToRemove;
    public static void main(String[] args){

        PApplet.main("src.MainClass");
        System.out.println("hello");
    }

    public void settings(){
        size(WIDTH , HEIGHT,P3D );

    }



    public void setup(){


        lookupTable=new LookupTables();

        kinect = new Kinect(this);
        //f1 = new Firework(WIDTH,HEIGHT,30,100f,4000,this);
        if(kinect.numDevices()>0) {

            kinect.activateDevice(0);
            kinect.initDepth();
            kinect.setTilt(10);


            tracker = new Tracker(kinect, this);
        }
        cam = new Camera(this,200,-250,300);


        //Menu
        menu = new SlideMenu(this, HEIGHT,WIDTH);
        slideMenu = menu.getMenu();
        slideMenu.setAutoDraw(false);
        slideMenu.setVisible(false);


        startMenu=new StartMenu(this,HEIGHT,WIDTH);
        startMenu.menu.setAutoDraw(false);
        startMenu.menu.setVisible(true);


        colorMenu=new ColorMenu(this,HEIGHT,WIDTH);
        colorMenu.menu.setAutoDraw(false);
        colorMenu.menu.setVisible(false);

        presetsMenu=new PresetsMenu(this,HEIGHT,WIDTH);
        presetsMenu.menu.setAutoDraw(false);
        presetsMenu.menu.setVisible(false);


        //new Main Menu;
       // menu2 = new LeftMenu(this,HEIGHT,WIDTH);
       // rightMenu = menu2.getMenu();
       // rightMenu.setAutoDraw(false);
       // rightMenu.setVisible(true);








        //Big circle

        //DropdownList d1 = jControl.addDropdownList("TESCHD").setPosition(100, 100).setBarHeight(30);








        minim = new Minim(this);
        jingle = minim.loadFile("song.mp3", 2048);
        //jingle.loop();
        fft = new FFT(jingle.bufferSize(), jingle.sampleRate());
        mainCircle=new CircleCalc(numberOfCircles,0,mainCircleRotSpeed,mainCircleRadius);
        smallCircles=new ArrayList<CircleCalc>();
        for(int i=0;i<numberOfCircles;i++){
            smallCircles.add(new CircleCalc(numberOfSmallPoints,0,smallCircleRotSpeed,smallCircleRadius));
        }
        renderPoints=new ArrayList<RenderPoint>();
        renderPointsToRemove=new ArrayList<RenderPoint>();

    }


    public void controlEvent(ControlEvent e) {

    }



    int x = 0;
    public void draw(){



       // mousePosition();

        if(!isPlaying)
            return;

        positionInArray=(int)(jingle.position()/16.6666666666f);

        if(positionInArray>3179){
            positionInArray=3179;
        }
      // System.out.println("pos in Array: "+positionInArray);
        //Debug
        /*
        System.out.println(frameRate+"    "+renderPoints.size());
        System.out.println(beatCounter);
           */
        if(keyPressed){

            if(key=='w'){
                menu.up();
            }

            if(key=='s'){
                menu.down();
            }
        }


        dt = 1 / frameRate;
        dtc = dt * 60;


        runFFT();
        runBeat();
        updateData();
        updateColors();
        background(0);
        strokeWeight(0);

       // System.out.println(jingle.position());

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

       //Firework.doFirework(jingle.position(),this);



        for(RenderPoint p:renderPointsToRemove){
            renderPoints.remove(p);
        }
        renderPointsToRemove.clear();
        mainCircle.update(1/frameRate);



        camera();

        slideMenu.draw();
        startMenu.menu.draw();
        colorMenu.menu.draw();
        presetsMenu.menu.draw();
       // rightMenu.draw();

        if(kinect.numDevices()>0) {
           // try {
           //     PImage img = kinect.getDepthImage();
           //     image(img, 0, 0);
           // } catch (Exception e) {
//
           // }
            tracker.update(1 / frameRate);
        }
        cam.aim(0,0,0);
        cam.jump(0, 0,200+1*1000.f);
        cam.feed();






     //   if (menu2.lMenu.get(0).isMouseOver()) {
     //       System.out.println("value: " + mainCircleRotSpeed);
     //       lookupTable.setValues(0,positionInArray,mainCircleRotSpeed);
     //   }
     //   if (menu2.lMenu.get(1).isMouseOver()) {
     //       System.out.println("value: " + mainCircleRadius);
     //       lookupTable.setValues(1,positionInArray,mainCircleRadius);
     //   }
     //   if (menu2.lMenu.get(2).isMouseOver()) {
     //       System.out.println("value: " + pointRadius);
     //       lookupTable.setValues(2,positionInArray,pointRadius);
     //   }
     //   if (menu2.lMenu.get(3).isMouseOver()) {
     //       System.out.println("value: " + smallCircleRadius);
     //       lookupTable.setValues(3,positionInArray,smallCircleRadius);
     //   }
     //   if (menu2.lMenu.get(4).isMouseOver()) {
     //       System.out.println("value: " + smallCircleRotSpeed);
     //       lookupTable.setValues(4,positionInArray,smallCircleRotSpeed);
     //   }
     //   if (menu2.lMenu.get(5).isMouseOver()) {
     //       System.out.println("value: " + lifeSpan);
     //       lookupTable.setValues(5,positionInArray,lifeSpan);
     //   }
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
                positionCounter = 0;
            }


        }
    }
    public void updateData(){
        mainCircle.setRadius(mainCircleRadius);
        if(slideMenu.isVisible())
            menu.update();
//        rightMenu.update();
        startMenu.update();
        colorMenu.update();
        presetsMenu.update();



        for(CircleCalc circle:smallCircles){
            circle.setRadius(smallCircleRadius);
            circle.setSpeed(smallCircleRotSpeed);
        }
        final int START = 80;
        final int END = START+32;

        mainCircle.setSpeed(mainCircleRotSpeed);

       // if(beatCounter>START&&beatCounter<END){
       //     mainCircle.setSpeed(0);
       // }else{
       //     mainCircle.setSpeed(mainCircleRotSpeed);
       // }
    }

    private int colorList[][]={{2,3,255},{1,2,255},{2,3,255},{3,2,255},{2,3,255}};

    public void updateColors(){
        if(jingle.position()>=TimeLookupTable.acts[partCounter]){

            //TODO Interpolation
            //TODO Chose better cColors

            partCounter=(partCounter+1)%5;
            this.r = (int)(Math.random()*(255/2)+(255/4));
            this.g = (int)(Math.random()*(255/2)+(255/4));
            this.b = 255;

        }
    }


    @Override
    public void keyPressed(){
        if(key==' ') {
            if (isPlaying) {
                jingle.pause();
                isPlaying = false;
            }
            else {
                jingle.loop();

                isPlaying = true;
            }

        }
       // if(key=='m') {
//
       //         slideMenu.setVisible(!slideMenu.isVisible());
//
       // }
        if(key=='d'){
            if(isPaused) {
                if(isColorMenu){
                    colorMenu.right(0.03f);
                    return;
                }
                if(isPresetsMenu){
                    presetsMenu.right(0.03f);
                    return;
                }
                startMenu.right(0.03f);
            }else{
                menu.right(0.03f);
            }

        }
        if(key=='a'){
            if(isPaused) {
                if(isColorMenu){
                    colorMenu.left(0.03f);
                    return;
                }
                if(isPresetsMenu){
                    presetsMenu.left(0.03f);
                    return;
                }
                startMenu.left(0.03f);
                System.out.println("start menu left");
            }else {
                menu.left(0.03f);
                System.out.println("game menu left");

            }
        }
        if(key=='g'){
            jingle.skip(10000);
        }

        if(key=='k'){
            kinect.stopDepth();
        }
        if(key=='w'&&isPaused){
            if(isColorMenu){
                colorMenu.press();
                return;
            }
            if(isPresetsMenu){
                presetsMenu.press();
                return;
            }
            startMenu.press();
            System.out.println("pressed start menu");
        }
        if(key=='p'){
            writeToCSV();
        }
        if(key=='o'){
            readCSV();
        }


    }


    public void startColorMenu(){
        isColorMenu=true;
        colorMenu.isMovingIn=true;
        colorMenu.moveIn();
        colorMenu.menu.setVisible(true);
        startMenu.menu.setVisible(false);
    }

    public void endColorMenu(int i){
        isColorMenu=false;
        colorMenu.menu.setVisible(false);
        startMenu.isMovingIn=true;
        startMenu.moveIn();
        startMenu.menu.setVisible(true);
    }

    public void startPresetsMenu(){
        isPresetsMenu=true;
        presetsMenu.isMovingIn=true;
        presetsMenu.moveIn();
        presetsMenu.menu.setVisible(true);
        startMenu.menu.setVisible(false);
    }
    public void endPresetsMenu(int i){
        isPresetsMenu=false;
        presetsMenu.menu.setVisible(false);
        startMenu.isMovingIn=true;
        startMenu.moveIn();
        startMenu.menu.setVisible(true);
    }


    public void endColorMenu1(int i){
        endColorMenu(i);
    }
    public void endColorMenu2(int i){
        endColorMenu(i);
    }

    public int getHEIGHT() {
        return HEIGHT;
    }

    public int getWIDTH() {
        return WIDTH;
    }


    public void setPaused(){

      if(!isPaused){
          jingle.pause();
          isPaused=true;
      }else{
          jingle.loop();
          isPaused=false;
      }
    }


    public void play(){
        slideMenu.setVisible(true);
        startMenu.menu.setVisible(false);
        jingle.loop();
        isPaused=false;
    }

    int i=0;

    private void firework(){


        if(jingle.position()>=TimeLookupTable.acts[i]){
            i+=i%TimeLookupTable.acts.length;
        }
        for(Firework f : fireworks){
            f.move(jingle.position());
            ellipse(f.getX(),f.getY(),20,20);
        }

    }
    ArrayList <Firework> fireworks = new ArrayList<>();

   // public void mousePosition(){
   //     if(mouseX>WIDTH-500) {
   //         menu2.update(true,  mainCircleRadius,mainCircleRotSpeed, numberOfCircles, smallCircleRadius,smallCircleRotSpeed, numberOfSmallPoints,pointRadius,lifeSpan);
   //     }
   //     else
   //         menu2.update(false, mainCircleRadius,mainCircleRotSpeed, numberOfCircles, smallCircleRadius,smallCircleRotSpeed, numberOfSmallPoints,pointRadius,lifeSpan);
//
//
//
   // }


    public void writeToCSV(){
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new File("MyNewData.csv"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        StringBuilder builder = new StringBuilder();

// No need give the headers Like: id, Name on builder.append

        for(int i=0;i<3180;i++){
            builder.append(lookupTable.value1[i]);
            builder.append(',');
            builder.append(lookupTable.value2[i]);
            builder.append(',');
            builder.append(lookupTable.value3[i]);
            builder.append(',');
            builder.append(lookupTable.value4[i]);
            builder.append(',');
            builder.append(lookupTable.value5[i]);
            builder.append(',');
            builder.append(lookupTable.value6[i]);
            builder.append(',');


        }

        pw.write(builder.toString());
        pw.close();
        System.out.println("done!");
    }

    public void readCSV(){
        Scanner scanner=null;
        try {
             scanner = new Scanner(new File("MyNewData.csv"));
        }catch (java.io.FileNotFoundException e){
            System.out.println("fileNotFound");
        }
        scanner.useDelimiter(",");
        int counter=0;
        int counter2=0;
        while(scanner.hasNext()){


                lookupTable.table.get(counter)[counter2] = Float.parseFloat(scanner.next());
                counter++;
                if (counter >= 6) {
                    counter = 0;
                    counter2++;
                    System.out.println("c2:  " + counter2);
                }

        }
        scanner.close();
        System.out.println("done Reading");
    }


}
