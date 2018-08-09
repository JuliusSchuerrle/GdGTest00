package src.gui;

/**
 * Created by js148 on 08.08.2018.
 */
public class Colors {
    MyColor[] array  = new MyColor[5];
    MyColor[] array2 = new MyColor[5];
    public int position=0;
    public Colors(){
        array[0] =new MyColor(255,32,0,255);
        array2[0]=new MyColor(255,154,5,255);
        array[1] =new MyColor(0,177,255,255);
        array2[1]=new MyColor(1,154,5,255);
        array[2] =new MyColor(255,200,0,255);
        array2[2]=new MyColor(3,154,60,255);
    }
    public MyColor getColorForeground(){

        return array[position];
    }
    public MyColor getColorBackground(){
        return array2[position];
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
