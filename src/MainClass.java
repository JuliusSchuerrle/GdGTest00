import processing.core.PApplet;

public class MainClass extends PApplet {

    public static void main(String[] args){

        PApplet.main("MainClass");
        System.out.println("hello");
    }

    public void settings(){
        size(1000,1000);
    }

    public void draw(){
        line(0,0,100,100);
    }
}
