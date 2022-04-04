package ru.denfad.simulation.object;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Point {

    double x;
    double y;
    Vector v;

    public Point(double x, double y, Vector v) {
        this.x = x;
        this.y = y;
        this.v = v;
    }

    public void addVector(Vector v){
        this.v = v;
    }

    public Point move(){
        x = x + v.x;
        y = y + v.y;
        return this;
    }

    public void setPoint(Point p) {
        this.x = p.x;
        this.y = p.y;
    }

    public void draw(BufferedImage img){
        double x2 = x+img.getWidth()/2;
        double y2 = y+img.getHeight()/2;
        if(x2<img.getWidth() & y2<img.getHeight() & x2>0 & y2>0){
            img.setRGB((int)x2,(int)y2, Color.WHITE.getRGB());
        }


    }
}
