package src.gui;

import controlP5.*;
import controlP5.Button;
import processing.core.PFont;
import src.MainClass;
import src.data.data;

import java.awt.*;

/**
 * Created by js148 on 08.08.2018.
 */
public class StartMenu {
    MainClass applet;
public    ControlP5 menu;
    CColor color = data.cColors[1];

    Font f = new Font(Font.SANS_SERIF,1,30);
    PFont font = new PFont(f,false);

    Button playButton;
    Button selectColors;
    int lenght = 200;
    String x;

    public StartMenu(MainClass applet,int height, int width){
        this.applet=applet;
        menu=new ControlP5(applet);

        //playButton=menu.addButton("play").setPosition(500,500).setSize(100,100);
        playButton=menu.addButton("play").setPosition(width/2-lenght,height/2-50).setSize(lenght,100).setFont(font).setColor(color);
        //selectColors=menu.addButton("Colors").setPosition(650,500).setSize(100,100);
        selectColors=menu.addButton("Colors").setPosition(width/2+0.5f*lenght,height/2-50).setSize(lenght,100).setFont(font).setColor(color);

        //TEST
        DropdownList list = menu.addDropdownList("Liste").setPosition(100,100);
        list.addItem("Test", x);



    }







}
