package ru.denfad;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.List;

public class Main {

    //ui элементы
    //окно программы
    static JFrame frame;
    //материнский контейнер
    static Container pane;
    //слайдер угла XZ вращения (вращение по горизонтали)
    static JSlider headingSlider;
    //слайдер угла YZ вращения (вращение по вертикали)
    static JSlider pitchSlider;
    //панель отрисовки объекта
    static JPanel renderPanel;

    static List<Polygon> polygons = new ArrayList<>();

    static {
        //координатные оси
        polygons.add(new Polygon(new Vertex[]{new Vertex(1000,0,0), new Vertex(-1000, 0,0)}, Color.BLUE));
        polygons.add(new Polygon(new Vertex[]{new Vertex(0,1000,0), new Vertex(0, -1000,0)}, Color.RED));
        polygons.add(new Polygon(new Vertex[]{new Vertex(0,0,1000), new Vertex(0, 0,-1000)}, Color.GREEN));
        //несколько полигонов
        polygons.add(new Polygon(new Vertex[]{new Vertex(100,0,0),new Vertex(0,100,0), new Vertex(0,0,100)},Color.WHITE));
        polygons.add(new Polygon(new Vertex[]{new Vertex(-100,0,0),new Vertex(0,-100,0), new Vertex(0,0,-100)},Color.WHITE));
        polygons.add(new Polygon(new Vertex[]{new Vertex(100,0,0),new Vertex(0,0,100), new Vertex(-100,0,0), new Vertex(0,0,-100)},Color.WHITE));
    }
    public static void main(String[] args) {
        frame = new JFrame();
        pane = frame.getContentPane();
        pane.setLayout(new BorderLayout());

        headingSlider = new JSlider(-180, 180, 0);
        pane.add(headingSlider, BorderLayout.SOUTH);

        pitchSlider = new JSlider(SwingConstants.VERTICAL, -90, 90, 0);
        pane.add(pitchSlider, BorderLayout.EAST);

        renderPanel = new JPanel() {
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setColor(Color.BLACK);
                g2.fillRect(0, 0, getWidth(), getHeight());

                //вращение XZ
                double heading = Math.toRadians(headingSlider.getValue()); //берём угл из слайдера
                Matrix headingTransform = new Matrix(new double[]{
                        Math.cos(heading), 0, Math.sin(heading),
                        0, 1, 0,
                        -Math.sin(heading), 0, Math.cos(heading)});
                //вращение YZ
                double pitch = Math.toRadians(pitchSlider.getValue()); //берём угл из слайдера
                Matrix pitchTransform = new Matrix(new double[]{
                        1, 0, 0,
                        0, Math.cos(pitch), Math.sin(pitch),
                        0, -Math.sin(pitch), Math.cos(pitch)});
                //итоговая матрица вращения
                Matrix transform = headingTransform.multiply(pitchTransform);
                g2.translate(getWidth() / 2, getHeight() / 2);
                g2.setColor(Color.WHITE);
                for(Polygon p: polygons) {
                    Vertex start = p.vertexes[0];
                    start = transform.transform(start);
                    Path2D path = new Path2D.Double();
                    g2.setColor(p.color);
                    path.moveTo(start.x, start.y);
                    for(Vertex v:p.vertexes){
                        Vertex v2 = transform.transform(v);
                        path.lineTo(v2.x, v2.y);
                    }
                    path.lineTo(start.x, start.y);
                    path.closePath();
                    g2.draw(path);
                }
            }
        };

        pane.add(renderPanel, BorderLayout.CENTER);
        frame.setSize(400, 400);
        frame.setVisible(true);

        headingSlider.addChangeListener(e -> renderPanel.repaint());
        pitchSlider.addChangeListener(e -> renderPanel.repaint());
    }
}
