package src.data;

import java.util.LinkedList;

public class LookupTables {
    public float[] value1=new float[3180];
    public float[] value2=new float[3180];
    public float[] value3=new float[3180];
    public float[] value4=new float[3180];
    public float[] value5=new float[3180];
    public float[] value6=new float[3180];
    public float[] value7=new float[3180];
    public float[] value8=new float[3180];
   public LinkedList<float[]>table;
    int counter=0;
    public LookupTables(){
        table=new LinkedList<float[]>();
        table.add(value1);
        table.add(value2);
        table.add(value3);
        table.add(value4);
        table.add(value5);
        table.add(value6);
        table.add(value7);
        table.add(value8);

        for(int i=0;i<3180;i++){
            value1[i]=0.1f;
            value2[i]=400.f;
            value3[i]=20.f;
            value4[i]=100.f;
            value5[i]=0.14f;
            value6[i]=2.f;
            value7[i]=0.f;
        }
    }


    public void setValues(int t,int p,float v){
        for(int i=counter+1;i<=p;i++){
            table.get(t)[i]=v;
        }
        counter=p;
    }

}
