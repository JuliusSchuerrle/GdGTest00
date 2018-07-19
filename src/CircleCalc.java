package src;

import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by js148 on 18.07.2018.
 *
 * used to calculate circular movement
 */

public class CircleCalc {
    private int numberOfPoints;
    private float angle;                //in radians
    private float speed;                //in radians per second
    private float radius;               //in world units
    private ArrayList<Point> points;       //representation of points on the circle
    private float[] rel;                //lookuptable of partial rad values, improves performance

    public CircleCalc(int numberOfPoints, float angle, float speed, float radius) {
        this.numberOfPoints = numberOfPoints;
        this.angle = angle;
        this.speed = speed;
        this.radius = radius;
        this.points=new ArrayList<Point>();
        points.add(new Point(1,1));

        rel=new float[numberOfPoints];
        for(int i=0;i<numberOfPoints;i++){
            rel[i]=((float)i/(float)numberOfPoints)*(float)(2*Math.PI);
            System.out.println(rel[i]);
        }


        for(int i=0;i<numberOfPoints;i++){

            points.add(new src.Point(
                            radius*(float)Math.sin(angle+rel[i]),
                            radius*(float)Math.cos(angle+rel[i])
                    )
            );
        }
    }

    /**
     *
     * update method
     * dt=length of current frame
     */
    public void update(float dt){

        angle+=speed*dt;

        points.clear();
        for(int i=0;i<numberOfPoints;i++){
            points.add(new Point(
                    radius*(float)Math.sin(angle+rel[i]),
                    radius*(float)Math.cos(angle+rel[i])
            ));
        }
    }


    public int getNumberOfPoints() {
        return numberOfPoints;
    }

    public void setNumberOfPoints(int numberOfPoints) {
        this.numberOfPoints = numberOfPoints;
        rel=new float[numberOfPoints];
        for(int i=0;i<numberOfPoints;i++){
            rel[i]=(i/numberOfPoints)*(float)(2*Math.PI);
        }
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public ArrayList<Point> getPoints() {
        return points;
    }

    public void setPoints(ArrayList<Point> points) {
        this.points = points;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }
}
