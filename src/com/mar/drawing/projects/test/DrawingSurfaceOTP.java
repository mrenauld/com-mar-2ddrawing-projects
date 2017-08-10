package com.mar.drawing.projects.test;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

import com.mar.drawing.guiobjects.DrawingSurface;
import com.mar.iotools.read.Read;

public class DrawingSurfaceOTP extends DrawingSurface {

    private static final long serialVersionUID = -6709443078268160025L;

    private final String ok = "OK";

    private final ArrayList<ArrayList<Integer>> points;

    public DrawingSurfaceOTP() {

        final String mainpath = "C:/Users/mrenauld/Sopra/TS/2014_09_17 B2CDigipass/TestOTPRandom/";

        ArrayList<ArrayList<Integer>> temp;

        // String path = mainpath + "20-11-2014/Report-15-13-20.516.txt";
        String path = mainpath + "21-11-2014/Report-10-39-07.112.txt";
        points = readData(path);

        // path = mainpath + "20-11-2014/Report-15-28-26.912.txt";
        path = mainpath + "21-11-2014/Report-11-05-00.205.txt";
        temp = readData(path);
        for (int i = 0; i < temp.size(); ++i) {
            points.add(temp.get(i));
        }

        // path = mainpath + "20-11-2014/Report-15-40-39.293.txt";
        path = mainpath + "21-11-2014/Report-11-22-07.957.txt";
        temp = readData(path);
        for (int i = 0; i < temp.size(); ++i) {
            points.add(temp.get(i));
        }
    }

    @Override
    protected void doDrawing(final Graphics g) {
        final Graphics2D g2d = (Graphics2D) g;

        final Dimension size = this.getSize();

        g2d.setStroke(new BasicStroke(10));

        final int linex = convertX(0, size.width);
        final int liney1 = 0;
        final int liney2 = size.height;

        g2d.drawLine(linex, liney1, linex, liney2);

        for (int i = 0; i < points.size(); ++i) {

            final int r1 = (int) (Math.random() * 256);
            final int r2 = (int) (Math.random() * 256);
            final int r3 = (int) (Math.random() * 256);

            g2d.setColor(new Color(r1, r2, r3));

            for (int j = 0; j < points.get(i).size(); ++j) {

                final int x = convertX(points.get(i).get(j), size.width);
                final int y = (int) ((i + 1) / (double) (points.size() + 2) * size.height);

                System.out.println(size.width);
                System.out.println(x + " " + y);

                g2d.draw(new Ellipse2D.Double(x, y, 10, 10));
            }
        }
    }

    private int convertX(final int p, final int width) {
        final int x = (int) ((p / 10000.0 * 0.8 * width) + (0.1 * width));
        return x;
    }

    private ArrayList<ArrayList<Integer>> readData(final String path) {
        final String[] data = Read.readText(path);

        ArrayList<Integer> pointsOk = new ArrayList<Integer>(4);
        final ArrayList<ArrayList<Integer>> points = new ArrayList<ArrayList<Integer>>(4);
        int sequence = -1;
        for (int i = 0; i < data.length; ++i) {
            final String[] split = data[i].split(" ");

            if (split.length < 10) {
                continue;
            }

            final int currentSeq = Integer.parseInt(split[9]);

            if (sequence == -1) {
                sequence = currentSeq;
            }

            if (sequence != currentSeq) {
                points.add(pointsOk);
                pointsOk = new ArrayList<Integer>(4);
                sequence = currentSeq;
            }

            if (ok.equals(split[1])) {
                pointsOk.add(Integer.parseInt(split[7]));
            }
        }

        points.add(pointsOk);

        return points;
    }

}
