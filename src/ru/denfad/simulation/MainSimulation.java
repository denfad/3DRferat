package ru.denfad.simulation;

import ru.denfad.simulation.object.Object;
import ru.denfad.simulation.object.Point;
import ru.denfad.simulation.object.Vector;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;
import java.util.List;


public class MainSimulation {

    //ui элементы
    //окно программы
    static JFrame frame;
    //материнский контейнер
    static Container pane;
    //панель отрисовки объекта
    static JPanel renderPanel;

    final static int width = 400, height = 400;

    public static void main(String[] args) {
        frame = new JFrame();
        pane = frame.getContentPane();
        pane.setLayout(new BorderLayout());
        frame.setSize(400, 400);

        Point[] points = new Point[100];
        for(int i = 0; i < points.length; i++){
            points[i] = new Point(-width / 2, - height / 2 + i*(height / points.length), new Vector(1, 0));
        }

        Object[] objects = new Object[1];
        List<Point> opoint = new ArrayList<>();
        opoint.add(new Point(0, height,null));
        opoint.add(new Point(width,0,null));
        objects[0] = new Object(opoint, true);

        renderPanel = new JPanel(){
            public void paintComponent(Graphics g) {
                BufferedImage img = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
                Graphics2D g2 = (Graphics2D) g;
                g2.setColor(Color.BLACK);
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.setColor(Color.WHITE);
                objects[0].draw(img);
                for(int i=0; i<points.length;i++){
                    points[i].move();
                    points[i].draw(img);
                }
                g2.drawImage(img,0,0,null);
            }
        };

        //world timer
        Timer timer = new Timer();
        TimerTask task  = new TimerTask() {
            @Override
            public void run() {
                renderPanel.repaint();
            }
        };
        timer.schedule(task,0,1);

        pane.add(renderPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }
}
