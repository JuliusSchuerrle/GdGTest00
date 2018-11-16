package src;

/**
 * Created by js148 on 18.07.2018.
 */

// Ich zeige Benni Git in IDEA
public class RenderPoint {
    private float x;
    private float y;
    private float z;
    private float r;
    private float g;
    private float b;
    private float a;
    private float radius;


    private float lifeTime=0.f;
    private float lifeSpan;
    private float lifeSpanInternal; //used for better performance
    public boolean isDead=false;

    public RenderPoint(float x, float y, float z, float r, float g, float b, float a, float radius,float lifeSpan) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
        this.radius = radius;
        this.lifeSpan=lifeSpan;
        lifeSpanInternal=1/lifeSpan;
    }

    public void setLifeSpan(float lifeSpan){
        this.lifeSpan=lifeSpan;
        this.lifeSpanInternal=1.f/lifeSpan;

    }
    public void update(float dt){
        lifeTime+=dt;
        if(lifeTime>=.36094f){
            a-=dt*255.f*lifeSpanInternal;
            if(a<=0){
                isDead=true;
                a=0;
            }
        }
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }

    public float getR() {
        return r;
    }

    public void setR(float r) {
        this.r = r;
    }

    public float getG() {
        return g;
    }

    public void setG(float g) {
        this.g = g;
    }

    public float getB() {
        return b;
    }

    public void setB(float b) {
        this.b = b;
    }

    public float getA() {
        return a;
    }

    public void setA(float a) {
        this.a = a;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public float getLifeTime() {
        return lifeTime;
    }

    public void setLifeTime(float lifeTime) {
        this.lifeTime = lifeTime;
    }

    public float getLifeSpan() {
        return lifeSpan;
    }

}
