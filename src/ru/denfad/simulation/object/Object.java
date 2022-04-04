package ru.denfad.simulation.object;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Object {

    List<Point> points = new ArrayList<>();


    boolean statistics = true;

    public Object(List<Point> points, boolean statistics) {
        this.points = points;
        this.statistics = statistics;
    }

    public Edge[] getEdges() {
        Edge[] edges = new Edge[points.size()];
        if(points.size()>1){
            for(int i = 0; i < points.size() - 1; i++) {
                edges[i] = new Edge(points.get(i), points.get(i+1));
            }
            edges[points.size()-1] = new Edge(points.get(0), points.get(points.size()-1));
        }
        return edges;
    }

    public void draw(BufferedImage img){
       Edge[] edges = getEdges();

       for(Edge edge: edges) {
           double x0 = edge.point1.x;
           double y0 = edge.point1.y;
           double x2 = edge.point2.x;
           double y2 = edge.point2.y;
           double dx = x2 - x0;
           double dy = y2 - y0;
           double err = dx - dy;
           int sx  = (dx >= 0) ? 1 : -1;
           int sy = (dy >=0) ? 1 : -1;
           while(!((x0 == x2) && (y0 == y2)))
           {
               double e2 = err * 2; //содержит значение ошибки
               //используем значение ошибки определения того, в какую сторону нужно округлять точку (вверх или вниз)
               if(e2 >= -dy)
               {
                   err -= dy;
                   x0 += sx;
               }
               if(e2 < dx)
               {
                   err += dx;
                   y0 += sy;
               }
               img.setRGB((int)x0,(int)y0, Color.WHITE.getRGB());
           }
       }

    }
}
