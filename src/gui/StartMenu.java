package src.gui;

import controlP5.Button;
import controlP5.ControlP5;
import controlP5.RadioButton;
import controlP5.Toggle;
import src.MainClass;

/**
 * Created by js148 on 08.08.2018.
 */
public class StartMenu {
    MainClass applet;
public    ControlP5 menu;

    Button playButton;
    Button selectColors;

    public StartMenu(MainClass applet){
        this.applet=applet;
        menu=new ControlP5(applet);

    playButton=menu.addButton("play").setPosition(500,500).setSize(100,100);
    selectColors=menu.addButton("Colors").setPosition(650,500).setSize(100,100);
    }


}
