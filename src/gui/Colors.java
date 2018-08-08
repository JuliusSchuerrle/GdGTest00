package src.gui;

/**
 * Created by js148 on 08.08.2018.
 */
public class Colors {
    MyColor[] array  = new MyColor[5];
    MyColor[] array2 = new MyColor[5];
    int position=0;
    public Colors(){

    }
    public MyColor getColorForeground(){

        return array[position];
    }
    public MyColor getColorBackground(){
        return array[position];
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
